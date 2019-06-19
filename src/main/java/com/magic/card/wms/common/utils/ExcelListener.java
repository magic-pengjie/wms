package com.magic.card.wms.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

	  /**
     * 自定义用于暂时存储data。
     * 可以通过实例获取该值
     */
    private final List<T> data = new ArrayList<>();

	
	@Override
	public void invoke(T object, AnalysisContext context) {
		 //数据存储
        data.add(object);
		
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public List<T> getData() {
        return data;
    }
}
