package com.magic.card.wms.baseset.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.model.dto.BatchBindStorehouseDTO;
import com.magic.card.wms.baseset.model.dto.BatchStorehouseConfigDTO;
import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.magic.card.wms.baseset.model.vo.StorehouseConfigVO;
import com.magic.card.wms.baseset.service.ICustomerBaseInfoService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.baseset.service.IStorehouseInfoService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.enums.StoreTypeEnum;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WebUtil;
import com.magic.card.wms.common.utils.WrapperUtil;

/**
 * com.magic.card.wms.baseset.service.impl
 * 库位配置服务实现类
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 17:46
 * @since : 1.0.0
 */
@Service
public class StorehouseConfigServiceImpl extends ServiceImpl<StorehouseConfigMapper, StorehouseConfig> implements IStorehouseConfigService {
    private static final Map<String, String> DEFAULT_COLUMNS = Maps.newConcurrentMap();
    static {
        DEFAULT_COLUMNS.put("id", "wsc.id");
        DEFAULT_COLUMNS.put("state", "wsc.state");
        DEFAULT_COLUMNS.put("skuName", "wcs.sku_name");
        DEFAULT_COLUMNS.put("commodityCode", "wcs.bar_code");

        DEFAULT_COLUMNS.put("houseCode", "wsi.house_code");
        DEFAULT_COLUMNS.put("storeCode", "wsi.store_code");
        DEFAULT_COLUMNS.put("customerCode", "wcbi.customer_code");
        DEFAULT_COLUMNS.put("customerName", "wcbi.customer_name");
    }

    @Autowired
    private ICustomerBaseInfoService customerService;
    @Autowired
    private IStorehouseInfoService storehouseInfoService;
    @Autowired
    private StorehouseConfigMapper storehouseConfigMapper;

    @Autowired
    private WebUtil webUtil;
    /**
     * 查询仓库配置信息
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.generatorPage();
        EntityWrapper wrapper = new EntityWrapper();
        WrapperUtil.autoSettingSearch(wrapper, DEFAULT_COLUMNS, loadGrid.getSearch());
        WrapperUtil.autoSettingOrder(wrapper, DEFAULT_COLUMNS, loadGrid.getOrder(), defaultSetOrder ->
            defaultSetOrder.orderBy("wsc.create_time", false));
        loadGrid.finallyResult(page, baseMapper.loadGrid(page, wrapper));

        return loadGrid;
    }

    /**
     * 添加库位配置
     *
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    @Override @Transactional
    public void add(StorehouseConfigDTO storehouseConfigDTO, String operator) {
        checkStorehouseConfig(storehouseConfigDTO, false);
        StorehouseConfig config = new StorehouseConfig();
        PoUtil.add(storehouseConfigDTO, config, operator);

        if (this.baseMapper.insert(config) < 1) {
            throw OperationException.DATA_OPERATION_ADD;
        }

    }

    /**
     * 批量配置库位信息
     *
     * @param batchStorehouseConfig
     */
    @Override
    public void batchConfig(BatchStorehouseConfigDTO batchStorehouseConfig) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.in("storehouse_id", batchStorehouseConfig.getStorehouseIds()).
                eq("state", StateEnum.normal.getCode());

        if (selectCount(entityWrapper) > 0) {
            throw OperationException.customException(ResultEnum.data_check_exist, "库位已经被配置");
        }

        // todo 拣货区商品库位限制
        StorehouseConfig baseConfig = new StorehouseConfig();
        BeanUtils.copyProperties(batchStorehouseConfig, baseConfig);
        PoUtil.add(baseConfig, webUtil.operator());
        List<StorehouseConfig> storehouseConfigs = Lists.newArrayList();

        for (String storehouseId :
                batchStorehouseConfig.getStorehouseIds()) {
            StorehouseConfig storehouseConfig = new StorehouseConfig();
            BeanUtils.copyProperties(baseConfig, storehouseConfig);
            storehouseConfig.setStorehouseId(storehouseId);
            storehouseConfigs.add(storehouseConfig);
        }

