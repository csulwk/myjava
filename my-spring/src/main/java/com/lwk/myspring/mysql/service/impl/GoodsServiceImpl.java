package com.lwk.myspring.mysql.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwk.myspring.mysql.dao.GoodsStockMapper;
import com.lwk.myspring.mysql.entity.GoodsReq;
import com.lwk.myspring.mysql.entity.GoodsStock;
import com.lwk.myspring.mysql.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品服务
 * 参考文档：https://blog.csdn.net/qq_35493807/article/details/105756761
 * @author kai
 * @date 2020-12-22 23:02
 */
@Service
@Slf4j
public class GoodsServiceImpl extends BaseServiceImpl<GoodsStock, Long> implements GoodsService {

    private GoodsStockMapper goodsStockMapper;
    @Autowired
    public void setMapper(GoodsStockMapper goodsStockMapper) {
        this.initMapper(goodsStockMapper);
        this.goodsStockMapper = goodsStockMapper;
    }

    private JSONObject deductStock(GoodsReq req) {
        // 扣减库存
        int opr = goodsStockMapper.deductStock(req.getGoodsId(), req.getCount());
        if (opr <= 0) {
            log.warn("库存不足...");
            throw new RuntimeException("库存不足...");
        }

        // 异常处理
        if (req.isGExp()) {
            log.warn("库存异常...");
            throw new NullPointerException("库存异常...");
        }
        return new JSONObject().fluentPut("code", "success");
    }

    /**
     * 作用：[spring的默认传播行为]
     *   支持事务，如果业务方法执行时在一个事务中，则加入当前事务，否则重新开始一个事务。
     *   外层事务提交了，内层才会提交。
     *   内/外只要有报错，他俩会一起回滚。（示例2/3）
     *   只要内层方法报错抛出异常，即使外层有try-catch，该事务也会回滚！（示例1）
     *   内层不存在事务，外层存在事务，即加入外层的事务，不管内层，外层报错，都会回滚事务。
     * 示例1：外层正常try-catch内层，内层出错。结果：事务回滚，内层外层都回滚。
     * 示例2：外层正常，内层出错，外层不try-catch。结果：事务回滚，内层外层都回滚。
     * 示例3：外层出错，内层正常。结果：事务回滚，内层外层都回滚。
     */
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.REQUIRED)
    @Override
    public JSONObject goodsRequired(GoodsReq req) {
        return deductStock(req);
    }

    /**
     * 作用：
     *   支持事务。当前有事务就加入当前事务。当前没有事务就算了，不会开启一个事务。
     * 示例1：外层正常有事务，内层报错。结果：外层回滚，内层回滚。
     * 示例2：外层正常有事务try/catch，内层报错。结果：外层回滚，内层回滚。
     * 示例3：外层报错有事务，内层正常。结果：外层回滚，内层回滚。
     * 示例4：外层正常无事务，内层报错。结果：外层提交，内层提交。
     */
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.SUPPORTS)
    @Override
    public JSONObject goodsSupports(GoodsReq req) {
        return deductStock(req);
    }

    /**
     * 作用：
     *   支持事务，如果业务方法执行时已经在一个事务中，则加入当前事务。否则抛出异常。
     * 示例1：外层正常有事务，内层报错。结果：外层回滚，内层回滚。
     * 示例2：外层正常无事务，内层报错。结果：外层提交，内层回滚。
     */
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.MANDATORY)
    @Override
    public JSONObject goodsMandatory(GoodsReq req) {
        return deductStock(req);
    }

    /**
     * 作用：
     *   支持事务。每次都是创建一个新事务，如果当前已经在事务中了，会挂起当前事务。
     *   内层事务结束，内层就提交了，不用等着外层一起提交。
     *   外层报错回滚，不影响内层。（栗子一）
     *   内层报错回滚，外层try-catch内层的异常，外层不会回滚。（栗子二）
     *   内层报错回滚，然后又会抛出异常，外层如果没有捕获处理内层抛出来的这个异常，外层还是会回滚的。（栗子三）
     * 示例1：内层正常，外层报错。结果：内层提交，外层回滚。
     * 示例2：内层报错，外层try-catch。结果：外层提交，内层回滚。
     * 示例3：内层报错，外层不try-catch。:结果：外层回滚，内层回滚。
     */
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.REQUIRES_NEW)
    @Override
    public JSONObject goodsRequiresNew(GoodsReq req) {
        return deductStock(req);
    }

    /**
     * 作用：
     *   不支持事务。如果业务方法执行时已经在一个事务中，则挂起当前事务，等方法执行完毕后，事务恢复进行。
     * 示例1：外层正常有事务，内层报错。结果：外层回滚，内层提交。
     * 示例2：外层正常有事务try-catch内层，内层报错。结果：外层提交，内层提交。
     */
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public JSONObject goodsNotSupported(GoodsReq req) {
        return deductStock(req);
    }

    /**
     * 作用：
     *   不支持事务。如果当前已经在一个事务中了，抛出异常。数据回滚。
     * 示例1：外层正常有事务，内层报错。结果：外层回滚，内层回滚。
     * 示例2：外层正常无事务，内层报错。结果：外层提交，内层提交。
     * 示例3：外层报错有事务，内层正常。结果：外层回滚，内层回滚。
     */
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.NEVER)
    @Override
    public JSONObject goodsNever(GoodsReq req) {
        return deductStock(req);
    }

    /**
     * 作用：
     *   支持事务。如果当前已经在一个事务中了，则嵌套在已有的事务中作为一个子事务。如果当前没在事务中则开启一个事务。
     *   内层事务结束，要等着外层一起提交。
     *   外层回滚，内层也回滚。（栗子一）
     *   如果只是内层回滚，影响外层。（栗子二）[因为默认成为了子事务]
     *   如果只是内层回滚，外层try-catch内层的异常，不影响外层。（栗子三）
     *   这个内层回滚不影响外层的特性是有前提的，否则内外都回滚。
     * 前提：
     *   1.JDK版本要在1.4以上，有java.sql.Savepoint。因为nested就是用savepoint来实现的。
     *   2.事务管理器的nestedTransactionAllowed属性为true。DataSourceTransactionManager.setNestedTransactionAllowed(true)。
     *   3.外层try-catch内层的异常。
     * 示例1：内层正常，外层报错。结果：内层回滚，外层回滚。
     * 示例2：内层报错，外层正常。结果：内层回滚，外层回滚。
     * 示例3：内层报错，外层正常try-catch内层。结果：内层回滚，外层提交。
     */
    @Transactional(rollbackFor = {RuntimeException.class}, propagation = Propagation.NESTED)
    @Override
    public JSONObject goodsNested(GoodsReq req) {
        return deductStock(req);
    }

    @Override
    public GoodsStock selectByGoodsId(String goodsId) {
        return goodsStockMapper.selectByGoodsId(goodsId);
    }
}
