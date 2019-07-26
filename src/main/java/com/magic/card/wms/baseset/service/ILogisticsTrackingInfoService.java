package com.magic.card.wms.baseset.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.MailDTO;
import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;

/**
 * <p>
 * 物流跟踪信息表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-13
 */
public interface ILogisticsTrackingInfoService extends IService<LogisticsTrackingInfo> {

	/***
	 * 根据快递单号实时查询邮政物流信息
	 * @param mailNos 快递单号
	 * @return
	 */
	ResponseData getTrackingInfo(List<String> mailNos) throws UnsupportedEncodingException;
	
	/***
     * 查询无物流信息的快递单
     * @param pageInfo
     * @param dto
     * @return
     */
    Page<MailPicking> selectNonLogisticsInfoList(MailDTO dto,PageInfo pageInfo,boolean sort);
    /**
     * 快递单物流信息批量查询
     */
    void runLogisticsInfo();
    /**
     *  物流信息预警
     */
    void runLogisticsInfoWarning();
}
