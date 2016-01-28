package open.platform.web.controller.utils;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import open.platform.module.utils.ExcelUtils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DownloadUtils {
	
	public static void downloadExcel(HttpServletRequest request, HttpServletResponse response, String outputName,
			XSSFWorkbook wordbook) throws Exception {
		response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			outputName = URLEncoder.encode(outputName, "UTF-8");
		} else {
			outputName = new String(outputName.getBytes("UTF-8"), "ISO8859-1");
		}
		response.setHeader("content-disposition", "attachment;filename=" + outputName);
		if(wordbook!=null){
			wordbook.write(response.getOutputStream());
		}
	}
	public static void downloadExeclTemplate(HttpServletRequest request, HttpServletResponse response, String templateName,
			LinkedHashMap<String,String> headerMap) throws Exception{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("模板");
		ExcelUtils.writeHeaders(headerMap, sheet);
		response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			templateName = URLEncoder.encode(templateName, "UTF-8");
		} else {
			templateName = new String(templateName.getBytes("UTF-8"), "ISO8859-1");
		}
		response.setHeader("content-disposition", "attachment;filename=" + templateName);
		if(workbook!=null){
			workbook.write(response.getOutputStream());
		}
	}
	
	public static void downloadTextTemplate(HttpServletRequest request,
			HttpServletResponse response, String templateName, String header)
			throws Exception {
		response.reset();// 不加这一句的话会出现下载错误
		response.setHeader("Content-disposition", "attachment; filename="
				+ templateName);// 设定输出文件头
		//response.setContentType("text/x-plain");// 定义输出类型
		response.setContentType("application/octet-stream; charset=utf-8");
		OutputStream out = response.getOutputStream();
		out.write(header.getBytes());
		out.flush();
		out.close();
	}
}
