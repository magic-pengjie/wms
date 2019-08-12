package com.magic.card.wms.common.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分页返回 基础类
 * </p>
 *
 * @author zhouhao
 * @since 2019年8月12日
 */
@Data
public class BasePageResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -2760348178003411165L;

    @ApiModelProperty(value = "总条数",name = "totalCount")
    private Integer totalCount;

    @ApiModelProperty(value = "当前页面",name = "pageCount")
    private Integer pageCount;

    @ApiModelProperty(value = "响应数据集合",name = "data")
    private List<T> data;

    public void setData(List<T> data) {
        if(CollectionUtils.isEmpty(data)){
            this.data = new ArrayList<>();
        }
        this.data = data;
    }

    public void setTotalCount(Integer totalCount){
        if (null == totalCount){
            totalCount = 0;
        }
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BasePageResponse{");
        sb.append("totalCount=").append(totalCount);
        sb.append(", pageCount=").append(pageCount);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}


