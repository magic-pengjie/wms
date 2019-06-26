package com.magic.card.wms.user.service.impl;

import com.magic.card.wms.user.model.dto.MenuQueryDto;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.po.MenuInfo;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.mapper.MenuInfoMapper;
import com.magic.card.wms.user.service.IMenuInfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  	服务实现类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Service
public class MenuInfoServiceImpl extends ServiceImpl<MenuInfoMapper, MenuInfo> implements IMenuInfoService {

	@Override
	public List<MenuInfo> queryMenuList(MenuQueryDto dto) throws BusinessException {
		Wrapper<MenuInfo> menuWrapper = new EntityWrapper<MenuInfo>();
		if(!StringUtils.isEmpty(dto)) {
			if(!StringUtils.isEmpty(dto.getParentKey())) {
				menuWrapper.eq("parent_key", dto.getParentKey());
			}
			if(!StringUtils.isEmpty(dto.getMenuCode())) {
				menuWrapper.eq("menu_code", dto.getMenuCode());
			}
			if(!StringUtils.isEmpty(dto.getMenuGrade())) {
				menuWrapper.eq("menu_grade", dto.getMenuGrade());
			}
			if(!StringUtils.isEmpty(dto.getMenuName())) {
				menuWrapper.like("menu_name", dto.getMenuName());
			}
			if(!StringUtils.isEmpty(dto.getPageBtnFlag())) {
				menuWrapper.eq("page_btn_flag", dto.getPageBtnFlag());
			}
			if(!StringUtils.isEmpty(dto.getRootMenuCode())) {
				menuWrapper.eq("root_menu_code", dto.getRootMenuCode());
			}
		}
		return this.selectList(menuWrapper);
	}

}
