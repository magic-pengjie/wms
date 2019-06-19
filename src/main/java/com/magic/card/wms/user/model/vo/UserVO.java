package com.magic.card.wms.user.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;

/**
 * <p>
 * 用户实体
 * </p>
 *
 * @author pengjie
 * @since 2019-06-13
 */
@Data
public class UserVO extends BaseRowModel  {


    /**
     * 用户名
     */
	@ExcelProperty(value = "用户名",index = 0)
    private String userNo;
    /**
     * 密码
     */
	@ExcelProperty(value = "密码",index = 1)
    private String password;
    /**
     * 姓名
     */
	@ExcelProperty(value = "姓名",index = 2)
    private String name;
   

}
