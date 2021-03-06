/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2010
 */

package com.up72.auth.dao;

import static com.up72.framework.util.ObjectUtils.isNotEmpty;

import org.springframework.stereotype.Repository;

import com.up72.auth.model.MemberPermission;
import com.up72.auth.vo.query.MemberPermissionQuery;
import com.up72.base.BaseHibernateDao;
import com.up72.framework.page.Page;

/**
 * 用户与权限表Hibernate数据处理
 * 
 * @author UP72
 * @version 1.0
 * @since 1.0
 */
@Repository
public class MemberPermissionDao extends BaseHibernateDao<MemberPermission,java.lang.Long>{

	@SuppressWarnings({ "unchecked" })
	public Class getEntityClass() {
		return MemberPermission.class;
	}
	
	@SuppressWarnings({ "unchecked" })
	public Page findPage(MemberPermissionQuery query) {
        //XsqlBuilder 
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from MemberPermission t where 1=1 "
			  	+ "/~ and t.memberId = {memberId} ~/"
			  	+ "/~ and t.permissionId = {permissionId} ~/"
			  	+ "/~ and t.state = {state} ~/"
				+ "/~ order by [sortColumns] ~/";

        //生成sql2的原因是为了不喜欢使用xsqlbuilder的同学，请修改生成器模板，删除本段的生成
        StringBuilder sql2 = new StringBuilder("select t from MemberPermission t where 1=1 ");
        if(isNotEmpty(query.getId())) {
            sql2.append(" and  t.id = :id ");
        }
        if(isNotEmpty(query.getMemberId())) {
            sql2.append(" and  t.memberId = :memberId ");
        }
        if(isNotEmpty(query.getPermissionId())) {
            sql2.append(" and  t.permissionId = :permissionId ");
        }
        if(isNotEmpty(query.getState())) {
            sql2.append(" and  t.state = :state ");
        }
        if(isNotEmpty(query.getSortColumns())) {
            sql2.append(" order by :sortColumns ");
        }	
        
		return pageQuery(sql,query);
	}
	

}
