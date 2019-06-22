package com.magic.card.wms.common.model.po;

import org.springframework.beans.BeanUtils;
import java.util.Date;

/**
 * com.magic.card.wms.common.model.po
 * Po类基础操作
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 12:33
 * @since : 1.0.0
 */
public class PoUtils {
    /**
     * PO类添加时操作 ：
     *  1.设置数据创建时间
     *  2.设置数据创建人
     *  3.设置数据修改时间
     *  4.设置数据修改人
     * @param dto
     * @param po
     * @param operator
     * @param <Po>
     * @param <DTO>
     */
    public static <Po extends BasePo, DTO> void add(DTO dto,Po po, String operator) {
        po.setCreateTime(new Date());
        po.setCreateUser(operator);
        update(dto, po, operator);
    }

    /**
     * PO类修改时操作 ：
     *  1.设置数据修改时间
     *  2.设置数据修改人
     * @param dto
     * @param po
     * @param operator
     * @param <Po>
     * @param <DTO>
     */
    public static <Po extends BasePo, DTO> void update(DTO dto,Po po, String operator) {
        BeanUtils.copyProperties(dto, po);
        po.setUpdateTime(new Date());
        po.setUpdateUser(operator);
    }

    /**
     * PO类添加时操作 ：
     *  1.设置数据创建时间
     *  2.设置数据创建人
     *  3.设置数据修改时间
     *  4.设置数据修改人
     * @param po
     * @param operator
     * @param <Po>
     */
    public static <Po extends BasePo> void add(Po po, String operator) {
        po.setCreateTime(new Date());
        po.setCreateUser(operator);
        update(po, operator);
    }

    /**
     * PO类修改时操作 ：
     *  1.设置数据修改时间
     *  2.设置数据修改人
     * @param po
     * @param operator
     * @param <Po>
     */
    public static <Po extends BasePo> void update(Po po, String operator) {
        po.setUpdateTime(new Date());
        po.setUpdateUser(operator);
    }
}
