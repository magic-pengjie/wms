package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.DictInfoDTO;
import com.magic.card.wms.baseset.service.IDictInfoService;
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
 * @date : 2019/6/20/020 14:40
 * @since : 1.0.0
 */
@Slf4j
@Api(value = "系统字典前端控制器", description = "系统字典前端控制器")
@RestController
@RequestMapping(value = "dict", headers = "accept=application/json")
public class DictInfoController {
    @Autowired
    private IDictInfoService dictInfoService;

    @ApiOperation(value = "加载字典数据（可分页查询）")
    @PostMapping("loadGrid")
    public ResponseData loadGrid( @ApiParam("搜索信息") @RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(dictInfoService.loadGrid(loadGrid));
    }

    @ApiOperation("加载所有字典数据")
    @GetMapping("loadAll")
    public ResponseData loadAll() {
        return ResponseData.ok(dictInfoService.loadAll());
    }

    @ApiOperation(value = "新增字典数据基本信息")
    @PostMapping("add")
    public ResponseData add(@RequestBody @Valid DictInfoDTO dto, BindingResult result) {
        dictInfoService.add(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "修改字典数据基本信息")
    @PostMapping("update")
    public ResponseData update(@RequestBody @Valid DictInfoDTO dto, BindingResult result) {
        dictInfoService.update(dto, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @ApiOperation(value = "删除字典基本信息(物理删除)")
    @GetMapping("delete")
    public ResponseData delete(@RequestParam Long id) {
        dictInfoService.delete(id);
        return ResponseData.ok();
    }
}
