package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.CommoditySkuDTO;
import com.magic.card.wms.baseset.service.ICommoditySkuService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * com.magic.card.wms.baseset.controller
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/20/020 14:16
 * @since : 1.0.0
 */
@Slf4j
@Api(value = "商品SKU 控制器", description = "商品SKU 控制器")
@RestController
@RequestMapping(value = "commoditySku", headers = "accept=application/json")
public class CommoditySkuController {

    @Autowired
    private ICommoditySkuService commoditySkuService;

    @ApiOperation(value = "新增商品SKU基本信息")
    @PostMapping("loadGrid")
    public ResponseData loadGrid( @ApiParam("搜索信息") @RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(commoditySkuService.loadGrid(loadGrid));
    }

    @ApiOperation(value = "新增商品SKU基本信息")
    @PostMapping("add")
    public ResponseData add(@RequestBody @Valid CommoditySkuDTO dto, BindingResult result) {
        commoditySkuService.add(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "修改商品SKU基本信息")
    @PostMapping("update")
    public ResponseData update(@RequestBody @Valid CommoditySkuDTO dto, BindingResult result) {
        commoditySkuService.update(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "删除商品SKU基本信息（物理删除）")
    @GetMapping("delete")
    public ResponseData delete(@ApiParam("主键ID")@RequestParam Long id) {
        return ResponseData.ok();
    }

    @PostMapping("excelImport")
    public ResponseData excelImport(@RequestParam MultipartFile[] commodityExcelFiles) {
        commoditySkuService.excelImport(commodityExcelFiles);
        return ResponseData.ok();
    }
}
