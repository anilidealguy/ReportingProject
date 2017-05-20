package com.uniper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.uniper.model.Steps;
import javax.sql.DataSource;

public class StepsDAOImpl implements StepsDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public StepsDAOImpl(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void saveOrUpdate(Steps steps) {
		

	}

	@Override
	public void delete(int reportId) {
		String sql = "DELETE FROM reports WHERE reportId=?";
		jdbcTemplate.update(sql, reportId);

	}

	@Override
	public List<Steps> get(int reportId) {
		String sql = "SELECT * FROM steps WHERE reportId = ?";
		Object[] args = new Object[1];
		args[0] = reportId;
		List<Steps> listSteps = jdbcTemplate.query(sql, args, new RowMapper<Steps>(){

			@Override
			public Steps mapRow(ResultSet rs, int arg1) throws SQLException {
				Steps step = new Steps();
				step.setReportId(rs.getInt("reportId"));
				step.setSequenceNumber(rs.getInt("sequenceNumber"));
				step.setActionType(rs.getInt("actionType"));
				step.setArg1(rs.getString("arg1"));
				step.setArg2(rs.getString("arg2"));
				step.setArg3(rs.getString("arg3"));
				step.setArg4(rs.getString("arg4"));
				return step;
			}
			
		});
		return listSteps;
	}

	

}
