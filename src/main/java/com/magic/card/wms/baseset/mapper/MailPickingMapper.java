package com.magic.card.wms.baseset.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.MailDTO;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.baseset.model.vo.MailDetailVO;
import com.magic.card.wms.common.model.enums.Constants;

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
     * 统计订单商品未拣货完成数
     * @param mailNo 快递单号
     * @return
     */
    @Select("SELECT COUNT(1) FROM wms_mail_picking_detail WHERE package_nums > pick_nums AND mail_no = #{mailNo}")
    Integer countPackageCommodityUnfinished(@Param("mailNo") String mailNo);

    /**
     * 拣货单所有复检完毕的包裹快递单号
     * @param pickNo 拣货单号
     * @return
     */
    List<String> finishedPackage(@Param("pickNo") String pickNo);

    /**
     * 获取拣货单所有漏检商品数据信息
     * @param pickNo
     * @param state
     * @return
     */
    List<Map> omitOrderCommodityList(@Param("pickNo") String pickNo, @Param("state") Integer state);

    /**
     * 获取拣货单中所有快递篮包裹基本信息
     * @param wrapper
     * @return
     */
    List<Map> pickBillMails(@Param("ew") EntityWrapper wrapper);
    
    /**
     * 查询当天无物流信息的包裹  昨天16点后 到 今天16点之前
     * @param startDate
     * @param endDate
     * @return
     */
    List<MailPicking> getLogisticsInfoWarningList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    /**
     * 包裹单列表展示
     * @return
     */
    List<MailDetailVO> selectPickInfoList(Page page,MailDTO dto);
    /**
     * 包裹单列表展示条数
     * @return
     */
    Integer selectPickInfoListCounts(MailDTO dto);
    
    /**
     * 根据快递单修改物流状态
     * @param mailNos
     * @param 状态
     */
    void updateBatchByMailNo(@Param("mailNo") String mailNo, @Param("state") int state);

    /**
     * 获取系统订单包裹基本信息
     * @param systemOrderNo
     * @return
     */
    List<Map> loadOrderPackage(@Param("systemOrderNo") String systemOrderNo);
}
