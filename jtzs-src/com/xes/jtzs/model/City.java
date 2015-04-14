/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2013
 */

package com.xes.jtzs.model;

import javax.persistence.*;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.up72.base.*;
import com.up72.framework.util.holder.ApplicationContextHolder;
import com.xes.jtzs.JTZSConstants;
import com.xes.jtzs.service.ProvinceManager;

/**
 * 
 * 
 * @author 
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "xes_jtzs_city")
public class City extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "城市";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "城市名称";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_EN_NAME = "城市拼音";
	public static final String ALIAS_PROVINCE_ID = "省份";
	public static final String ALIAS_STATUS = "状态";//(1为启用，0为不禁用)默认1
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	/** */
	
	private java.lang.Long id;
	
	/** 城市名称*/
	@Length(max=50)
	private java.lang.String name;
	
	/** 排序*/
	
	private java.lang.Long sort;
	
	/** 城市拼音*/
	@Length(max=200)
	private java.lang.String enName;
	
	/** 省份ID*/
	
	private java.lang.Long provinceId;
	
	/** 是否显示(1为显示，0为不显示)*/
	@Max(127)
	private Integer status;
	
	//columns END


	public City(){
	}

	public City(
		java.lang.Long id
	){
		this.id = id;
	}

	

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
	public java.lang.Long getId() {
		return this.id;
	}
	
	@Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	@Column(name = "SORT", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getSort() {
		return this.sort;
	}
	
	public void setSort(java.lang.Long value) {
		this.sort = value;
	}
	
	@Column(name = "EN_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getEnName() {
		return this.enName;
	}
	
	public void setEnName(java.lang.String value) {
		this.enName = value;
	}
	
	@Column(name = "PROVINCE_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getProvinceId() {
		return this.provinceId;
	}
	
	public void setProvinceId(java.lang.Long value) {
		this.provinceId = value;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Sort",getSort())
			.append("EnName",getEnName())
			.append("ProvinceId",getProvinceId())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof City == false) return false;
		if(this == obj) return true;
		City other = (City)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	@Transient
	public Province getProvince(){
		ProvinceManager provinceManager = (ProvinceManager)ApplicationContextHolder.getBean("provinceManager");
		Province province = null;
		if(null != this.getProvinceId()){
			province = provinceManager.getById(this.getProvinceId());
		}
		if(null == province){
			province = new Province();
		}
		return province;
	}
	
	@Transient
	public String getStatusStr(){
		String result = JTZSConstants.Pubilc.DISABLE.getName();
		if(null != this.status && JTZSConstants.Pubilc.ENABLED.getIndex()==this.status){
			result = JTZSConstants.Pubilc.ENABLED.getName();
		}
		return result;
	}
}

