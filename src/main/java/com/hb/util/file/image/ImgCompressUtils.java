package com.hb.util.file.image;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.*;

/**
 * 图片压缩处理
 * 
 * @author kuang hj
 */
public class ImgCompressUtils {
	
	// 图片对象
	private Image img;

	// 原图的宽度
	private int width;

	// 原图的高度
	private int height;

	private static BufferedImage image;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		System.out.println("开始：" + new Date().toLocaleString());
		ImgCompressUtils imgCom = new ImgCompressUtils("D:\\jjj.jpg");
		// 处理图片得到 image
		imgCom.resizeFix(80, 80);
		// 输出结果文件
		outputNewFile("D:\\result.jpg", image);
		System.out.println("结束：" + new Date().toLocaleString());
		
	    cut(30, 50, 300, 400, "d:/1.jpg", "d:/100.jpg");  
	}

	/**
	 * 构造函数 取到需要处理的图片，得到宽 和 高
	 */
	public ImgCompressUtils(String fileName) throws IOException {
		File file = new File(fileName);// 读入文件
		img = ImageIO.read(file); // 构造Image对象
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param destwidth
	 *            int 最大宽度
	 * @param destheight
	 *            int 最大高度
	 */
	public void resizeFix(int destwidth, int destheight) throws IOException {
		if (width / height > destwidth / destheight) {
			resizeByWidth(destwidth);
		} else {
			resizeByHeight(destheight);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param destwidth
	 *            int 新宽度
	 */
	public void resizeByWidth(int destwidth) throws IOException {
		int tmph = (int) (height * destwidth / width);
		resize(destwidth, tmph);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param destheight
	 *            int 新高度
	 */
	public void resizeByHeight(int destheight) throws IOException {
		int tmpw = (int) (width * destheight / height);
		resize(tmpw, destheight);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public BufferedImage resize(int destwidth, int destheight) {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		image = new BufferedImage(destwidth, destheight,
				BufferedImage.TYPE_INT_RGB);

		// 绘制缩小后的图
		image.getGraphics().drawImage(img, 0, 0, destwidth, destheight, null);

		return image;
	}

	/**
	 * 新生成的压缩文件 <一句话功能简述> <功能详细描述>
	 * 
	 * @param path
	 * @param image
	 * @see [类、类#方法、类#成员]
	 */
	public static void outputNewFile(String path, BufferedImage image) {
		try {
			File destFile = new File(path);
			FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 图片裁切 
     * @param x1 选择区域左上角的x坐标 
     * @param y1 选择区域左上角的y坐标 
     * @param width 选择区域的宽度 
     * @param height 选择区域的高度 
     * @param sourcePath 源图片路径 
     * @param descpath 裁切后图片的保存路径 
     */  
    public static void cut(int x1, int y1, int width, int height,  
            String sourcePath, String descpath) {  
  
        FileInputStream is = null;  
        ImageInputStream iis = null;  
        try {  
            is = new FileInputStream(sourcePath);  
            String fileSuffix = sourcePath.substring(sourcePath  
                    .lastIndexOf(".") + 1);  
            Iterator<ImageReader> it = ImageIO  
                    .getImageReadersByFormatName(fileSuffix);  
            ImageReader reader = it.next();  
            iis = ImageIO.createImageInputStream(is);  
            reader.setInput(iis, true);  
            ImageReadParam param = reader.getDefaultReadParam();  
            Rectangle rect = new Rectangle(x1, y1, width, height);  
            param.setSourceRegion(rect);  
            BufferedImage bi = reader.read(0, param);  
            ImageIO.write(bi, fileSuffix, new File(descpath));  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } finally {  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                is = null;  
            }  
            if (iis != null) {  
                try {  
                    iis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                iis = null;  
            }  
        }  
  
    }  
	
	
}