package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.common.model.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * 系统主页控制器
 * @author PENGJIE
 * @date 2019年8月13日
 */
@Slf4j
@RestController
@RequestMapping("/index")
@Api(value = "系统主页控制器", description = "系统主页控制器")
public class IndexController {
    @Autowired
    private IOrderService orderService;

    /**
     * 订单量统计
     * @param loadGrid
     * @return
     */
    @ApiOperation(value = "订单量统计", notes = "订单量统计")
    @GetMapping("/orderStatistics")
    public ResponseData loadGrid(@RequestParam String orderDate) {
       return ResponseData.ok(orderService.orderStatistics(orderDate));
    }

   
}
