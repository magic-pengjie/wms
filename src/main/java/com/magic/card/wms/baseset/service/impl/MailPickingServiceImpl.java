package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.MailPickingMapper;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.common.model.po.PoUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.magic.card.wms.baseset.service.impl
 *  快递篮拣货服务接口实现类
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 9:58
 * @since : 1.0.0
 */
@Service
public class MailPickingServiceImpl extends ServiceImpl<MailPickingMapper, MailPicking> implements IMailPickingService {
    /**
     * 自动生成快递篮拣货单服务
     *
     * @param mailPicking
     * @param operator
     */
    @Override @Transactional
    public void generatorMailPicking(MailPicking mailPicking, String operator) {
        PoUtils.add(mailPicking, operator);
        this.insert(mailPicking);
    }
}
