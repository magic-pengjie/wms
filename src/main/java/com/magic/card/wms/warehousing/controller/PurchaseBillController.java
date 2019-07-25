package com.magic.card.wms.warehousing.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.warehousing.model.dto.ComfirmReqDTO;
import com.magic.card.wms.warehousing.model.dto.PurchaseBillDTO;
import com.magic.card.wms.warehousing.model.dto.BillQueryDTO;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillVO;
import com.magic.card.wms.warehousing.service.IPurchaseBillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 采购单据表 前端控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@RestController
@RequestMapping("/warehousing/purchaseBill")
@Api(value = "采购单据", description = "采购单据")
@Slf4j
public class PurchaseBillController {

	@Autowired
	private IPurchaseBillService purchaseBillService;
	@javax.annotation.Resource
	private ResourceLoader resourceLoader;
	
	@ApiOperation(value = "采购单据列表查询", notes = "采购单据列表查询")
	@RequestMapping(value = "/selectList", method = RequestMethod.POST)
	public ResponseData selectPurchaseBillList(@RequestBody BillQueryDTO dto,PageInfo pageInfo) {
		try {
			Page<PurchaseBillVO> page = purchaseBillService.selectPurchaseBillList(dto, pageInfo);
			return ResponseData.ok(page);
		} catch (Exception e) {
			log.error("查询采购单据列表失败:{}",e);
			return ResponseData.error(ResultEnum.query_error);
		}
		
	}
	@ApiOperation(value = "采购单据新增", notes = "采购单据新增")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseData add(@RequestBody @Valid PurchaseBillDTO dto,BindingResult bindingResult) {
		try {
			purchaseBillService.add(dto);
			return ResponseData.ok();
		}catch (OperationException o) {
			log.error("新增采购单据失败:{}",o);
			return ResponseData.error(o.getErrCode(),o.getErrMsg());
		}catch (Exception e) {
			log.error("新增采购单据失败:{}",e);
			return ResponseData.error(ResultEnum.add_error);
		}
		
	}
	
	@ApiOperation(value = "采购单据修改", notes = "采购单据修改")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseData update(@RequestBody @Valid PurchaseBillDTO dto,BindingResult bindingResult) {
		try {
			purchaseBillService.update(dto);
			return ResponseData.ok();
		}catch (OperationException b) {
			log.error("修改采购单据失败:{}",b);
			return ResponseData.error(b.getErrCode(),b.getErrMsg());
		}catch (Exception e) {
			log.error("修改采购单据失败:{}",e);
			return ResponseData.error(ResultEnum.update_error);
		}
		
	}
	@ApiOperation(value = "采购单据删除", notes = "采购单据删除")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ResponseData delete(@PathVariable(required = true)long id) {
		try {
			purchaseBillService.delete(id);
			return ResponseData.ok();
		}catch (OperationException b) {
			log.error("删除采购单据失败:{}",b);
			return ResponseData.error(b.getErrCode(),b.getErrMsg());
		}catch (Exception e) {
			log.error("删除采购单据失败:{}",e);
			return ResponseData.error(ResultEnum.delete_error);
		}
		
	}
	
	@ApiOperation(value = "采购单据导入模版下载", notes = "采购单据导入模版下载")
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
	public ResponseData downloadTemplate(@RequestParam(value="isFood",required = true) boolean isFood,HttpServletRequest request,HttpServletResponse response) {
		InputStream in = null;
		OutputStream out = null;
		try {
			String fileName = "采购单导入模版";
			if(isFood) {
				fileName = "采购单导入模版-食品";
			}
			String path = "templates/"+fileName+".xlsx";
			Resource resource = resourceLoader.getResource("classPath:"+path);
			EasyExcelUtil.prepareResponds(request, response, fileName, ExcelTypeEnum.XLSX);
			in = resource.getInputStream();
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			response.flushBuffer();
		}catch (Exception e) {
			log.error("采购单据导入模版下载:{}",e);
			return ResponseData.error(ResultEnum.download_error);
		}finally {
			try {
				if(null != in) {
					in.close();
				}
				if(null != out) {
					out.close();
				}
			} catch (IOException e) {
				log.error("close io error:{}",e);
			}
			
		}
		return null;
		
	}
	
	@ApiOperation(value = "采购单据导入", notes = "采购单据导入")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public ResponseData importPurchase(@RequestParam(value="file",required = true) MultipartFile file,HttpServletRequest request,HttpServletResponse response) {
		try {
			long size = file.getSize();
			if(0 == size) {
				return ResponseData.error(ResultEnum.purchase_file_size_zero);
			}
			purchaseBillService.importPurchase(file);
			return ResponseData.ok();
		}catch (OperationException b) {
			log.error("采购单据导入失败:{}",b);
			return ResponseData.error(b.getErrCode(),b.getErrMsg());
		}catch (Exception e) {
			log.error("采购单据导入失败:{}",e);
			return ResponseData.error(ResultEnum.upload_error);
		}
		
	}
	
	@ApiOperation(value = "开始收货操作", notes = "开始收货操作")
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ResponseData confirm(@RequestBody @Valid ComfirmReqDTO dto,BindingResult bindingResult) {
		String warningStr = null;
		try {
			warningStr = purchaseBillService.confirm(dto);
			if(!ObjectUtils.isEmpty(warningStr)) {
				return ResponseData.ok(00,warningStr);
			}
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("采购单据失败:{}",e);
			return ResponseData.error(ResultEnum.update_error);
		}
		
	}
}

