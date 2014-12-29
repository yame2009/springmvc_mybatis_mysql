package com.hb.models;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class FormatModel {

	/**
	 * 基于注解的格式化
	 */
	@NumberFormat(style = Style.CURRENCY)
	private double money;
	/**
	 * 基于注解的格式化
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	private String moneyStr;
	private String dateStr;

	public String getMoneyStr() {
		return moneyStr;
	}

	public void setMoneyStr(String moneyStr) {
		this.moneyStr = moneyStr;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public double getMoney() {
		return money;
	}

	public Date getDate() {
		return date;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
