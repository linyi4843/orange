package com.linyi.mall.orange.web.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.linyi.mall.orange.common.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * create with IDEA
 *
 * @author linyi
 * @email linyi4843@gmail.com
 */
@Aspect
@Component//定义一个切面
@Slf4j
public class LogRecordAspect {
    private final String string = "execution(* com.linyi.mall.orange.web.controller.*.*(..))";
    private static final String UTF_8 = "utf-8";

    // 定义切点Pointcut
    @Pointcut(string)
    public void excudeService() {
    }

    //执行切点 之前
    @Before("excudeService()")
    public void exBefore(JoinPoint pjp) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        String queryString = request.getQueryString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        Object[] args = pjp.getArgs();
        String params = "";
        try {
            params = getParams(args, method, params, queryString);
            log.info(" \n request : \n ip -> {}  \n requestMethod -> {}, \n url -> {} \n params -> {} \n", IpUtils.getIpAddr(request), method, uri, params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("log error !!", e);
        }

    }

    // 通知（环绕）
    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();


        long endTime = System.currentTimeMillis();
//        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        try {
            long startTime = (long) request.getAttribute("startTime");
            //获取请求参数集合并进行遍历拼接
            params = getParams(args, method, params, queryString);
            log.info("\n response : \n ip -> {}, \n requestMethod -> {}, \n url -> {}, \n params -> {}, \n responseBody -> {}, \n elapsed -> {}ms. \n", IpUtils.getIpAddr(request), method, uri, params,
                    JSON.toJSONString(result, SerializerFeature.WriteMapNullValue), (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("log error !!", e);
        }
        return result;
    }

    private String getParams(Object[] args, String method, String params, String queryString) throws UnsupportedEncodingException {
        if (queryString == null){
            queryString = "";
        }
        if (args.length > 0) {
            if ("POST".equals(method)) {
                Object object = args[0];
                params = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
            } else if ("GET".equals(method)) {
                params = queryString;
            }
            return URLDecoder.decode(params, UTF_8);
        }
        return "";
    }

    //    执行切点之后
    @After("excudeService()")
    public void exAfter(JoinPoint joinPoint) {
    }

}
