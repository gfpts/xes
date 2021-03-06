/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2013
 */


package com.xes.jtzs.controller;

import static com.up72.common.CommonUtils.*;

import com.up72.common.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.up72.base.BaseRestSpringController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.up72.framework.page.Page;
import com.up72.framework.web.scope.Flash;

import java.util.*;

import com.xes.jtzs.JTZSConstants;
import com.xes.jtzs.model.*;
import com.xes.jtzs.service.*;
import com.xes.jtzs.vo.query.*;
/**
 * 数据提取跳转
 * 
 * @author 
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/jtzs/school")
public class AdminSchoolController extends BaseRestSpringController<School,java.lang.Long>{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	private ProvinceManager provinceManager;
	@Autowired
	private GradeManager gradeManager;
	private SchoolManager schoolManager;
	
	private final String LIST_ACTION = "redirect:/admin/jtzs/school";
	
	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写
	 **/
	public void setSchoolManager(SchoolManager manager) {
		this.schoolManager = manager;
	}
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}
	   
	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		//model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 列表  
	  * @throws IOException */
	@RequestMapping
	@SuppressWarnings({ "unchecked" })
	public String index(ModelMap model,SchoolQuery query,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Page page = this.schoolManager.findPage(query);
		
		this.addShowLabelAttrbite("/admin/jtzs/school", request, response);
		model.addAttribute("provinceList",provinceManager.getShowListBySort());
		model.addAllAttributes(toModelMap(page, query));
		return "/admin/jtzs/school/index";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		School school = (School)schoolManager.getById(id);
		model.addAttribute("school",school);
		return "/admin/jtzs/school/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public String _new(ModelMap model,School school,HttpServletRequest request,HttpServletResponse response) throws Exception {
		model.addAttribute("school",school);
		model.addAttribute("provinceList",provinceManager.getShowListBySort());
		model.addAttribute("divisionArray",JTZSConstants.Division.values());
		return "/admin/jtzs/school/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	public String create(ModelMap model,@Valid School school,BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return  "/admin/jtzs/school/new";
		}
		String[] division = paramUtils.getParameters(request, "division", "");
		String divisions = "";
		for (int i = 0; i < division.length; i++) {
			if(division[i].matches("\\d+")){
				if(i != 0){
					divisions+=",";
				}
				divisions+="["+division[i]+"]";
			}
		}
		school.setDivisions(divisions);
		school.setEnName(CommonUtils.pinyinUtil.paraseStringToPinyin(school.getName()));
		school.setStatus(JTZSConstants.Pubilc.ENABLED.getIndex());
		schoolManager.save(school);
		school.setSort(school.getId());
		schoolManager.update(school);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		School school = (School)schoolManager.getById(id);
		model.addAttribute("school",school);
		model.addAttribute("provinceList",provinceManager.getShowListBySort());
		model.addAttribute("divisionArray",JTZSConstants.Division.values());
		return "/admin/jtzs/school/edit";
	}
	
	/** 选项卡编辑 */
	@RequestMapping(value="/{id}/tabEdit")
	public String tabEdit(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		School school = (School)schoolManager.getById(id);
		model.addAttribute("school",school);
		return "/admin/jtzs/school/tab_edit";
	}
	
	
	/** 选项卡显示 */
	@RequestMapping(value="/{id}/tabShow")
	public String tabShow(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		School school = (School)schoolManager.getById(id);
		model.addAttribute("school",school);
		return "/admin/jtzs/school/tab_show";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(ModelMap model,@PathVariable java.lang.Long id,@Valid School school,BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return "/admin/jtzs/school/edit";
		}
		String[] division = paramUtils.getParameters(request, "division", "");
		String divisions = "";
		for (int i = 0; i < division.length; i++) {
			if(division[i].matches("\\d+")){
				if(i != 0){
					divisions+=",";
				}
				divisions+="["+division[i]+"]";
			}
		}
		school.setDivisions(divisions);
		school.setEnName(CommonUtils.pinyinUtil.paraseStringToPinyin(school.getName()));
		schoolManager.update(school);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(ModelMap model,@PathVariable java.lang.Long id) {
		schoolManager.removeById(id);
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	/** 批量删除 */
	@RequestMapping(method=RequestMethod.DELETE)
	public String batchDelete(ModelMap model,@RequestParam("items") java.lang.Long[] items) {
		for(int i = 0; i < items.length; i++) {
			schoolManager.removeById(items[i]);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	@RequestMapping(value="/queryJsonSchoolList")
	@ResponseBody
	public String queryJsonSchoolList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Long provinceId = paramUtils.getLongParameter(request, "provinceId", 0L);
		Long gradeId = paramUtils.getLongParameter(request, "gradeId", 0L);
		Grade grade = gradeManager.getById(gradeId);
		List<Map<String,Object>> schoolList = null;
		if(null != grade){
			schoolList = schoolManager.getSchoolByInitial(provinceId,0L,0L,grade.getDivision(),true);
		}
		if(null == schoolList){
			schoolList = new ArrayList<Map<String,Object>>();
		}
		jsonMap.put("schoolList", schoolList);
		return jsonUtil.object2Json(jsonMap);
	}
	
}

