package com.magic.card.wms.common.utils;

import com.google.common.collect.Lists;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.po.BasePo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * com.magic.card.wms.common.model.po
 * Po类基础操作
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 12:33
 * @since : 1.0.0
 */
public class PoUtil {
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
    public static <Po extends BasePo, DTO> void add(DTO dto, Po po, String operator) {
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
     * 批量添加操作
     * @param operator
     * @param pos
     * @param <Po>
     */
    public static <Po extends  BasePo> void batchAdd(String operator, List<Po> pos) {
        Date now = new Date();

        if (pos == null || pos.size() < 1) return;

        pos.forEach(po -> {
            po.setUpdateTime(now);
            po.setUpdateUser(operator);
        });
    }

    /**
     * 批量更新操作
     * @param operator 操作人
     * @param pos     更新数据
     * @param <Po>
     */
    public static <Po extends  BasePo> void batchUpdate(String operator, List<Po> pos) {
        Date now = new Date();

        if (pos == null || pos.size() < 1) return;

        pos.forEach(po -> {
            po.setCreateTime(now);
            po.setUpdateTime(now);
            po.setCreateUser(operator);
            po.setUpdateUser(operator);
        });
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

    /**
     * 主键检查
     * @param id
     */
    public static void checkId(Long id) {

        if (id == null || id == 0l)
            throw OperationException.DATA_ID;

    }
}
