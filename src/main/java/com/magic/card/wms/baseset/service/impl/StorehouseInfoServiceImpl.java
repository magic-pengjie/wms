package com.magic.card.wms.baseset.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.magic.card.wms.baseset.mapper.StorehouseInfoMapper;
import com.magic.card.wms.baseset.model.dto.BatchStorehouseDTO;
import com.magic.card.wms.baseset.model.dto.StorehouseInfoDTO;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.magic.card.wms.baseset.model.vo.ExcelStorehouseVO;
import com.magic.card.wms.baseset.service.IStorehouseInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.common.utils.PoUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Service
public class StorehouseInfoServiceImpl extends ServiceImpl<StorehouseInfoMapper, StorehouseInfo> implements IStorehouseInfoService {

    /**
     * 加载库位信息列表
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.generatorPage();
        EntityWrapper wrapper = new EntityWrapper<>();
        wrapper.eq("wsi.state", Constants.ACTIVITY_STATE);

        if (MapUtils.isNotEmpty(loadGrid.getSearch())) {

        }

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {

        } else {
            wrapper.orderBy("wdi.dict_name").orderBy("wsi.area_code").orderBy("wsi.store_code");
        }

        loadGrid.finallyResult(page, this.baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }

    @Override @Transactional
    public void add(StorehouseInfoDTO storehouseInfoDTO, String operator) {
        checkStorehouse(storehouseInfoDTO, false);
        StorehouseInfo storehouseInfo = new StorehouseInfo();
        PoUtil.add(storehouseInfoDTO, storehouseInfo, operator);

        if (this.baseMapper.insert(storehouseInfo) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    /**
     * 批量添加仓库库位信息
     *
     * @param batchStorehouseDTO
     */
    @Override
    public void batchAdd(BatchStorehouseDTO batchStorehouseDTO) {
        LinkedList<StorehouseInfo> storehouses = Lists.newLinkedList();
        LinkedList<String> storeCodes = Lists.newLinkedList();
        checkStoreArea(batchStorehouseDTO.getHouseCode(), batchStorehouseDTO.getAreaCode());
        for (int storeNo = batchStorehouseDTO.getStoreMaxNo(); storeNo <= batchStorehouseDTO.getStoreMaxNo(); storeNo++) {

            String number = RandomStringUtils.random(
                    batchStorehouseDTO.getStoreMaxNo().toString().length() - ("" + storeNo).length(),
                    '0'
            ) + storeNo;

            StorehouseInfo storehouse = new StorehouseInfo();
            BeanUtils.copyProperties(batchStorehouseDTO, storehouse);
            String storeCode = StringUtils.joinWith(
                    "-",
                    batchStorehouseDTO.getAreaCode(),
                    batchStorehouseDTO.getChannelNo()
            );

            if (StringUtils.isAnyBlank(batchStorehouseDTO.getTierNo(), batchStorehouseDTO.getRockNo())) {
                // 大区
                storeCode = StringUtils.joinWith("-", storeCode, number);
            } else {
                // 小区
                storeCode = StringUtils.joinWith(null,  batchStorehouseDTO.getRockNo(), batchStorehouseDTO.getTierNo(), number);
            }
            storeCodes.add(storeCode);
            storehouse.setStoreCode(storeCode);
            storehouses.add(storehouse);
        }
        PoUtil.batchAdd(Constants.DEFAULT_USER, storehouses);
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.in("store_code", storeCodes).ne("state", StateEnum.delete.getCode());

        if (selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.store_house_error, "库位编号已被占用");
        }

