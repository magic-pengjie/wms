package com.magic.card.wms.user.mapper;

import com.magic.card.wms.user.model.po.RoleInfo;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {

	List<RoleInfo> queryRoleByUserKey(@Param("userKey")Integer userKey);
	
}
