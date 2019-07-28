package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.CommodityStockMapper;
import com.magic.card.wms.baseset.model.po.CommodityStock;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;
import com.magic.card.wms.baseset.service.ICommodityStockService;
import com.magic.card.wms.baseset.service.ICustomerBaseInfoService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.security.CommodityStockSecurity;
import com.magic.card.wms.common.utils.PoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.magic.card.wms.baseset.service.impl
 * 商品整体库存情况服务接口实现类
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/27 18:17
 * @since : 1.0.0
 */
@Slf4j
@Service
public class CommodityStockServiceImpl extends ServiceImpl<CommodityStockMapper, CommodityStock> implements ICommodityStockService {
    @Autowired
    private CommodityStockSecurity stockSecurity;

    @Autowired
    private ICustomerBaseInfoService customerBaseInfoService;

    /**
     * 商品库存初始设置
     *
     * @param customerId  客户ID
     * @param commodityCode 商品条形码
     */
    @Override @Transactional
    public void initSetting(String customerId, String commodityCode) {
        CustomerBaseInfo customerBaseInfo = customerBaseInfoService.selectById(customerId);

        if (customerBaseInfo == null) return;

        // 判断数据是否已经存在
        try {
            checkOutSetting(customerBaseInfo.getCustomerCode(), commodityCode);
        } catch (OperationException operation) {
            CommodityStock stock = new CommodityStock();
            stock.setCustomerCode(customerBaseInfo.getCustomerCode());
            stock.setCommodityCode(commodityCode);
            stock.setStockNum(0l);              //初始库存
            stock.setStockOccupyNum(0l);        //初始占用库存
            stock.setStockFreezeNum(0l);        //初始冻结库存
            stock.setStockDeficiencyNum(0l);    //初始预警库存量
            PoUtil.add(stock, Constants.DEFAULT_USER);
            insert(stock);
        }
    }

    /**
     * 设置预警值
     *
     * @param customerCode  客户code
     * @param commodityCode 商品条形码
     * @param deficiencyNum 预警值
     * @param operator      操作人
     */
    @Override @Transactional
    public void setDeficiency(String customerCode, String commodityCode, Long deficiencyNum, String operator) {
        // 判断数据是否已经存在
        CommodityStock commodityStock = checkOutSetting(customerCode, commodityCode);
        String token = stockSecurity.accessLazyToken(customerCode, commodityCode);
        commodityStock.setStockDeficiencyNum(deficiencyNum);
        PoUtil.update(commodityStock, operator);
        updateById(commodityStock);
        stockSecurity.destroyToken(token);
    }

    /**
     * 占用库存操作 (订单导入时库存占用)
     *
     * @param customerCode  客户code
     * @param commodityCode 商品条形码
     * @param occupyNum     占用值
     * @param operator      操作人
     */
    @Override @Transactional
    public void occupyCommodityStock(String customerCode, String commodityCode, Long occupyNum, String operator) {
        String token = stockSecurity.accessLazyToken(customerCode, commodityCode);
        CommodityStock stock = checkOutSetting(customerCode, commodityCode);
        stock.setStockOccupyNum( stock.getStockOccupyNum() + occupyNum);
        PoUtil.update(stock, operator);
        updateById(stock);
        stockSecurity.destroyToken(token);
    }

    /**
     * 撤销库存占用量操作
     *
     * @param customerCode  客户code
     * @param commodityCode 商品条形码
     * @param occupyNum     撤销占用值
     * @param operator      操作人
     */
    @Override @Transactional
    public void repealOccupyCommodityStock(String customerCode, String commodityCode, Long occupyNum, String operator) {
        String token = stockSecurity.accessLazyToken(customerCode, commodityCode);
        CommodityStock stock = checkOutSetting(customerCode, commodityCode);
        stock.setStockOccupyNum(stock.getStockOccupyNum() - occupyNum);
        PoUtil.update(stock, operator);
        updateById(stock);
        stockSecurity.destroyToken(token);
    }

    /**
     * 释放库存操作
     *
     * @param customerCode  客户code
     * @param commodityCode 商品条形码
     * @param releaseNum    释放值
     * @param operator      操作人
     */
    @Override @Transactional
    public void releaseCommodityStock(String customerCode, String commodityCode, Long releaseNum, String operator) {
        String token = stockSecurity.accessLazyToken(customerCode, commodityCode);
        CommodityStock stock = checkOutSetting(customerCode, commodityCode);
        // 减库存
        Long stockNum = stock.getStockNum() - releaseNum;
        // 释放库存占位
        Long occupyNum = stock.getStockOccupyNum() - releaseNum;

        stock.setStockNum(stockNum);
        stock.setStockOccupyNum(occupyNum);
        PoUtil.update(stock, operator);
        updateById(stock);
        stockSecurity.destroyToken(token);

    }

    /**
     * 检出商品库存设置信息
     * @param customerCode
     * @param commodityCode
     * @return
     */
    private CommodityStock checkOutSetting(String customerCode, String commodityCode) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("customer_code", customerCode).
                eq("commodity_code", commodityCode).
                eq("state", StateEnum.normal.getCode());

        CommodityStock stock = selectOne(wrapper);

        if (stock == null){
            throw OperationException.customException(ResultEnum.commodity_stock_setting_deficiency);
        }

        return stock;
    }

	@Override
	public void addCommodityStock(String customerCode, String commodityCode, Long addNum, String operator) {
		CommodityStock stock = checkOutSetting(customerCode, commodityCode);
        String token = stockSecurity.accessLazyToken(customerCode, commodityCode);
        // 加库存
        Long stockNum = stock.getStockNum() + addNum;
        stock.setStockNum(stockNum);
        PoUtil.update(stock, operator);
        updateById(stock);
        stockSecurity.destroyToken(token);

	}
}
