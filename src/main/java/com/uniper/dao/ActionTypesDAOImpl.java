package com.uniper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.uniper.model.ActionTypes;


public class ActionTypesDAOImpl implements ActionTypesDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public ActionTypesDAOImpl(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void saveOrUpdate(ActionTypes actionTypes) {
		
		if(actionTypes.getId() > 0){
			String sql = "UPDATE actionTypes SET name = ?, WHERE id = ?";
			jdbcTemplate.update(sql, actionTypes.getName(), actionTypes.getId());
		}else{
			String sql = "INSERT INTO actionTypes (name)" + "VALUES(?)";
			jdbcTemplate.update(sql,actionTypes.getName());
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM actionTypes WHERE id=?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public ActionTypes get(int id) {
		String sql = "SELECT * FROM actionTypes WHERE id = " + id;
		return jdbcTemplate.query(sql, new ResultSetExtractor<ActionTypes>(){

			@Override
			public ActionTypes extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					ActionTypes actionTypes = new ActionTypes();
					actionTypes.setId(rs.getInt("Id"));
					actionTypes.setName(rs.getString("name"));
					return actionTypes;
				}
				return null;
			}
			
		});
	}

	@Override
	public List<ActionTypes> list() {
		String sql = "SELECT * FROM actionTypes";
		List<ActionTypes> listActionTypes = jdbcTemplate.query(sql, new RowMapper<ActionTypes>(){

			@Override
			public ActionTypes mapRow(ResultSet rs, int arg1) throws SQLException {
				
				
				ActionTypes actionTypes = new ActionTypes();
				
				actionTypes.setId(rs.getInt("id"));
				actionTypes.setName(rs.getString("name"));
				
				return actionTypes;
			}
			
		});
		return listActionTypes;
		
	}

}
