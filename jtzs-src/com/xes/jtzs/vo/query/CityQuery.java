/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2013
 */

package com.xes.jtzs.vo.query;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import static com.up72.common.CommonUtils.*;
import java.io.Serializable;

import java.util.*;

import com.up72.base.*;
import com.up72.util.*;

import com.up72.framework.util.*;
import com.up72.framework.web.util.*;
import com.up72.framework.page.*;
import com.up72.framework.page.impl.*;

import com.xes.jtzs.model.*;
import com.xes.jtzs.dao.*;
import com.xes.jtzs.service.*;
import com.xes.jtzs.vo.query.*;/**
 * 查询辅助bean
 * 
 * @author 
 * @version 1.0
 * @since 1.0
 */
public class CityQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 城市名称 */
	private java.lang.String name;
	/** 排序 */
	private java.lang.Long sort;
	/** 城市拼音 */
	private java.lang.String enName;
	/** 省份ID */
	private java.lang.Long provinceId;
	/** 是否显示(1为显示，0为不显示) */
	private Integer status;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.Long getSort() {
		return this.sort;
	}
	
	public void setSort(java.lang.Long value) {
		this.sort = value;
	}
	
	public java.lang.String getEnName() {
		return this.enName;
	}
	
	public void setEnName(java.lang.String value) {
		this.enName = value;
	}
	
	public java.lang.Long getProvinceId() {
		return this.provinceId;
	}
	
	public void setProvinceId(java.lang.Long value) {
		this.provinceId = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

