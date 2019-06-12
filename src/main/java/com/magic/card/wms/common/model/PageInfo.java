package com.magic.card.wms.common.model;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.plugins.Page;

import lombok.Data;

/***
 * 分页信息
 * @author PENGJIE
 * @date 2019年6月12日
 */
@Data
public class PageInfo<T> {

	public static final String REQUEST_ATTRIBUTE = "PAGE_INFO_JSON_ATTRIBUTE";
	
	private int current = 1;
	
	private int pageSize = 15;
			
	private boolean asc = false ;
	
	private String orderBy = "ID";
	
	public PageInfo() {
		super();
	}
			
	public PageInfo(int current, int pageSize) {	
		super();
		this.current = current;
		this.pageSize = pageSize;
	}
	
	public PageInfo(int current, int pageSize,boolean asc,String orderBy) {	
		super();
		this.current = current;
		this.pageSize = pageSize;
		this.asc = asc;
		if(StringUtils.isNotEmpty(orderBy)) {
			this.orderBy = orderBy;
		}
	}
	
	public Page<T> getPage(){
		Page<T> page = new Page<T>(this.current,this.pageSize,this.orderBy,this.asc);
		return page;
	}
}
