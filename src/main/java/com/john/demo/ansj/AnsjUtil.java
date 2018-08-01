package com.john.demo.ansj;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;

public class AnsjUtil {

	public static void main(String[] args) {
		Result result = ToAnalysis.parse("我是中国人");
		System.out.println(result);
	}
	
}
