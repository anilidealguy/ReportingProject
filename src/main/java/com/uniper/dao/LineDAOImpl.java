package com.uniper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.uniper.model.Line;

public class LineDAOImpl implements LineDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public LineDAOImpl(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Line> get(int id) {
		String sql = "SELECT * FROM mailbody WHERE id = " + id;
		List<Line> listLine = jdbcTemplate.query(sql, new RowMapper<Line>(){

			@Override
			public Line mapRow(ResultSet rs, int arg1) throws SQLException {
				Line line = new Line();
				line.setId(rs.getInt("id"));
				line.setLineNumber(rs.getInt("lineNumber"));
				line.setLineType(rs.getInt("LineType"));
				line.setArg1(rs.getString("arg1"));
				line.setArg2(rs.getString("arg2"));
				line.setArg3(rs.getString("arg3"));
				line.setArg4(rs.getString("arg4"));
				return line;
			}
			
		});
		return listLine;
	}

}
