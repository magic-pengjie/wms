package com.magic.card.wms.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.magic.card.wms.common.annotation.ExcelDataConvertor;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.magic.card.wms.common.model.EasyExcelParams;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillExcelVO;

/**
 * excel处理工具
 * 
 * @author PENGJIE
 * @date 2019年6月18日
 */
@Slf4j
public class EasyExcelUtil {
	
	/**
	 * 读取excle数据
	 * 
	 * @param <T>         excel数据
	 * @param inputStream
	 * @param clazz       模型类型
	 * @param sheetNo     sheet序号
	 * @param headRow     表头行数
	 * @return
	 */
	public static  List<Object> readExcel2(final InputStream inputStream,
			Class<? extends BaseRowModel> clazz, int sheetNo, int headRow) {
		if (null == inputStream) {
			throw new NullPointerException("the inputStream is null!");
		}
		ExcelListener listener = new ExcelListener();
		ExcelReader reader = new ExcelReader(inputStream, null, listener);
		reader.read(new Sheet(sheetNo, headRow, PurchaseBillExcelVO.class));
		List<Object> data = listener.getData();
		return data;
	}

	/**
	 * 读取excle数据
	 *
	 * @param inputStream excel数据
	 * @param clazz		  模型类型
	 * @param sheetNo	  sheet序号
	 * @param headRow	  表头行数
	 * @param <T>
	 * @return
	 */
	public static <T extends BaseRowModel> List<T> readExcel(final InputStream inputStream,
			final Class<T> clazz, int sheetNo, int headRow) {
		if (null == inputStream) {
			throw new NullPointerException("the inputStream is null!");
		}
		
		List<T> data = new ArrayList<>();
		AnalysisEventListener listener = new AnalysisEventListener<T>() {


			@Override
			public void invoke(T object, AnalysisContext context) {
				for (Method method : object.getClass().getMethods()) {
					if (method.getAnnotation(ExcelDataConvertor.class) != null) {
						try {
							method.invoke(object, null);
						} catch (Exception e) {
							log.error("Excel 数据转化异常，请及时查看！");
						}
					}
				}

				data.add(object);
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext context) {
			}
			
		};
		ExcelReader reader = new ExcelReader(inputStream, null, listener);

		reader.read(new Sheet(sheetNo, headRow, clazz));

		return data;
	}

	public static void exportExcel2007Format(EasyExcelParams excelParams) throws IOException {
		exportExcel(excelParams, ExcelTypeEnum.XLSX);
	}

	public static void exportExcel2003Format(EasyExcelParams excelParams) throws IOException {
		exportExcel(excelParams, ExcelTypeEnum.XLS);
	}

	/**
	 * * 根据参数和版本枚举导出excel文件 * @param excelParams 参数实体 * @param typeEnum excel类型枚举
	 * * @throws IOException
	 */
	public static void exportExcel(EasyExcelParams excelParams, ExcelTypeEnum typeEnum) throws IOException {
		Validate.isTrue(excelParams.isValid(), "easyExcel params is not valid");
		HttpServletResponse response = excelParams.getResponse();
		ServletOutputStream out = response.getOutputStream();
		ExcelWriter writer = new ExcelWriter(out, typeEnum, excelParams.isNeedHead());
		prepareResponds(excelParams.getRequest(), response, excelParams.getExcelNameWithoutExt(), typeEnum);
		Sheet sheet1 = new Sheet(1, 0, excelParams.getDataModelClazz());
		if (StringUtils.isNotBlank(excelParams.getSheetName())) {
			sheet1.setSheetName(excelParams.getSheetName());
		}
		writer.write(excelParams.getData(), sheet1);
		writer.finish();
	}

	/**
	 * 需要写入的Excel，有模型映射关系
	 *
	 * @param file 需要写入的Excel，格式为xlsx
	 * @param list 写入Excel中的所有数据，继承于BaseRowModel
	 */
	public static void writeExcel(final File file, List<? extends BaseRowModel> list) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			// 写第一个sheet, 有模型映射关系
			Class t = list.get(0).getClass();
			Sheet sheet = new Sheet(1, 0, t);
			writer.write(list, sheet);
			writer.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * * 将文件输出到浏览器（导出文件） * @param response 响应 * @param fileName 文件名（不含拓展名） * @param
	 * typeEnum excel类型
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static void prepareResponds(HttpServletRequest request, HttpServletResponse response, String fileName,
			ExcelTypeEnum typeEnum) throws UnsupportedEncodingException {
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.addHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.addHeader("Prama", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-disposition",
				"attachment;filename=" + encodeCNFileName(request, fileName) + typeEnum.getValue());
	}

	/**
	 * 处理中文文件名
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeCNFileName(HttpServletRequest request,String fileName) throws UnsupportedEncodingException {
		String newFileName = null;
		String agent = request.getHeader("USER-AGENT");
		if(null != agent) {
			if(-1 != agent.indexOf("Firefox")) {
				newFileName = "=?UTF-8?B?"+(new String(Base64.encodeBase64(fileName.getBytes("UTF-8"))))+"?=";
			}else if (StringUtils.contains(agent, "MSIE")		
					|| StringUtils.contains(agent, "Trident")		
					|| StringUtils.contains(agent, "Edge")) { //IE edge 浏览器
				newFileName = URLEncoder.encode(fileName, "UTF-8");
			}else if(-1 != agent.indexOf("Chrome")) { 
				newFileName = new String(fileName.getBytes(),"ISO8859-1");
			}	
			else {
				newFileName = URLEncoder.encode(fileName,"UTF-8");
				newFileName = newFileName.replace("+", "%20");
			}
		}else {
			newFileName = fileName;
		}
		
		return newFileName;
	}

	/**
	 * 验证文件是否为excel
	 * @param fileName
	 */
	public static void validatorFileSuffix(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			throw OperationException.customException(ResultEnum.upload_file_inexistence);
		}
		if (!fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue()) && !fileName.toLowerCase().endsWith(ExcelTypeEnum.XLSX.getValue())) {
			throw OperationException.customException(ResultEnum.upload_file_suffix_err);
		}
	}
}
