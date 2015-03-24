/* 
 * Copyright (c) 2015, S.F. Express Inc. All rights reserved.
 */
package com.hb.designPatterns.observer;

/**
 * 描述：代表事件源的对象
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
public class Even {

	private Person person;

	public Even() {

		super();

		// TODO Auto-generated constructor stub

	}

	public Even(Person person) {

		super();

		this.person = person;

	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
