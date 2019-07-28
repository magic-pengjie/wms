package com.magic.card.wms.baseset.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.magic.card.wms.baseset.model.dto.CommodityInfoDTO;
import com.magic.card.wms.baseset.model.po.Commodity;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
public interface ICommodityInfoService extends IService<Commodity> {

    /**
     * 添加商品关联信息
     * @param commodityInfoDTO
     * @param operator
     * @return
     */
    void add(CommodityInfoDTO commodityInfoDTO, String operator);

    /**
     * 修改商品关联信息
     * @param commodityInfoDTO
     * @param operator
     * @return
     */
    void update(CommodityInfoDTO commodityInfoDTO, String operator);

    /**
     * 删除数据
     * @param id
     */
    void delete(Long id);
    
    /**
     * 查询滞销品
     * @return
     */
    List<Commodity> selectUnsalableGood();


}
