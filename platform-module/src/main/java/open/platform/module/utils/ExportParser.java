package open.platform.module.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExportParser {

	/**
	 * 根据传入的数据类信息，得到导出需要的数据信息
	 * 
	 * @param class1
	 * @param headers
	 * @param propertys
	 */
	public static void getNeedExportInfo(Class<?> class1, List<String> headers, List<String> propertys,
			List<Class<?>> types) {
		if(headers == null){
			headers = new ArrayList<String>();
		}
		if(propertys == null){
			propertys = new ArrayList<String>();
		}
		if(types == null){
			types = new ArrayList<Class<?>>();
		}
		
		int deepth = 0;
		Field[] fields = null;
		Export annotation = null;
		while (class1 != null && deepth < 10) {
			fields = class1.getDeclaredFields();
			for (Field field2 : fields) {
				annotation = field2.getAnnotation(Export.class);
				if (null != annotation) {
					headers.add(annotation.header());
					propertys.add(field2.getName());
					types.add(field2.getType());
				}
			}
			class1 = class1.getSuperclass();
		}
	}

	public static void getProper2HeaderMap(Class<?> class1, LinkedHashMap<String, String> proper2HeaderMap) {
		if(proper2HeaderMap == null){
			proper2HeaderMap = new LinkedHashMap<String, String>();
		}
		
		int deepth = 0;
		Field[] fields = null;
		Export annotation = null;
		while (class1 != null && deepth < 10) {
			fields = class1.getDeclaredFields();
			for (Field field2 : fields) {
				annotation = field2.getAnnotation(Export.class);
				if (null != annotation) {
					proper2HeaderMap.put(field2.getName(), annotation.header());
				}
			}
			class1 = class1.getSuperclass();
		}
	}
}
