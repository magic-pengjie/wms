package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.po.Order;
import com.magic.card.wms.baseset.model.vo.ExcelOrderImport;
import com.magic.card.wms.baseset.model.vo.OrderStatisticsVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * com.magic.card.wms.baseset.mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:43
 * @since : 1.0.0
 */
public interface OrderInfoMapper extends BaseMapper<Order> {
    List<Map> orderCommodityWeightMap(@Param("orderNo") String orderNo,@Param("customerCode") String customerCode);

    List<Map> loadGrid(Page page, @Param("ew")Wrapper entityWrapper);

    List<Map> orderPackage(@Param("orderNo") String orderNO);
    /***
     * 根据拣货单or订单查询订单及商品信息
     * @param pickNo
     * @param orderNo
     * @return
     */
    List<OrderInfoDTO> selectOrderByNo(@Param("pickNo") String pickNo, @Param("orderNo") String orderNo, @Param("sendState") String sendState);

    /**
     * 加载包裹数据信息
     * @param page
     * @param wrapper
     * @return
     */
    List<Map> loadPackageGrid(Page page, @Param("ew") EntityWrapper wrapper);

    List<ExcelOrderImport> excelExport(@Param("orderNos") List<String> orderNos);

    /**
     * 加载原始订单商品数据
     * @param orderNo 原始订单号
     * @param customerCode 商家编号
     * @return
     */
    List<Map> loadCommodity(@Param("orderNos") String[] orderNos, @Param("customerCode") String customerCode);

    /**
     * 加载系统订单包裹数据
     * @param systemOrderNo
     * @return
     */
    List<Map> loadMailDetails(@Param("systemOrderNo") String systemOrderNo);

	 /**
     * 主页订单量统计
     * @param orderDate 订单时间
     * @return OrderStatisticsVO
     */
    OrderStatisticsVO selectOrderStatistics(String orderDate);

    /**
     * 获取订单商品
     * @param systemOrderNo
     * @return
     */
    TreeMap<String, Integer> commodityNumMap(String systemOrderNo);

    /**
     * 获取订单商品数据： 库位、购买量。。。
     * @param systemOrderNo 系统订单号
     * @param houseCode 仓库功能编号
     * @return
     */
    List<Map> commodityGrid(@Param("systemOrderNo") String systemOrderNo, @Param("houseCode") String houseCode);

    /**
     * 加载可合单的数据信息
     * @param page 分页信息
     * @param wrapper 查询排序信息
     * @return
     */
    List<Map> loadCanMergeGrid(Page page, @Param("ew") EntityWrapper wrapper);

    /**
     * 批量加载订单商品数据
     * @param systemOrderNos 系统订单号
     * @return
     */
    List<Map> batchLoadOrderCommodities(@Param("systemOrderNos") List<String> systemOrderNos);

}
