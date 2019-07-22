package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.magic.card.wms.baseset.controller
 * 拣货单Controller
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/4 13:44
 * @since : 1.0.0
 */
@Api("拣货单相关操作")
@RestController
@RequestMapping(value = "pickBill", headers = "accept=application/json")
public class PickBillController {
    @Autowired
    private IPickingBillService pickingBillService;

    @ApiOperation("加载数据")
    @PostMapping("loadGrid")
    public ResponseData loadGrid(@RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(pickingBillService.loadGrid(loadGrid));
    }

    @ApiOperation("加载锁定拣货单数据")
    @GetMapping("lockPickBillData")
    public ResponseData lockPickBillData(@RequestParam String pickNo) {
        return ResponseData.ok(pickingBillService.pickBillLoadGrid(pickNo));
    }

    @ApiOperation("手动触发拣货单生成")
    @GetMapping("executeGenerator")
    public ResponseData executeGenerator() {
        pickingBillService.timingGenerator();
        return ResponseData.ok();
    }

    @ApiOperation("锁定拣货单")
    @GetMapping("lockPick")
    public ResponseData lockPick(@RequestParam String pickNo) {
        pickingBillService.pickLockProcess(pickNo, true);
        return ResponseData.ok();
    }

    @ApiOperation("解锁拣货单")
    @GetMapping("unlockPick")
    public ResponseData unlockPick(@RequestParam String pickNo) {
        pickingBillService.pickLockProcess(pickNo, false);
        return ResponseData.ok();
    }

    @ApiOperation("配货复核")
    @GetMapping("invoiceCheck")
    public ResponseData invoiceCheck(@RequestParam String pickNo, @RequestParam String commodityCode) {
        return ResponseData.ok(pickingBillService.checkInvoice(pickNo, commodityCode));
    }

    @ApiOperation("配货复核手动关闭")
    @GetMapping("invoiceCheckClose")
    public ResponseData invoiceCheckClose(@RequestParam String pickNo) {
        return ResponseData.ok(pickingBillService.checkInvoiceClose(pickNo, Constants.DEFAULT_USER));
    }
}