        insertBatch(storehouseConfigs);
    }

    /**
     * 修改库位配置
     *
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    @Override @Transactional
    public void update(StorehouseConfigDTO storehouseConfigDTO, String operator) {
        checkStorehouseConfig(storehouseConfigDTO, true);
        StorehouseConfig config = new StorehouseConfig();
        PoUtil.update(storehouseConfigDTO, config, operator);

        if (this.baseMapper.updateById(config) < 1) {
            throw OperationException.DATA_OPERATION_UPDATE;
        }



    }

    /**
     * 删除数据
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
     * 库位配置添加修改数据检查
     * @param storehouseConfigDTO
     * @param updateOperation
     */
    private void checkStorehouseConfig(StorehouseConfigDTO storehouseConfigDTO, Boolean updateOperation) {

        if (updateOperation) {
            PoUtil.checkId(storehouseConfigDTO.getId());
        }

        // 检查当前库位是否已经使用
        EntityWrapper<StorehouseConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 1)
                .eq("storehouse_id", storehouseConfigDTO.getStorehouseId());

        if (updateOperation) {
            wrapper.ne("id", storehouseConfigDTO.getId());
        }

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.data_check_exist, "库区已经被配置");
        }

        if (StringUtils.isBlank(storehouseConfigDTO.getCommodityId())) return;

        // TODO 判断当前库位是否为拣货区且已近存放该商品
        StorehouseInfo storehouseInfo = this.storehouseInfoService.selectById(storehouseConfigDTO.getStorehouseId());

        if (storehouseInfo != null && StringUtils.equalsAnyIgnoreCase(Constants.PICKING_AREA_CODE, storehouseInfo.getHouseCode())) {
            wrapper.eq("commodity_id", storehouseConfigDTO.getCommodityId());

            if (this.selectCount(wrapper) > 0) {
                throw OperationException.customException(ResultEnum.data_check_exist, "该商品在拣货区已经有库位了！");
            }

        }


    }

    @Override
   	@Transactional
   	public void save(String commodityId,String storehouseId, int numbers) {
   		Wrapper<StorehouseConfig> w = new EntityWrapper<>();
   		w.eq("storehouse_id", storehouseId);
   		w.eq("state",Constants.STATE_1);
   		StorehouseConfig config = this.selectOne(w);
   		config.setAvailableNums(numbers+(ObjectUtils.isEmpty(config.getAvailableNums())?0:config.getAvailableNums()));
   		config.setOprType(1);
   		config.setCommodityId(commodityId);
   		PoUtil.add(config, Constants.DEFAULT_USER);
   		this.update(config, w);
   		
   	}

	@Override
	public List<StorehouseConfigVO> recommendStore(String customerCode) {
		List<StorehouseConfigVO> configList = storehouseConfigMapper.recommendStore(customerCode);
		if(ObjectUtils.isEmpty(configList)) {
			throw new OperationException(-1,"该商品无可用库位");
		}
		return configList;
	}

    /**
     * 客户商品补货推荐(存储区)数据
     *
     * @param customerCode
     * @param commodityCode
     * @return
     */
    @Override
    public List<Map> replenishmentDataList(String customerCode, String commodityCode) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("wsc.state", StateEnum.normal.getCode())
                .eq("wcbi.customer_code", customerCode)
                .eq("wcs.bar_code", commodityCode)
                .eq("wsi.house_code", StoreTypeEnum.CCQ.getCode())
                .gt("wsc.available_nums", 0)
                .orderBy("wsc.entry_time")
                .orderBy("wsc.end_time");

        return baseMapper.storehouseConfig(wrapper);
    }

    /**
     * 库位批量绑定
     *
     * @param batchBindStorehouseDTO
     */
    @Override
    public void batchBind(BatchBindStorehouseDTO batchBindStorehouseDTO) {
        // 检测商家数据是否有误
        customerService.checkoutCustomerById(batchBindStorehouseDTO.getCustomerId());
       // 检测库位是否已经绑定
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.in("storehouse_id", batchBindStorehouseDTO.getStorehouseIds()).
                eq("state", StateEnum.normal.getCode());

        if (selectCount(wrapper) > 0) {
           throw OperationException.customException(ResultEnum.store_house_config_exist);
        }

        StorehouseConfig baseStorehouseConfig = new StorehouseConfig();
        baseStorehouseConfig.setCustomerId(batchBindStorehouseDTO.getCustomerId());
        PoUtil.add(baseStorehouseConfig, webUtil.operator());

        List<StorehouseConfig> storehouseConfigs = batchBindStorehouseDTO.getStorehouseIds().stream().map(storehouseId -> {
            StorehouseConfig storehouseConfig = new StorehouseConfig();
            BeanUtils.copyProperties(baseStorehouseConfig, storehouseConfig);

            storehouseConfig.setStorehouseId(storehouseId);
            return storehouseConfig;
        }).collect(Collectors.toList());

        insertBatch(storehouseConfigs);
    }

    /**
     * 推荐存储库位信息
     *
     * @param commodityIds 客户关系商品IDs
     * @return
     */
    @Override
    public List<Map> recommendCustomerCommodityStorage(List<String> commodityIds) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.
                in("wsc.commodity_id", commodityIds).
                eq("wsi.house_code", StoreTypeEnum.CCQ.getCode()).
                eq("wsi.state", StateEnum.normal.getCode()).
                gt("wsc.available_nums", 0).
                orderBy("wsc.entry_time, wsc.end_time, wsc.available_nums");
        return baseMapper.loadGrid(null, entityWrapper);
    }
}
