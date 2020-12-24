package com.lwk.myspring;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.controller.OrderController;
import com.lwk.myspring.mysql.entity.OrderReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试类
 * @author kai
 * @date 2020-12-22 21:49
 */
@Slf4j
public class OrderControllerTest extends BaseTest {

    @Autowired
    private OrderController orderController;

    /**
     * testOrder
     * @param type REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
     * @param goodsId goodsId
     * @param userId userId
     * @param count count
     * @param oe oe
     * @param ge ge
     */
    private void testOrder(String type, String goodsId, String userId, int count, boolean oe, boolean ge) {
        OrderReq req = new OrderReq();
        req.setGoodsId(goodsId);
        req.setUserId(userId);
        // RandomUtils.nextInt(1, 5);
        req.setCount(count);
        req.setOExp(oe);
        req.setGExp(ge);
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        req.setType(type);
        JSONObject result = orderController.submitOrder(req);
        log.info("result: {}", result);
    }

    @Test
    public void REQUIRED1() {
        // 支持事务，如果业务方法执行时在一个事务中，则加入当前事务，否则重新开始一个事务。
        // 示例1：外层正常try-catch内层，内层出错。结果：事务回滚，内层外层都回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("REQUIRED", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：5；已售出数：5
     * 提交之后：总订单数：5；已售出数：5
     */
    @Test
    public void REQUIRED2() {
        // 示例2：外层正常，内层出错，外层不try-catch。结果：事务回滚，内层外层都回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("REQUIRED", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"订单异常..."}
     * 提交之前：总订单数：5；已售出数：5
     * 提交之后：总订单数：5；已售出数：5
     */
    @Test
    public void REQUIRED3() {
        // 示例3：外层出错，内层正常。结果：事务回滚，内层外层都回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("REQUIRED", "GID0001", "UID01", 1, true, false);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：5；已售出数：5
     * 提交之后：总订单数：5；已售出数：5
     */
    @Test
    public void SUPPORTS1() {
        // 支持事务。当前有事务就加入当前事务。当前没有事务就算了，不会开启一个事务。
        // 示例1：外层正常有事务，内层报错。结果：外层回滚，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("SUPPORTS", "GID0001", "UID01", 1, false, true);
    }

    @Test
    public void SUPPORTS2() {
        // 示例2：外层正常有事务try/catch，内层报错。结果：外层回滚，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("SUPPORTS", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"订单异常..."}
     * 提交之前：总订单数：5；已售出数：5
     * 提交之后：总订单数：5；已售出数：5
     */
    @Test
    public void SUPPORTS3() {
        // 示例3：外层报错有事务，内层正常。结果：外层回滚，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("SUPPORTS", "GID0001", "UID01", 1, true, false);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：5；已售出数：5
     * 提交之后：总订单数：6；已售出数：6
     */
    @Test
    public void SUPPORTS4() {
        // 示例4：外层正常无事务，内层报错。结果：外层提交，内层提交。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("SUPPORTS", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：6；已售出数：6
     * 提交之后：总订单数：6；已售出数：6
     */
    @Test
    public void MANDATORY1() {
        // 支持事务，如果业务方法执行时已经在一个事务中，则加入当前事务。否则抛出异常。
        // 示例1：外层正常有事务，内层报错。结果：外层回滚，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("MANDATORY", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"No existing transaction found for transaction marked with propagation 'mandatory'"}
     * 提交之前：总订单数：6；已售出数：6
     * 提交之后：总订单数：7；已售出数：6
     */
    @Test
    public void MANDATORY2() {
        // 示例2：外层正常无事务，内层报错。结果：外层提交，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("MANDATORY", "GID0001", "UID01", 1, false, true);
    }


    /**
     * 提交结果：{"code":"error","data":"订单异常..."}
     * 提交之前：总订单数：7；已售出数：6
     * 提交之后：总订单数：7；已售出数：7
     */
    @Test
    public void REQUIRES_NEW1() {
        // 支持事务。每次都是创建一个新事务，如果当前已经在事务中了，会挂起当前事务。
        // 示例1：内层正常，外层报错。结果：内层提交，外层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("REQUIRES_NEW", "GID0001", "UID01", 1, true, false);
    }

    @Test
    public void REQUIRES_NEW2() {
        // 示例2：内层报错，外层try-catch。结果：外层提交，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("REQUIRES_NEW", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：7；已售出数：7
     * 提交之后：总订单数：7；已售出数：7
     */
    @Test
    public void REQUIRES_NEW3() {
        // 示例3：内层报错，外层不try-catch。:结果：外层回滚，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("MANDATORY", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：7；已售出数：7
     * 提交之后：总订单数：7；已售出数：8
     */
    @Test
    public void NOT_SUPPORTED1() {
        // 不支持事务。如果业务方法执行时已经在一个事务中，则挂起当前事务，等方法执行完毕后，事务恢复进行。
        // 示例1：外层正常有事务，内层报错。结果：外层回滚，内层提交。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NOT_SUPPORTED", "GID0001", "UID01", 1, false, true);
    }

    @Test
    public void NOT_SUPPORTED2() {
        // 示例2：外层正常有事务try-catch内层，内层报错。结果：外层提交，内层提交。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NOT_SUPPORTED", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"Existing transaction found for transaction marked with propagation 'never'"}
     * 提交之前：总订单数：7；已售出数：8
     * 提交之后：总订单数：7；已售出数：8
     */
    @Test
    public void NEVER1() {
        // 不支持事务。如果当前已经在一个事务中了，抛出异常。数据回滚。
        // 示示例1：外层正常有事务，内层报错。结果：外层回滚，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NEVER", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：7；已售出数：8
     * 提交之后：总订单数：8；已售出数：9
     */
    @Test
    public void NEVER2() {
        // 示例2：外层正常无事务，内层报错。结果：外层提交，内层提交。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NEVER", "GID0001", "UID01", 1, false, true);
    }

    /**
     * 提交结果：{"code":"error","data":"Existing transaction found for transaction marked with propagation 'never'"}
     * 提交之前：总订单数：8；已售出数：9
     * 提交之后：总订单数：8；已售出数：9
     */
    @Test
    public void NEVER3() {
        // 示例3：外层报错有事务，内层正常。结果：外层回滚，内层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NEVER", "GID0001", "UID01", 1, true, false);
    }

    /**
     * 提交结果：{"code":"error","data":"订单异常..."}
     * 提交之前：总订单数：8；已售出数：9
     * 提交之后：总订单数：8；已售出数：9
     */
    @Test
    public void NESTED1() {
        // 支持事务。如果当前已经在一个事务中了，则嵌套在已有的事务中作为一个子事务。如果当前没在事务中则开启一个事务。
        // 示例1：内层正常，外层报错。结果：内层回滚，外层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NESTED", "GID0001", "UID01", 1, true, false);
    }

    /**
     * 提交结果：{"code":"error","data":"库存异常..."}
     * 提交之前：总订单数：8；已售出数：9
     * 提交之后：总订单数：8；已售出数：9
     */
    @Test
    public void NESTED2() {
        // 示例2：内层报错，外层正常。结果：内层回滚，外层回滚。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NESTED", "GID0001", "UID01", 1, false, true);
    }

    @Test
    public void NESTED3() {
        // 示例3：内层报错，外层正常try-catch内层。结果：内层回滚，外层提交。
        // REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
        testOrder("NESTED", "GID0001", "UID01", 1, false, true);
    }
}
