package com.hb.util.file.image.ValidateCode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 随机生成验证码
 * @author 338342
 *
 */
public class CheckCode
{
    
    public final String regexEmpty = " "; 
    
    private int width = 145;
    private int height = 30;
    
    /**
     * 获取随机颜色
     * @param random
     * @param fc
     * @param bc
     * @return
     * 2014年8月4日
     */
    private Color getRandColor(Random random, int fc, int bc)
    {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 获取随机验证码
     * @return
     *      resultMap.put("result", result);       计算的结果
            resultMap.put("checkCode", checkCode); 结果字符串
     * 2014年8月4日
     */
    private Map<String,Object> generateCheckCode()
    {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Random random = new Random();
        int intTemp;
        int intFirst = random.nextInt(100);
        int intSec = random.nextInt(100);
        StringBuilder checkCode = new StringBuilder();
        Integer result = 0;
        switch (random.nextInt(7))
        {
            case 0:
                if (intFirst < intSec)
                {
                    intTemp = intFirst;
                    intFirst = intSec;
                    intSec = intTemp;
                }
//                checkCode = intFirst + regexEmpty+"-"+regexEmpty + intSec + regexEmpty+"="+regexEmpty+"?";
                checkCode.append(intFirst).append(regexEmpty)
                          .append("-").append(regexEmpty)
                          .append(intSec).append(regexEmpty)
                          .append("=").append(regexEmpty)
                          .append("?");
                
                result = intFirst - intSec;
                break;
            case 1:
                if (intFirst < intSec)
                {
                    intTemp = intFirst;
                    intFirst = intSec;
                    intSec = intTemp;
                }
//                checkCode = intFirst + " - ? = " + (intFirst - intSec);
                checkCode.append(intFirst).append(regexEmpty)
                        .append("-").append(regexEmpty)
                        .append("?").append(regexEmpty)
                        .append("=").append(regexEmpty)
                        .append((intFirst - intSec));
                
                result = intSec;
                
                break;
            case 2:
                if (intFirst < intSec)
                {
                    intTemp = intFirst;
                    intFirst = intSec;
                    intSec = intTemp;
                }
//                checkCode = "? - " + intSec + " = " + (intFirst - intSec);
                checkCode.append("?").append(regexEmpty)
                        .append("-").append(regexEmpty)
                        .append(intSec).append(regexEmpty)
                        .append("=").append(regexEmpty)
                        .append((intFirst - intSec));
                
                result = intFirst;
                break;
            case 3:
//                checkCode = intFirst + " + " + intSec + " = ?";
                checkCode.append(intFirst).append(regexEmpty)
                        .append("+").append(regexEmpty)
                        .append(intSec).append(regexEmpty)
                        .append("=").append(regexEmpty)
                        .append("?");
                
                result = intFirst + intSec;
                
                break;
            case 4:
//                checkCode = intFirst + " + ? =" + (intFirst + intSec);
                checkCode.append(intFirst).append(regexEmpty)
                        .append("+").append(regexEmpty)
                        .append("?").append(regexEmpty)
                        .append("=").append(regexEmpty)
                        .append((intFirst + intSec));
                
                result = intSec;
                
                break;
            case 5:
//                checkCode = "? + " + intSec + " =" + (intFirst + intSec);
                checkCode.append("?").append(regexEmpty)
                        .append("+").append(regexEmpty)
                        .append(intSec).append(regexEmpty)
                        .append("=").append(regexEmpty)
                        .append((intFirst + intSec));
                
                result = intFirst;
                
                break;
            case 6:
                intFirst = random.nextInt(9)+1;//范围是[0-8]+1 = [1-9]
                intSec = random.nextInt(9)+1;//范围是[0-8]+1 = [1-9]
//                checkCode = intFirst + " * " + intSec + " = ?";
                checkCode.append(intFirst).append(regexEmpty)
                        .append("*").append(regexEmpty)
                        .append(intSec).append(regexEmpty)
                        .append("=").append(regexEmpty)
                        .append("?");
                
                result = intFirst*intSec;
                break;
        }
        // request.getSession().setAttribute("VERIFY_CODE", result);
        // result 结果值
        
        if(result == null ||result == 0 || checkCode.length() == 0)
        {
            System.out.println();
        }
        
        resultMap.put("result", result);
        System.out.println("result  "+result);
        resultMap.put("checkCode", checkCode.toString());
        System.out.println("checkCode img "+checkCode);
        
        return resultMap;
    }

    /**
     * 生成验证码图片
     * @return
     *        resultMap.put("result", result);   计算的结果
     *        resultMap.put("image", image);     生成的验证码图片
     * 2014年8月4日
     */
    public Map<String,Object> getCodeImage()
    {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        try
        {
            // response.setHeader("Pragma", "No-cache");
            // response.setHeader("Cache-Control", "no-cache");
            // response.setDateHeader("Expires", 0);

            Map<String,Object> resultMapTemp = generateCheckCode();
            String checkCode = String.valueOf(resultMapTemp.get("checkCode"));
            
            resultMap.put("result", resultMapTemp.get("result"));

            // OutputStream os = response.getOutputStream();
            Graphics g = image.getGraphics();

            Random random = new Random();

            g.setColor(getRandColor(random, 200, 250));
            g.fillRect(0, 0, width, height);
            //[宋体, 新宋体, 黑体, 楷体, 隶书]
            String[] fontTypes =
            { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53",
                    "\u6977\u4f53", "\u96b6\u4e66" };
            int fontTypesLength = fontTypes.length;

            g.setColor(getRandColor(random, 160, 200));
            g.setFont(new Font("Times New Roman", Font.PLAIN, 14 + random
                    .nextInt(6)));

            for (int i = 0; i < 255; i++)
            {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }
         
            String[] baseChar = checkCode.split(regexEmpty);
            for (int i = 0; i < baseChar.length; i++)
            {
                g.setColor(getRandColor(random, 30, 150));
                g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],
                        Font.BOLD, 22 + random.nextInt(6)));
                g.drawString(baseChar[i], 24 * i + 10, 24);
            }

            g.dispose();

            // ImageIO.write(image, "JPEG", os);
            // os.flush();
            // os.close();
            // os = null;
            // response.flushBuffer();
            // out.clear();
            // out = pageContext.pushBody();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        resultMap.put("image", image);
        return resultMap;
    }
    
    public static void main(String[] args)
    {
        for (int i = 0; i < 10; i++)
        {
            Map<String,Object> resultMap = new CheckCode().getCodeImage();
             BufferedImage tempImage = (BufferedImage) resultMap.get("image");
             Integer result = (Integer) resultMap.get("result");
             
            try
            {
                ImageIO.write(tempImage, "JPEG", new File("d:\\dd\\答案为" +result+" ,序列号为："+ i
                        + ".jpeg"));
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
//        Random random = new Random();
//        
//        Map<Object,Object> resultMap = new HashMap<>();
//        for (int i = 0; i < 1000; i++)
//        {
//            int temp = random.nextInt(7);
//            resultMap.put(temp,temp);
//           
//        }
//        System.out.println(resultMap);
       
    }
}
