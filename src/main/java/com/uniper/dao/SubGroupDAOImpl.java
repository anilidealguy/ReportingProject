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
import com.uniper.model.SubGroup;

public class SubGroupDAOImpl implements SubGroupDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public SubGroupDAOImpl(DataSource datasource){
		jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override
	public void saveOrUpdate(SubGroup subGroup) {
		if(subGroup.getId() > 0){
			String sql = "UPDATE groups SET GroupId = ? , SubGroupName = ?,  WEHRE Id = ?";
			jdbcTemplate.update(sql,subGroup.getId(),subGroup.getSubGroupName(),subGroup.getId());
		}else{
			String sql = "INSERT INTO subgroups (GroupId, SubGroupName)" + "VALUES(?,?)";
			jdbcTemplate.update(sql,subGroup.getGroupId(),subGroup.getSubGroupName());
		}

	}

	@Override
	public void delete(int subGroupId) {
		String sql = "DELETE FROM subgroups WHERE id=?";
		jdbcTemplate.update(sql,subGroupId);
	}

	@Override
	public Group get(String SubGroupName) {
		String sql = "SELECT * FROM subgroups WHERE SubGroupName = " + SubGroupName;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Group>(){

			@Override
			public Group extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					SubGroup subGroup = new SubGroup();
					subGroup.setId(rs.getInt("Id"));
					subGroup.setGroupId(rs.getInt("GroupId"));
					subGroup.setSubGroupName(rs.getString("SubGroupName"));
				}
				return null;
			}
			
		});
	}

	@Override
	public List<SubGroup> list() {
		String sql = "SELECT * FROM subgroups";
		List<SubGroup> listSubGroup = jdbcTemplate.query(sql, new RowMapper<SubGroup>(){

			@Override
			public SubGroup mapRow(ResultSet rs, int arg1) throws SQLException {
				SubGroup subGroup = new SubGroup();
				
				subGroup.setId(rs.getInt("id"));
				subGroup.setGroupId(rs.getInt("GroupId"));
				subGroup.setSubGroupName(rs.getString("SubGroupName"));
				
				return subGroup;
			}
			
		});
		return listSubGroup;
		
	}

}
