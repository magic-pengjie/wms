package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.service.IExpressNumberService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ExpressKeyEnum;
import com.magic.card.wms.config.WmsBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * com.magic.card.wms.baseset.controller
 * 快递单号前端控制器
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/4 11:19
 * @since : 1.0.0
 */
@RestController
@RequestMapping("expressNumber")
public class ExpressNumberController extends WmsBaseController {
    @Autowired
    private IExpressNumberService expressNumberService;

    @GetMapping("getExpressNumber")
    public ResponseData getExpressNumber() {
        return ResponseData.ok(expressNumberService.getExpressNumber(ExpressKeyEnum.YZPK.getCode()));
    }

    @GetMapping("batchAdd")
    public ResponseData batchAdd(@RequestParam Long startNumber, @RequestParam Long endNumber) {
        expressNumberService.batchAddExpressNumber(ExpressKeyEnum.YZPK.getCode(), startNumber, endNumber);
        return ResponseData.ok();
    }

    @PostMapping("loadGrid")
    public ResponseData loadGrid(@RequestBody LoadGrid loadGrid, BindingResult bindingResult) {
        expressNumberService.loadGrid(loadGrid);
        return ResponseData.ok(loadGrid);
    }

    @GetMapping("available")
    public ResponseData available() {
        return ResponseData.ok(expressNumberService.expressNumberAvailable());
    }
}
