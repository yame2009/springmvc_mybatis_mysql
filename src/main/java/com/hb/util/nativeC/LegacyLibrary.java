/**  
 * @Title: LegacyLibrary.java 
 * @Package com.hb.util.nativeC 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年7月6日 上午8:58:56 
 * @version V1.0  
 */ 
package com.hb.util.nativeC;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

/** 
 * @ClassName: LegacyLibrary 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年7月6日 上午8:58:56  
 */
 
@Platform(include="LegacyLibrary.h")
@Namespace("LegacyLibrary")
public class LegacyLibrary {
    public static class LegacyClass extends Pointer {
        static { Loader.load(); }
        public LegacyClass() { allocate(); }
        private native void allocate();
 
        // to call the getter and setter functions 
        public native @StdString String get_property(); public native void set_property(String property);
 
        // to access the member variable directly
        public native @StdString String property();     public native void property(String property);
    }
 
    public static void main(String[] args) {
        // Pointer objects allocated in Java get deallocated once they become unreachable,
        // but C++ destructors can still be called in a timely fashion with Pointer.deallocate()
        LegacyClass l = new LegacyClass();
        l.set_property("Hello World!");
        System.out.println(l.property());
    }
}