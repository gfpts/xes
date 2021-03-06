<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<div class="up72_edit">
  <form:form id="admin_jtzs_city_edit_form" method="post" action="${ctx}/admin/jtzs/city/" modelAttribute="city" >
    <table border="0" cellspacing="0" cellpadding="0" width="100%" class="edit_table">
      <%@ include file="form_include.jsp" %>
    </table>
    <div class="up72_submit">
      <div class="btn btn_sub" title="完成"><input type="submit" id="submitButton" name="submitButton" value="完成" /></div>
      <div class="btn btn_cel" title="取消"><input type="button" id="close_button" value="取消" onclick="closeBox();" /></div>
    </div>
  </form:form>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#admin_jtzs_city_edit_form").validate({
			/*  
			// ajax提交方式
			submitHandler:function(form){   
				form.submit();
			},
			*/
			rules: {
				name:{
					required:true,
					byteMax:50,
					remote:{
						url:'${ctx}/admin/jtzs/city/isUnique',
						data:{
							id:'${city.id}',
							provinceId:function(){
								return $("#admin_jtzs_city_edit_form #provinceId option:selected").val();
							}
						}
					}
				}
			},
			messages:{
				name:{
					remote:'内容不能重复',
					required:'请填写内容',
					byteMax:'不能大于50字'
				}
			},
			errorPlacement: function(error, element) {
				error.appendTo(element.parent());
			}
		});	
	});
</script> 
