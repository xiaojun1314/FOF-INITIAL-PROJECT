package com.fof.component.handler;

import com.fof.common.bean.JsonResult;
import com.fof.common.enums.ResultCode;
import com.fof.common.util.ResultTool;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.util.List;

/**
 * @className: GlobalExceptionHandler
 * @author: jun
 * @date: 2021-03-27 14:43
 * @Depiction: 全局异常控制类
 **/
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //自定义异常类
    @ExceptionHandler(value = GlobalException.class)
    public JsonResult<String> globalExceptionHandler(HttpServletRequest request, GlobalException e) {
        logger.error(e.getResultCode().getMessage());
        return ResultTool.fail(e.getResultCode());
    }
    //参数检测不合格
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResult<String> bindExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        logger.error(e.getClass().toString());

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        //此处仅取第一个
        // ObjectError objectError = errors.get(0);
        for (ObjectError error : errors) {
            errorMsg.append(error.getDefaultMessage()).append(" ");
        }
        // String errorMsg = objectError.getDefaultMessage();
        //填充具体异常
        return ResultTool.fail(ResultCode.BIND_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public JsonResult<String> defaultExceptionHandler(HttpServletRequest request, Exception e) {
        logger.error(e.getClass().toString());
        logger.error(e.toString());
        e.printStackTrace();
        return ResultTool.fail(ResultCode.UNKNOWN_ERROR);
    }
}
