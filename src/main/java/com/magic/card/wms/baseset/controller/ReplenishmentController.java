package com.magic.card.wms.baseset.controller;

import com.google.common.base.CaseFormat;
import com.magic.card.wms.baseset.model.dto.CommodityReplenishmentDTO;
import com.magic.card.wms.baseset.service.ICommodityReplenishmentService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.config.WmsBaseController;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * com.magic.card.wms.baseset.controller
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/31 11:07
 * @since : 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("replenishment")
@Api(value = "补货通知前端控制器", description = "补货通知前端控制器")
public class ReplenishmentController extends WmsBaseController {
    @Autowired
    private ICommodityReplenishmentService replenishmentService;

    @PostMapping("loadGrid")
    public ResponseData loadGrid(@Valid @RequestBody LoadGrid loadGrid) {
        replenishmentService.loadGrid(loadGrid);
        return ResponseData.ok(loadGrid);
    }

    @GetMapping("loadStorehouse")
    public ResponseData loadStorehouse(@RequestParam String replenishmentNo) {
        return ResponseData.ok(replenishmentService.loadStorehouse(replenishmentNo));
    }

    @PostMapping("update")
    public ResponseData update(@Valid @RequestBody CommodityReplenishmentDTO commodityReplenishmentDTO) {
        replenishmentService.replenishmentFinished(commodityReplenishmentDTO);
        return ResponseData.ok();
    }
}
