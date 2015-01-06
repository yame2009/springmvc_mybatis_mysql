package com.hb.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 *  User.java   
 *
 *  @version ： 1.1
 *  
 *  @author  ： huangbing   <a href="mailto:hb0504511129@126.com">发送邮件</a>
 *    
 *  @since   ： 1.0        创建时间:    2013-4-13  上午10:42:20
 *     
 *  TODO     : 
 *
 */
public class StudentInfo {
    
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @NotNull(message="用户名不能为空")
    private String name;
    
    @NotNull(message="密码不能为空")
    @Size(min=4,max=10,message="密码长度必须在4-10的长度")
    private String password;
    
    @Pattern(regexp="^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+.[a-zA-Z]{2,5}?((.cn)|(.jp))?$", message="邮箱格式不正确")
    private String email;
    
    @Pattern(regexp="^(1|0)$", message="1表示男，0表示女")
    private int sex;            //1表示男 0表示女
    
    /**
     * 年龄
     */
    @Pattern(regexp="^([1-9][0-9]{0,2})$", message="年龄填写不正确")
    private int age;
    
	/**
	 *  生日
	 * 基于注解的格式化
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    
    /**
     * 家庭住址
     */
    private String address;
    
	/**
	 *  创建时间
	 * 基于注解的格式化
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
	/**
	 *  修改时间
	 * 基于注解的格式化
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    
    public StudentInfo(){
        
    }
    
    public StudentInfo(String username, String password, String email) {
        super();
        this.name = username;
        this.password = password;
        this.email = email;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "StudentInfo [id=" + id + ", name=" + name + ", password="
				+ password + ", email=" + email + ", sex=" + sex + ", age="
				+ age + ", birthday=" + birthday + ", address=" + address
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime
				+ "]";
	}

	
    
	
}
