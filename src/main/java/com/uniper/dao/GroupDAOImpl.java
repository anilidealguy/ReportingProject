package com.uniper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.uniper.model.Group;

public class GroupDAOImpl implements GroupDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public GroupDAOImpl(DataSource datasource){
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void saveOrUpdate(Group group) {
		if(group.getId() > 0){
			String sql = "UPDATE groups SET GroupName = ?";
			jdbcTemplate.update(sql, group.getGroupName());
		}else{
			String sql = "INSERT INTO groups (GroupName)" + "VALUES(?)";
			jdbcTemplate.update(sql,group.getGroupName());
		}

	}

	@Override
	public void delete(int groupId) {
		String sql = "DELETE FROM groups WHERE id=?";
		jdbcTemplate.update(sql,groupId);
	}

	@Override
	public Group get(String name) {
		String sql = "SELECT * FROM groups WHERE GroupName = " + name;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Group>(){

			@Override
			public Group extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Group group = new Group();
					group.setId(rs.getInt("Id"));
					group.setGroupName(rs.getString("GroupName"));
				}
				return null;
			}
			
		});
	}

	@Override
	public List<Group> list() {
		String sql = "SELECT * FROM groups";
		List<Group> listGroup = jdbcTemplate.query(sql, new RowMapper<Group>(){

			@Override
			public Group mapRow(ResultSet rs, int arg1) throws SQLException {
				Group group = new Group();
				
				group.setId(rs.getInt("id"));
				group.setGroupName(rs.getString("GroupName"));
				
				return group;
			}
			
		});
		return listGroup;
		
	}

}
