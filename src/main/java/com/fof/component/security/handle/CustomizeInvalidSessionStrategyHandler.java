package com.fof.component.security.handle;

import com.alibaba.fastjson.JSON;
import com.fof.common.bean.JsonResult;
import com.fof.common.enums.ResultCode;
import com.fof.common.util.ResultTool;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomizeInvalidSessionStrategyHandler implements InvalidSessionStrategy {

    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        System.out.println("session失效");

        JsonResult result = ResultTool.fail(ResultCode.USER_ACCOUNT_EXPIRED);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
