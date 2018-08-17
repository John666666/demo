package com.john.demo.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemClassLoader extends ClassLoader {

	private String classPath = "d:/temp/lib";

	public FileSystemClassLoader() {
	}

	public FileSystemClassLoader(String rootPath) {
		this.classPath = rootPath;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = readClass(name);
		if (classData != null) {
			return defineClass(name, classData, 0, classData.length);
		}
		throw new ClassNotFoundException();
	}
	
	private byte[] readClass(String name) throws ClassNotFoundException {
		if (name == null || name.trim().length() < 1) {
			throw new RuntimeException("必须指定要加载的类名");
		}
		String path = null;
		if (name.contains("jar!")) {
			path = jarNameToPath(name);
		} else {
			path = classNameToPath(name);
		}
		byte[] buffer = new byte[2048];
		InputStream in = null;
		ByteArrayOutputStream out = null;
		int len = -1;
		try {
			in = new FileInputStream(path);
			out = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			return out.toByteArray();
		} catch (FileNotFoundException e) {
			throw new ClassNotFoundException("无法找到类：" + path);
		} catch (IOException e) {
			throw new ClassNotFoundException("加载类出错" + path);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private String jarNameToPath(String name) {
		
		String jarPath = name.substring(0, name.indexOf(".jar!") + ".jar".length() + 1);
		
		String className = name.substring(name.indexOf(".jar!") + ".jar!".length());
		
		return "file://" + classPath + jarPath + "/" + className.replace('.', '/') + ".class";
	}

	private String classNameToPath(String className) {
		return classPath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
	}

}
