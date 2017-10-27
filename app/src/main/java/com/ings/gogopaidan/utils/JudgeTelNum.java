package com.ings.gogopaidan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeTelNum {
	public static boolean isTelNum(String telNum) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(telNum);
		return m.matches();
	}
}
