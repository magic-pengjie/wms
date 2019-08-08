package com.magic.card.wms.statistic.service.impl;


import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.EasyExcelParams;
import com.magic.card.wms.common.utils.BeanCopyUtil;
import com.magic.card.wms.common.utils.DateUtil;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.statistic.model.dto.*;
import com.magic.card.wms.statistic.service.StatisticsService;
import com.magic.card.wms.warehousing.mapper.PurchaseBillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  	服务实现类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@Service
public class StatisticsServceImpl implements StatisticsService {


    @Autowired
    private PurchaseBillMapper purchaseBillMapper;

    @Override
    public List<ParchaseBillResponseDto> queryParchaseBill(ParchaseBillDto dto) {

        List<ParchaseBillResponseDto> list  = purchaseBillMapper.queryPurchaseBillCountList(dto.getCustomerCode(),dto.getStartDate(),dto.getEndDate());
        log.info("===>> queryParchaseBill.response:{}",list);
        return list;

    }

    @Override
    public void exportParchaseBillExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException {

        List<ParchaseBillResponseDto> list  = purchaseBillMapper.queryPurchaseBillCountList(dto.getCustomerCode(),dto.getStartDate(),dto.getEndDate());
        List<ParchaseBillResponseVO> excelVoList = BeanCopyUtil.copyList(list, ParchaseBillResponseVO.class);
        if(!CollectionUtils.isEmpty(list)){
            EasyExcelParams excelParams = new EasyExcelParams();
            excelParams.setData(excelVoList);
            excelParams.setSheetName("入库报表");
            excelParams.setDataModelClazz(ParchaseBillResponseVO.class);
            excelParams.setRequest(request);
            excelParams.setResponse(response);
            excelParams.setExcelNameWithoutExt(DateUtil.getStringAllDate()+"入库报表");
            try {
                EasyExcelUtil.exportExcel2007Format(excelParams);
            } catch (IOException e) {
                throw new BusinessException(40013,"导出excel失败！");
            }
        }
    }

    @Override
    public List<OutStorehouseResponseDto> queryOutStorehouseList(ParchaseBillDto dto) {
        return null;
    }

    @Override
    public void exportOutStorehouseExcel(ParchaseBillDto dto) {

    }

    @Override
    public List<StorehouseCountResponseDto> queryStorehouseCountList(ParchaseBillDto dto) {
        return null;
    }

    @Override
    public void exportStorehouseCountExcel(ParchaseBillDto dto) {

    }

    @Override
    public List<StorehouseUsedResponseDto> queryStorehouseUsedList(ParchaseBillDto dto) {
        return null;
    }

    @Override
    public void exportStorehouseUsedExcel(ParchaseBillDto dto) {

    }
}
