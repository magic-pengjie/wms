package com.magic.card.wms.statistic.service.impl;


import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.magic.card.wms.check.mapper.CheckRecordMapper;
import com.magic.card.wms.check.model.dto.CheckRecordInfoDto;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.EasyExcelParams;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.PageInfoArgumentResolver;
import com.magic.card.wms.common.model.po.BasePageResponse;
import com.magic.card.wms.common.utils.BeanCopyUtil;
import com.magic.card.wms.common.utils.DateUtil;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.statistic.model.dto.*;
import com.magic.card.wms.statistic.service.StatisticsService;
import com.magic.card.wms.warehousing.mapper.PurchaseBillMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
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

    @Autowired
    private CheckRecordMapper checkRecordMapper;

    //查询入库报表
    @Override
    public BasePageResponse<ParchaseBillResponseDto> queryParchaseBill(ParchaseBillDto dto) {
        BasePageResponse<ParchaseBillResponseDto> pageResponse = new BasePageResponse<>();
        PageHelper.startPage(dto.getCurrent(),dto.getPageSize());
        List<ParchaseBillResponseDto> list  = purchaseBillMapper.queryPurchaseBillCountList(dto.getCustomerCode(),dto.getStartDate(),dto.getEndDate());
        if (!CollectionUtils.isEmpty(list)){
            pageResponse.setTotalCount(list.size());
            pageResponse.setData(list);

        }
        pageResponse.setPageCount(dto.getCurrent());
        log.info("===>> queryParchaseBill.response:{}",list);
        return pageResponse;

    }

    //导出入库报表excel
    @Override
    public void exportParchaseBillExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException {

        List<ParchaseBillResponseDto> list  = purchaseBillMapper.queryPurchaseBillCountList(dto.getCustomerCode(),dto.getStartDate(),dto.getEndDate());
        if(!CollectionUtils.isEmpty(list)){
            List<ParchaseBillResponseVO> excelVoList = BeanCopyUtil.copyList(list, ParchaseBillResponseVO.class);
            if (!CollectionUtils.isEmpty(excelVoList)) {
                EasyExcelParams excelParams = new EasyExcelParams();
                excelParams.setData(excelVoList);
                excelParams.setSheetName("入库报表");
                excelParams.setDataModelClazz(ParchaseBillResponseVO.class);
                excelParams.setRequest(request);
                excelParams.setResponse(response);
                excelParams.setExcelNameWithoutExt(DateUtil.getStringAllDate() + "入库报表");
                try {
                    EasyExcelUtil.exportExcel2007Format(excelParams);
                } catch (IOException e) {
                    throw new BusinessException(40013, "导出excel失败！");
                }
            }
        }
    }

    //查询出库报表
    @Override
    public BasePageResponse<OutStorehouseResponseDto> queryOutStorehouseList(ParchaseBillDto dto) {
        BasePageResponse<OutStorehouseResponseDto> pageResponse = new BasePageResponse<>();
        PageHelper.startPage(dto.getCurrent(),dto.getPageSize());
        List<OutStorehouseResponseDto> outStoresList = checkRecordMapper.queryOutStorehouseList(dto.getCustomerCode(), dto.getStartDate(), dto.getEndDate());
        if(!CollectionUtils.isEmpty(outStoresList)){
            pageResponse.setTotalCount(outStoresList.size());
            pageResponse.setData(outStoresList);
        }
        pageResponse.setPageCount(dto.getCurrent());
        return pageResponse;
    }


    //出库报表导出excel
    @Override
    public void exportOutStorehouseExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        List<OutStorehouseResponseDto> outStorehouseList = checkRecordMapper.queryOutStorehouseList(dto.getCustomerCode(), dto.getStartDate(), dto.getEndDate());
        if(!CollectionUtils.isEmpty(outStorehouseList)){
            List<OutStorehouseResponseVO> excelVoList = BeanCopyUtil.copyList(outStorehouseList,OutStorehouseResponseVO.class);
            if (!CollectionUtils.isEmpty(excelVoList)){
                EasyExcelParams excelParams = new EasyExcelParams();
                excelParams.setData(excelVoList);
                excelParams.setSheetName("出库报表");
                excelParams.setDataModelClazz(OutStorehouseResponseVO.class);
                excelParams.setRequest(request);
                excelParams.setResponse(response);
                excelParams.setExcelNameWithoutExt(DateUtil.getStringAllDate() + "出库报表");
                try {
                    EasyExcelUtil.exportExcel2007Format(excelParams);
                } catch (IOException e) {
                    throw new BusinessException(40013, "导出excel失败！");
                }
            }
        }
    }


    //查询库存报表
    @Override
    public BasePageResponse<StorehouseCountResponseDto> queryStorehouseCountList(ParchaseBillDto dto) {
        BasePageResponse<StorehouseCountResponseDto> pageResponse = new BasePageResponse<>();
        PageHelper.startPage(dto.getCurrent(),dto.getPageSize());
        List<StorehouseCountResponseDto> storeCountList = checkRecordMapper.queryStorehouseCountList(dto.getCustomerCode());
        if(!CollectionUtils.isEmpty(storeCountList)){
            pageResponse.setTotalCount(storeCountList.size());
            pageResponse.setData(storeCountList);
        }
        pageResponse.setPageCount(dto.getCurrent());
        return pageResponse;
    }

    //导出库存报表excel
    @Override
    public void exportStorehouseCountExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        List<StorehouseCountResponseDto> countResponseDtoList = checkRecordMapper.queryStorehouseCountList(dto.getCustomerCode());
        if(!CollectionUtils.isEmpty(countResponseDtoList)){
            List<StorehouseCountResponseVO> excelVoList = BeanCopyUtil.copyList(countResponseDtoList, StorehouseCountResponseVO.class);
            if (!CollectionUtils.isEmpty(excelVoList)){
                EasyExcelParams excelParams = new EasyExcelParams();
                excelParams.setData(excelVoList);
                excelParams.setSheetName("库存报表");
                excelParams.setDataModelClazz(StorehouseCountResponseVO.class);
                excelParams.setRequest(request);
                excelParams.setResponse(response);
                excelParams.setExcelNameWithoutExt(DateUtil.getStringAllDate() + "库存报表");
                try {
                    EasyExcelUtil.exportExcel2007Format(excelParams);
                } catch (IOException e) {
                    throw new BusinessException(40013, "导出excel失败！");
                }
            }
        }
    }

    //库位使用报表
    @Override
    public BasePageResponse<StorehouseUsedResponseDto> queryStorehouseUsedList(ParchaseBillDto dto) {
        BasePageResponse<StorehouseUsedResponseDto> pageResponse = new BasePageResponse<>();
        PageHelper.startPage(dto.getCurrent(),dto.getPageSize());
        List<StorehouseUsedResponseDto> storeUsedlist = checkRecordMapper.queryStorehouseUsedList(dto.getCustomerCode());
        if(!CollectionUtils.isEmpty(storeUsedlist)){
            pageResponse.setTotalCount(storeUsedlist.size());
            pageResponse.setData(storeUsedlist);
        }
        pageResponse.setPageCount(dto.getCurrent());
        return null;
    }

    //库位使用报表导出excel
    @Override
    public void exportStorehouseUsedExcel(ParchaseBillDto dto, HttpServletRequest request, HttpServletResponse response) throws BusinessException{
        List<StorehouseUsedResponseDto> usedResponseDtos = checkRecordMapper.queryStorehouseUsedList(dto.getCustomerCode());
        if(!CollectionUtils.isEmpty(usedResponseDtos)){
            List<StorehouseUsedResponseVO> excelVoList = BeanCopyUtil.copyList(usedResponseDtos, StorehouseUsedResponseVO.class);
            if (!CollectionUtils.isEmpty(excelVoList)){
                EasyExcelParams excelParams = new EasyExcelParams();
                excelParams.setData(excelVoList);
                excelParams.setSheetName("库位使用报表");
                excelParams.setDataModelClazz(StorehouseUsedResponseVO.class);
                excelParams.setRequest(request);
                excelParams.setResponse(response);
                excelParams.setExcelNameWithoutExt(DateUtil.getStringAllDate() + "库位使用报表");
                try {
                    EasyExcelUtil.exportExcel2007Format(excelParams);
                } catch (IOException e) {
                    throw new BusinessException(40013, "导出excel失败！");
                }

            }
        }

    }

    //库存报表，库位使用报表， 查询库位信息List
    @Override
    public BasePageResponse<CheckRecordInfoDto> queryStoreDetailsInfo(ParchaseBillDto dto) {
        //调用盘点查询库位接口
        return null;
    }
}
