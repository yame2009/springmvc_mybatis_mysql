package com.hb.util.file.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
import javax.imageio.ImageIO;
 
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
/**
 * 水印_缩略图
 * @author 338342
 *
 */
public class PicChoose {
    public static void main(String[] args) {
         
        new PicChoose().getImgForImg("E:/1.jpg", "E:/2.jpg",1,1,0.5f);
//      new Shuiyi().getImgForStr("E:/1.jpg", "小米制作",0,50,Color.CYAN, 1,4);
    }
    /**
     * 生成缩略图
     * 
     * @param:参数设置
     * img:要缩略的图片
     * shortLine:对于图片相对比较短的一边为基准
     * */
    public void thumbnails(String img,int shortLine){
        int x = 0;
        int y = 0;
        try {
            // 得到水印图片文件
            Image backImg = ImageIO.read(new FileInputStream(img));
            int wb = backImg.getWidth(null);
            int hb = backImg.getHeight(null);
//          System.out.println("宽："+wb+"  高："+hb);
//          System.out.println((float)4*3.2);
            float temp;
            if(wb<=hb){
                 temp = (float)hb/wb;
                 x = shortLine;
                 y = (int)((float)temp * shortLine);
            }else{
                 temp = (float)wb/hb;
                 x = (int)((float)temp * shortLine);
                 y = shortLine;
            }
//          System.out.println("宽："+x+"  高："+y);
             
             
            // 构建一个画板
            BufferedImage image = new BufferedImage(x, y,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics(); // 得到画笔
            // drawImage(Image,初始X轴,初始Y轴,图片的宽度,图片的高度,null);
            g.drawImage(backImg, 0, 0, x, y, null);// 绘制一张图片
            g.dispose();// 生成图片
            // 输出流，完成合成图片的输出
            FileOutputStream fileOutputStream = new FileOutputStream("E:/suoLueT.jpg");
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            encoder.encode(image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    /**
     * 文字水印
     * 
     * 
     * @param:参数含义
     * backStr:为背景图片,topStr水印,x/y定义水印的初始位置
     * backStr:背景图片
     * topText:水印内容
     * textStyle:字体的类型
     * textSize:字体的大小
     * color:画笔绘制字体时的颜色,要求填写RGB
     * Location:图片为9块，从上到下一次为1:左上方，2:右上方，3:左下方，4:右下方    以此来定义该选择区域的位置
     * fontNum:文字水印的个数
     * */
    public void getImgForStr(String backStr, String topText,int textStyle,int textSize,Color color, int location,int fontNum) {
        try {
            // 得到水印图片文件
            Image backImg = ImageIO.read(new FileInputStream(backStr));
            int wb = backImg.getWidth(null);
            int hb = backImg.getHeight(null);
            int x = 0;
            int y = 0;
            //构建水印的位置
            switch(location){
            case 1:
                x = 0;
                y = 0;
                break;
            case 2:
                x = wb-fontNum*textSize;
                y = 0;
                break;
            case 3:
                x = 0;
                y = hb-textSize-10;
                break;
            case 4:
                x = wb-fontNum*textSize;
                y = hb-textSize-10;
                break;
            default:
                return;
            }
            y+=textSize;
            // 构建一个画板
            BufferedImage image = new BufferedImage(wb, hb,BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics(); // 得到画笔
            // drawImage(Image,初始X轴,初始Y轴,图片的宽度,图片的高度,null);
            g.drawImage(backImg, 0, 0, wb, hb, null);// 绘制一张图片
            g.setFont(new Font(null,textStyle,textSize));// 再绘制一张图片
            g.setColor(color);
            g.drawString(topText, (int)x, (int)y);
            g.dispose();// 生成图片
            // 输出流，完成合成图片的输出
            FileOutputStream fileOutputStream = new FileOutputStream(backStr);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            encoder.encode(image);
            fileOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
    /**
     * 图片水印
     * 
     * @param:参数含义
     * backStr:为背景图片,topStr水印,x/y定义水印的初始位置
     * backStr:背景图片
     * topStr:水印图片
     * Location:图片为9块，从上到下一次为1:左上方，2:右上方，3:左下方，4:右下方    以此来定义该选择区域的位置
     * topImgTimes:水印放大倍数 
     * alpha:设置图片的透明度(该透明度的范围在0~1之间)
     * */
    public void getImgForImg(String backStr, String topStr, int location,int topImgTimes,float alpha) {
        try {
            // 得到背景图片文件
            Image topImg = ImageIO.read(new FileInputStream(topStr));
            int wt = topImg.getHeight(null);
            int ht = topImg.getHeight(null);
            // 得到水印图片文件
            Image backImg = ImageIO.read(new FileInputStream(backStr));
            int wb = backImg.getWidth(null);
            int hb = backImg.getHeight(null);
             
            int x = 0;
            int y = 0;
            //构建水印的位置
            switch(location){
            case 1:
                x = 0;
                y = 0;
                break;
            case 2:
                x = wb-wt*topImgTimes;
                y = 0;
                break;
            case 3:
                x = 0;
                y = hb-ht*topImgTimes;
                break;
            case 4:
                x = wb-wt*topImgTimes;
                y = hb-ht*topImgTimes;
                break;
            default:
                return;
            }
             
            // 构建一个画板
            BufferedImage image = new BufferedImage(wb, hb,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics(); // 得到画笔
            // drawImage(Image,初始X轴,初始Y轴,图片的宽度,图片的高度,null);
            g.drawImage(backImg, 0, 0, wb, hb, null);// 绘制一张图片
            //设置透明度
             
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
            g.drawImage(topImg, x, y, wt*topImgTimes, ht*topImgTimes, null);// 再绘制一张图片
             
            g.dispose();// 生成图片
            // 输出流，完成合成图片的输出
            FileOutputStream fileOutputStream = new FileOutputStream(backStr);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            encoder.encode(image);
            fileOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}