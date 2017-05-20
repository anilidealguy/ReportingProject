package com.uniper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.uniper.model.LineType;

public class LineTypeDAOImpl implements LineTypeDAO {
	
	
	private JdbcTemplate jdbcTemplate;
	
	public LineTypeDAOImpl(DataSource datasource){
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void saveOrUpdate(LineType lineType) {
		
		if(lineType.getId() > 0){
			String sql = "UPDATE linetypes SET LineType = ?, WHERE id = ?";
			jdbcTemplate.update(sql, lineType.getName(), lineType.getId());
		}else{
			String sql = "INSERT INTO linetypes (LineType)" + "VALUES(?)";
			jdbcTemplate.update(sql,lineType.getName());
		}

	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM linetypes WHERE id=?";
		jdbcTemplate.update(sql,id);
	}

	@Override
	public LineType get(int lineType) {
		String sql = "SELECT * FROM linetypes WHERE id = " + lineType;
		return jdbcTemplate.query(sql, new ResultSetExtractor<LineType>(){

			@Override
			public LineType extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					LineType lineType = new LineType();
					lineType.setId(rs.getInt("Id"));
					lineType.setName(rs.getString("LineType"));
					return lineType;
				}
				return null;
			}
			
		});
	}

	@Override
	public List<LineType> list() {
		String sql = "SELECT * FROM linetypes";
		List<LineType> listLineTypes = jdbcTemplate.query(sql, new RowMapper<LineType>(){

			@Override
			public LineType mapRow(ResultSet rs, int arg1) throws SQLException {
				
				
				LineType lineType = new LineType();
				
				lineType.setId(rs.getInt("id"));
				lineType.setName(rs.getString("LineType"));
				
				return lineType;
			}
			
		});
		return listLineTypes;
		
	}

}
