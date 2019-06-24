package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.DictInfoMapper;
import com.magic.card.wms.baseset.model.dto.DictInfoDTO;
import com.magic.card.wms.baseset.model.po.DictInfo;
import com.magic.card.wms.baseset.service.IDictInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.po.PoUtils;
import com.magic.card.wms.common.utils.WrapperUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表（维护商品类型、商品属性等） 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
@Service
public class DictInfoServiceImpl extends ServiceImpl<DictInfoMapper, DictInfo> implements IDictInfoService {
    /**
     * 默认提供的Columns
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();
    static {
        defaultColumns.put("id", "wdi.id");
        defaultColumns.put("dictCode", "wdi.dict_code");
        defaultColumns.put("dictName", "wdi.dict_name");
        defaultColumns.put("parentDictCode", "wdi.dict_code_p");
        defaultColumns.put("parentDictName", "wdi.dict_name_p");
    }

    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        //TODO 字典常量LoadGrid
        Page page = loadGrid.page();
        EntityWrapper wrapper = new EntityWrapper<>();
        wrapper.eq("wdi.state", 1);
        WrapperUtil.searchSet(wrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.orderSet(wrapper, defaultColumns, loadGrid.getOrder());
        } else {
            wrapper.orderBy("wdi.dict_name");
        }

        loadGrid.finallyResult(page, this.baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }

    @Override
    public LoadGrid loadChild(String dictCode) {
        //TODO 字典常量LoadChile
        return null;
    }

    @Override @Transactional
    public void add(DictInfoDTO dictInfoDTO, String operator) {
        // 判断当前添加数据 dict_code 是否已经存在
        checkDict(null, dictInfoDTO.getDictCode(), false);
        DictInfo dictInfo = new DictInfo();
        PoUtils.add(dictInfoDTO, dictInfo, operator);

        if (this.baseMapper.insert(dictInfo) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    @Override @Transactional
    public void update(DictInfoDTO dictInfoDTO, String operator) {
        checkDict(dictInfoDTO.getId(), dictInfoDTO.getDictCode(), true);
        DictInfo dictInfo = new DictInfo();
        PoUtils.update(dictInfoDTO, dictInfo, operator);

        if (this.baseMapper.updateById(dictInfo) < 1)
            throw OperationException.DATA_OPERATION_UPDATE;

    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Override @Transactional
    public void delete(Long id) {

        if (this.baseMapper.deleteById(id) < 1)
            throw OperationException.DATA_OPERATION_DELETE;

    }

    /**
     * 检测dict_code 是否已经存在数据库
     * @param id
     * @param dictCode
     * @param updateOperation 是否是更新操作
     */
    private void checkDict(Long id, String dictCode, Boolean updateOperation) {
        EntityWrapper<DictInfo> dictInfoEntityWrapper = new EntityWrapper<>();
        dictInfoEntityWrapper.eq("dict_code", dictCode);
        // 是否为更新数据时检查
        if (updateOperation) {
            // 检测 ID 是否异常
            if (id == null || id == 0l) {
                throw new BusinessException(ResultEnum.req_params_error.getCode(), ResultEnum.req_params_error.getMsg());
            }

            dictInfoEntityWrapper.ne("id", id);
        }
        // 判断当前添加数据 dict_code 是否已经存在
        if (this.selectCount(dictInfoEntityWrapper) > 0) {
            throw new BusinessException(ResultEnum.dist_exist.getCode(), ResultEnum.dist_exist.getMsg());
        }
    }
}
