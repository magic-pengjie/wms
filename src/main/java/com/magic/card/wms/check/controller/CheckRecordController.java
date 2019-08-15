package com.magic.card.wms.check.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.magic.card.wms.check.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.check.service.ICheckRecordService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.ResponseData;

import io.swagger.annotations.Api;
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
@Api(value="盘点记录维护Controller")
public class CheckRecordController {
	
	@Autowired
	private ICheckRecordService checkRecordService;
	
	/**
	 * 	根据盘点规则查询统计当前库存信息
	 *	 盘点要素：时间(月盘，季盘，半年盘，年盘)，维度(商品，商家，库区，全盘)
	 * @throws BusinessException 
	 */
	@ApiOperation(value="根据盘点规则查询统计当前库存信息")
	@RequestMapping(value = "/countCheck", method = RequestMethod.POST)
	public ResponseData countCheckRecordInfo(@RequestBody @Valid CheckCountDto dto, BindingResult bindingResult) throws BusinessException {
		List<CheckRecordInfoDto> countCheckRecord = null;
		try {
			countCheckRecord = checkRecordService.countCheckRecord(dto);
		} catch (BusinessException e) {
			log.info("===>> 查询库存业务逻辑失败：{}",e);
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
	@ApiOperation(value="开始盘点,冻结库位,登记盘点记录")
	@RequestMapping(value = "/checkRecordStart", method = RequestMethod.POST)
	public ResponseData checkRecordStart(@RequestBody @Valid CheckRecordStartDto dto, BindingResult bindingResult ) throws BusinessException {
		try {
			return checkRecordService.checkRecordStart(dto);
		} catch (BusinessException e) {
			log.info("===>> 盘点失败：{}",e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		} catch (Exception e) {
			log.info("===>> 盘点异常：{}",e);
			return ResponseData.error("盘点异常");
		}
	}

	/**
	 * 保存盘点信息
	 */
	@ApiOperation(value="保存盘点信息")
	@RequestMapping(value = "/saveCheckRecord", method = RequestMethod.POST)
	public ResponseData saveCheckRecord(@RequestBody @Valid CheckRecordUpdateDto checkRecordSaveDto, BindingResult bindingResult) throws BusinessException {
		return ResponseData.ok(checkRecordService.saveCheckRecord(checkRecordSaveDto.getCheckRecordList()));
	}


	
	/**
	 * 取消盘点,解除冻结
	 */
	@ApiOperation(value="取消盘点,解除冻结")
	@RequestMapping(value = "/cannelCheckRecord", method = RequestMethod.POST)
	public ResponseData cannelCheckRecord(@RequestBody @Valid CheckRecordCanellDto dto, BindingResult bindingResult ) throws BusinessException {
		try {
			boolean cannelFlag = checkRecordService.cannelCheckRecord(dto);
			if(!cannelFlag) {
				return ResponseData.error("取消盘点失败");
			}
		} catch (BusinessException e) {
			log.info("===>> 取消盘点失败：{}",e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		} catch (Exception e) {
			log.info("===>> 取消盘点异常：{}",e);
			return ResponseData.error("取消盘点异常");
		}
		return ResponseData.ok();
	}
	
	
	/**
	 * 	盘点结束，更新库存信息，及盘点信息
	 * @throws BusinessException 
	 */
	@ApiOperation(value="盘点结束，更新盘点记录,修改审批状态为：审批中")
	@RequestMapping(value = "/updCheckRecord", method = RequestMethod.POST)
	public ResponseData updateCheckRecordInfo(@RequestBody @Valid CheckRecordUpdateDto CheckRecordUpdateDto, BindingResult bindingResult) {

		try {
			checkRecordService.updateRecordInfo(CheckRecordUpdateDto.getCheckRecordList());
		} catch (BusinessException e) {
			log.info("===>> 取消盘点失败：{}",e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		} catch (Exception e) {
			log.info("===>> 取消盘点异常：{}",e);
			return ResponseData.error("取消盘点异常");
		}
		return ResponseData.ok();
	}
	
	
	/**
	 * 	查询 盘点记录
	 * @throws BusinessException 
	 */
	@ApiOperation(value="查询 盘点记录")
	@RequestMapping(value = "/queryCheckRecord", method = RequestMethod.POST)
	public ResponseData queryCheckRecordInfo(@RequestBody @Valid QueryAuditCheckRecordDto auditDto, BindingResult bindingResult) {
		try {
			return ResponseData.ok(checkRecordService.queryCheckRecord(auditDto));
		} catch (BusinessException e) {
			log.info("===>> 查询 盘点失败：{}",e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		} catch (Exception e) {
			log.info("===>> 查询 盘点异常：{}",e);
			return ResponseData.error("查询 盘点异常");
		}
	}
	
	
	/**
	 * 	审批盘点记录
	 * @throws BusinessException 
	 */
	@ApiOperation(value="审批盘点记录,审批通过后修改库存信息")
	@RequestMapping(value = "/auditCheckRecord", method = RequestMethod.POST)
	public ResponseData auditCheckRecordInfo(@RequestBody @Valid AuditCheckRecordDto auditCheckRecordList, BindingResult bindingResult) {
		boolean auditFlag = false;
		try {
			auditFlag = checkRecordService.auditCheckRecord(auditCheckRecordList);
		} catch (BusinessException e) {
			log.info("===>> 取消盘点失败：{}",e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		} catch (Exception e) {
			log.info("===>> 取消盘点异常：{}",e);
			return ResponseData.error("取消盘点异常");
		}
		if(auditFlag) {
			return ResponseData.ok();
		}
		return ResponseData.error("审批失败");
	}
	
	
}

