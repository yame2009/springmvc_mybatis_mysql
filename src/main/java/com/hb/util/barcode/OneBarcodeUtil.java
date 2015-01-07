package com.hb.util.barcode;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

/**
 * 利用jbarcode生成各种条形码！测试成功！分享给大家！
 */
public class OneBarcodeUtil {

	public static void main(String[] paramArrayOfString) {
		try {
			JBarcode localJBarcode = new JBarcode(EAN8Encoder.getInstance(),
					WidthCodedPainter.getInstance(),
					EAN8TextPainter.getInstance());
			String str = "2219644";
			BufferedImage localBufferedImage = localJBarcode.createBarcode(str);

			saveToGIF(localBufferedImage, "EAN8.jpg");
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	static void saveToJPEG(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "jpeg");
	}

	static void saveToFile(BufferedImage paramBufferedImage,
			String paramString1, String paramString2) {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(
					"C:\\Users\\Administrator\\Desktop/" + paramString1);
			ImageUtil.encodeAndWrite(paramBufferedImage, paramString2,
					localFileOutputStream, 96, 96);
			localFileOutputStream.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}