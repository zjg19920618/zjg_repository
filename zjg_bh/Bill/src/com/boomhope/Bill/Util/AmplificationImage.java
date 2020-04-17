package com.boomhope.Bill.Util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * Title:
 * Description:上传图片-小图片放大图片不变型，但会模糊(取决于图片本身的像素)
 * @author mouchunyue
 * @date 2016年11月8日 下午3:14:29
 */
public class AmplificationImage {

	/**
	 * 判断图片是否大于目标尺寸
	 * 
	 * @param srcPath
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static boolean isBigImage(String srcPath, int maxWidth, int maxHeight) {
		BufferedImage bufferedImage = null;
		try {
			File of = new File(srcPath);
			if (of.canRead()) {
				bufferedImage = ImageIO.read(of);
			}
		} catch (Exception e) {
			return false;
		}
		if (bufferedImage != null) {
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if (width > maxWidth && height > maxHeight) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 图片放大的方法（不会变色）
	 * @param inputUrl 图片输入路劲
	 * @param outputUrl 图片输出路劲
	 * @param maxWidth 目标宽
	 * @param maxHeight 目标高
	 * @param proportion 是否等比缩放
	 * @return
	 */
	public static boolean zoomPicture(String inputUrl, String outputUrl,
			int maxWidth, int maxHeight, boolean proportion) {
		try {
			// 获得源文件
			File file = new File(inputUrl);
			if (!file.exists()) {
				return false;
			}
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return false;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) maxWidth;
					double rate2 = ((double) img.getHeight(null))
							/ (double) maxHeight;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate2 : rate1;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = maxWidth; // 输出的图片宽度
					newHeight = maxHeight; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(outputUrl);
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 对图片进行放大（部分图片会变红）
	 * @param srcPath
	 *            原始图片路径(绝对路径)
	 * @param newPath
	 *            放大后图片路径（绝对路径）
	 * @param times
	 *            放大倍数
	 * @return 是否放大成功
	 */

	public static boolean zoomInImage(String srcPath, String newPath,
			int maxWidth, int maxHeight) {
		BufferedImage bufferedImage = null;
		try {
			File of = new File(srcPath);
			if (of.canRead()) {
				bufferedImage = ImageIO.read(of);
			}
		} catch (IOException e) {
			// TODO: 打印日志
			return false;
		}
		if (bufferedImage != null) {
			bufferedImage = zoomInImage(bufferedImage, maxWidth, maxHeight);
			try {
				// TODO: 这个保存路径需要配置下子好一点
				ImageIO.write(bufferedImage, "JPG", new File(newPath)); // 保存修改后的图像,全部保存为JPG格式
			} catch (IOException e) {
				// TODO 打印错误信息
				return false;
			}
		}
		return true;
	}

	/**
	 * 对图片进行放大
	 * 
	 * @param originalImage
	 *            原始图片
	 * @param maxWidth
	 *            目标宽度
	 * @param maxHeight
	 *            目标高度
	 * @return
	 */
	private static BufferedImage zoomInImage(BufferedImage originalImage,
			int maxWidth, int maxHeight) {
		int times = 1; // 放大倍数
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		double sw = (maxWidth * 1.0) / (width * 1.0);
		double sh = (maxHeight * 1.0) / (height * 1.0);
		if (width > maxWidth && height > maxHeight) {
			return originalImage;
		} else if (width < maxWidth && height < maxHeight) {
			if (sw > sh) {
				times = (int) (sw + 0.8);
			} else {
				times = (int) (sh + 0.8);
			}
		} else if (width < maxWidth && height > maxHeight) {
			times = (int) (sw + 0.8);
		} else {
			times = (int) (sh + 0.8);
		}
		int lastW = times * width;
		int lastH = times * height;
		BufferedImage newImage = new BufferedImage(lastW, lastH, originalImage
				.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, lastW, lastH, null);
		g.dispose();
		return newImage;
	}
}