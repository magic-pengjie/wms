package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.magic.card.wms.baseset.mapper.DictInfoMapper;
import com.magic.card.wms.baseset.model.dto.DictInfoDTO;
import com.magic.card.wms.baseset.model.po.DictInfo;
import com.magic.card.wms.baseset.service.IDictInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.po.PoUtils;
import com.magic.card.wms.common.utils.WrapperUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * 加载所有数据
     *
     * @return
     */
    @Override
    public Map loadAll() {
        // 获取所有数据
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state", Constants.ACTIVITY_STATE);
        wrapper.orderBy("dict_code_p");
        List<DictInfo> dictInfos = this.selectList(wrapper);
        //获取一级常量
        Map<String, List> dictThree = Maps.newLinkedHashMap();
        dictInfos.stream()
                .filter(dictInfo -> {
                   boolean flag = StringUtils.isBlank(dictInfo.getDictCodeP());

                   if (flag) {
                       LinkedList<Object> list = Lists.newLinkedList();
                       dictThree.put(dictInfo.getDictCode(), list);
                   }

                   return !flag;
                })
                .filter(dictInfo -> {
                    // 二级常量
                    boolean flag = dictThree.containsKey(dictInfo.getDictCodeP());

                    if (!flag) {
                        LinkedList<Object> list = Lists.newLinkedList();
                        dictThree.put(dictInfo.getDictCode(), list);
                    } else {
                        LinkedHashMap<String, Object> map = Maps.newLinkedHashMap();
                        map.put("id", dictInfo.getId());
                        map.put("code", dictInfo.getDictCode());
                        map.put("name", dictInfo.getDictName());
                        dictThree.get(dictInfo.getDictCodeP()).add(map);
                    }

                    return flag;
                })
                .collect(Collectors.toList());

        return dictThree;
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

    private void bulidDictThree(DictInfo dictInfo, Map<String, List> dictThree) {
    }
}
