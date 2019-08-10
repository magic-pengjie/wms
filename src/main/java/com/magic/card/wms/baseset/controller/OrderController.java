package com.magic.card.wms.baseset.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.dto.OrderUpdateDTO;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.exception.OperationException;
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
import org.apache.commons.io.IOUtils;
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
    public void generationPicking(@RequestParam String customerCode, @RequestParam(required = false, defaultValue = "20")Integer executeSize) {
        pickingBillService.triggerGenerator(customerCode, executeSize);
    }

    @ApiOperation("测试触发生配货单")
    @GetMapping("generationInvoices")
    public ResponseData generationInvoices(@RequestParam String[] pickNos) {
        return ResponseData.ok(pickingBillService.generatorInvoice(Constants.DEFAULT_USER, 1, pickNos));
    }

    @ApiOperation("订单物品称重")
    @GetMapping("weigh")
    public ResponseData orderWeigh(
            @ApiParam("订单号")@RequestParam String orderNo,
            @ApiParam("称重重量")@RequestParam BigDecimal realWeight) {
        orderService.orderWeighContrast(orderNo, realWeight, false,Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation("订单物品称重忽略重量差异")
    @GetMapping("weighIgnore")
    public ResponseData orderWeighIgnore(
            @ApiParam("订单号")@RequestParam String orderNo,
            @ApiParam("称重重量")@RequestParam BigDecimal realWeight) {
        orderService.orderWeighContrast(orderNo, realWeight, true,Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation("订单打包材料提醒")
    @GetMapping("package")
    public ResponseData orderPackage(@ApiParam("订单号") @RequestParam String orderNo) {
        return ResponseData.ok(orderService.orderPackage(orderNo));
    }

    @ApiOperation("订单称重数据加载")
    @PostMapping("/weigh/loadGrid")
    public ResponseData orderWeighLoadGrid(
            @RequestBody LoadGrid loadGrid
    ) {
        return ResponseData.ok(orderService.orderWeighLoadGrid(loadGrid));
    }
}
