package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.CommodityInfoDTO;
import com.magic.card.wms.baseset.model.dto.CommoditySkuDTO;
import com.magic.card.wms.baseset.service.ICommodityConsumablesConfigService;
import com.magic.card.wms.baseset.service.ICommodityInfoService;
import com.magic.card.wms.baseset.service.ICommoditySkuService;
import com.magic.card.wms.common.exception.BusinessException;
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

import javax.validation.Valid;

/**
 * com.magic.card.wms.baseset.controller
 * 产品管理前端控制器
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/20/020 10:17
 * @since : 1.0.0
 */
@Slf4j
@Api(value = "产品管理前端控制器", description = "产品管理前端控制器")
@RestController
@RequestMapping(value = "commodity", headers = "accept=application/json")
public class CommodityInfoController {

    @Autowired
    private ICommodityInfoService commodityInfoService;

    @Autowired
    private ICommodityConsumablesConfigService commodityConsumablesConfigService;

    @ApiOperation(value = "新增商品-客户的关系")
    @PostMapping("add")
    public ResponseData addCommodityInfo(@RequestBody @Valid CommodityInfoDTO dto, BindingResult result) {

        if (!commodityInfoService.addCommodityInfo(dto, Constants.DEFAULT_USER))
            return ResponseData.failed(ResultEnum.data_add_failed);

        return ResponseData.ok();
    }

    @ApiOperation("修改商品-客户的关系")
    @PostMapping("update")
    public ResponseData updateCommodityInfo(@RequestBody @Valid CommodityInfoDTO dto, BindingResult result) {

        if (!commodityInfoService.updateCommodityInfo(dto, Constants.DEFAULT_USER))
            return ResponseData.failed(ResultEnum.data_update_failed);

        return ResponseData.ok();
    }

    @ApiOperation("删除商品-客户的关系（物理删除）")
    @GetMapping("delete")
    public ResponseData delete(@RequestParam @ApiParam("数据标识") String id) {

        if (!commodityInfoService.deleteById(id))
            return ResponseData.failed(ResultEnum.data_add_failed);

        return ResponseData.ok();
    }

    @ApiOperation("耗材关系列表")
    @PostMapping("consumables/load")
    public ResponseData loadGridCC(@RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(commodityConsumablesConfigService.loadGrid(loadGrid));
    }

}
