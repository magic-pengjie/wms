package com.magic.card.wms.common.service.impl;

import com.magic.card.wms.common.model.po.CodeProduct;
import com.magic.card.wms.common.mapper.CodeProductMapper;
import com.magic.card.wms.common.service.ICodeProductService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 编号生成表 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-23
 */
@Service
public class CodeProductServiceImpl extends ServiceImpl<CodeProductMapper, CodeProduct> implements ICodeProductService {

	@Autowired
	private CodeProductMapper codeProductMapper;
	
	@Override
	@Transactional
	public String getCode(CodeProduct codeProduct) {
		String code = codeProductMapper.getCode(codeProduct);
		if(StringUtils.isEmpty(code)) {
			code = codeProduct.getProductDate() + String.format("%0" + codeProduct.getLength() + "d", 1);
			codeProduct.setCode(code);
			//新增
			this.insert(codeProduct);
		}else {
			code = code.substring(0,code.length()-codeProduct.getLength()) + String.format("%0" + codeProduct.getLength()  + "d", Integer.parseInt(code.substring(code.length()-codeProduct.getLength())) + 1);
			codeProduct.setCode(code);
			//修改
			this.updateById(codeProduct);
		}
		return code;
	}
	

}
