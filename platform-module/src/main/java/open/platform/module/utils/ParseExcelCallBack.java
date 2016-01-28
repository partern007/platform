package open.platform.module.utils;

import org.apache.poi.ss.usermodel.Row;

public interface ParseExcelCallBack<T> {

	public T parseObject(Row row);
	
	public T parseObject(Row row,short cellNum);
	
}
