package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.invoice.InvoiceCheckOmitDTO;
import com.magic.card.wms.baseset.model.dto.invoice.OmitStokeDTO;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

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
    @Autowired
    private IMailPickingService mailPickingService;
    @Autowired
    private IStorehouseConfigService storehouseConfigService;

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
        pickingBillService.executorGenerator();
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
        return ResponseData.ok(pickingBillService.checkInvoiceClose(pickNo));
    }

    @ApiOperation("配货复核完毕")
    @GetMapping("invoiceCheckFinished")
    public ResponseData invoiceCheckFinished(@RequestParam String pickNo, @RequestParam String pickOperator) {
        pickingBillService.checkInvoiceFinished(pickNo, pickOperator);
        return ResponseData.ok();
    }

    @ApiOperation("配货单漏检")
    @PostMapping("invoiceCheckOmit")
    public ResponseData invoiceCheckOmit(@RequestBody @Valid InvoiceCheckOmitDTO invoiceCheckOmitDTO, BindingResult bindingResult) {
        pickingBillService.checkInvoiceOmit(invoiceCheckOmitDTO);
        return ResponseData.ok();
    }

    @ApiOperation("获取缺货商品库位信息")
    @PostMapping("omitStoke")
    public ResponseData invoiceOmitStoke(@RequestBody @Valid OmitStokeDTO omitStokeDTO) {
        return ResponseData.ok(storehouseConfigService.invoiceOmitStoke(omitStokeDTO));
    }

    @GetMapping("sendOrder")
    public ResponseData sendOrderToPost(@RequestParam String pickNo, @RequestParam String orderNo) throws UnsupportedEncodingException {
        mailPickingService.sendOrder(pickNo, orderNo);
        return ResponseData.ok();
    }
}
