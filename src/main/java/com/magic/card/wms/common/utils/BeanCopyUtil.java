package com.magic.card.wms.common.utils;

import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import springfox.documentation.spring.web.json.Json;

public class BeanCopyUtil {
	
	private BeanCopyUtil() {}
	
	public static <F,T> List<T> copyList(List<F> sourceList, Class<T> destiantionClass){
		
		if(CollectionUtils.isEmpty(sourceList)) {
			return Collections.<T>emptyList();
		}
		if(sourceList.get(0).getClass().equals(destiantionClass)) {
			return (List<T>)sourceList;
		}
		List<T> destinationList = Lists.newArrayListWithExpectedSize(sourceList.size());
		for (Object sourceObj : sourceList) {
			T desObj = copy(sourceObj, destiantionClass);
			destinationList.add(desObj);
		}
		return destinationList;
	}
	
	public static<F,T> T copy(F from, Class<T> destinationClass) {
		if(null == from ) {
			return null;
		}
		if(from.getClass().equals(destinationClass)) {
			return (T)from ;
		}
		return JSON.parseObject(JSON.toJSONString(from),destinationClass);
	}

}
