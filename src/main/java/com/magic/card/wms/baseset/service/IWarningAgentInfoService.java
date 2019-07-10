package com.magic.card.wms.baseset.service;

import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.warehousing.model.dto.BillQueryDTO;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillVO;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 预警代办表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-10
 */
public interface IWarningAgentInfoService extends IService<WarningAgentInfo> {

	/**
	 * 预警代办列表
	 * @param dto 请求参数
	 * @param page 分页对象
	 * @return
	 */
	Page<WarningAgentInfo> getList(BillQueryDTO dto,PageInfo page);
}
