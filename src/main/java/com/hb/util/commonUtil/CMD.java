package com.hb.util.commonUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * java中执行cmd命令行的工具类。
	注意：本工具类只支持执行单行命令。

	1、使用cmd自带的命令如move、rd等，请用CMD方法（否则会出现弹出cmd窗口没关闭的情况）；
	2、使用其他的命令如java -jar，请使用runCMD方法；
 * @author 338342
 *
 */
public class CMD {
	public static Process CMD(String cmd){
        Process p = null;
        try {
            cmd = "cmd.exe /c "+cmd;
            System.out.println(cmd);
            p = Runtime.getRuntime().exec(cmd);
            new Thread(new cmdResult(p.getInputStream())).start();
            new Thread(new cmdResult(p.getErrorStream())).start();
            p.getOutputStream().close();
        } catch (Exception e) {
            System.out.println("命令行出错！");
            e.printStackTrace();
        }
        return p;
    }
     
    public static Process CMD(String cmd,String ...args){
        return CMD(String.format(cmd, args));
    }
     
    public static Process runCMD(String cmd){
        Process p = null;
        try {
            cmd = "cmd.exe /c start "+cmd;
            System.out.println(cmd);
            p = Runtime.getRuntime().exec(cmd);
            new Thread(new cmdResult(p.getInputStream())).start();
            new Thread(new cmdResult(p.getErrorStream())).start();
            p.getOutputStream().close();
        } catch (Exception e) {
            System.out.println("命令行出错！");
            e.printStackTrace();
        }
        return p;
    }
     
    public static Process runCMD(String cmd,String ...args){
        return runCMD(String.format(cmd, args));
    }
     
    static class cmdResult implements Runnable{
        private InputStream ins;
         
        public cmdResult(InputStream ins){
            this.ins = ins;
        }
 
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
                String line = null;
                while ((line=reader.readLine())!=null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         
    }
 
}
