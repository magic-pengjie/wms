package com.magic.card.wms.common.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;

import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;

@Data
public class EasyExcelParams {

	/** * excel文件名（不带拓展名) */
	private String excelNameWithoutExt;
	/** * sheet名称 */
	private String sheetName;
	/** * 是否需要表头 */
	private boolean needHead = true;
	/** * 数据 */
	private List<? extends BaseRowModel> data;
	/** * 数据模型类型 */
	private Class<? extends BaseRowModel> dataModelClazz;
	/** * 响应 */
	private HttpServletResponse response;
	/** * 请求 */
	private HttpServletRequest request;

	public EasyExcelParams() {
	}

	/** * 检查不允许为空的属性 */
	public boolean isValid() {
		return ObjectUtils.allNotNull(excelNameWithoutExt, data, dataModelClazz, response);
	}

}
