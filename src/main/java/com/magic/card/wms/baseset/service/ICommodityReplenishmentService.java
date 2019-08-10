package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.CommodityReplenishmentDTO;
import com.magic.card.wms.baseset.model.dto.ReplenishmentFinishedDTO;
import com.magic.card.wms.baseset.model.po.CommodityReplenishment;
import com.magic.card.wms.common.model.LoadGrid;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.service
 * 商品补货 Service 接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/12 15:47
 * @since : 1.0.0
 */
public interface ICommodityReplenishmentService extends IService<CommodityReplenishment> {
    /**
     * 分页查询排序数据加载
     * @param loadGrid
     * @return
     */
    void loadGrid(LoadGrid loadGrid);

    /**
     * 处理相关商品零拣区库存不足
     * @param storeCode 零拣库位编号
     * @param stockoutNums 商品缺少量
     */
    void addReplenishmentNotice(String storeCode, Integer stockoutNums);

    /**
     * 补货单清单
     * @param ids
     * @return
     */
    List<Map> replenishmentList(List<String> ids);

    /**
     * 加载补货单推荐的存储库位信息
     * @param replenishmentNo
     * @return
     */
    List<Map> replenishmentStorehouse(String replenishmentNo);

    /**
     * 补货完成信息
     * @param replenishmentFinished
     */
    void replenishmentFinished(CommodityReplenishmentDTO replenishmentFinished);

    /**
     * 执行补货操作
     * @param commodityReplenishments
     */
    void executorReplenishmentOperation(List<CommodityReplenishment> commodityReplenishments);

    /**
     * 获取存储库位
     * @param replenishmentNo
     * @return
     */
    List<Map> loadStorehouse(String replenishmentNo);
}
