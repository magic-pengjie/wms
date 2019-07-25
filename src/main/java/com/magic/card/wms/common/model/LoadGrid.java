package com.magic.card.wms.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.common.model
 * LoadGrid 主要是用于加载查询分页数据
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/20/020 13:55
 * @since : 1.0.0
 */
@Data
@ApiModel("加载分页查询搜索排序数据")
public class LoadGrid {
    /**
     * 总数
     */
    @ApiModelProperty("数据总量")
    private long total;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty("每页数据，默认 10 条")
    private int size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页， 默认 1 ")
    private int current = 1;

    /**
     * 查询信息
     */
    @ApiModelProperty("搜索条件， 默认格式 search : { address: '上海' 或者 address: {type: '匹配类型', value: '上海'}, ....}")
    @JSONField(serialize = false)
    private Map<String, Object> search;

    /**
     * 排序信息
     */
    @ApiModelProperty("排序， 默认格式 order : { id: 'asc', ....}")
    @JSONField(serialize = false)
    private Map<String, String> order;

    /**
     * 查询数据列表
     */
    @ApiModelProperty("查询获得数据，无需提供")
    private List rows = Collections.emptyList();

    public Page generatorPage() {
        return new Page<>(this.current, this.size);
    }

    /**
     * @param pagination
     * @param data
     * @return
     */
    public void finallyResult(Pagination pagination, List data) {
        setRows(data);
        BeanUtils.copyProperties(pagination, this);
    }

    /**
     *
     * @param pagination
     * @param data
     * @return
     */
    public static LoadGrid instance(Pagination pagination, List data) {
        LoadGrid loadGrid = new LoadGrid();
        loadGrid.setRows(data);
        BeanUtils.copyProperties(pagination, loadGrid);
        return loadGrid;
    }

}
