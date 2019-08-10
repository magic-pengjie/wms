package com.magic.card.wms.baseset.controller;

import com.magic.card.wms.baseset.model.dto.split.SplitPackageRuleDTO;
import com.magic.card.wms.baseset.service.ISplitPackageRuleService;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.config.WmsBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * com.magic.card.wms.baseset.controller
 * 拆包规则前端控制器
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/8 14:58
 * @since : 1.0.0
 */
@RestController
@Api(value = "拆包规则控制器", description = "拆包规则控制器")
@RequestMapping("splitRule")
public class SplitPackageRuleController extends WmsBaseController {
    @Autowired
    private ISplitPackageRuleService splitPackageRuleService;

    @ApiOperation("创建拆包规则")
    @PostMapping("generate")
    public ResponseData generateRule(@Valid @RequestBody SplitPackageRuleDTO splitPackageRule, BindingResult bindingResult) {
        splitPackageRuleService.generateRule(splitPackageRule);
        return ResponseData.ok();
    }
}
