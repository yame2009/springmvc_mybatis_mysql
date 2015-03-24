/* 
 * Copyright (c) 2015, S.F. Express Inc. All rights reserved.
 */
package com.hb.designPatterns.observer;

/**
 * 描述：// 监听器接口：Interface 
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015年3月18日      338342         Create
 * ****************************************************************************
 * </pre>
 * @author 338342
 * @since 1.0
 */
public interface PersonListener {

	 public void dorun(Even even);
	 
     
	 
     public void doeat(Even even);  
}
