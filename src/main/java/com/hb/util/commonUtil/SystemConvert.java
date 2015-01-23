package com.hb.util.commonUtil;


import java.util.Scanner;

/**
 * Java写复杂的进制转换器<a>http://201212262922.iteye.com/blog/2096794</a>
 *
 */
public class SystemConvert {

   static Scanner scanner = new Scanner(System.in);
   static String s = "";

   /** 定义10进制转2进制的方法。 */
   public static String C10T2(int numb) {
       String result = "";
       for (int i = numb; i > 0; i /= 2)
           result = i % 2 + result;
       return result;
   }
   
   /** 定义10进制转2进制的方法。 */
   public static String C10T2(String numb) {  
       return C10T2(Integer.valueOf(numb));  
   }
   
   /**  定义2进制转10进制的方法。 */
   public static int C2T10(int numb) {
       int k = 0, result = 0;
       // String result=null;
       for (int i = numb; i > 0; i /= 10) {
           result += (i % 10) * Math.pow(2, k);
           k++;
       }
       return result;
   }
   
   /** 定义2进制转10进制的方法。 */
   public static int C2T10(String numb) {  
       return C2T10(Integer.valueOf(numb));  
   }
   
   /**  定义10进制转8进制的方法。 */
   public static String C10T8(int numb) {
       String result = "";
       for (int i = numb; i > 0; i /= 8)
           result = i % 8 + result;
       return result;
   }
   
   /**  定义10进制转8进制的方法。 */
   public static String C10T8(String numb) {  
       return C10T8(Integer.valueOf(numb));  
   }
   
   /**  定义8进制转10进制的方法。 */
   public static int C8T10(int numb) {
       int k = 0, temp = 0;
       for (int i = numb; i > 0; i /= 10) {
           temp += (i % 10) * Math.pow(8, k);
           k++;
       }
       return temp;
   }
   
   /**  定义8进制转10进制的方法。 */
   public static int C8T10(String numb) {  
       return C8T10(Integer.valueOf(numb));  
   } 

   public static void convert10(int numb, int to) {
       String s = "";
       switch (to) {
       case 2:
           s = "" + C10T2(numb);
           break;
       case 8:
           s = "" + C10T8(numb);
           break;
       case 16:
           s = Integer.toHexString(numb).toUpperCase();
           break;
       default:
           System.out.println("wrong input!");
       }
       System.out.println(s);
   }

	public static void convert2(int numb, int to) {
       String s = "";
       switch (to) {
       case 10:
           s = "" + C2T10(numb);
           break;
       case 8:
           s = "" + C10T8(C2T10(numb));
           break;
       case 16:
           s = Integer.toHexString(C2T10(numb)).toUpperCase();
           break;
       default:
           System.out.println("wrong input!");
       }
       System.out.println(s);

   }

   public static void convert8(int numb, int to) {
       String s = "";
       switch (to) {
       case 2:
           s = "" + C10T2(C8T10(numb));
           break;
       case 10:
           s = "" + C8T10(numb);
           break;
       case 16:
           s = Integer.toHexString(C8T10(numb)).toUpperCase();
           break;
       default:
           System.out.println("wrong input!");
       }
       System.out.println(s);

   }
   

   public static void convert16(String numb, int to) {
       String s = "";
       switch (to) {
       case 2:
           int temp2 = Integer.parseInt(numb, 16);
           s = C10T2(temp2);
           break;
       case 8:
           int temp3 = Integer.parseInt(numb, 16);
           s = C10T8(temp3);
           break;
       case 10:
           int temp = Integer.parseInt(numb, 16);
           s = "" + temp;
           break;
       default:
           System.out.println("wrong input!");
       }
       System.out.println(s);

   }

   public static void convert(int numb, int from, int to) {

       switch (from) {
       case 10:
           convert10(numb, to);
           break;
       case 2:
           convert2(numb, to);
           break;
       case 8:
           convert8(numb, to);
           break;
       default:
           System.out.println("wrong input!");

       }
   }

   public static void convert(String numb, int from, int to) {

       switch (from) {
       case 16:
           convert16(numb, to);
           break;
       default:
           System.out.println("wrong input!");

       }
   }
   
 public static void convertTest() {  
	   
       System.out.println("please input a number:");  
       String numb = scanner.next();  
  
       System.out.println("choose a way:\n输入1，表示10进制转2进制;\n"  
               + "输入2，表示2进制转10进制;\n" + "输入3，表示10进制转8进制;\n"  
               + "输入4，表示8进制转10进制;\n" + "输入5，表示10进制转16进制;\n"  
               + "输入6，表示16进制转10进制;\n" + "输入7，表示2进制转8进制;\n"  
               + "输入8，表示2进制转16进制;\n" + "输入9，表示8进制转2进制;\n"  
               + "输入10，表示8进制转16进制;\n" + "输入11，表示16进制转2进制;\n"  
               + "输入12，表示16进制转8进制;\n");  
       int input = scanner.nextInt();  
  
       switch (input) {  
       case 1: // 10>>>2  
           s = "" + C10T2(numb);  
           break;  
       case 2: // 2>>>10  
           s += C2T10(numb);  
           break;  
       case 3: // 10>>>8  
           s = "" + C10T8(numb);  
           break;  
       case 4: // 8>>>10  
           s = "" + C8T10(numb);  
           break;  
       case 5: // 10>>>16  
           s = Integer.toHexString(Integer.valueOf(numb)).toUpperCase();  
           break;  
       case 6:// 16>>>10  
           int temp = Integer.parseInt(numb, 16);  
           s = "" + temp;  
           break;  
       case 7: // 2>>>8  
           s = "" + C10T8(Integer.toString(C2T10(numb)));  
           break;  
  
       case 8: // 2>>>16  
           s = Integer.toHexString(Integer.valueOf(C2T10(numb))).toUpperCase();  
           break;  
  
       case 9: // 8>>>2  
           s = "" + C10T2(Integer.toString(C8T10(numb)));  
           break;  
  
       case 10:// 8>>>16  
           s = Integer.toHexString(Integer.valueOf(C8T10(numb))).toUpperCase();  
           break;  
       case 11:// 16>>>2  
           int temp2 = Integer.parseInt(numb, 16);  
           s = Integer.toBinaryString(temp2);  
           break;  
       case 12:// 16>>>8  
           int temp3 = Integer.parseInt(numb, 16);  
           s = C10T8(Integer.toString(temp3));  
           break;  
       default:  
           System.out.println("Wrong input!");  
       }  
       System.out.println(s);  
   }  
   

   public static void main(String[] args) {

       System.out.println("要转的是16进制数吗？\n输入1。代表是；\n输入2.代表不是.\n");
       int input = scanner.nextInt();
       switch (input) {
       case 1:
           System.out.println("请输入一个16进制数：");
           String numb = scanner.next();
           System.out.println("转成什么进制的数？");
           int to = scanner.nextInt();
           convert(numb, 16, to);
           break;
       case 2:
           System.out.println("请输入一个数：");
           int numb2 = scanner.nextInt();
           System.out.println("从什么进制数转起？");
           int from = scanner.nextInt();
           System.out.println("转成什么进制的数？");
           int to2 = scanner.nextInt();

           convert(numb2, from, to2);
           break;
       default:
           System.out.println("wrong input!");
       }
       
       //第二个实例
//       convertTest();

   }

}