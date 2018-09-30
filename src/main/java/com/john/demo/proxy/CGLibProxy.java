package com.john.demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        enhancer.setCallback(new MyLogIntercepor());

        IStudent stu = (IStudent) enhancer.create();
        System.out.println("通过cglib代理方式调用：" + stu.add(3, 7));
    }
}

class MyLogIntercepor implements MethodInterceptor {
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("[log] Called methoded: "+method.getName()+", args: " + args);
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("[log] Called methoded: "+method.getName()+", result: " + result);
        return result;
    }
}