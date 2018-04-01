package net.javafxchina.xmdp.core.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * JSON字符串与配置对象映射工具类
 * 
 * @author Victor
 *
 */
public class JSONUtil {
	/**
	 * 根据指定的json字符串生成对应的映射类对象
	 * 
	 * @param json
	 *            json字符串
	 * @param claz
	 *            结果对象的映射类
	 * @return 对应的映射类对象
	 */
	public static <T> T getObject(String json, Class<T> claz) {
		Gson gson = new Gson();
		return gson.fromJson(json, claz);
	}

	/**
	 * 根据指定url对应的文件中的json字符串生成对应的映射类对象
	 * 
	 * @param url
	 *            JSON文件所在位置的URL，一般是类路径下的配置文件
	 * @param claz
	 *            结果对象的映射类
	 * @return 对应的映射类对象
	 * @throws Exception
	 */
	public static <T> T getObject(URL url, Class<T> claz) throws Exception {
		Gson gson = new Gson();
		InputStream in = url.openStream();
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		return gson.fromJson(reader, claz);
	}

	/**
	 * 根据指定的json字符串生成对应的映射类对象列表
	 * 
	 * @param json
	 *            json字符串
	 * @param claz
	 *            结果对象的映射类
	 * @return 对应的映射类对象列表
	 */
	public static <T> List<T> getList(String json, Class<T[]> type) {
		Gson gson = new Gson();
		T[] list = gson.fromJson(json, type);
		return Arrays.asList(list);
	}

	/**
	 * 根据指定url对应的文件中的json字符串生成对应的映射类对象列表
	 * 
	 * @param url
	 *            JSON文件所在位置的URL，一般是类路径下的配置文件
	 * @param claz
	 *            结果对象的映射类
	 * @return 对应的映射类对象列表
	 * @throws Exception
	 */
	public static <T> List<T> getList(URL url, Class<T[]> type) throws Exception {
		Gson gson = new Gson();
		InputStream in = url.openStream();
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		T[] list = gson.fromJson(reader, type);
		return Arrays.asList(list);
	}

}
