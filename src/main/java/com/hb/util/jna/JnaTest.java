package com.hb.util.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
  
/** 
 * http://my.oschina.net/u/1385143/blog/175053
 * JNA（Java Native Access ）提供一组Java工具类用于在运行期动态访问系统本地库（native library：如Window的dll）
 * 而不需要编写任何Native/JNI代码。开发人员只要在一个java接口中描述目标native library的函数与结构，
 * JNA将自动实现Java接口到native function的映射。
 * 说白了就是Java直接访问/调用本地动态库。最好的入门方法就是从JNA的官网开始。
 * 官网下载地址：https://github.com/twall/jna
 */
public class JnaTest {
  
    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
            Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
                               CLibrary.class);
    
        void printf(String format, Object... args);
    }
  
    public static void main(String[] args) {
       // CLibrary.INSTANCE.printf("Hello, World/n");
        for (int i=0;i < args.length;i++) {
            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
        }
    }
}
