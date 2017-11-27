package com.travelsky.ypb.publics;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>Tilte:随机数工具类</p>
 * <p>Description:取Random随机数 将字母转换数字 取16位</p>
 * @author huc
 */
public final class RandomMathId {
	private RandomMathId(){}
	private static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put("A", "1");
		map.put("B", "2");
		map.put("C", "3");
		map.put("D", "4");
		map.put("E", "5");
		map.put("F", "6");
		map.put("G", "7");
		map.put("H", "8");
		map.put("I", "9");
		map.put("J", "0");
		map.put("K", "1");
		map.put("L", "2");
		map.put("M", "3");
		map.put("N", "4");
		map.put("O", "5");
		map.put("P", "6");
		map.put("Q", "7");
		map.put("R", "8");
		map.put("S", "9");
		map.put("T", "0");
		map.put("U", "1");
		map.put("V", "2");
		map.put("W", "3");
		map.put("X", "4");
		map.put("Y", "5");
		map.put("Z", "6");
	}
	
	/**
	 * 获取一个Long型的随机数
	 * @return
	 */
	public static long getRandomId(){
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		char[] chars = uuid.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			sb.append(map.containsKey(String.valueOf(c)) ? map.get(String.valueOf(c)) : c);
		}
		return Long.parseLong(sb.toString().substring(4,20));
	}
}
