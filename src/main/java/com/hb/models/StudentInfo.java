package com.hb.models;

import java.util.Date;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.format.annotation.DateTimeFormat;

import com.hb.util.java2xml.jaxb.DateAdapter;

/**
 * 
 * <pre>
 * @since   ： 1.0        创建时间:    2015-1-13  上午17:52:20
 * @author  ： huangbing   <a href="mailto:hb0504511129@126.com">发送邮件</a>
 * @version ： 1.1
 * 
 * 参考资料：<a>http://my.oschina.net/zhaoqian/blog/179334</a>
 *
 * @Table，@Column，@NotNull，@Pattern ,为引入jpa的实现包.hibernate-validator随便.做验证用的.
 * 
 * 下面为JAXB也提供了将XML实例文档反向生成Java对象树的方法，并能将Java对象树的内容重新写到XML实例文档.
 * JAXB 2.0是JDK 1.6的组成部分。JAXB 2.2.3是JDK 1.7的组成部分。
 * @XmlAccessorType(XmlAccessType.FIELD)指定映射本类的所有字段
 * @XmlRootElement 用在class类的注解，常与@XmlRootElement，@XmlAccessorType一起使用.也可以单独使用,如果单独使用,需要在get方法上加@XmlElement等注解.
 * @XmlType,在使用@XmlType的propOrder 属性时，必须列出JavaBean对象中的所有XmlElement，否则会报错。
 * </pre>
 */	
@Named
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "StudentInfo")
@XmlType(name = "StudentInfo", propOrder = { "name","password","email","sex","age","birthday","address","createTime","modifyTime"})
@Table(name = "studentinfo")
public class StudentInfo {
    
	/**
	  *  其实@XmlType已经默认会读取下面的name和age.@XmlElement在@XmlType存在的情况下,只会起到一个标识作用.
	  */ 
    @XmlAttribute
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @XmlElement
    @NotNull(message="用户名不能为空")
    private String name;
    
    @XmlElement
    @NotNull(message="密码不能为空")
    @Size(min=4,max=10,message="密码长度必须在4-10的长度")
    private String password;
    
    @XmlElement
    @Pattern(regexp="^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+.[a-zA-Z]{2,5}?((.cn)|(.jp))?$", message="邮箱格式不正确")
    private String email;
    
    @XmlElement
    @Pattern(regexp="^(1|0)$", message="1表示男，0表示女")
    private int sex;            //1表示男 0表示女
    
    /**
     * 年龄
     */
    @XmlElement
    @Pattern(regexp="^([1-9][0-9]{0,2})$", message="年龄填写不正确")
    private int age;
    
	/**
	 *  生日
	 * 基于注解的格式化
	 */
    @XmlElement
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(value = DateAdapter.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    
    /**
     * 家庭住址
     */
    @XmlElement
    private String address;
    
	/**
	 *  创建时间
	 * 基于注解的格式化
	 * @XmlElement(name = "TransactionTime", required = true)，@XmlSchemaType ，@XmlJavaTypeAdapter
	 *  在某个类中如下使用,解析出对应的时间格式.必须重载那2个方法,用于JAXB marshal xml,xml unmarshal object时候使用
	 */
    @XmlElement//(name = "TransactionTime", required = true)
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(value = DateAdapter.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
	/**
	 *  修改时间
	 * 基于注解的格式化
	 */
    @XmlElement
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(value = DateAdapter.class)
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
