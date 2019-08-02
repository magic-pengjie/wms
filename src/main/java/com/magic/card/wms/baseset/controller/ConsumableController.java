package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.BatchConsumablesConfigDTO;
import com.magic.card.wms.baseset.service.ICommodityConsumablesConfigService;
import com.magic.card.wms.common.annotation.RequestJsonParam;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.config.WmsBaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * com.magic.card.wms.baseset.controller
 * 耗材设置
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/1 14:59
 * @since : 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("consumable")
public class ConsumableController extends WmsBaseController {
    @Autowired
    private ICommodityConsumablesConfigService consumablesConfigService;

    @PostMapping("batchDeleted")
    public ResponseData batchDeleted(@RequestJsonParam List<String> ids) {
        consumablesConfigService.deleteBatchIds(ids);
        return ResponseData.ok();
    }

    @PostMapping("batchConfig")
    public ResponseData batchConfig(@Valid @RequestBody BatchConsumablesConfigDTO batchConsumablesConfig, BindingResult bindingResult) {
        consumablesConfigService.batchConfig(batchConsumablesConfig);
        return ResponseData.ok();
    }

    @PostMapping("loadGrid")
    public ResponseData loadGrid(@RequestBody LoadGrid loadGrid) {
        consumablesConfigService.loadGrid(loadGrid);
        return ResponseData.ok(loadGrid);
    }
}
