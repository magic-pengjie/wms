package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
public class ImportOrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPickingBillService pickingBillService;

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
}
