<%@ page contentType="text/html;charset=UTF-8" %>
<div class="up72_sidebar">
<div style="font-size:14px;font-weight:bold; height:25px; padding-left:10px; display:none" title="${AUTH_PRODUCT.name}" href="javascript:;">
    ${AUTH_PRODUCT.name}
</div>
<!-- <div style="margin-bottom:8px;padding-top:10px;">
    <jsp:include page="/admin/cms/site/siteList" flush="true" />
</div>
 -->
     <div class="up72_cnav">
        <ul class="up72_folders">
        <%@include file="/pages/admin/permLabel.jsp" %>
        </ul>
    </div>
    <div class="up72_bline"></div>
</div>