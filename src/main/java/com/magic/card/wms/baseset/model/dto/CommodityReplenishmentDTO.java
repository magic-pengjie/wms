package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/4 16:36
 * @since : 1.0.0
 */
@Data
@ApiModel("拣货数据")
public class CommodityReplenishmentDTO implements Serializable {
    private static final long serialVersionUID = 509727773556652987L;
    @NotBlank(message = "补货单号不可为空")
    private String replenishmentNo;
    @Size(min = 1, message = "请填写库位补货信息")
    private List<ReplenishmentInfo> replenishmentInfos;

    @Data
    @ApiModel("补货信息")
    public class ReplenishmentInfo {
        @NotNull(message = "库位id不可为空")
        private Long id;
        @Min(value = 1, message = "补货数量至少为1")
        private Integer nums;
    }
}
