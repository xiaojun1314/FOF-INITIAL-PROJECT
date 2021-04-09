package com.fof.common.enums;

/**
 * @Author: Hutengfei
 * @Description: 返回码定义
 * 规定:
 * #1表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * @Date Create in 2019/7/22 19:28
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功",1),

    /* 默认失败 */
    COMMON_FAIL(999, "失败",1),
    BIND_ERROR(500300, "参数校验异常：%s",1),
    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效",1),
    PARAM_IS_BLANK(1002, "参数为空",1),
    PARAM_TYPE_ERROR(1003, "参数类型错误",1),
    PARAM_NOT_COMPLETE(1004, "参数缺失",1),
    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录",1),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期",1),
    USER_CREDENTIALS_ERROR(2003, "密码错误",1),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期",1),
    USER_ACCOUNT_DISABLE(2005, "账号不可用",1),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定",1),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在",1),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在",1),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线",1),
    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限",1),
    UNKNOWN_ERROR(4000, "未知错误",0),
    COMMIT_FAIL(4001, "操作失败",0);
    private Integer code;
    private String message;
    private Integer showType; //错误显示类型：0；1消息.警告; 2 消息.错误；4通知；9页

    ResultCode(Integer code, String message,Integer showType) {
        this.code = code;
        this.message = message;
        this.showType = showType;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", showType=" + showType +
                '}';
    }

}
