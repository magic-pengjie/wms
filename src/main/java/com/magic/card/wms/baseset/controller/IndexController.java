package com.magic.card.wms.baseset.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.dto.OrderUpdateDTO;
import com.magic.card.wms.baseset.model.vo.ExcelOrderImport;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.EasyExcelParams;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

/***
 * 系统主页控制器
 * @author PENGJIE
 * @date 2019年8月13日
 */
@Slf4j
@RestController
@RequestMapping("/index")
@Api(value = "系统主页控制器", description = "系统主页控制器")
public class IndexController {
    @Autowired
    private IOrderService orderService;

    /**
     * 订单量统计
     * @param loadGrid
     * @return
     */
    @ApiOperation(value = "订单量统计", notes = "订单量统计")
    @GetMapping("/orderStatistics")
    public ResponseData loadGrid(@RequestParam String orderDate) {
       return ResponseData.ok(orderService.orderStatistics(orderDate));
    }

   
}
