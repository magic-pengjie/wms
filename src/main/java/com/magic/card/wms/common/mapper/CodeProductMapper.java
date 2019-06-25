package com.magic.card.wms.common.mapper;

import com.magic.card.wms.common.model.po.CodeProduct;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 编号生成表 Mapper 接口
 * </p>
 *
 * @author pengjie
 * @since 2019-06-23
 */
public interface CodeProductMapper extends BaseMapper<CodeProduct> {

	/**
	 * 获取编码
	 * @param codeProduct
	 * @return
	 */
	String getCode(CodeProduct codeProduct);
}
