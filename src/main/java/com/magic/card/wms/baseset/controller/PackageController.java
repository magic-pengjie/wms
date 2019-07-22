package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.service.IMailPickingDetailService;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.config.WmsBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     * 包裹称重
     * @param mailNo 快递单号
     * @param realWeight 包裹真实重量
     * @return
     */
    @ApiOperation("包裹称重")
    @RequestMapping("weigh")
    public ResponseData weigh(String mailNo, BigDecimal realWeight) {
        mailPickingService.packageWeigh(mailNo, realWeight, false);
        return ResponseData.ok();
    }

    @ApiOperation("忽略称重异常")
    @RequestMapping("ignore")
    public ResponseData ignore(String mailNo, BigDecimal realWeight) {
        mailPickingService.packageWeigh(mailNo, realWeight, true);
        return ResponseData.ok();
    }

    @ApiOperation("包裹清单(完成)")
    @RequestMapping("finishedList")
    public ResponseData finishedList(String mailNo) {
        return ResponseData.ok(mailPickingDetailService.packageFinishedList(mailNo));
    }

    @ApiOperation("未完成的包裹清单")
    @RequestMapping("unfinishedList")
    public ResponseData unfinishedList(String mailNo) {
        return ResponseData.ok(mailPickingDetailService.packageUnfinishedList(mailNo));
    }
}
