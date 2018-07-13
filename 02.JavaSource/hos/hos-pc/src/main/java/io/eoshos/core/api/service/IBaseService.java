package io.eoshos.core.api.service;

import java.sql.SQLException;

import com.chuangke18.framework.api.response.CKResponse;

public interface IBaseService<Bo, Dto>{
	
	/**
	 * 
	* @Title: save 
	* @Description: 保存,id为空时增加否则修改
	* @param @param bo
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带保存是否成功信息)
	* @throws
	 */
	CKResponse save(Bo bo) ;
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除,建议使用id做查询条件
	* @param @param bo
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带删除是否成功信息)
	* @throws
	 */
	CKResponse delete(Bo bo) ;
	
	
	/**
	 * 
	* @Title: getObject 
	* @Description: 查询单个对象,建议使用id做查询条件
	* @param @param dto
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型(带对象信息) 
	* @throws
	 */
	CKResponse getObject(Dto dto) ;
	
	/**
	 * 
	* @Title: listAllObjects 
	* @Description: 不分页查询
	* @param @param dto
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带对象信息) 
	* @throws
	 */
	CKResponse listAllObjects(Dto dto) ;
	
	/**
	 * 
	* @Title: listPagingObjects 
	* @Description: 分页查询
	* @param @param dto
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带对象信息)
	* @throws
	 */
	CKResponse listPagingObjects(Dto dto) ;
	
}
