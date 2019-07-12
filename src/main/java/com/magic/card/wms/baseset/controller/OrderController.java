package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * com.magic.card.wms.baseset.controller
 * 导入订单前端控制器
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 16:35
 * @since : 1.0.0
 */
@Api("订单导入系统控制器")
@RestController
@RequestMapping(value = "order", headers = "accept=application/json")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPickingBillService pickingBillService;

    @PostMapping("loadGrid")
    public ResponseData loadGrid(@RequestBody LoadGrid loadGrid) {
       return ResponseData.ok(orderService.loadGrid(loadGrid));
    }

    @GetMapping("loadOrderCommodityGrid")
    public ResponseData loadOrderCommodityGrid(@RequestParam String orderNo) {
       return ResponseData.ok(orderService.loadOrderCommodityGrid(orderNo));
    }

    @ApiOperation("订单导入")
    @PostMapping("import")
    public ResponseData importOrder(@RequestBody @ApiParam("订单信息")@Valid OrderInfoDTO orderInfoDTO, BindingResult bindingResult) {
        this.orderService.importOrder(orderInfoDTO, Constants.DEFAULT_USER);
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

    @ApiOperation("配货复检")
    @GetMapping("invoiceCheck")
    public ResponseData invoiceCheck(@RequestParam String pickNo, @RequestParam String commodityCode) {
        return ResponseData.ok(pickingBillService.checkInvoice(pickNo, commodityCode, Constants.DEFAULT_USER));
    }

    @ApiOperation("配货复检手动关闭")
    @GetMapping("invoiceCheckClose")
    public ResponseData invoiceCheckClose(@RequestParam String pickNo) {
        return ResponseData.ok(pickingBillService.checkInvoiceClose(pickNo, Constants.DEFAULT_USER));
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
