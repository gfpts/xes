/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2013
 */


package com.xes.jtzs.controller;
import static com.up72.common.CommonUtils.encryptUtil;
import static com.up72.common.CommonUtils.jsonUtil;
import static com.up72.common.CommonUtils.paramUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.up72.base.BaseRestSpringController;
import com.up72.common.excel.ColumnConfig;
import com.up72.common.excel.ExcelExportPoiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
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
@RequestMapping("/admin/jtzs/student")
public class AdminStudentController extends BaseRestSpringController<Student,java.lang.Long>{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private GradeManager gradeManager;
	@Autowired
	private ProvinceManager provinceManager;
	private StudentManager studentManager;
	
	private final String LIST_ACTION = "redirect:/admin/jtzs/student";
	
	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写
	 **/
	public void setStudentManager(StudentManager manager) {
		this.studentManager = manager;
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
	public String index(ModelMap model,StudentQuery query,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Page page = this.studentManager.findPage(query);
		
		this.addShowLabelAttrbite("/admin/jtzs/student", request, response);
		model.addAttribute("provinceList",provinceManager.getShowListBySort());
		model.addAttribute("gradeList",gradeManager.getGradeListBySort());
		model.addAttribute("statusArray",JTZSConstants.Status.values());
		model.addAllAttributes(toModelMap(page, query));
		return "/admin/jtzs/student/index";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		Student student = (Student)studentManager.getById(id);
		model.addAttribute("student",student);
		return "/admin/jtzs/student/show";
	}

	/** 进入新增 */
	@RequestMapping(value="/new")
	public String _new(ModelMap model,Student student,HttpServletRequest request,HttpServletResponse response) throws Exception {
		model.addAttribute("student",student);
		model.addAttribute("provinceList",provinceManager.getShowListBySort());
		model.addAttribute("gradeList",gradeManager.getGradeListBySort());
		model.addAttribute("statusArray",JTZSConstants.Status.values());
		model.addAttribute("sexArray",JTZSConstants.Sex.values());
		return "/admin/jtzs/student/new";
	}
	
	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method=RequestMethod.POST)
	public String create(ModelMap model,@Valid Student student,BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return  "/admin/jtzs/student/new";
		}
		if(null == student.getPassword()){
			student.setPassword("");
		}
		if(null == student.getImgPath()){
			if(student.getSex()==JTZSConstants.Sex.WOMEN.getIndex()){
				student.setImgPath(JTZSConstants.DEFOULT_IMG_WOMAN);
			}else{
				student.setImgPath(JTZSConstants.DEFOULT_IMG_MAN);
			}
		}
		if(StringUtils.isBlank(student.getPassword())){
			student.setPassword("111111");
		}
		student.setPassword(encryptUtil.md5(student.getPassword()));
		student.setAddTime(new Date().getTime());
		studentManager.save(student);
		Flash.current().success("创建用户成功，默认密码为：111111"); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	/** 编辑 */
	@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		Student student = (Student)studentManager.getById(id);
		model.addAttribute("student",student);
		model.addAttribute("provinceList",provinceManager.getShowListBySort());
		model.addAttribute("gradeList",gradeManager.getGradeListBySort());
		model.addAttribute("statusArray",JTZSConstants.Status.values());
		model.addAttribute("sexArray",JTZSConstants.Sex.values());
		return "/admin/jtzs/student/edit";
	}
	
	/** 选项卡编辑 */
	@RequestMapping(value="/{id}/tabEdit")
	public String tabEdit(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		Student student = (Student)studentManager.getById(id);
		model.addAttribute("student",student);
		return "/admin/jtzs/student/tab_edit";
	}
	
	
	/** 选项卡显示 */
	@RequestMapping(value="/{id}/tabShow")
	public String tabShow(ModelMap model,@PathVariable java.lang.Long id) throws Exception {
		Student student = (Student)studentManager.getById(id);
		model.addAttribute("student",student);
		return "/admin/jtzs/student/tab_show";
	}
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(ModelMap model,@PathVariable java.lang.Long id,@Valid Student student,BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return "/admin/jtzs/student/edit";
		}
		Student s = studentManager.getById(id);
		if(null != s){
			s.setImgPath(student.getImgPath());
			s.setStatus(student.getStatus());
			s.setSex(student.getSex());
			s.setNickName(student.getNickName());
			s.setProvinceId(student.getProvinceId());
			s.setSchoolId(student.getSchoolId());
			s.setGradeId(student.getGradeId());
			studentManager.update(s);
			Flash.current().success(UPDATE_SUCCESS);
		}else{
			Flash.current().error("用户不存在");
		}
		
		return LIST_ACTION;
	}
	
	/** 删除 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(ModelMap model,@PathVariable java.lang.Long id) {
		studentManager.removeById(id);
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}

	/** 批量删除 */
	@RequestMapping(method=RequestMethod.DELETE)
	public String batchDelete(ModelMap model,@RequestParam("items") java.lang.Long[] items) {
		for(int i = 0; i < items.length; i++) {
			studentManager.removeById(items[i]);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	/** 修改密码 */
	@RequestMapping(value="/{id}/editPassword")
	public String editPassword(ModelMap model,@PathVariable java.lang.Long id) {
		Student student = studentManager.getById(id);
		model.addAttribute("student", student);
		return "/admin/jtzs/student/editPassword";
	}
	
	/** 修改密码 */
	@RequestMapping(value="/{id}/doEditPassword")
	public String doEditPassword(ModelMap model,@PathVariable java.lang.Long id,HttpServletRequest request, HttpServletResponse response) {
		Student student = studentManager.getById(id);
		if(null!=student){
			String password = paramUtils.getParameter(request, "password", "");
			String rePassword = paramUtils.getParameter(request, "rePassword", "");
			if(!"".equals(password)){
				if(password.equals(rePassword)){
					student.setPassword(encryptUtil.md5(password));
					Flash.current().success("密码修改成功");
				}else{
					Flash.current().success("两次密码不一致");
				}
			}else{
				Flash.current().success("密码不能为空");
			}
		}else{
			Flash.current().success("用户不存在");
		}
		return LIST_ACTION;
	}
	
	/** 
	 * 设置启用禁用
	 * @author liutongling
	 **/
	@RequestMapping(value="/{id}/doValid")
	@ResponseBody
	public String doValid(@PathVariable java.lang.Long id,HttpServletRequest request) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String status = ERROR;

		Student student = studentManager.getById(id);
		if(null != student){
			if(JTZSConstants.Status.NORMAL.getIndex()==student.getStatus()){
				student.setStatus(JTZSConstants.Status.FREEZE.getIndex());
			}else{
				student.setStatus(JTZSConstants.Status.NORMAL.getIndex());
			}
			studentManager.update(student);
			jsonMap.put("valid", student.getStatus());
			status = SUCCESS;
		}
		jsonMap.put("status", status);
		return jsonUtil.object2Json(jsonMap);
	}
	
	/** 
	 * 判断是否唯一
	 * @author liutongling
	 **/
	@RequestMapping(value="/isUnique")
	@ResponseBody
	public boolean isUnique(@Valid Student student) throws Exception {
		return studentManager.isUniqueLoginName(student.getLoginName(), student.getId()) && teacherManager.isUniqueLoginName(student.getLoginName(), null);
	}
	
	/** 导出数据 */
	@RequestMapping(value="/exportData")
	public String exportData(ModelMap model,StudentQuery query,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Student> dataList = new LinkedList<Student>();
		String loginName    = paramUtils.getParameter(request,"loginName") ;
		int status       = paramUtils.getIntParameter(request,"status", -1) ;
		long provinceId   = paramUtils.getLongParameter(request,"provinceId", 0) ;
		long gradeId      = paramUtils.getLongParameter(request,"gradeId", 0) ;
		long schoolId     = paramUtils.getLongParameter(request,"schoolId", 0) ;
        
		query.setLoginName(loginName);
		if(status!=-1){
        query.setStatus(status);
        }
		if(provinceId!=0){
        query.setProvinceId(provinceId);
		}
		if(gradeId!=0){
		query.setGradeId(gradeId);	
		}
		if(schoolId!=0){
		query.setSchoolId(schoolId);
		}
        Page page = this.studentManager.findPage(query);
		int count  = page.getLastPageNumber();
		if(count>=1){
			for(int i=1;i<=count;i++){
			  query.setPageNumber(i);
			  List<Student> list = page.getResult();
			  dataList.addAll(list);
			}
		}
		
		List<ColumnConfig> configList = new ArrayList<ColumnConfig>();
		configList.add(new ColumnConfig("statusStr", "状态"));
		configList.add(new ColumnConfig("shengfen", "省份"));
		configList.add(new ColumnConfig("nickName", "昵称"));
		configList.add(new ColumnConfig("loginName", "账户"));
		configList.add(new ColumnConfig("xuexiao", "学校"));
		configList.add(new ColumnConfig("nianji", "年级"));
		configList.add(new ColumnConfig("sexStr", "性别"));
		configList.add(new ColumnConfig("imgPath", "头像路径"));
		configList.add(new ColumnConfig("lastLoginTimeStrs", "最后登录时间"));
		configList.add(new ColumnConfig("addTimeStrs", "注册时间"));

        long currenttime = System.currentTimeMillis();
        String filePath = "D:/temp/"+"StudentList_"+currenttime+".xls";
        String fileName = "学生列表导出文件.xls";
		ExcelExportPoiUtil eep = new ExcelExportPoiUtil(filePath, 0,
				configList);

		eep.exportHeader(new HSSFColor.BLACK(),
				new HSSFColor.WHITE());

		eep.export(dataList);
		eep.save();
		request.setAttribute("fileName", fileName);
		request.setAttribute("filePath", filePath);
		request.getRequestDispatcher("/fileDownload/commonDownload").forward(request,response);
		
		this.addShowLabelAttrbite("/admin/jtzs/student", request, response);
		
		model.addAllAttributes(toModelMap(page, query));
		return "/admin/jtzs/student/index";
	}
	
	
}

