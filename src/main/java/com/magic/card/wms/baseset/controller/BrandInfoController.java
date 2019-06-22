package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.BrandInfoDTO;
import com.magic.card.wms.baseset.service.IBrandInfoService;
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
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/20/020 14:24
 * @since : 1.0.0
 */
@Slf4j
@Api(value = "品牌前端控制器", description = "品牌前端控制器")
@RestController
@RequestMapping(value = "brand", headers = "accept=application/json")
public class BrandInfoController {

    @Autowired
    private IBrandInfoService brandInfoService;

    @ApiOperation(value = "加载品牌数据（可分页查询）")
    @PostMapping("loadGrid")
    public ResponseData loadGrid(@ApiParam("搜索信息") @RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(brandInfoService.loadGrid(loadGrid));
    }

    @ApiOperation(value = "新增品牌基本信息")
    @PostMapping("add")
    public ResponseData add(@RequestBody @Valid BrandInfoDTO dto, BindingResult result) {

        if (!brandInfoService.addBrandInfo(dto, Constants.DEFAULT_USER))
            return ResponseData.failed(ResultEnum.data_add_failed);

        return ResponseData.ok();
    }

    @ApiOperation(value = "修改品牌基本信息")
    @PostMapping("update")
    public ResponseData update(@RequestBody @Valid BrandInfoDTO dto, BindingResult result) {

        if (!brandInfoService.updateBrandInfo(dto, Constants.DEFAULT_USER))
            return ResponseData.failed(ResultEnum.data_update_failed);

        return ResponseData.ok();
    }

    @ApiOperation(value = "删除品牌基本信息(标记删除)")
    @GetMapping("updateDelete")
    public ResponseData updateDelete(@RequestParam Long id) {

        if (!brandInfoService.deleteBrandInfo(id, Constants.DEFAULT_USER, false))
            return ResponseData.failed(ResultEnum.data_delete_failed);

        return ResponseData.ok();
    }
}
