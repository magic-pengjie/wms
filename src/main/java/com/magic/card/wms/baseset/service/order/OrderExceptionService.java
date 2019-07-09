package com.magic.card.wms.baseset.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.OrderExceptionMapper;
import com.magic.card.wms.baseset.model.po.OrderException;
import org.springframework.stereotype.Service;

/**
 * com.magic.card.wms.baseset.service.order
 *  订单异常记录处理服务
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/1 14:49
 * @since : 1.0.0
 */
@Service
public class OrderExceptionService extends ServiceImpl<OrderExceptionMapper, OrderException> {
}
