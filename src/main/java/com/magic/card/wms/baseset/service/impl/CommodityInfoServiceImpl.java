package com.magic.card.wms.baseset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.dto.CommodityInfoDTO;
import com.magic.card.wms.baseset.model.dto.RulesConfigReqDTO;
import com.magic.card.wms.baseset.model.po.Commodity;
import com.magic.card.wms.baseset.model.po.RulesConfig;
import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.baseset.mapper.CommodityInfoMapper;
import com.magic.card.wms.baseset.service.ICommodityInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.service.ICommodityStockService;
import com.magic.card.wms.baseset.service.IRulesConfigService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.AgentTypeEnum;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.DateUtil;
import com.magic.card.wms.common.utils.PoUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Service
@Slf4j
public class CommodityInfoServiceImpl extends ServiceImpl<CommodityInfoMapper, Commodity> implements ICommodityInfoService {
	
	private static final long defalt_days = 30;

    /**
     *
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();

    static {

    }
    @Autowired
    private ICommodityStockService commodityStockService;
    @Autowired
    private CommodityInfoMapper commodityInfoMapper;
    @Autowired
    private IRulesConfigService rulesConfigService;

    /**
     * 添加商品关联信息
     *
     * @param commodityInfoDTO
     * @param operator
     * @return
     */
    @Override @Transactional
    public void add(CommodityInfoDTO commodityInfoDTO, String operator) {
        checkCommodityInfo(commodityInfoDTO, false);
        Commodity commodity = new Commodity();
        PoUtil.add(commodityInfoDTO, commodity, operator);

        if (this.baseMapper.insert(commodity) < 1) {
            throw OperationException.DATA_OPERATION_ADD;
        }

        // 初始化商品库存
        new Thread(()->
                commodityStockService.initSetting(
                        commodityInfoDTO.getCustomerId(),
                        commodityInfoDTO.getCommodityCode()
                ),
                "Commodity-Stock-Init-Setting." + System.currentTimeMillis()
        ).start();
    }

    /**
     * 修改商品关联信息
     *
     * @param commodityInfoDTO
     * @param operator
     * @return
     */
    @Override @Transactional
    public void update(CommodityInfoDTO commodityInfoDTO, String operator) {
        checkCommodityInfo(commodityInfoDTO, true);
        Commodity commodity = new Commodity();
        PoUtil.update(commodityInfoDTO, commodity, operator);

        if (this.baseMapper.updateById(commodity) < 1)
            throw OperationException.DATA_OPERATION_UPDATE;

    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Override @Transactional
    public void delete(Long id) {

        if (this.baseMapper.deleteById(id) < 1) throw OperationException.DATA_OPERATION_DELETE;

    }

    private void checkCommodityInfo(CommodityInfoDTO commodityInfoDTO, Boolean updateOperator) {

        if (updateOperator && (commodityInfoDTO.getId() == null || commodityInfoDTO.getId() == 0l))
            throw OperationException.DATA_ID;

        EntityWrapper<Commodity> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 1)
                .eq("customer_id", commodityInfoDTO.getCustomerId())
                .eq("commodity_code", commodityInfoDTO.getCommodityCode());

        if (updateOperator) wrapper.ne("id", commodityInfoDTO.getId());

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.data_check_exist, "当前商品已关联客户");
        }
    }

	@Override
	public List<Commodity> selectUnsalableGood() {
		//查询滞销品间隔配置时间
		RulesConfigReqDTO dto = new RulesConfigReqDTO();
		dto.setRuleType(AgentTypeEnum.unsalable_goods.getCode());
		RulesConfig config = rulesConfigService.selectObjectByType(dto);
		List<Commodity>  list = null;
		if(ObjectUtils.isEmpty(config) || ObjectUtils.isEmpty(config.getLeftValue())) {
			log.info("selectObjectByType no config so task not until run");
			return null;
		}
		String nextDate = config.getLeftValue();
		String curDate = DateUtil.getStringDateShort();
		log.info("selectUnsalableGood nextDate:{},curDate:{}",nextDate,curDate);
		try {
			//如果下次运行时间刚好等于今天，则进行预警
			if(0==DateUtil.getTwoDays(nextDate, curDate)) {
				list = commodityInfoMapper.selectUnsalableGood();
			}else if(0>DateUtil.getTwoDays(nextDate, curDate) && 30==DateUtil.getTwoDays(curDate,nextDate )) {
				//如果下次运行已过，且用户没有更新配置，则默认每过30天再次运行
				list = commodityInfoMapper.selectUnsalableGood();
			}else {
				//其他情况不进行预警
				return null;
			}
		} catch (ParseException e) {
			log.error("date ParseException:{}",e);
		}
		
		if(ObjectUtils.isEmpty(list)) {
			log.info("selectUnsalableGood no data to warning");
			return null;
		}
		//保存预警信息
		WarningAgentInfo warningAgentInfo = new WarningAgentInfo();
		warningAgentInfo.setFid(String.valueOf(curDate));
		warningAgentInfo.setTypeCode(AgentTypeEnum.unsalable_goods.getCode());
		warningAgentInfo.setTypeName(AgentTypeEnum.unsalable_goods.getName());
		warningAgentInfo.setAgentName("滞销商品预警代表");
		warningAgentInfo.setAgentState(Constants.STATE_0);
		warningAgentInfo.setParamters(JSONObject.toJSONString(list));
		warningAgentInfo.insert();
		return list;
	}
}
