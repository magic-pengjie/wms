package com.magic.card.wms.statistic.contorller;


import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.statistic.model.dto.ParchaseBillDto;
import com.magic.card.wms.statistic.model.dto.ParchaseBillResponseDto;
import com.magic.card.wms.statistic.service.StatisticsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 统计分析Controller
 * </p>
 *
 * @author zhouhao
 * @since 2019年8月6日
 */
@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {


    @Autowired
    private StatisticsService statisticsService;


    /**
     * 查询：可按月、年、商家、查询入库信息，明细可导出
     * @param dto
     * @return
     */
    @ApiOperation(value = "入库报表查询", notes = "入库报表查询")
    @RequestMapping(value = "/query/purchaseBill", method = RequestMethod.GET)
    public ResponseData queryPurchaseBill(@Valid ParchaseBillDto dto) {
        log.info("===>> 入库报表查询queryPurchaseBill.request:{}", dto);
        List<ParchaseBillResponseDto> responseDtoList = statisticsService.queryParchaseBill(dto);
        return ResponseData.ok(responseDtoList);
    }

    @ApiOperation(value = "入库报表导出excel", notes = "入库报表导出excel")
    @RequestMapping(value = "/export/purchaseBillExcel", method = RequestMethod.GET)
    public void exportPurchaseBill(@Valid ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        statisticsService.exportParchaseBillExcel(dto,request,response);
    }

    /**
     * 查询；同上；查询年、月各商家的出库详细信息
     * @param dto
     * @return
     */
    @ApiOperation(value = "出库报表查询", notes = "出库报表查询")
    @RequestMapping(value = "/query/outStorehouse", method = RequestMethod.GET)
    public ResponseData queryOutStorehouseList(@Valid ParchaseBillDto dto) {

        return ResponseData.ok(statisticsService.queryOutStorehouseList(dto));
    }

    @ApiOperation(value = "出库报表导出excel", notes = "出库报表导出excel")
    @RequestMapping(value = "/export/outStorehouseExcel", method = RequestMethod.GET)
    public void exportOutStorehouse(@Valid ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        statisticsService.exportOutStorehouseExcel(dto,request,response);
    }

    /**
     * 库存报表 查询：按商家，查询商品库存，可用库存，占用库存，不可用库存（残次品）各个商品库存汇总显示。点击“库存数量“可查看库位相关信息
     * @param dto
     * @return
     */
    @ApiOperation(value = "库存报表查询", notes = "库存报表查询")
    @RequestMapping(value = "/query/storehouse", method = RequestMethod.GET)
    public ResponseData queryStorehouseCount(@Valid ParchaseBillDto dto) {

        return ResponseData.ok(statisticsService.queryStorehouseCountList(dto));
    }

    @ApiOperation(value = "库存报表导出excel", notes = "库存报表导出excel")
    @RequestMapping(value = "/export/outStoreInfoExcel", method = RequestMethod.GET)
    public void exportStorehouseCountExcel(@Valid ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
            statisticsService.exportStorehouseCountExcel(dto,request,response);
    }

    /**
     * 库位使用报表；各商家分配的库位，使用情况，共多个，已用多少个，剩余多少个；点击数量可查询库位相关明细
     * @param dto
     * @return
     */
    @ApiOperation(value = "库位使用报表查询", notes = "库位使用报表查询")
    @RequestMapping(value = "/query/storeUsed", method = RequestMethod.GET)
    public ResponseData queryStorehouseUsed(@Valid ParchaseBillDto dto) {

        return ResponseData.ok(statisticsService.queryStorehouseUsedList(dto));
    }

    @ApiOperation(value = "库位使用报表导出excel", notes = "库位使用报表导出excel")
    @RequestMapping(value = "/export/storeUsedExcel", method = RequestMethod.GET)
    public void exportStorehouseUsed(@Valid ParchaseBillDto dto,HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        statisticsService.exportStorehouseUsedExcel(dto,request,response);
    }

}
