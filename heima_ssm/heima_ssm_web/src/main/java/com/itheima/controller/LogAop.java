package com.itheima.controller;

import com.itheima.domain.SysLog;
import com.itheima.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

//配置切面，用于做记录访问日志
@Component
@Aspect
public class LogAop {
    //注入请求域获取访问ip地址
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysLogService;

    private Date visitTime;//开始访问的时间
    private Class clazz;//访问的类
    private Method method;//访问的方法


    //配置前置通知，主要是获取访问开始时间，执行的类和执行的类中的方法
    @Before("execution(* com.itheima.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date();//记录开始访问时间
        clazz = jp.getTarget().getClass();//记录访问的类
        String methodName = jp.getSignature().getName();//获取访问的方法名
        Object[] args = jp.getArgs();//获取访问的方法参数

        //获取到具体的访问方法
        if ( args== null || args.length == 0) {
            method = clazz.getMethod(methodName);//根据方法名获取无参构造方法
        } else {
            Class[] classes = new Class[args.length];//创建一个数组，数组的长度和访问方法的参数长度一致
            //遍历数组
            for (int i = 0; i < args.length; i++) {
                //把获取到的访问方法参数给新数组赋值
                classes[i] = args[i].getClass();
            }
            clazz.getMethod(methodName, classes);//获取带参构造方法
        }

    }

    //配置后置通知，
    @After("execution(* com.itheima.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        //获取访问的时长
        long time = new Date().getTime() - visitTime.getTime();//退出时间减去开始访问的时间

        String url = "";
        //获取url,访问的类不为空，方法不为空并且不是访问记录类
        if (clazz != null && method != null && clazz != LogAop.class) {
            //获取类上@RequestMapping 注解中的值
            RequestMapping clazzAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (clazzAnnotation != null) {
                String[] values = clazzAnnotation.value();

                //获取方法上的@RequestMapping 注解中的值
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (annotation != null) {
                    String[] value = annotation.value();

                    //类名拼接方法名就能获取到访问的具体路径
                    url = values[0] + value[0];


                    //获取访问的ip地址
                    String ip = request.getRemoteAddr();

                    SecurityContext context = SecurityContextHolder.getContext();//获取security核心容器
                    /*也可以中session域中获取security的user登陆对象
                     User spring_security_context = (User) request.getSession().getAttribute("spring_security_context");
                     通过user对象获取登陆用户名
                     String username1 = spring_security_context.getUsername();*/

                    //这里的user对象不是数据库中的用户表的实体类对象，是security框架提供的对象，存放了当前登陆的用户信息
                    User user = (User) context.getAuthentication().getPrincipal();//从容器中获取到当前登陆信息

                    String username = user.getUsername();//用登陆信息获取访问用户的用户名


                    //把获取到的相关信息封装到SysLog对象中
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);//封装访问时长
                    sysLog.setIp(ip);//封装ip地址
                    sysLog.setMethod("[类名]" + clazz.getName() + "方法名" + method.getName());//封装执行方法
                    sysLog.setUrl(url);//封装访问路径
                    sysLog.setUsername(username);//封装访问用户名
                    sysLog.setVisitTime(visitTime);//封装开始访问时间

                    //调用添加方法往日志表添加数据
                    sysLogService.save(sysLog);

                }

            }
        }
    }

}
