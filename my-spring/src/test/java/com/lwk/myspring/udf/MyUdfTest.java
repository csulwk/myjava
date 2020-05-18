package com.lwk.myspring.udf;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.ql.udf.generic.SimpleGenericUDAFParameterInfo;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kai
 * @date 2020-05-17 23:15
 */
@Slf4j
public class MyUdfTest {

    @Test
    public void test() {
        log.info("test...");
    }

    @Test
    public void testPartial1() throws Exception {
        TypeInfo[] inputTi = new TypeInfo[]{
                TypeInfoFactory.stringTypeInfo,
                TypeInfoFactory.stringTypeInfo
        };
        MyUdf udf = new MyUdf();
        GenericUDAFEvaluator evaluator = udf.getEvaluator(inputTi);

        ObjectInspector strOi = ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        ObjectInspector listOi = ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        ObjectInspector[] inputObjectInspectorList = new ObjectInspector[]{listOi};

        ObjectInspector outputOi = evaluator.init(GenericUDAFEvaluator.Mode.PARTIAL1, inputObjectInspectorList);
        Object[] parameters1 = new Object[]{"62220001", 100};
        Object[] parameters2 = new Object[]{"62220002", 50 };
        Object[] parameters3 = new Object[]{"62220003", 80 };
        Object[] parameters4 = new Object[]{"62220004", 20 };
        Object[] parameters5 = new Object[]{"62220005", 30 };

        // Process the data
        MyUdf.MyEvaluator.MyAgg agg = (MyUdf.MyEvaluator.MyAgg) evaluator.getNewAggregationBuffer();
        evaluator.reset(agg);
        evaluator.iterate(agg, parameters1);
        evaluator.iterate(agg, parameters2);
        evaluator.iterate(agg, parameters3);
        evaluator.iterate(agg, parameters4);
        evaluator.iterate(agg, parameters5);
        Object result = evaluator.terminatePartial(agg);
        System.out.println("result: " + JSONObject.toJSONString(result));

        outputOi = evaluator.init(GenericUDAFEvaluator.Mode.PARTIAL2, inputObjectInspectorList);
        List<String> list1 = Arrays.asList("62220007");
        List<String> list2 = Arrays.asList("62220008", "62220009");
        evaluator.reset(agg);
        evaluator.merge(agg, list1);
        evaluator.merge(agg, list2);
        result = evaluator.terminatePartial(agg);
        System.out.println("result: " + JSONObject.toJSONString(result));
    }

    @Test
    public void testPartial2() throws Exception {
        ObjectInspector[] inputOis = new ObjectInspector[]{
                PrimitiveObjectInspectorFactory.javaStringObjectInspector,
                PrimitiveObjectInspectorFactory.javaStringObjectInspector
        };
        GenericUDAFParameterInfo paramInfo = new SimpleGenericUDAFParameterInfo(inputOis, false, false, false);
        MyUdf udf = new MyUdf();
        GenericUDAFEvaluator evaluator = udf.getEvaluator(paramInfo.getParameters());

        ObjectInspector listOi = ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        ObjectInspector[] inputObjectInspectorList = new ObjectInspector[]{listOi};

        ObjectInspector outputOi = evaluator.init(GenericUDAFEvaluator.Mode.PARTIAL2, inputObjectInspectorList);

        // Create the two values to merge
        List<Object> parameter1 = new ArrayList<>();
        parameter1.add(Arrays.asList("beta"));
        parameter1.add(Arrays.asList(300L));
        parameter1.add(Arrays.asList("alpha", null, "beta", null, "gamma", null, "epsilon", null));

        List<Object> parameter2 = new ArrayList<>();
        parameter2.add(Arrays.asList("gamma", "alpha"));
        parameter2.add(Arrays.asList(400L, 200L));
        parameter1.add(Arrays.asList("alpha", null, "beta", null, "gamma", null, "epsilon", null));

        // Process the data
        MyUdf.MyEvaluator.MyAgg agg = (MyUdf.MyEvaluator.MyAgg) evaluator.getNewAggregationBuffer();
        evaluator.reset(agg);
        evaluator.merge(agg, parameter1);
        evaluator.merge(agg, parameter2);
        Object result = evaluator.terminatePartial(agg);

    }
}
