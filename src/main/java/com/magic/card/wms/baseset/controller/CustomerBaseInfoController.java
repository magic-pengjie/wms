package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.BatchBindCommodityDTO;
import com.magic.card.wms.baseset.model.dto.CustomerBaseInfoDTO;
import com.magic.card.wms.baseset.service.ICustomerBaseInfoService;
import com.magic.card.wms.common.annotation.RequestJsonParam;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * com.magic.card.wms.baseset.controller
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/20/020 14:31
 * @since : 1.0.0
 */
@Slf4j
@Api(value = "客户前端控制器", description = "客户前端控制器")
@RestController
@RequestMapping(value = "customer", headers = "accept=application/json")
public class CustomerBaseInfoController {

    @Autowired
    private ICustomerBaseInfoService customerBaseInfoService;

    @ApiOperation(value = "加载客户数据（可分页查询）")
    @PostMapping("loadGrid")
    public ResponseData loadGrid(@ApiParam("搜索信息") @RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(customerBaseInfoService.loadGrid(loadGrid));
    }
    @ApiOperation(value = "加载所有客户数据")
    @GetMapping("comboGrid")
    public ResponseData comboGrid() {
        LoadGrid loadGrid = new LoadGrid();
        loadGrid.setCurrent(1);
        loadGrid.setPageSize(1000);
        customerBaseInfoService.loadGrid(loadGrid);
        return ResponseData.ok(loadGrid.getRows());
    }

    @ApiOperation(value = "新增客户基本信息")
    @PostMapping("add")
    public ResponseData add(@RequestBody @Valid CustomerBaseInfoDTO dto, BindingResult result) {
        customerBaseInfoService.add(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "修改品牌基本信息")
    @PostMapping("update")
    public ResponseData update(@RequestBody @Valid CustomerBaseInfoDTO dto, BindingResult result) {
        customerBaseInfoService.update(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "删除品牌基本信息(标记删除)")
    @GetMapping("updateDelete")
    public ResponseData updateDelete(@RequestParam Long id) {
        customerBaseInfoService.delete(id, Constants.DEFAULT_USER, false);
        return ResponseData.ok();
    }

    @ApiOperation("加载客户的所有产品")
    @PostMapping("loadCommodities/{customerId}")
    public ResponseData loadCustomerCommodities(
            @ApiParam("客户ID") @PathVariable(required = false) String customerId,
            @ApiParam("搜索信息")@RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(customerBaseInfoService.loadCustomerCommodities(loadGrid, customerId));
    }

    @ApiOperation("加载客户未关联的商品数据")
    @PostMapping("notBindCommodities")
    public ResponseData comboGridNotBindCommodities(@ApiParam("客户Code") @RequestJsonParam String customerCode, @RequestJsonParam LoadGrid loadGrid) {
        return ResponseData.ok(customerBaseInfoService.comboGridNotBindCommodities(customerCode, loadGrid));
    }

    @ApiOperation("批量绑定商品")
    @PostMapping("batchBindCommodities")
    public ResponseData batchBindCommodities(@Valid @RequestBody BatchBindCommodityDTO batchBindCommodity, BindingResult bindingResult) {
        customerBaseInfoService.batchBindCommodity(batchBindCommodity);
        return ResponseData.ok();
    }
    @ApiOperation("批量解除绑定")
    @PostMapping("batchUnbindCommodities")
    public ResponseData batchUnbindCommodities(@RequestJsonParam List<String> ids) {
        customerBaseInfoService.batchUnbindCommodity(ids);
        return ResponseData.ok();
    }
}
