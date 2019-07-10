package com.magic.card.wms.check.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.check.model.po.dto.CheckCountDto;
import com.magic.card.wms.check.model.po.dto.CheckRecordInfoDto;
import com.magic.card.wms.check.model.po.dto.CheckRecordStartDto;
import com.magic.card.wms.check.service.ICheckRecordService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.ResponseData;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 	盘点记录表 前端控制器
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@Slf4j
@RestController
@RequestMapping("/check")
public class CheckRecordController {
	
	@Autowired
	private ICheckRecordService checkRecordService;
	
	/**
	 * 	根据盘点规则查询统计当前库存信息
	 *	 盘点要素：时间(月盘，季盘，半年盘，年盘)，维度(商品，商家，库区，全盘)
	 * @throws BusinessException 
	 */
	@ApiOperation("根据盘点规则查询统计当前库存信息")
	@RequestMapping(value = "/countCheck", method = RequestMethod.POST)
	public ResponseData countCheckRecordInfo(@RequestBody @Valid CheckCountDto dto, BindingResult bindingResult) throws BusinessException {
		List<CheckRecordInfoDto> countCheckRecord = null;
		try {
			countCheckRecord = checkRecordService.countCheckRecord(dto);
		} catch (BusinessException e) {
			log.info("===>> 查询库存业务逻辑异常：{}",e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		}  catch (Exception e) {
			log.info("===>> 查询库存异常：{}",e);
			return ResponseData.error("查询库存异常");
		}
		return ResponseData.ok(countCheckRecord);
	}
	
	/**
	 * 	盘点开始，冻结库位，登记盘点记录
	 * @throws BusinessException 
	 */
	@ApiOperation("开始盘点,冻结库位,登记盘点记录")
	@RequestMapping(value = "/checkRecordStart", method = RequestMethod.POST)
	public ResponseData checkRecordStart(@RequestBody @Valid CheckRecordStartDto dto, BindingResult bindingResult ) throws BusinessException {
		try {
			boolean checkRecordStart = checkRecordService.checkRecordStart(dto);
			if(!checkRecordStart) {
				return ResponseData.error("盘点失败");
			}
		} catch (BusinessException e) {
			log.info("===>> 盘点异常：{}",e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		} catch (Exception e) {
			log.info("===>> 盘点异常：{}",e);
			return ResponseData.error("盘点异常");
		}
		return ResponseData.ok();
	}
	
	/**
	 * 	盘点结束，更新库存信息，及盘点信息
	 * @throws BusinessException 
	 */
	@ApiOperation("开始盘点,冻结库位,登记盘点记录")
	@RequestMapping(value = "/updCheckRecord", method = RequestMethod.POST)
	public ResponseData updateCheckRecordInfo(@RequestBody @Valid CheckRecordStartDto dto, BindingResult bindingResult) {
		return null;
	}
	
}

