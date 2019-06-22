package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.dto.StorehouseInfoDTO;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.baseset.service.IStorehouseInfoService;
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
 * @date : 2019/6/20/020 14:49
 * @since : 1.0.0
 */
@Slf4j
@Api(value = "库位设置前端控制器", description = "库位设置前端控制器")
@RestController
@RequestMapping(value = "storehouse", headers = "accept=application/json")
public class StorehouseInfoController {
    @Autowired
    private IStorehouseInfoService storehouseInfoService;

    @ApiOperation(value = "加载库位数据（可分页查询）")
    @PostMapping("loadGrid")
    public ResponseData loadGrid(@ApiParam("当前页数")@RequestBody LoadGrid loadGrid ) {
        return ResponseData.ok(storehouseInfoService.loadGrid(loadGrid));
    }

    @ApiOperation(value = "新增库位基本信息")
    @PostMapping("add")
    public ResponseData add(@RequestBody @Valid StorehouseInfoDTO dto, BindingResult result) {

        if (!storehouseInfoService.addStorehouseInfo(dto, Constants.DEFAULT_USER))
            return ResponseData.failed(ResultEnum.data_add_failed);

        return ResponseData.ok();
    }

    @ApiOperation(value = "修改库位基本信息")
    @PostMapping("update")
    public ResponseData update(@RequestBody @Valid StorehouseInfoDTO dto, BindingResult result) {

        if (!storehouseInfoService.updateStorehouseInfo(dto, Constants.DEFAULT_USER))
            return ResponseData.failed(ResultEnum.data_update_failed);

        return ResponseData.ok();
    }

    @ApiOperation(value = "删除库位基本信息(物理删除)")
    @GetMapping("delete")
    public ResponseData delete(@RequestParam Long id) {

        if (!storehouseInfoService.deleteById(id))
            return ResponseData.failed(ResultEnum.data_delete_failed);

        return ResponseData.ok();
    }
}
