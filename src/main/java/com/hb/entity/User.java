package com.hb.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class User {
    
	private long id;
	
    @NotNull(message="用户名不能为空")
    private String username;
    
    @NotNull(message="密码不能为空")
    @Size(min=4,max=10,message="密码长度必须在4-10的长度")
    private String password;
    
    @Pattern(regexp="^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+.[a-zA-Z]{2,5}?((.cn)|(.jp))?$", message="邮箱格式不正确")
    private String email;
    
    @Pattern(regexp="^(1|0)$", message="1表示男，0表示女")
    private int sex;            //1表示男 0表示女
    
    public User(){
        
    }
    
    public User(String username, String password, String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", email=" + email + ", sex=" + sex + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
    
}
