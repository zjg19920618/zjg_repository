package com.boomhope.Bill.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Date;

public class FileUtil {
	/**
	 * 文件拷贝
	 * @param form
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public static long copy(File form, File to) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(form);
		FileOutputStream out = new FileOutputStream(to);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		ByteBuffer b = null;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return new Date().getTime() - time;
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else
				length = 2097152;
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}
	
	/**
	 * 文件复制
	 * @param from 源文件
	 * @param to 目标路径
	 * @throws IOException
	 */
	public static void copyFileUsingJava7Files(File from_file, File to_file)throws IOException {

			if (!to_file.getParentFile().exists()){
				to_file.getParentFile().mkdirs();
			}
			if (to_file.exists()) {
				to_file.delete();
			}
			Files.copy(from_file.toPath(), to_file.toPath());
			
	}
	
	public static void main(String[] args) {
		// 源目录
		File from_file = new File("d:\\me_face.bmp");
		
		// 目标目录
		File to_file = new File("d:\\anhm\\me_face_copy7.bmp");
		System.out.println(to_file.getName());
		try {
			copyFileUsingJava7Files(from_file, to_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