        insertBatch(storehouses);
    }

    /**
     * 停用库位
     *
     * @param ids
     */
    @Override
    public void stop(String... ids) {

    }

    @Override @Transactional
    public void update(StorehouseInfoDTO storehouseInfoDTO, String operator) {
        checkStorehouse(storehouseInfoDTO, true);
        StorehouseInfo storehouseInfo = new StorehouseInfo();
        PoUtil.update(storehouseInfoDTO, storehouseInfo, operator);

        if (this.baseMapper.updateById(storehouseInfo) < 1)
            throw OperationException.DATA_OPERATION_UPDATE;

    }

    /**
     * 数据删除
     *
     * @param id
     */
    @Override @Transactional
    public void delete(Long id) {

        if (this.baseMapper.deleteById(id) < 1) {
            throw OperationException.DATA_OPERATION_DELETE;
        }
    }

    /**
     * excel 导入库位信息
     *
     * @param excelFile
     */
    @Override
    public void excelImport(MultipartFile excelFile) {
        String fileName = excelFile.getOriginalFilename();

        if (fileName == null ) {
            throw OperationException.customException(ResultEnum.upload_file_inexistence);
        }
        if (!fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue()) && !fileName.toLowerCase().endsWith(ExcelTypeEnum.XLSX.getValue())) {
            throw OperationException.customException(ResultEnum.upload_file_suffix_err);
        }

        try {
            List<ExcelStorehouseVO> storehouses = EasyExcelUtil.readExcel(excelFile.getInputStream(), ExcelStorehouseVO.class, 1, 1);

            if (CollectionUtils.isNotEmpty(storehouses)) {
                for (ExcelStorehouseVO excelStorehouse: storehouses) {
                    // 移除空数据
                    if (StringUtils.isBlank(excelStorehouse.getStoreCode())) continue;

                    StorehouseInfo storehouse = new StorehouseInfo();
                    storehouse.setHouseCode(excelStorehouse.getHouseCode());
                    storehouse.setAreaCode(excelStorehouse.getAreaCode());
                    storehouse.setStoreCode(excelStorehouse.getStoreCode());
                    storehouse.setPriorityValue(excelStorehouse.getPriorityValue());
                    storehouse.setState(excelStorehouse.getState());
                    checkStoreArea(excelStorehouse.getHouseCode(), excelStorehouse.getAreaCode());
                    EntityWrapper wrapper = new EntityWrapper();
                    wrapper.eq("store_code", excelStorehouse.getStoreCode()).ne("state", StateEnum.delete.getCode());

                    if (selectCount(wrapper) > 0) {
                        throw OperationException.customException(ResultEnum.store_house_error, "库位编号已被占用");
                    }

                    PoUtil.add(storehouse, Constants.DEFAULT_USER);
                    insert(storehouse);
                }
            }

        } catch (IOException e) {
            throw OperationException.customException(ResultEnum.upload_file_suffix_err);
        }
    }

    /**
     * 仓储信息添加修改数据检查
     * @param storehouseInfoDTO
     * @param updateOperation
     * @throws BusinessException
     */
    private void checkStorehouse(StorehouseInfoDTO storehouseInfoDTO, Boolean updateOperation) {

        if (updateOperation) {
            PoUtil.checkId(storehouseInfoDTO.getId());
        }

        // 判断功能区 区域编号是否被占用
        checkStoreArea(storehouseInfoDTO.getHouseCode(), storehouseInfoDTO.getAreaCode());

        // 判断库位编号是否
        EntityWrapper wrapper = new EntityWrapper<>();
        wrapper.eq("store_code", storehouseInfoDTO.getStoreCode());

        if (updateOperation) {
            wrapper.ne("id", storehouseInfoDTO.getId());
        }

        // 确保数据是可用状态
        wrapper.eq("state", 1);

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.store_house_error, "库位编号已被占用");
         }

    }

    /**
     * 库区编号时候被占用
     * @param houseCode
     * @param areaCode
     */
    private void checkStoreArea(String houseCode, String areaCode) {
        EntityWrapper<StorehouseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("area_code", houseCode);
        wrapper.ne("house_code", areaCode);
        // 确保数据是可用状态
        wrapper.eq("state", 1);

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.store_house_error, "区域编号已被其他功能区占用");
        }
    }
}

