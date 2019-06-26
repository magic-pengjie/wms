package com.magic.card.wms.user.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.magic.card.wms.user.model.po.MenuInfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-22
 */
public interface MenuInfoMapper extends BaseMapper<MenuInfo> {

	List<MenuInfo> queryMenuByRoleKey(List<Integer> roleKeyList);
	
}
