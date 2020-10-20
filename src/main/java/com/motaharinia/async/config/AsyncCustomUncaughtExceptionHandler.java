package com.motaharinia.async.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-09-02<br>
 * Time: 12:02:24<br>
 * Description:<br>
 *     کلاس تنظیمات خطاهای ناهمزمانی در پروژه
 */
public class AsyncCustomUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("Method Name::" + method.getName());
        System.out.println("Exception occurred::" + ex);
    }
}
