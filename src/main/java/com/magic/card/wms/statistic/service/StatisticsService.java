package com.magic.card.wms.statistic.service;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.statistic.model.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 	统计分析 服务类
 * </p>
 *
 * @author zhouhao
 * @since 2019年8月6日19:33:34
 */
public interface StatisticsService {

    /**
     * 入库报表查询
     */
    public List<ParchaseBillResponseDto> queryParchaseBill(ParchaseBillDto dto);

    /**
     * 导出入库报表明细excel
     */
    public void exportParchaseBillExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response)throws BusinessException;

    /**
     * 出库报表查询
     */
    public List<OutStorehouseResponseDto> queryOutStorehouseList(ParchaseBillDto dto);

    /**
     * 出库报表 明细导出excel
     */
    public void exportOutStorehouseExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response)throws BusinessException;

    /**
     * 库存报表
     */
    public List<StorehouseCountResponseDto> queryStorehouseCountList(ParchaseBillDto dto);

    /**
     * 库存报表明细 导出excel
     */
    public void exportStorehouseCountExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response)throws BusinessException;

    /**
     * 库存使用查询
     */
    public List<StorehouseUsedResponseDto> queryStorehouseUsedList(ParchaseBillDto dto);

    /**
     * 库存使用查询
     */
    public void exportStorehouseUsedExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response)throws BusinessException;


}
