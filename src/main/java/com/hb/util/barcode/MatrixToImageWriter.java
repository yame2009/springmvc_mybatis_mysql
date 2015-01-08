package com.hb.util.barcode;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.awt.image.BufferedImage;


 
/**
 * 生成二维码
 * @author 338342
 *
 */
public final class MatrixToImageWriter {
 
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
 
    private MatrixToImageWriter() {
    }
 
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, 
                                                  BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
 
    public static void writeToFile(BitMatrix matrix, String format, File file) 
                                                          throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + 
                                                      format + " to " + file);
        }
        System.out.println("二维码图片生成成功");
    }
 
    public static void writeToStream(BitMatrix matrix, String format, 
                                     OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format "+format);
        }
    }
    
    public static void main(String[] args) throws Exception {
        String text = "http://www.csyor.com";
        int width = 250;
        int height = 250;
        // 二维码的图片格式
        String format = "gif";
        Hashtable<EncodeHintType,String> hints = 
                                      new Hashtable<EncodeHintType,String>();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter()
                  .encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
        File outputFile = new File("d:" + File.separator + "csyor.com.gif");
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
    }
 
}