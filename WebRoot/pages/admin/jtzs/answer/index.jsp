<%@page import="com.xes.jtzs.model.*" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<up72:override name="head">
  <title><%=Answer.TABLE_ALIAS%> 维护</title>
  <script src="${ctx}/scripts/rest.js" ></script>
  <link href="${ctx}/scripts/simpletable/simpletable.css" type="text/css" rel="stylesheet">
  <script type="text/javascript" src="${ctx}/scripts/simpletable/simpletable.js"></script> 
  <script type="text/javascript" src="${ctx}/scripts/columnshow.js"></script>
  <link href="${ctx}/scripts/grid/css/flexigrid.css" type="text/css" rel="stylesheet">
  <script type="text/javascript" src="${ctx}/scripts/grid/flexigrid-source.js"></script> 
  <script type="text/javascript" >
		$(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('admin_jtzs_answer_search_form',${page.thisPageNumber},${page.pageSize},'${pageRequest.sortColumns}');
		});
	</script> 
  <script type="text/javascript" src="<c:url value="/scripts/extend.div.1.0.js"/>"></script> 
</up72:override>
<up72:override name="content"> 
  
  <!--搜索-->
  <div class="up72_search">
    <form id="admin_jtzs_answer_search_form" name="admin_jtzs_answer_search_form" method="get">
      <div class="search_con"> 

        <div class="search_txt"><%=Answer.ALIAS_QUESTION_ID%>：
          <input type="text" id="questionId" name="questionId" class="input_text" value="${query.questionId}">
        </div>

        <div class="search_txt"><%=Answer.ALIAS_TEACHER_ID%>：
          <input type="text" id="teacherId" name="teacherId" class="input_text" value="${query.teacherId}">
        </div>

        <div class="search_txt"><%=Answer.ALIAS_ANSWER%>：
          <input type="text" id="answer" name="answer" class="input_text" value="${query.answer}">
        </div>

        <div class="search_txt"><%=Answer.ALIAS_IDER%>：
          <input type="text" id="ider" name="ider" class="input_text" value="${query.ider}">
        </div>

        <div class="search_txt"><%=Answer.ALIAS_ANSWER_TIME%>：
          <input type="text" id="answerTime" name="answerTime" class="input_text" value="${query.answerTime}">
        </div>

        <div class="search_txt"><%=Answer.ALIAS_STATUS%>：
          <input type="text" id="status" name="status" class="input_text" value="${query.status}">
        </div>
        <div class="search_btn">
          <div class="input_button">
            <button name="btnU" type="submit" onclick="$(this).parents('form').submit();" class="button" value="查询"><span>查询</span></button>
          </div>
        </div>
      </div>
    </form>
  </div>
  <!--end搜索-->
  
  <form id="admin_jtzs_answer_page_form" name="admin_jtzs_answer_page_form" method="get">
    <table id="admin_jtzs_answer_table">
      <thead>
        <tr>
          <th showColumn="checkbox" width="25"><input type="checkbox" id="checkall" onclick="setAllCheckboxState('items',this.checked);" /></th>
          <th showColumn="index" width="20">序号</th>
          <th showColumn="option" width="30"><label>操作</label></th>
                    <th sortColumn="QUESTION_ID" showColumn="questionId" width="50"><%=Answer.ALIAS_QUESTION_ID%></th>
                    <th sortColumn="TEACHER_ID" showColumn="teacherId" width="50"><%=Answer.ALIAS_TEACHER_ID%></th>
                    <th sortColumn="ANSWER" showColumn="answer" width="50"><%=Answer.ALIAS_ANSWER%></th>
                    <th sortColumn="IDER" showColumn="ider" width="50"><%=Answer.ALIAS_IDER%></th>
                    <th sortColumn="ANSWER_TIME" showColumn="answerTime" width="50"><%=Answer.ALIAS_ANSWER_TIME%></th>
                    <th sortColumn="STATUS" showColumn="status" width="50"><%=Answer.ALIAS_STATUS%></th>
           </tr>
      </thead>
      <tbody>
        <c:forEach items="${page.result}" var="item" varStatus="status">
          <tr rel="${ctx}/pages/admin/jtzs/answer/tab.jsp?id=${item.id}">
            <td showColumn="checkbox"><input type="checkbox" id="items" name="items" value="${item.id}" class="sel" tags="null"></td>
            <td showColumn="index">${page.thisPageFirstElementNumber + status.index}</td>
            <td showColumn="option"><a href="javascript:;"  onclick="show('${ctx}/admin/jtzs/answer/${item.id}/edit','<%=Answer.TABLE_ALIAS%>',600)" class="sysiconBtnNoIcon">编辑</a>&nbsp;</td>
                        <td showColumn="questionId"><c:out value='${item.questionId}'/>
&nbsp; </td>
                        <td showColumn="teacherId"><c:out value='${item.teacherId}'/>
&nbsp; </td>
                        <td showColumn="answer"><c:out value='${item.answer}'/>
&nbsp; </td>
                        <td showColumn="ider"><c:out value='${item.ider}'/>
&nbsp; </td>
                        <td showColumn="answerTime"><fmt:formatDate value="${item.answerTimeDate}" pattern="yyyy-MM-dd HH:mm"/>
&nbsp; </td>
                        <td showColumn="status"><c:out value='${item.status}'/>
&nbsp; </td>
             </tr>
        </c:forEach>
      </tbody>
    </table>
    <simpletable:pageToolbar page="${page}"></simpletable:pageToolbar>
  </form>
  <script type="text/javascript">
	// 列选择显示处理
	$.showcolumn(${showColumn});
	// 排序处理
	$.sortcolumn({
		form : "#admin_jtzs_answer_search_form",
		data : "pageNumber=${page.thisPageNumber}&pageSize=${page.pageSize}",
		columns : $("#admin_jtzs_answer_table th[sortColumn]"),
		sortColumns : "${pageRequest.sortColumns}"
	});
	
	$("#admin_jtzs_answer_table").rowAction({	
		"url" : "/pages/admin/jtzs/answer/tab.jsp",
		"except" : ["checkbox","index","option"],
		"pop" : true,
		"poptitle" : "<%=Answer.TABLE_ALIAS%>标签",
		"popw" : 600
	});
	// 表格列表处理
	$('#admin_jtzs_answer_table').flexigrid({
		height: 'auto',
		striped : true,
		buttons : [
			{name: "添加", bclass: "addorder", onpress : function(){show("${ctx}/admin/jtzs/answer/new","<%=Answer.TABLE_ALIAS%>添加",600)}},
			{name: '删除', bclass: 'delete', onpress : function(){doRestEdit({confirmMsg:'确认删除选中的记录吗？',url:'${ctx}/admin/jtzs/answer/',batchBox:'items',boxCon:'#admin_jtzs_answer_page_form',form:'#admin_jtzs_answer_page_form',method:'delete'})}}
		]
	});
</script> 
</up72:override>
<%@ include file="base.jsp" %>
