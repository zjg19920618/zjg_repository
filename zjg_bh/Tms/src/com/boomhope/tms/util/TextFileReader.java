package com.boomhope.tms.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class TextFileReader {
	protected static Logger log = Logger.getLogger(TextFileReader.class);
	
	public static String KEY_FTP_SPLIT_CHAR = "||";
	public static String KEY_FTP_ENCODING = "UTF-8";
	
	public static Map<Integer, Object> padding(String filePath, Class<?>... t) throws IOException, InstantiationException, IllegalAccessException {
		return padding(filePath, KEY_FTP_SPLIT_CHAR, t);
	}

	public static <T> List<T> paddingList(String filePath, Class<T> t) throws IOException, InstantiationException, IllegalAccessException {
		return paddingList(filePath, KEY_FTP_SPLIT_CHAR, t);
	}

	public static <T> List<T> paddingList(String filePath, String splitChar, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException {
		List<T> result = new ArrayList<T>();
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		
		LinkedList<Field> fieldList = new LinkedList<Field>();
		sortFields(fieldList, clazz);

		LinkedList<String> lines = new LinkedList<String>();
		
		// 获取数据条目
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RandomAccessFile rFile = new RandomAccessFile(file, "r");
		int code = 0;
		while ((code = rFile.read()) > 0) {
			if (code == '\n') {
				lines.add(new String(out.toByteArray(), "GBK").trim());
				out.reset();
			}
			else{
				out.write(code);
			}
		}
		rFile.close();
		log.info("分裂符:" + splitChar + " 文件内容:信息条数:" + lines.size());
		
		String value;
		while (null != (value = lines.poll())) {
			String[] items = value.split(splitChar.replace("|", "\\|"));
			if(items==null||items[0].length()<10){
				continue;
			}
			T t = clazz.newInstance();
			for (int i = 0, len = items.length; i < len; i++) {
				fieldList.get(i).set(t, items[i]);
			}
			result.add(t);
		}
		
		return result;
	}

	public static Map<Integer, Object> padding(String filePath, String splitChar, Class<?>... clazzes) throws IOException, InstantiationException, IllegalAccessException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		
		
		Map<Integer, Object> result = new HashMap<Integer, Object>();
		
		int index=0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RandomAccessFile rFile = new RandomAccessFile(file, "r");
		int code = 0;
		while ((code = rFile.read()) > 0) {
			if (code == '\n') {
				String line = new String(out.toByteArray(), KEY_FTP_ENCODING).trim();
				out.reset();
				
				try{
					LinkedList<Field> fieldList = new LinkedList<Field>();
					sortFields(fieldList, clazzes[index]);
					
					String[] items = line.split(splitChar);
					Object t = clazzes[index].newInstance();
					for (int i = 0, len = items.length; i < len; i++) {
						fieldList.get(i).set(t, items[i]);
					}
					result.put((index+1), t);
				}
				catch(Exception ex){
					log.error("", ex);
				}
			}
			else{
				out.write(code);
			}
		}
		
		rFile.close();
		
		return result;

	}

	/***
	 * 根据注解初始化属性读取顺序
	 * 
	 * @param clazz
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected static void sortFields(LinkedList<Field> fieldList, Class<?> clazz) throws InstantiationException, IllegalAccessException {
		// 取得序列标记
		Object number = clazz.getAnnotation(Number.class);
		if (null == number){
			throw new AnnotationFormatError("can find Number annotation");
		}
		
		// 排序
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			FieldSort sort = field.getAnnotation(FieldSort.class);
			if (null == sort){
				continue;
			}
			
			field.setAccessible(true);
			fieldList.add(sort.NO(), field);
		}
	}
	
}
