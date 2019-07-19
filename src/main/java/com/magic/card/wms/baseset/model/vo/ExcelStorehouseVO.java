package com.magic.card.wms.baseset.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * com.magic.card.wms.baseset.model.vo
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/19 10:17
 * @since : 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelStorehouseVO extends BaseRowModel {
    @ExcelProperty(value = "库位编号", index = 0)
    private String storeCode;
    @ExcelProperty(value = "优先值", index = 1)
    private Integer priorityValue;

    public String getHouseCode() {

        try {
            if (storeCode.contains("PA")) {
                return "CK-GN-JHQ";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw OperationException.customException(ResultEnum.upload_file_suffix_err);
        }
        // 存储区
        return "CK-GN-CCQ";
    }

    public String getAreaCode() {
        String[] codes = storeCode.split("-");

        if (codes.length == 2) {
            return codes[0];
        }

        try {
            Integer.parseInt(codes[1]);
            return codes[0];
        } catch (NumberFormatException e) {
            return StringUtils.joinWith("-", codes[0], codes[1]);
        }
    }

    public Integer getState() {
        if (priorityValue == null || priorityValue == 0) {
            // 停用
            return 3;
        } else {
            // 正常使用
            return 1;
        }
    }
}
