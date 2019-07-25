package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.PickingBillExceptionMapper;
import com.magic.card.wms.baseset.model.po.PickingBillException;
import com.magic.card.wms.baseset.service.IPickingBillExceptionService;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.utils.PoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.magic.card.wms.baseset.service.impl
 *  拣货异常处理接口实现
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/27 15:37
 * @since : 1.0.0
 */
@Service
public class PickingBillExceptionImpl extends ServiceImpl<PickingBillExceptionMapper, PickingBillException> implements IPickingBillExceptionService {
    /**
     * 检验配货单时异常处理
     * @param uniteBillNo 联合票据单号 （拣货单号/拣货单号&&订单号&&快递单号）
     * @param commodityInfo 商品信息 （商品条形码/商品条形码&&数量）
     * @param type 拣货异常类型
     * @param operator 操作人
     */
    @Override @Transactional
    public void handleException(String uniteBillNo, String commodityInfo, BillState type, String operator) {
        // 判断系统中是否已有数据存在
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("exception_state", type.getCode());
        String orderNo = null;
        String mailNo = null;
        Integer numbers = 1;

        if (StringUtils.contains(uniteBillNo, "&&")) {
            String[] split = StringUtils.split(uniteBillNo, "&&");
            uniteBillNo = split[0];
            orderNo = split[1];
            mailNo = split[2];
            wrapper.eq("order_no", orderNo).
                    eq("mail_no", mailNo);
        }

        if (StringUtils.contains(commodityInfo, "&&")) {
            String[] split = StringUtils.split(commodityInfo, "&&");
            commodityInfo = split[0];
            numbers = Integer.valueOf(split[1]);
        }

        wrapper.eq("pick_no", uniteBillNo).
                eq("commodity_code", commodityInfo);
        PickingBillException billException = selectOne(wrapper);

        if (billException == null) {
            // 新建此类异常
            billException = new PickingBillException();
            billException.setPickNo(uniteBillNo);
            billException.setOrderNo(orderNo);
            billException.setMailNo(mailNo);
            billException.setCommodityCode(commodityInfo);
            billException.setExceptionState(type.getCode());
            billException.setExceptionNumber(numbers);
            PoUtil.add(billException, operator);
            insert(billException);
        } else {
            // 增量
            billException.exceptionNumberPlus(numbers);
            PoUtil.update(billException, operator);
            updateById(billException);
        }
    }
}
