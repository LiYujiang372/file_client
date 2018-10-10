package com.demo.file_client.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Utils {
	
	/**
	 * 线程睡眠
	 * @param s 睡眠时间
	 */
	public static void threadSleep(int s) {
		try {
			System.err.println(Thread.currentThread().getName() + "---正在睡眠");
			Thread.sleep(s);
		} catch (InterruptedException e) {}
	}
	
	/**
	 * 集合是否为空
	 * @param c
	 * @return
	 */
	public static boolean isBlank(Collection<?> c) {
		return c == null || c.size() == 0;
	}
	
	/**
	 * 判断字符串是否为(null "")
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	/**
	 * 集合中是否包含值中的一个
	 * @param list
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean containOne(Collection<T> list, T... t) {
		for (T obj : t) {
			if (list.contains(obj)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 集合转化为字符串
	 * @param c
	 * @return
	 */
	public static String toString(Collection<?> c) {
		if (isBlank(c)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Object object : c) {
			sb.append(object + ",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 生成0~max的序号的集合
	 * 0,1,2,3,4,5
	 * @param max >= 0
	 * @return
	 */
	public static List<Integer> getList(int max){
		List<Integer> list = new ArrayList<>(max + 1);
		for (int i = 0; i <= max; i++) {
			list.add(i);
		}
		return list;
	}
	
	/**
	 * 使用base64对字节数组进行编码
	 * @param bytes 字节数组
	 */
	public static String encodeBytes(byte[] bytes) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bytes);
	}
	
	/**
	 * 使用base64对字符串进行解码
	 * @param bytes 字节数组
	 */
	public static byte[] decodeStr(String str) {
		Decoder decoder = Base64.getDecoder();
		return decoder.decode(str);
	}
	
	/**
	 * 获取随机Long值
	 * @return
	 */
	public static long getRandomLong() {
		return (long) (Math.random() * Long.MAX_VALUE);
	}
	
	public static void printThreadInfo(String message) {
		System.err.println(Thread.currentThread().getName() + Thread.currentThread().getId() + "-->" + message);
	}

}
