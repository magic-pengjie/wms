package com.magic.card.wms.user.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.model.dto.MenuQueryDto;
import com.magic.card.wms.user.model.po.MenuInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
public interface IMenuInfoService extends IService<MenuInfo> {

	public List<MenuInfo> queryMenuList(MenuQueryDto dto) throws BusinessException;

}
