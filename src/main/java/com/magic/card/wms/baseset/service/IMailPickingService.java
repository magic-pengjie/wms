package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.MailPicking;

/**
 * com.magic.card.wms.baseset.service
 * 快递篮拣货服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 9:57
 * @since : 1.0.0
 */
public interface IMailPickingService extends IService<MailPicking> {

    /**
     * 自动生成快递篮拣货单服务
     * @param mailPicking
     * @param operator
     */
    void generatorMailPicking(MailPicking mailPicking, String operator);
}
