package com.magic.card.wms.common.model;

import org.springframework.util.StringUtils;

import com.magic.card.wms.common.model.enums.ResultEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 返回结果模版
 * @author PENGJIE
 * @date 2019年6月12日
 * @param <T>
 */
@ApiModel(value = "返回结果模版")
@Data
public class ResponseData<T> {

	
	/**
	 * 返回代码 0-成功
	 */
	@ApiModelProperty("返回代码 0-成功")
	private int code;
	/**
	 * 返回描述信息
	 */
	@ApiModelProperty("返回描述信息")
	private String msg;
	/**
	 * 是否有业务数据0-没有 1-有
	 */
	@ApiModelProperty("是否有业务数据0-没有 1-有")
	private int status;
	/**
	 * 业务对象数据
	 */
	@ApiModelProperty("业务对象数据")
	private T data;
	
	public static ResponseData ok() {
		ResponseData result = new ResponseData();
		result.setCode(ResultEnum.success.getCode());
		result.setMsg(ResultEnum.success.getMsg());
		return result;
	}
	
	public static <T> ResponseData ok(T data) {
		ResponseData result = new ResponseData();
		result.setCode(ResultEnum.success.getCode());
		result.setMsg(ResultEnum.success.getMsg());
		if(StringUtils.isEmpty(data)) {
			result.setStatus(0);
		}else {
			result.setStatus(1);
		}
		result.setData(data);
		return result;
	}
	
	public static ResponseData error(int code ,String msg) {
		ResponseData result = new ResponseData();
		result.setCode(code);
		result.setMsg(msg);
		result.setStatus(0);
		return result;
	}
	public static ResponseData error(String msg) {
		ResponseData result = new ResponseData();
		result.setCode(ResultEnum.fail.getCode());
		result.setMsg(msg);
		result.setStatus(0);
		return result;
	}
	public static ResponseData error() {
		ResponseData result = new ResponseData();
		result.setCode(ResultEnum.fail.getCode());
		result.setMsg(ResultEnum.fail.getMsg());
		result.setStatus(0);
		return result;
	}
}
