package com.magic.card.wms.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ExcelListener extends AnalysisEventListener{

	  /**
     * 自定义用于暂时存储data。
     * 可以通过实例获取该值
     */
    private List<Object> data = new ArrayList<>();

	
	@Override
	public void invoke(Object object, AnalysisContext context) {
		 //数据存储
        data.add(object);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Object> getData() {
        return data;
    }
	 public void setData(List<Object> data) {
	        this.data = data;
	    }
}
