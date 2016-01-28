package open.platform.module.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportUtil {
	private static Logger logger = LoggerFactory.getLogger(ExportUtil.class);
	private static int DEFAULT_WIDTH = 2500;
	private static short DEFAULT_HEIGHT = 400;

	@SuppressWarnings("unchecked")
	public static void exportExcel(OutputStream outputStream,
			List<ExcelObject> excelObjects) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		if (CollectionUtils.isEmpty(excelObjects)) {
			return;
		}
		List<Object> list = null;
		String sheetName = null;
		List<String> headers = null;
		List<String> propertys = null;
		List<Class<?>> types = null;
		for (ExcelObject excelObject : excelObjects) {
			//数据验证
			if (null == excelObject) {
				continue;
			}
			list = (List<Object>) excelObject.getData();
			sheetName = excelObject.getSheetName();
			headers = excelObject.getHeaders();
			propertys = excelObject.getPropertys();
			types = excelObject.getTypes();
			if (CollectionUtils.isEmpty(headers) || CollectionUtils.isEmpty(propertys)) {
				continue;
			}
			if(StringUtils.isBlank(sheetName)){
				sheetName = "导出数据" + System.currentTimeMillis();
			}
			//设置样式
			HSSFSheet sheet = wb.createSheet(sheetName);
			sheet.setDefaultRowHeight(DEFAULT_HEIGHT);
			for (int i = 0; i < propertys.size(); i++) {
				sheet.setColumnWidth(i, DEFAULT_WIDTH);
			}
			//生成第一行头
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < headers.size(); i++) {
				row.createCell(i).setCellValue(headers.get(i));
			}
			
			if(CollectionUtils.isEmpty(list)){
				continue;
			}
			
			//根据数据填充下面真实数据
			for (int i = 0; i < list.size(); i++) {
				Object object = list.get(i);
				if (object == null) {
					continue;
				}
				row = sheet.createRow(i + 1);// 有个头，所以都需要加1

				for (int j = 0; j < propertys.size(); j++) {
					try {
						String value = null;
						Class<?> type = types.get(j);
						if(type!=null && type.equals(Date.class)){
							try {
								Date date = (Date) PropertyUtils.getProperty(object, propertys.get(j));
								value = sdf.format(date);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}else {
							value = BeanUtils.getProperty(object, propertys.get(j));
						}
						
						row.createCell(j).setCellValue(value);
					} catch (IllegalAccessException e) {
						logger.error("异常:" + e.getMessage(), e);
					} catch (InvocationTargetException e) {
						logger.error("异常:" + e.getMessage(), e);
					} catch (NoSuchMethodException e) {
						logger.error("异常:" + e.getMessage(), e);
					}
				}
			}
		}

	
		try {
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public static class ExcelObject {
		private List<?> data;
		private String sheetName;
		private List<String> headers = new ArrayList<String>();
		private List<String> propertys = new ArrayList<String>();
		private List<Class<?>> types = new ArrayList<Class<?>>();
		
		public List<?> getData() {
			return data;
		}

		public void setData(List<?> data) {
			this.data = data;
		}

		public String getSheetName() {
			return sheetName;
		}

		public void setSheetName(String sheetName) {
			this.sheetName = sheetName;
		}

		public List<String> getHeaders() {
			return headers;
		}

		public void setHeaders(List<String> headers) {
			this.headers = headers;
		}

		public List<String> getPropertys() {
			return propertys;
		}

		public void setPropertys(List<String> propertys) {
			this.propertys = propertys;
		}

		public List<Class<?>> getTypes() {
			return types;
		}

		public void setTypes(List<Class<?>> types) {
			this.types = types;
		}
	}
}
