/* 
 * Copyright (c) 2015, S.F. Express Inc. All rights reserved.
 */
package com.hb.designPatterns.observer;

/**
 * 描述： 实现监听器功能的类
 * 
 *  观察者模式应用案例：
 *  餐馆有多部点菜设备，和一台已点菜单打印机，要求每台点菜设备点完菜，打印机都会自动打印出菜单给厨房，请问如何设计该自动打印系统 。
 
	方案一：定时器扫描菜单数据库（缺点：会造成CUP空转——没有点菜的时间也会扫描数据库。）
 
	方案二：采用观察者模式——每次点菜设备执行点菜操作后，都会触发监听器，使打印机主动打印菜单
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015年3月18日      338342         Create
 * ****************************************************************************
 * </pre>
 * 
 * @author 338342
 * @since 1.0
 */
public class MyListener1 implements PersonListener {
	
	public void doeat(Even even) {
		System.out.println(even.getPerson() + "你天天吃，你就知道吃，你猪啊！！");

	}

	public void dorun(Even even) {

		System.out.println(even.getPerson() + "你吃完就跑，有病！！");

	}
}
