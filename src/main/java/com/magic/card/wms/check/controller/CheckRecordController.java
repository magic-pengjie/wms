package com.magic.card.wms.check.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.check.model.po.dto.CheckCountDto;
import com.magic.card.wms.check.service.ICheckRecordService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.ResponseData;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 	盘点记录表 前端控制器
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@RestController
@RequestMapping("/check")
public class CheckRecordController {
	
	@Autowired
	private ICheckRecordService checkRecordService;
	
	/**
	 *	 盘点要素：时间(月盘，季盘，半年盘，年盘)，维度(商品，商家，库区，全盘)，
	 * @throws BusinessException 
	 */
	@ApiOperation("根据盘点规则查询统计当前库存信息")
	@RequestMapping(value = "/countCheck", method = RequestMethod.POST)
	public ResponseData countCheckRecordInfo(@RequestBody @Valid CheckCountDto dto, BindingResult bindingResult) throws BusinessException {
		
		CheckRecord checkRecord = checkRecordService.countCheckRecord(dto);
		
		return ResponseData.ok(checkRecord);
	}
}

