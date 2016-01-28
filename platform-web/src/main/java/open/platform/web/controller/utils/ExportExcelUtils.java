package open.platform.web.controller.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import open.platform.module.utils.ExportParser;
import open.platform.module.utils.ExportUtil;
import open.platform.module.utils.ExportUtil.ExcelObject;

public class ExportExcelUtils<T> {
//	private Class<T> clazz;
//	
//	@SuppressWarnings("unchecked")
//    public ExportExcelUtils() {
//        @SuppressWarnings("rawtypes")
//        Class clazz = getClass();
//
//        while (clazz != Object.class) {
//            Type t = clazz.getGenericSuperclass();
//            if (t instanceof ParameterizedType) {
//                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
//                if (args[0] instanceof Class) {
//                    this.clazz = (Class<T>) args[0];
//                    break;
//                }
//            }
//            clazz = clazz.getSuperclass();
//        }
//	}

	public void exportDataAsExcel(T t,List<T> result, HttpServletResponse response)
			throws IOException {
		List<String> headers = new ArrayList<String>();
		List<String> propertys = new ArrayList<String>();
		List<Class<?>> types = new ArrayList<Class<?>>();
		// 传入数据所在的类，得到需要导出的属性和头
		ExportParser.getNeedExportInfo(t.getClass(), headers, propertys, types);

		//封装所有必须信息
		List<ExcelObject> excelObjects = new ArrayList<ExportUtil.ExcelObject>();
		ExcelObject excelObject = new ExcelObject();
		excelObject.setData(result);
		excelObject.setSheetName("数据统计");
		excelObject.setHeaders(headers);
		excelObject.setPropertys(propertys);
		excelObject.setTypes(types);
		excelObjects.add(excelObject);
		
		//导出
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String("数据统计".getBytes("gb2312"), "iso8859-1") + ".xls");
		response.setCharacterEncoding("utf-8");
		OutputStream outputStream = response.getOutputStream();
		ExportUtil.exportExcel(outputStream, excelObjects);
	}

}
