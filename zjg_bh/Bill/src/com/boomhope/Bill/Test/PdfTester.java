package com.boomhope.Bill.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.boomhope.Bill.Util.FtpUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PdfTester {


    public File Pdf(String imagePath, String mOutputPdfFileName) {
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
                    mOutputPdfFileName));
            doc.open();
            // 解决中文问题  
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            //样式
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL); 
            doc.add(new Paragraph("存单",FontChinese));
            doc.add(new Paragraph("—————————————————————————————————————————————",FontChinese));
            Image png1 = Image.getInstance(imagePath);
            float heigth = png1.getHeight();
            float width = png1.getWidth();
            int percent = this.getPercent2(heigth, width);
            png1.setAlignment(Image.MIDDLE);
            png1.setAlignment(Image.TEXTWRAP);
            png1.scalePercent(percent + 3);
            doc.add(png1); 
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
//            doc.newPage();
            String pdfUrl1 = "C:\\20160818170556953_rear.JPG";
            Image png11 = Image.getInstance(pdfUrl1);
            float heigth1 = png1.getHeight();
            float width1 = png1.getWidth();
            int percent1 = this.getPercent2(heigth1, width1);
            png11.setAlignment(Image.MIDDLE);
            png11.setAlignment(Image.TEXTWRAP);
            png11.scalePercent(percent1 + 3);
            doc.add(png11);
            //换页
//            doc.newPage();
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            //图片标题
          // this.handleText(writer, "This is a test", "red", 400, 725, 0);
            doc.add(new Paragraph("本人身份证",FontChinese));
            doc.add(new Paragraph("—————————————————————————————————————————————",FontChinese));
            String pdfUrl11 = "C:\\66.jpg";
            Image png111 = Image.getInstance(pdfUrl11);
            float heigth11 = png1.getHeight();
            float width11 = png1.getWidth();
            int percent11 = this.getPercent0(heigth11, width11);
            png111.setAlignment(Image.MIDDLE);
            png111.setAlignment(Image.TEXTWRAP);
            png111.scalePercent(percent11 + 3);
            doc.add(png111);
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
          
          doc.add(new Paragraph("代理人身份证",FontChinese));
          doc.add(new Paragraph("—————————————————————————————————————————————",FontChinese));
          String pdfUrl11s = "C:\\66.jpg";
          Image png111s = Image.getInstance(pdfUrl11s);
          float heigth11s = png1.getHeight();
          float width11s = png1.getWidth();
          int percent11s = this.getPercent0(heigth11s, width11s);
          png111s.setAlignment(Image.MIDDLE);
          png111s.setAlignment(Image.TEXTWRAP);
          png111s.scalePercent(percent11s + 3);
          doc.add(png111s);
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          
//          String pdfUrl11sf = "C:\\fj.JPG";
//          Image png111sf = Image.getInstance(pdfUrl11sf);
//          float heigth11sf = png1.getHeight();
//          float width11sf = png1.getWidth();
//          int percent11sf = this.getPercent1(heigth11sf, width11sf);
//          png111sf.setAlignment(Image.MIDDLE);
//          png111sf.setAlignment(Image.TEXTWRAP);
//          png111sf.scalePercent(percent11sf + 3);
//          doc.add(png111sf);
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
//          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("身份信息",FontChinese));
          doc.add(new Paragraph("—————————————————————————————————————————————",FontChinese));
          doc.add(new Paragraph("姓名:奥巴马",FontChinese));
          doc.add(new Paragraph("性别:男 ",FontChinese));
          doc.add(new Paragraph("名族:汉",FontChinese));
          doc.add(new Paragraph("出生日期:1925-06-02 ",FontChinese));
          doc.add(new Paragraph("出生地址:夏威夷州檀香山 ",FontChinese));
          doc.add(new Paragraph("身份证编号:111151542451254545",FontChinese));
          doc.add(new Paragraph("有效日期:1925.06.02-2016.09.09",FontChinese));
          doc.add(new Paragraph("发证机关:夏威夷州檀香山公安局",FontChinese));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          
          String pdfUrl11sa = "C:\\a.jpg";
          Image png111sa = Image.getInstance(pdfUrl11sa);
          float heigth11sa = png1.getHeight();
          float width11sa = png1.getWidth();
          int percent11sa = this.getPercent3(heigth11sa, width11sa);
          png111sa.setAlignment(Image.MIDDLE);
          png111sa.setAlignment(Image.TEXTWRAP);
          png111sa.scalePercent(percent11sa + 3);
          doc.add(png111sa);
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("代理人身份信息",FontChinese));
          doc.add(new Paragraph("—————————————————————————————————————————————",FontChinese));
          doc.add(new Paragraph("姓名:凤姐",FontChinese));
          doc.add(new Paragraph("性别:女",FontChinese));
          doc.add(new Paragraph("名族:汉",FontChinese));
          doc.add(new Paragraph("出生日期:1985-06-02 ",FontChinese));
          doc.add(new Paragraph("出生地址:中国北京 ",FontChinese));
          doc.add(new Paragraph("身份证编号:6666661542451254545",FontChinese));
          doc.add(new Paragraph("有效日期:1985.06.02-2016.09.09",FontChinese));
          doc.add(new Paragraph("发证机关:中国北京公安局",FontChinese));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
          doc.add(new Paragraph("\n"));
//          String pdfUrl11saq = "C:\\a.jpg";
          Image png111saq = Image.getInstance("pic/a.jpg");
          float heigth11saq = png1.getHeight();
          float width11saq = png1.getWidth();
          int percent11saq = this.getPercent3(heigth11saq, width11saq);
          png111saq.setAlignment(Image.MIDDLE);
          png111saq.setAlignment(Image.TEXTWRAP);
          png111saq.scalePercent(percent11saq + 3);
          doc.add(png111saq);

            doc.close();
            System.out.println("文档创建成功"); 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);
        if (!mOutputPdfFile.exists()) {
            mOutputPdfFile.deleteOnExit();
            return null;
        }
        return mOutputPdfFile;
    }
    //图片设置小
    public int getPercent1(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 1000 / h * 100;
        } else {
            p2 = 1000 / w * 110;
        }
        p = Math.round(p2);
        return p;
    }
    //图片设置小
    public int getPercent3(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 1000 / h * 100;
        } else {
            p2 = 1000 / w * 110;
        }
        p = Math.round(p2);
        return p;
    }
    //单张大图片平铺
    private int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 95;
        p = Math.round(p2);
        return p;
    }
    //单张大图片平铺
    private int getPercent0(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 67;
        p = Math.round(p2);
        return p;
    }
    public static void main(String[] args) {
    	PdfTester gp = new PdfTester();
//    	String title=DateUtil.getDateTime("yyyyMMddhhmmss");
        String pdfUrl = "C:\\pdf.pdf";
        File file = gp
                .Pdf("C:\\20160818170556953_front.JPG",pdfUrl);
        
        try {  
	        File files = new File("C:\\pdf.pdf");
	        if(!files.exists())
	            throw new RuntimeException("文件不存在..");
	        FileInputStream fis = new FileInputStream(files);
	        
         FtpUtils.uploadFile( "198.1.245.247", 21, "6666", "6666", "F:/pdfs/aa", "", "pdf.pdf", fis);
//        out.close();
        
     
     
//            file.createNewFile();
        } catch (IOException e) {
        	System.out.println(e);
//            e.printStackTrace();
        }

    }

}
