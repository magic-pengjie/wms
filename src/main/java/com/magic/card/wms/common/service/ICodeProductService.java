package com.magic.card.wms.common.service;

import com.magic.card.wms.common.model.po.CodeProduct;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 编号生成表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-23
 */
public interface ICodeProductService extends IService<CodeProduct> {

	/***
	 * 获取编码
	 * @param codeProduct
	 * @return
	 */
	String getCode(CodeProduct codeProduct);
}
