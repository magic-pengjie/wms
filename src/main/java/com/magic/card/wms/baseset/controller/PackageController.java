package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.service.IMailPickingDetailService;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.common.annotation.RequestJsonParam;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.config.WmsBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * com.magic.card.wms.baseset.controller
 * 包裹Controller
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/22 10:04
 * @since : 1.0.0
 */
@Api("包裹操作API")
@RestController
@RequestMapping("package")
public class PackageController extends WmsBaseController {
    @Autowired
    private IMailPickingDetailService mailPickingDetailService;
    @Autowired
    private IMailPickingService mailPickingService;

    @GetMapping("details")
    public ResponseData details(@RequestParam String mailNo) {
       return ResponseData.ok(mailPickingService.details(mailNo));
    }

    @GetMapping("monitoringFlow")
    public ResponseData monitoringFlow() {
       return ResponseData.ok(mailPickingService.monitoringFlow());
    }

    @PostMapping("monitoringFlowDetails")
    public ResponseData monitoringFlowDetails(@RequestJsonParam String monitoringType, @RequestJsonParam LoadGrid loadGrid) {
        mailPickingService.monitoringFlowDetails(monitoringType, loadGrid);
        return ResponseData.ok(loadGrid);
    }

    /**
     * 包裹称重
     * @param mailNo 快递单号
     * @param realWeight 包裹真实重量
     * @return
     */
    @ApiOperation("包裹称重")
    @GetMapping("weigh")
    public ResponseData weigh(@RequestParam String mailNo, @RequestParam BigDecimal realWeight) {
        mailPickingService.packageWeigh(mailNo, realWeight, false);
        return ResponseData.ok();
    }

    @ApiOperation("忽略称重异常")
    @GetMapping("ignore")
    public ResponseData ignore(@RequestParam String mailNo, @RequestParam BigDecimal realWeight) {
        mailPickingService.packageWeigh(mailNo, realWeight, true);
        return ResponseData.ok();
    }

    @ApiOperation("包裹清单(完成)")
    @GetMapping("finishedList")
    public ResponseData finishedList(@RequestParam String mailNo, HttpServletRequest request) {
        return ResponseData.ok(mailPickingDetailService.packageFinishedList(mailNo));
    }

    @ApiOperation("未完成的包裹清单")
    @GetMapping("unfinishedList")
    public ResponseData unfinishedList(@RequestParam String mailNo) {
        return ResponseData.ok(mailPickingDetailService.packageUnfinishedList(mailNo));
    }
}
