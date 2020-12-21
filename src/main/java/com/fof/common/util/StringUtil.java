package com.fof.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringUtil {

    public static String arrayToStr(String[] arr) {
        if (arr != null && arr.length > 0) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                buffer.append(arr[i]).append(",");
            }
            return buffer.toString().substring(0, buffer.lastIndexOf(","));
        }
        return null;
    }

    public static String[] strToArray(String str) {
        if (str != null && !"".equals(str)) {
            String[] arr = split(str);
            return arr;
        }
        return null;
    }

    public static String[] split(String str) {
        Collection<String> result = new ArrayList<String>();
        String curStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                result.add(curStr);
                curStr = "";
            } else {
                curStr += str.charAt(i);
            }
        }
        result.add(curStr);
        return result.toArray(new String[result.size()]);
    }

    public static boolean isBlank(String value) {
        return null == value || value.trim().length() == 0;
    }

    public static boolean isNotBlank(String value) {
        return null != value && value.trim().length() != 0;
    }

    public static boolean isNotBlank(Object value) {
        return isNotBlank(parseString(value));
    }

    public static String parseString(Object val) {
        if (null == val) {
            return "";
        }
        return val.toString();
    }

    public static boolean isBlank(Object value) {
        return isBlank(parseString(value));
    }

    public static String concat(Object s, Object d) {
        return parseString(s).concat(parseString(d));
    }

    public static boolean equals(Object s, Object d) {
        return parseString(s).equals(parseString(d));
    }

    public static boolean isBlank(String[] value) {
        return null == value || value.length == 0;
    }

    public static String[] split(String str, String symbol) {
        if (str != null && !"".equals(str)) {
            String[] arr = str.split(symbol);
            return arr;
        }
        return null;
    }

    public static List<String> convertStr2List(String str) {
        if (str != null && !"".equals(str)) {
            String[] arr = str.split(",");
            return convertArray2List(arr);
        }
        return null;
    }

    public static List<String> convertArray2List(String[] arrs) {
        List<String> list = new ArrayList<String>();
        if (null != arrs) {
            for (String arr : arrs) {
                list.add(arr);
            }
        }
        return list;
    }

    public static String join(Collection<String> cols, String symbol) {
        StringBuffer buffer = new StringBuffer();
        if (null != cols) {
            int i = 0;
            for (String col : cols) {

                if (i != 0) {
                    buffer.append(symbol);
                }
                buffer.append(col);
                i++;
            }
        }
        return buffer.toString();
    }


    public static String formatObjectString(Object o) {
        String str = "";
        if (null != o) {
            str = String.valueOf(o);
        }
        return str;
    }

    public static String formatLString2String(Object o) {
        String[] str = null;
        if (null != o) {
            str = (String[]) o;
        }

        if (null != str && str.length > 0) {
            return str[0];
        } else {
            return "";
        }
    }

    /**
     * 转换多选框的值
     * @param o
     * @return
     */
    public static String formatCheckBoxValue(Object o) {
        String str = "";
        if (null != o) {
            str = (String) o;
        } else {
            return "0";
        }

        if ("on".equals(str)) {
            return "1";
        } else {
            return "0";
        }
    }
    
    public static String replaceAllString(String source, String strReplaced, String strReplace)
       {
         if ((isEmpty(source)) || (isEmpty(strReplaced)) || (strReplace == null)) {
           return source;
         }
        StringBuffer buf = new StringBuffer(source.length());
         int start = 0; int end = 0;
         while ((end = source.indexOf(strReplaced, start)) != -1) {
          buf.append(source.substring(start, end)).append(strReplace);
           start = end + strReplaced.length();
         }
         buf.append(source.substring(start));
         return buf.toString();
       }
    
    public static boolean isEmpty(String str)
       {
         return ((str == null) || (str.length() == 0));
      }

}
