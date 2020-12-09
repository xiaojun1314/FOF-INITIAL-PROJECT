package com.fof.common.util;

public class Constants {
    //逻辑删除标志【0--未删除，1--已删除】
    public static final String    DELFLG_N  = "0";

    public static final String    DELFLG_Y  = "1";

    
    public static final String    SUCCESS  = "1";
    public static final String    FAIL  = "0";
    /**权限名称*/
    public static enum AuthorityTypeName {
        _0("菜单的访问权限"), _1("页面元素的可见性控制"), _2("功能模块的操作权限"), _3("文件的修改权限");
        AuthorityTypeName(String value) {
            this.value = value;
        }
        private String value;
        public String getValue() {
            return value;
        }
    }
     /**权限类型*/
    public static enum AuthorityType {
        MENU("0"), ELEMENT("1"), OPERATION("2"),FILE("3");
        AuthorityType(String value) {
            this.value = value;
        }
        private String value;
        public String getValue() {
            return value;
        }
    }

   public static enum TriggerStateName {
	   All("暂停"),PAUSED("暂停"),ACQUIRED("正常执行"),WAITING("正常执行"),ERROR("异常"),BLOCKED("正常执行");
	   TriggerStateName(String value) {
           this.value = value;
       }
       private String value;
       public String getValue() {
           return value;
       }
   }
}
