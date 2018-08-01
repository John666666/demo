package com.john.demo;

import java.lang.reflect.Method;

import com.john.demo.classloader.FileSystemClassLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       try {
		
			ClassLoader cl = App.class.getClassLoader();
			while (cl != null) {
				System.out.println(cl);
				cl = cl.getParent();
			}
			FileSystemClassLoader fccl = new FileSystemClassLoader();
			Class<?> johnTest = fccl.loadClass("JohnTest");
			Object jt1 = johnTest.newInstance();
			
			FileSystemClassLoader fccl2 = new FileSystemClassLoader();
			Class<?> johnTest2 = fccl.loadClass("JohnTest");	//只有用同一个ClassLoader defineClass的才被JVM认为是同一个类
//			Class<?> johnTest2 = fccl2.loadClass("JohnTest");
			Object jt2 = johnTest2.newInstance();

			Method setInstance = johnTest.getMethod("setInstance", johnTest);
			setInstance.invoke(jt1, jt2);
			System.out.println("setInstance: " + jt2);
			
			Method getInstance = johnTest.getMethod("getInstance", null);
			Object ret = getInstance.invoke(jt1, null);
			System.out.println("getInstance: " + ret);
			
			/*Method sayHelloM = johnTest.getMethod("sayHello", String.class);
			sayHelloM.invoke(null, "John");*/
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}
