/* 
 * Copyright (c) 2015, S.F. Express Inc. All rights reserved.
 */
package com.hb.designPatterns.observer;

/**
 * 描述：
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
public class Person {

	private PersonListener listener;

	public void registerListener(PersonListener listener) {

		this.listener = listener;

	}

	public void run() {

		if (listener != null) {

			Even even = new Even(this);

			this.listener.dorun(even);

		}

		System.out.println("runn!!");

	}

	public void eat() {

		if (listener != null) {

			Even e = new Even(this);

			this.listener.doeat(e);

		}

		System.out.println("eat!!");

	}

}
