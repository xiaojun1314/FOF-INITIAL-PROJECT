package com.fof.common.util;

public class StringHelper {
	/**非空字符串转换，如果输入参数是Null默认转换为""*/
	public static String null2String(String strIn) {
		return strIn == null ? "" : strIn;
	}
	public static String null2String(Object strIn) {
		return String.valueOf(strIn) == "null" ? "" : String.valueOf(strIn);
	}
}
