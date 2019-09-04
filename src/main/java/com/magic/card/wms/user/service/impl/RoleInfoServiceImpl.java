package com.magic.card.wms.user.service.impl;

import java.util.Date;
import java.util.List;

import com.magic.card.wms.common.model.po.UserSessionUo;
import com.magic.card.wms.common.utils.WebUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.user.mapper.RoleInfoMapper;
import com.magic.card.wms.user.model.dto.RoleAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuUpdateDto;
import com.magic.card.wms.user.model.dto.RoleQueryDto;
import com.magic.card.wms.user.model.dto.RoleUpdateDto;
import com.magic.card.wms.user.model.po.RoleInfo;
import com.magic.card.wms.user.service.IRoleInfoService;
import com.magic.card.wms.user.service.IRoleMenuMappingService;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 *  服务实现类
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements IRoleInfoService {

	@Autowired
	private IRoleMenuMappingService roleMenuMappingService;

	@Autowired
	private WebUtil webUtil;
	
	/**
	 * 查询角色列表
	 */
	@Override
	public List<RoleInfo> getRoleList(RoleQueryDto dto) {
		//查询所以正常状态的角色
		Wrapper<RoleInfo> wrapper= new EntityWrapper<RoleInfo>();
		wrapper.eq("state", StateEnum.normal.getCode());
		if(!StringUtils.isEmpty(dto.getRoleCode())) {
			wrapper.eq("role_code", dto.getRoleCode());
		}
		if(!StringUtils.isEmpty(dto.getRoleName())) {
			wrapper.like("role_name", dto.getRoleName());
		}
		if(!StringUtils.isEmpty(dto.getRoleDesc())) {
			wrapper.like("role_desc", dto.getRoleDesc());
		}
		if(!StringUtils.isEmpty(dto.getRoleType())) {
			wrapper.eq("role_type", dto.getRoleType());
		}
		if(!StringUtils.isEmpty(dto.getState())) {
			wrapper.eq("state", dto.getState());
		}
		wrapper.eq("display_flag", 1);
		return this.selectList(wrapper);
	}

	/**
	 * 新增角色信息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void addRoleInfo(RoleAddDto dto) throws BusinessException {
		Wrapper<RoleInfo> wrapper = new EntityWrapper<RoleInfo>();
		wrapper.eq("role_name", dto.getRoleName());
		wrapper.eq("state", StateEnum.normal.getCode());
		RoleInfo roleInfo = this.selectOne(wrapper);
		if(StringUtils.isEmpty(roleInfo)) {
			UserSessionUo userSession = webUtil.getUserSession();
			String userName = userSession.getName();
			roleInfo = new RoleInfo();
			BeanUtils.copyProperties(dto, roleInfo);

			//取角色名称首字母作为code
			String roleCode = getPinYinHeadChar(dto.getRoleName());
			log.info("===>> 角色Code：{}",roleCode);
			roleInfo.setRoleCode(roleCode);
			roleInfo.setCreateTime(new Date());
			roleInfo.setCreateUser(userName);
			roleInfo.setState(StateEnum.normal.getCode());
			roleInfo.setDisplayFlag(1);
			log.info("===inserRoleInfo.params:{}",roleInfo);
			boolean insertFlag = this.insert(roleInfo);
			if(insertFlag && !CollectionUtils.isEmpty(dto.getMenuKeyList())) {
				RoleMenuAddDto roleMenu = new RoleMenuAddDto();
				roleMenu.setRoleKey(roleInfo.getId());
				roleMenu.setMenuKeyList(dto.getMenuKeyList());
				roleMenuMappingService.addRoleMenuMapping(roleMenu);
			}
		}else {
			log.info("===角色信息已存在！req:{}", dto);
			throw new BusinessException(00, "角色信息已存在,请更换角色名称！");
		}
	}
	

	/**
	 * 修改/删除角色信息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void updateRoleInfo(RoleUpdateDto dto) throws BusinessException {
		UserSessionUo userSession = webUtil.getUserSession();
		Wrapper<RoleInfo> wrapper = new EntityWrapper<RoleInfo>();
//		wrapper.eq("role_code", dto.getRoleCode());
		wrapper.eq("state", StateEnum.normal.getCode());
		wrapper.eq("id", dto.getRoleKey());
		RoleInfo roleInfo = this.selectOne(wrapper);
		//判断roleCode是否重复
		if(StringUtils.isEmpty(roleInfo)) {
			throw new BusinessException(00, "角色信息不存在！");
		}
		BeanUtils.copyProperties(dto, roleInfo);
		roleInfo.setUpdateTime(new Date());
		roleInfo.setUpdateUser(userSession.getName());
		roleInfo.setId(dto.getRoleKey());
		boolean updateFlag = this.updateById(roleInfo);
		if(updateFlag) {
			RoleMenuUpdateDto roleMenu = new RoleMenuUpdateDto();
			roleMenu.setRoleKey(dto.getRoleKey());
			roleMenu.setMenuKeyList(dto.getMenuKeyList());
			roleMenu.setState(dto.getState());
			log.info("===>> 修改角色菜单，请求参数：{}", roleMenu);
			roleMenuMappingService.updateRoleMenuMapping(roleMenu);
//			if(!CollectionUtils.isEmpty(dto.getAddMenuKeyList()) || !CollectionUtils.isEmpty(dto.getAddMenuKeyList())) {
//				RoleMenuUpdateDto roleMenu = new RoleMenuUpdateDto();
//				roleMenu.setRoleKey(dto.getRoleKey());
//				if(!CollectionUtils.isEmpty(dto.getAddMenuKeyList())) {
//					roleMenu.setAddMenuKeyList(dto.getAddMenuKeyList());
//				}
//				if(!CollectionUtils.isEmpty(dto.getDelMenuKeyList())) {
//					roleMenu.setDelMenuKeyList(dto.getDelMenuKeyList());
//				}
//				log.info("===>> 修改角色菜单，请求参数：{}", roleMenu);
//				roleMenuMappingService.updateRoleMenuMapping(roleMenu);
//			}
		}
	}

	/**
	 * 得到中文首字母
	 *
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {

		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

}
