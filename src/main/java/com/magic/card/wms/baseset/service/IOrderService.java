package com.magic.card.wms.baseset.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.dto.OrderUpdateDTO;
import com.magic.card.wms.baseset.model.po.Order;
import com.magic.card.wms.common.model.LoadGrid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.service
 * 订单信息服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:46
 * @since : 1.0.0
 */
public interface IOrderService extends IService<Order> {
    /**
     * 自动锁定订单
     */
    void autoLockOrder();
    /**
     * 系统订单查询
     * @param loadGrid
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 获取订单商品
     * @param orderNo
     */
    List<Map> loadOrderCommodityGrid(String orderNo);

    /**
     * 导入其他系统订单
     * @param orderInfoDTO 订单基本信息
     */
    void importOrder(OrderInfoDTO orderInfoDTO);

    /**
     * 修改订单
     * @param orderUpdateDTO 订单基本信息
     */
    void updateOrder(OrderUpdateDTO orderUpdateDTO);

    /**
     * 获取满足要求的所有订单
     * @param customerCode
     * @return
     */
    List<Order> obtainOrderList(String customerCode, Integer executeSize);

    /**
     * 获取订单所有商品的总重量
     * @param orderNo
     * @param customerCode
     * @return
     */
//    BigDecimal orderCommodityWeight(String orderNo, String customerCode);

    /**
     * 订单称重对比
     * @param orderNo 订单号
     * @param realWight 称重实际重量
     * @param ignore 是否忽略重量差异比较
     * @param operator 操作人
     */
    void orderWeighContrast(String orderNo, BigDecimal realWight, Boolean ignore, String operator);

    /**
     * 订单打包推荐耗材
     * @param orderNO
     */
    List<Map> orderPackage(String orderNO);

    /**
     * 称重订单数据加载
     * @param loadGrid
     * @return
     */
    LoadGrid orderWeighLoadGrid(LoadGrid loadGrid);

    /**
     * 检出系统订单是否存在或是已取消
     * @param orderNo 系统订单 订单号 + 商家号
     */
    Order checkoutOrder(String orderNo);

    /**
     * 获取订单 key -> value Map
     * @param systemOrderNo
     * @return
     */
    Map<String, Order> ordersMap(List<String> systemOrderNo);

//    /**
//     * EXCEL 导入订单
//     * @param excelOrders
//     */
//    void excelImport(MultipartFile[] excelOrders) throws IOException;

    /**
     * EXCEL 导入订单
     * @param excelOrder
     */
    void excelNewImport(MultipartFile excelOrder) throws IOException;
    
    /**
         * 订单超时预警
     */
    void runOrderTimeOutWarning();

    /**
     * 导出订单数据
     * @param orderNos
     * @return
     */
    List<? extends BaseRowModel> excelExport(List<String> orderNos);

    /**
     * 获取订单详情（商品 以及 对应的包裹信息）
     * @param orderNo 系统订单号
     * @param customerCode 商家Code
     * @return
     */
    Map loadDetails(String orderNo, String customerCode);
}
