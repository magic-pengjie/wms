package com.magic.card.wms.report.controller;

import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.report.model.dto.ExpressFeeConfigDTO;
import com.magic.card.wms.report.service.ExpressFeeConfigService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * com.magic.card.wms.report.controller
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/11 19:13
 * @since : 1.0.0
 */
@Api("快递费配置")
@RestController
@RequestMapping("expressFeeConfig")
public class ExpressFeeConfigController {
    @Autowired
    private ExpressFeeConfigService expressFeeConfigService;

    @PostMapping("add")
    public ResponseData add(@Valid @RequestBody ExpressFeeConfigDTO expressFeeConfigDTO) {
        expressFeeConfigService.add(expressFeeConfigDTO, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }

    @PostMapping("loadGrid")
    public ResponseData loadGrid(@RequestBody LoadGrid loadGrid) {
        return ResponseData.ok(expressFeeConfigService.loadGrid(loadGrid));
    }

    @PostMapping("update")
    public ResponseData update(@Valid @RequestBody ExpressFeeConfigDTO expressFeeConfigDTO) {
        expressFeeConfigService.update(expressFeeConfigDTO, Constants.DEFAULT_USER);
        return ResponseData.ok();
    }
}
