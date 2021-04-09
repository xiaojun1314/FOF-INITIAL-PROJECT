package com.fof.component.handler;

import com.fof.common.enums.ResultCode;

/**
 * @className: GlobalException
 * @author: jun
 * @date: 2021-03-27 14:54
 * @Depiction:
 **/
public class GlobalException extends RuntimeException {

    private ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
