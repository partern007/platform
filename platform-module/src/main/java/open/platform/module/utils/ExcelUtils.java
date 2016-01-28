package open.platform.module.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	
	/**
	 * 通过反射将标题及数据写入excel.
	 * 
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws IOException
	 */
	public static <T> XSSFWorkbook writeStatData2Excel(List<T> data, LinkedHashMap<String, String> headerMap)
			throws IOException {
		if (CollectionUtils.isEmpty(data)) {
			return null;
		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("报表");

		// 写入标题
		writeHeaders(headerMap, sheet);

		// 写入各行数据
		wirteColumns(data, headerMap, sheet);
		return workbook;
	}
	
	/**
	 * 写入标题.
	 * 
	 * @param headerMap
	 * @param sheet
	 */
	public static void writeHeaders(LinkedHashMap<String, String> headerMap, XSSFSheet sheet) {
		XSSFRow row = sheet.createRow(0);
		int cellIndex = 0;
		for (String headerName : headerMap.values()) {
			row.createCell(cellIndex++).setCellValue(headerName);
		}
	}
	

	public static <T> List<T> parseObject(ParseExcelCallBack<T> parseExcelCallBack, InputStream in, String fileType)throws IOException{
		List<T> retList = null;
		try{
			Workbook wb = getWorkbook(in, fileType);
			Sheet sheet = wb.getSheetAt(0); 
			retList = parseObject(parseExcelCallBack, sheet);
		}catch (IOException e) {
			log.error("解析excel错误!", e);
		}finally{
			closeInputStream(in);
		}
		return retList;
	}
	public static <T> List<T> parseObject(ParseExcelCallBack<T> parseExcelCallBack, Sheet sheet){
		List<T> retList = new ArrayList<T>();
		int rowNum = sheet.getPhysicalNumberOfRows(); // 第i个sheet的总行数
		
		for (int j = 1; j < rowNum; j++) {// 从第二行开始遍历，第一行为title
			Row row = sheet.getRow(j);
			if(row != null){
				T t = parseExcelCallBack.parseObject(row);
				if (t!=null){				
					retList.add(t);
				}
				
				if(CollectionUtils.isNotEmpty(retList) && retList.size() > 1000){
					throw new RuntimeException("你所上传的文件过大，不能超过1000行数据!");
				}
			}
		}
		return retList;
	}
	/**
	 * 写入各行数据.
	 * 
	 * @param data
	 * @param headerMap
	 * @param sheet
	 */
	private static <T> void wirteColumns(List<T> data, LinkedHashMap<String, String> headerMap, XSSFSheet sheet) {
		XSSFRow row;
		int cellIndex;
		for (int i = 0; i < data.size(); i++) {
			Object obj = data.get(i);
			row = sheet.createRow(i + 1);
			Class clazz = obj.getClass();
			cellIndex = 0;
			for (String fieldName : headerMap.keySet()) {
				String getMethodName = "get" + StringUtils.capitalize(fieldName);
				Method method;
				Object value;
				try {
					method = clazz.getMethod(getMethodName, null);
					value = method.invoke(obj);
					String cellValue = "";
					if (value != null) {
						if (value instanceof Date) {
							cellValue = DateUtil.dateFormat((Date) value, DateUtil.DATEFORMAT_SECOND);
						} else {
							cellValue = value.toString();
						}
					}
					row.createCell(cellIndex++).setCellValue(cellValue);
				} catch (Exception e) {
					log.error("writeStatData2Excel exceptioni:{}", e);
				}
			}
		}
	}
	
	public static String getCellValue(Cell cell){
		if(cell == null){
			return "";
		}
		int cellType = cell.getCellType();
		if (Cell.CELL_TYPE_STRING == cellType) {
			return cell.getStringCellValue();
		} else if (Cell.CELL_TYPE_NUMERIC == cellType) {
			return Double.toString(cell.getNumericCellValue());
		}
		return "";
	}
	
	
	private static void closeInputStream(InputStream in){
		if(in != null){
			try {
				in.close();
			} catch (IOException e) {
				log.error("关闭流错误!", e);
			}
		}
	}
	
	private static Workbook getWorkbook(InputStream in, String fileType) throws IOException{
		Workbook wb = null;
		if (fileType.equalsIgnoreCase("xlsx")) {
			wb = new XSSFWorkbook(in);
		} else {
			wb = new HSSFWorkbook(in);
		}
		return wb;
	}
}
