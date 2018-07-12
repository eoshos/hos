package io.eoshos.core.provider.dao;

import java.sql.SQLException;
import java.util.List;

public interface BaseMapper<Po, Dto, Vo> {
	
	int insert(Po po) throws SQLException;
	
	int update(Po po) throws SQLException;
	
	int delete(Dto dto) throws SQLException;
	
	Vo getObject(Dto dto) throws SQLException;

	List<Vo> listAllObjects(Dto dto) throws SQLException;
	
	List<Vo> listPagingObjects(Dto dto) throws SQLException;
	
	int countObjects(Dto dto) throws SQLException;
	

}