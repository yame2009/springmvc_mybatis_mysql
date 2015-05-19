/**  
 * @Title: Employee.java 
 * @Package com.hb.util.file.excel.help 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午8:56:00 
 * @version V1.0  
 */ 
package com.hb.util.file.excel.help;

import java.util.Date;
 
public class Employee {
    private long id;
    private String name;
    private int age;
    private String job;
    private double salery;
    private Date addtime;
 
    public Employee() {
 
    }
 
    public Employee(long id, String name, int age, String job, double salery) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.job = job;
        this.salery = salery;
        this.addtime = new Date();
    }
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
 
    public String getJob() {
        return job;
    }
 
    public void setJob(String job) {
        this.job = job;
    }
 
    public double getSalery() {
        return salery;
    }
 
    public void setSalery(double salery) {
        this.salery = salery;
    }
 
    public Date getAddtime() {
        return addtime;
    }
 
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
 
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age
                + ", job=" + job + ", salery=" + salery + ", addtime="
                + addtime + "]";
    }
 
}