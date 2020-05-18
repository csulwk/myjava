package com.lwk.myspring.udf;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kai
 * @date 2020-05-17 19:52
 */
public class MyUdf extends AbstractGenericUDAFResolver {

    private static final int ARG_NUM = 2;

    /**
     * 检查参数长度和类型
     * 根据参数返回对应的实际处理对象
     * @param info info
     * @return MyEvaluator
     * @throws SemanticException SemanticException
     */
    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] info) throws SemanticException {
        System.out.println("getEvaluator..." + info[0].getTypeName());
        if(info.length != ARG_NUM){
            throw new UDFArgumentTypeException(info.length - 1, "Exactly two argument is expected.");
        }
        if(info[0].getCategory() != ObjectInspector.Category.PRIMITIVE){
            throw new UDFArgumentTypeException(0, "Only primitive type arguments are accepted but "
                    + info[0].getTypeName() + " was passed as parameter 1.");
        }
        return new MyEvaluator();
    }

    public static class MyEvaluator extends GenericUDAFEvaluator {

        /**
         * For PARTIAL1 and COMPLETE: ObjectInspectors for original data
         */
        private ObjectInspector inputOi;
        // For PARTIAL2 and FINAL: ObjectInspectors for partial aggregations (list of objs)
        private StandardListObjectInspector listOi;
        private ListObjectInspector mergeOi;

        /**
         * PARTIAL1 : 相当于map阶段，是从原始数据到部分数据聚合，调用iterate()和terminatePartial()
         * PARTIAL2 : 相当于combiner阶段，是对map阶段的结果进行一次聚合，调用merge()和terminatePartial()
         * FINAL    : 相当于reduce阶段调用merge()和terminate()
         * COMPLETE : 相当于没有reduce阶段map，调用iterate()和terminate()
         * 初始化方法，在Mode的每一个阶段启动时会执行init方法。
         * 该方法有两个参数，第一个参数是Mode，可以根据此参数判断当前执行的是哪个阶段，进行该阶段相应的初始化工作。
         *  ObjectInspector[]的长度不是固定的，要看当前是处于哪个阶段。
         *  如果是PARTIAL1，那么与使用时传入该UDAF的参数个数一致；
         *  如果是FINAL阶段，长度就是1了，因为map阶段返回的结果只有一个对象。
         * @param m Mode
         * @param parameters ObjectInspector
         * @return ObjectInspector
         * @throws HiveException HiveException
         */
        @Override
        public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
            super.init(m, parameters);

            if (m == Mode.PARTIAL1 || m == Mode.COMPLETE){
                System.out.println("Mode: " +  m.toString()
                        + ";" + parameters[0].getTypeName() + "," + parameters[0].getCategory());
                // 该阶段的parameters为原始输入数据
                inputOi = parameters[0];
                return ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
            } else if (m == Mode.PARTIAL2 || m == Mode.FINAL){
                System.out.println("Mode: " + m.toString());
                if (!(parameters[0] instanceof ListObjectInspector)) {
                    System.out.println("StandardObjectInspector...");
                    //no map aggregation.
                    inputOi = ObjectInspectorUtils.getStandardObjectInspector(parameters[0]);
                    return ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
                } else {
                    System.out.println("ListObjectInspector...");
                    mergeOi = (ListObjectInspector) parameters[0];
                    inputOi = mergeOi.getListElementObjectInspector();
                    listOi = (StandardListObjectInspector) ObjectInspectorUtils.getStandardObjectInspector(mergeOi);
                    return listOi;
                }
            } else {
                throw new HiveException("Unknown Mode: " + m.toString());
            }
        }

        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            System.out.println("getNewAggregationBuffer...");
            MyAgg buffer = new MyAgg();
            buffer.data = new ArrayList<>();
            return buffer;
        }

        @Override
        public void reset(AggregationBuffer agg) throws HiveException {
            System.out.println("reset...");
            ((MyAgg) agg).data = new ArrayList<>();
        }

        /**
         * iterate方法存在于MR的M阶段，用于处理每一条输入记录。
         * Object[]作为输入传入UFAF，AggregationBuffer作为中间缓存暂存结果。
         * 需要注意的是，每次调用iterate传入的AggregationBuffer并不一定是同一个对象。
         * Hive调用UDAF的时候会用一个Map来管理AggregationBuffer，Map的key即为需要聚合的key。
         * 就通过实际运行过程来看，在每一次iterate调用之前，会根据聚合key从Map中查找对应的AggregationBuffer，
         * 若能找到则直接返回AggregationBuffer对象，找不到则调用getNewAggregationBuffer方法新建并插入Map中并返回结果。
         * @param agg AggregationBuffer
         * @param parameters Object[]
         * @throws HiveException HiveException
         */
        @Override
        public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
            System.out.println("iterate..." + parameters[0].toString());
            MyAgg myAgg = (MyAgg) agg;
            Object p = parameters[0];
            if (p != null) {
                myAgg.data.add(p.toString());
            }
            System.out.println("list: " + myAgg.toString());
        }

        /**
         * terminatePartial方法在iterate处理完所有输入后调用，用于返回初步的聚合结果。
         * @param agg MyAgg
         * @return Object
         * @throws HiveException HiveException
         */
        @Override
        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            System.out.println("terminatePartial...");
            MyAgg myAgg = (MyAgg) agg;
            return new ArrayList<>(myAgg.data);
        }

        /**
         * merge方法存在于MR的R阶段（也同样存在于Combine阶段），用于最后的聚合。
         * Object类型的partial参数与terminatePartial返回值一致，AggregationBuffer参数与上述一致。
         * @param agg MyAgg
         * @param partial Object
         * @throws HiveException HiveException
         */
        @Override
        public void merge(AggregationBuffer agg, Object partial) throws HiveException {
            System.out.println("merge..." + mergeOi.getTypeName());
            MyAgg myAgg = (MyAgg) agg;
            List<Object> partialResult = (List<Object>) mergeOi.getList(partial);
            if (partialResult != null) {
                for(Object obj : partialResult) {
                    myAgg.data.add(obj.toString());
                }
            }
        }

        /**
         * terminate方法在merge方法执行完毕之后调用，用于进行最后的处理，并返回最后结果。
         * @param agg MyAgg
         * @return Object
         * @throws HiveException HiveException
         */
        @Override
        public Object terminate(AggregationBuffer agg) throws HiveException {
            System.out.println("terminate...");
            MyAgg myagg = (MyAgg) agg;
            return new ArrayList<>(myagg.data);
        }

        public static class MyAgg extends AbstractAggregationBuffer {
            List<String> data;

            @Override
            public String toString() {
                return JSONObject.toJSONString(this.data);
            }
        }
    }
}
