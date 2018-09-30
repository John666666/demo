package com.john.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InternallyDynamicProxy {

    public static void main(String[] args) {
        IStudent stu = new Student();
        int result = stu.add(3, 7);
        System.out.println("非代理方式计算结果: " + result);

        IStudent stu_proxy = (IStudent) Proxy.newProxyInstance(IStudent.class.getClassLoader(),
                new Class<?>[]{IStudent.class}, new LogProxyHandler(new Student()));
        int result2 = stu_proxy.add(3, 7);
        System.out.println("代理方式计算结果： " + result2);
    }
}

class LogProxyHandler implements InvocationHandler {

    private Object target;

    public LogProxyHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("[log] Called methoded: "+method.getName()+", args: " + args);
        Object result = method.invoke(target, args);
        System.out.println("[log] Called methoded: "+method.getName()+", result: " + result);
        return result;
    }
}
