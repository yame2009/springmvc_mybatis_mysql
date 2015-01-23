package com.hb.util.commonUtil;

import java.awt.Dimension;
import java.awt.Toolkit;


public class WindowUtil {

	 /**
     * 获取屏幕的宽度和高度
     *
     * @return
     */
    public static int[] getScreenSize()
    {
        int[] screenSize =
        { 0, 0 };
        Dimension screeDimension = Toolkit.getDefaultToolkit().getScreenSize();
        if (screeDimension != null)
        {
            screenSize[0] = (int) screeDimension.getWidth();
            screenSize[1] = (int) screeDimension.getHeight();
        }
        return screenSize;
    }

}
