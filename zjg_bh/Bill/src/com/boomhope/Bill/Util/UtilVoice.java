package com.boomhope.Bill.Util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 语音工具类
 * @author zy
 *
 */
public class UtilVoice {

	/**
	 * 语音朗读
	 * @param voiceFile
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public SourceDataLine voice(String voiceFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		// 获取音频输入流
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(voiceFile));
		// 获取音频编码对象
		AudioFormat audioFormat = audioInputStream.getFormat();
		// 设置数据输入
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat, AudioSystem.NOT_SPECIFIED);
		SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();

		/*
		 * 从输入流中读取数据发送到混音器
		 */
		int count;
		byte tempBuffer[] = new byte[1024];
		while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
			if (count > 0) {
				sourceDataLine.write(tempBuffer, 0, count);
			}
		}
		return sourceDataLine;
	}
	
	/**
	 * 
	 * @param 语音关闭
	 */
	public void close(SourceDataLine sourceDataLine){
		// 清空数据缓冲,并关闭输入
		sourceDataLine.drain();
		sourceDataLine.close();
	}
}
