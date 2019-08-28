package com.magic.card.wms.baseset.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.dto.OrderUpdateDTO;
import com.magic.card.wms.baseset.model.vo.ExcelOrderImport;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.annotation.RequestJsonParam;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.EasyExcelParams;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.controller
 * 导入订单前端控制器
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 16:35
 * @since : 1.0.0
 */
@Slf4j
@Api("订单导入系统控制器")
@RestController
@RequestMapping(value = "order", headers = "accept=application/json")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPickingBillService pickingBillService;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 加载订单数据
     * @param loadGrid
     * @return
     */
    @PostMapping("loadGrid")
    public ResponseData loadGrid(@RequestBody LoadGrid loadGrid) {
       return ResponseData.ok(orderService.loadGrid(loadGrid));
    }

    @PostMapping("loadCanMergeGrid")
    public ResponseData loadCanMergeGrid(@RequestBody LoadGrid loadGrid) {
        orderService.loadCanMergeGrid(loadGrid);
        return ResponseData.ok(loadGrid);
    }

    @PostMapping("mergeOrders")
    public ResponseData mergeOrders(@RequestJsonParam List<String> systemOrderNos) {
        orderService.mergeOrders(systemOrderNos);
        return ResponseData.ok();
    }

    @ApiOperation("获取订单商品信息以及包裹信息")
    @GetMapping("loadDetails")
    public ResponseData loadDetails(@RequestParam String orderNo, @RequestParam String customerCode, @RequestParam String systemOrderNo) {
        return ResponseData.ok(orderService.loadDetails(orderNo, customerCode, systemOrderNo));
    }

    @ApiOperation("订单拆包")
    @GetMapping("splitPackage")
    public ResponseData orderSplitPackage(@RequestParam String orderNo, @RequestParam String customerCode, @RequestParam String systemOrderNo) {
        orderService.splitPackage(orderNo, customerCode, systemOrderNo);
        return ResponseData.ok();
    }


    @GetMapping("excelExport")
    public void excelExport(HttpServletResponse response, HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> searchMap = Maps.newHashMap();

        if (MapUtils.isNotEmpty(parameterMap)) {
            request.getParameterMap().forEach( (key, values) -> {
                searchMap.put(key, values[0]);
            });
        }

        EasyExcelParams easyExcelParams = new EasyExcelParams();
        easyExcelParams.setSheetName("订单信息");
        easyExcelParams.setRequest(request);
        easyExcelParams.setResponse(response);
        easyExcelParams.setDataModelClazz(ExcelOrderImport.class);
        easyExcelParams.setExcelNameWithoutExt("订单数据导出" + DateTime.now().toString("yyyyMMddHHmmss"));
        easyExcelParams.setData(orderService.excelExport(searchMap));
        try {
            EasyExcelUtil.exportExcel2007Format(easyExcelParams);
        } catch (IOException e) {
            throw OperationException.customException(ResultEnum.order_excel_export_err);
        }
    }

//    @PostMapping("excelExport")
//    public void excelExport(@RequestBody List<String> orderNos, HttpServletResponse response, HttpServletRequest request) {
//        EasyExcelParams easyExcelParams = new EasyExcelParams();
//        easyExcelParams.setSheetName("订单信息");
//        easyExcelParams.setRequest(request);
//        easyExcelParams.setResponse(response);
//        easyExcelParams.setDataModelClazz(ExcelOrderImport.class);
//        easyExcelParams.setExcelNameWithoutExt("订单数据导出" + DateTime.now().toString("yyyyMMddHHmmss"));
//        easyExcelParams.setData(orderService.excelExport(orderNos));
//        try {
//            EasyExcelUtil.exportExcel2007Format(easyExcelParams);
//        } catch (IOException e) {
//            throw OperationException.customException(ResultEnum.order_excel_export_err);
//        }
//    }


    @ApiModelProperty("Excel订单导入")
    @PostMapping("excelImport")
    public ResponseData excelImport(@RequestParam MultipartFile excelOrders) {
        try {
            orderService.excelNewImport(excelOrders);
        } catch (IOException e) {
            throw OperationException.customException(ResultEnum.order_excel_import_err);
        }
        return ResponseData.ok();
    }

    @ApiOperation("下载订单导入模板")
    @GetMapping("downloadTemplate")
    public ResponseData downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String fileName = "订单导入模板-v2.0";
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

    /**
     * 更新订单信息
     * @param orderUpdateDTO
     * @param bindingResult
     * @return
     */
    @ApiOperation("更新订单信息")
    @PostMapping("updateOrder")
    public ResponseData updateOrder(@RequestBody @ApiParam("订单信息")@Valid OrderUpdateDTO orderUpdateDTO, BindingResult bindingResult) {
        orderService.updateOrder(orderUpdateDTO);
        return ResponseData.ok();
    }

    @GetMapping("loadOrderCommodityGrid")
    public ResponseData loadOrderCommodityGrid(@RequestParam String orderNo) {
       return ResponseData.ok(orderService.loadOrderCommodityGrid(orderNo));
    }

    @ApiOperation("订单导入")
    @PostMapping("import")
    public ResponseData importOrder(@RequestBody @ApiParam("订单信息")@Valid OrderInfoDTO orderInfoDTO, BindingResult bindingResult) {
        this.orderService.importOrder(orderInfoDTO);
        return ResponseData.ok();
    }

    @ApiOperation("测试触发生成拣货单")
    @GetMapping("generationPicking")
    public void generationPicking(@RequestParam String customerCode, @RequestParam(required = false, defaultValue = "15")Integer executeSize) {
        pickingBillService.triggerGenerator(customerCode, executeSize);
    }



    @GetMapping("cancel")
    public ResponseData cancel(@RequestParam String systemOrderNo) {
        orderService.cancelOrder(systemOrderNo);
        return ResponseData.ok();
    }

//    @ApiOperation("订单物品称重")
//    @GetMapping("weigh")
//    public ResponseData orderWeigh(
//            @ApiParam("订单号")@RequestParam String orderNo,
//            @ApiParam("称重重量")@RequestParam BigDecimal realWeight) {
////        orderService.orderWeighContrast(orderNo, realWeight, false,Constants.DEFAULT_USER);
//        return ResponseData.ok();
//    }

//    @ApiOperation("订单物品称重忽略重量差异")
//    @GetMapping("weighIgnore")
//    public ResponseData orderWeighIgnore(
//            @ApiParam("订单号")@RequestParam String orderNo,
//            @ApiParam("称重重量")@RequestParam BigDecimal realWeight) {
////        orderService.orderWeighContrast(orderNo, realWeight, true,Constants.DEFAULT_USER);
//        return ResponseData.ok();
//    }

//    @ApiOperation("订单打包材料提醒")
//    @GetMapping("package")
//    public ResponseData orderPackage(@ApiParam("订单号") @RequestParam String orderNo) {
//        return ResponseData.ok(orderService.orderPackage(orderNo));
//    }

//    @ApiOperation("订单称重数据加载")
//    @PostMapping("/weigh/loadGrid")
//    public ResponseData orderWeighLoadGrid(
//            @RequestBody LoadGrid loadGrid
//    ) {
//        return ResponseData.ok(orderService.orderWeighLoadGrid(loadGrid));
//    }
}
