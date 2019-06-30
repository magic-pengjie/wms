package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.magic.card.wms.baseset.model.po.MailPicking;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 * 快递篮拣货表 Mapper 接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 9:52
 * @since : 1.0.0
 */
public interface MailPickingMapper extends BaseMapper<MailPicking> {

    /**
     * 获取配货清单数据
     * @param pickNo 拣货单号
     * @param billState 取消订单状态码
     * @param houseCode 拣货区编码
     * @return
     */
    List<Map> invoiceList(@Param("pickNo")String pickNo, @Param("billState")String billState, @Param("houseCode")String houseCode);

    /**
     * 获取配货单检验数据
     * @param pickNo 拣货单号
     * @param commodityCode 商品条形码
     * @return
     */
    List<Map> invoiceCheckList(@Param("pickNo") String pickNo, @Param("barCode") String commodityCode);

    /**
     * 获取拣货单各个拣货篮的完成状态
     * @param pickNo
     * @return
     */
    List<Map> obtainPickingFinishState(@Param("pickNo") String pickNo);

    /**
     * 统计订单商品未拣货完成数
     * @param orderNo
     * @return
     */
    @Select("SELECT COUNT(1) FROM wms_order_commodity WHERE numbers > pick_numbers AND order_no = #{orderNo}")
    Integer countOrderCommodityUnfinished(@Param("orderNo") String orderNo);

    /**
     * 获取拣货单所有漏检商品数据信息
     * @param pickNo
     * @param state
     * @return
     */
    List<Map> omitOrderCommodityList(@Param("pickNo") String pickNo, @Param("state") Integer state);
}
