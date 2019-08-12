package com.magic.card.wms.common.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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

    @ApiModelProperty(value = "总条数",name = "total")
    private Integer total;

    @ApiModelProperty(value = "当前页面",name = "current")
    private Integer current;

    @ApiModelProperty(value = "每页显示最大行数",name = "pageSize")
    private Integer pageSize;

    @ApiModelProperty(value = "响应数据集合",name = "records")
    private List<T> records;

    public void setRecords(List<T> records) {
        if(CollectionUtils.isEmpty(records)){
            this.records = new ArrayList<>();
        }
        this.records = records;
    }

    public void setTotal(Integer total){
        if (null == total){
            total = 0;
        }
        this.total = total;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BasePageResponse{");
        sb.append("total=").append(total);
        sb.append(", current=").append(current);
        sb.append(", records=").append(records);
        sb.append('}');
        return sb.toString();
    }
}


