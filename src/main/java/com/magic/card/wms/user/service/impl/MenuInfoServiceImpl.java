package com.magic.card.wms.user.service.impl;

import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.user.model.dto.MenuQueryDto;
import com.magic.card.wms.user.model.dto.MenuQueryResponseDto;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.po.MenuInfo;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.utils.BeanCopyUtil;
import com.magic.card.wms.user.mapper.MenuInfoMapper;
import com.magic.card.wms.user.service.IMenuInfoService;

import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  	服务实现类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@Service
public class MenuInfoServiceImpl extends ServiceImpl<MenuInfoMapper, MenuInfo> implements IMenuInfoService {

	@Override
	public List<MenuQueryResponseDto> queryMenuList(MenuQueryDto dto) throws BusinessException {
		
		Wrapper<MenuInfo> menuWrapper = new EntityWrapper<MenuInfo>();
		menuWrapper.eq("state", StateEnum.normal.getCode());
		menuWrapper.orderBy("id");
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
		List<MenuInfo> menuList = this.selectList(menuWrapper);
		List<MenuQueryResponseDto> buildTree = null;
		if(!CollectionUtils.isEmpty(menuList)) {
			log.info("===>> selectMenuList.size:{}",menuList.size());
			List<MenuQueryResponseDto> menuTreeSourceList = BeanCopyUtil.copyList(menuList, MenuQueryResponseDto.class);
			log.info("===>> menuTreeSourceList.size:{}",menuTreeSourceList.size());
			buildTree = buildTree(menuTreeSourceList, 0);
		}
		return buildTree;
	}
	
	private List<MenuQueryResponseDto> buildTree(List<MenuQueryResponseDto> menuList, Integer parentId){
		List<MenuQueryResponseDto> menuTreeList = new ArrayList<MenuQueryResponseDto>();
		for (MenuQueryResponseDto menu : menuList) {
			Integer menuId = menu.getId().intValue();
			Integer pId = menu.getParentKey();
			if(parentId == pId) {
				List<MenuQueryResponseDto> subMenuLists = buildTree(menuList, menuId);
				menu.setChildMenu(subMenuLists);
				menuTreeList.add(menu);
			}
		}
		return menuTreeList;
	}

}
