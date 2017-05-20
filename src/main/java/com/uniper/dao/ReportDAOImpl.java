package com.uniper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.uniper.controller.MainController;
import com.uniper.model.Report;
import com.uniper.model.Steps;

public class ReportDAOImpl implements ReportDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public ReportDAOImpl(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void saveOrUpdate(Report report) {
		if(report.getId() > 0){
			String sql = "update query";
			jdbcTemplate.update(sql);
		}else{
			//String sql = "INSERT INTO reports ( groupId, subGroupId, name) VALUES (?,?,?)";
			String sql = "INSERT INTO reports (groupId, subGroupId, name) VALUES (" + report.getGroupId() + ", " + report.getSubGroupId() + ", '" + report.getName() + "')";
			try {
				Statement stmt = jdbcTemplate.getDataSource().getConnection().createStatement();
				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()){
					System.out.println(rs.getInt(1));
					report.setId(rs.getInt(1));
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//jdbcTemplate.update(sql, report.getGroupId(), report.getSubGroupId(), report.getName());
			
			List<Steps> steps = report.getSteps();
						
			for (Steps step : steps) {
				String stepSql = "INSERT INTO steps (reportId, sequenceNumber, actionType, arg1, arg2, arg3, arg4) VALUES (?,?,?,?,?,?,?)";
				jdbcTemplate.update(stepSql, report.getId(), step.getSequenceNumber(), step.getActionType(), step.getArg1(), step.getArg2(), step.getArg3(), step.getArg4());
			}
		}

	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM reports WHERE id=?";
		jdbcTemplate.update(sql, id);

	}

	@Override
	public Report get(int id) {
		String sql = "SELECT r.id, r.groupId, g.GroupName, r.subGroupId, sg.SubGroupName, r.name FROM reports r, groups g, subgroups sg where r.groupId = g.id and r.subGroupId = sg.id and r.id = "+id;
		Report selectedReport =  jdbcTemplate.query(sql, new ResultSetExtractor<Report>(){

			@Override
			public Report extractData(ResultSet rs) throws SQLException, DataAccessException {
				Report report = new Report();
				if(rs.next()){
					report.setId(rs.getInt("id"));
					report.setGroupId(rs.getInt("groupId"));
					report.setGroupName(rs.getString("GroupName"));
					report.setSubGroupId(rs.getInt("subGroupId"));
					report.setSubGroupName(rs.getString("SubGroupName"));
					report.setName(rs.getString("name"));
					StepsDAO stepsDAO = new StepsDAOImpl(MainController.getDataSource());
					List<Steps> steps = stepsDAO.get(id);
					report.setSteps(steps);
					
					
				}
				return report;
			}
			
		});
		return selectedReport;
	}

	@Override
	public List<Report> list() {
		String sql = "SELECT r.id, r.groupId, g.GroupName, r.subGroupId, sg.SubGroupName, r.name FROM reports r, groups g, subgroups sg where r.groupId = g.id and r.subGroupId = sg.id";
		List<Report> listReport = jdbcTemplate.query(sql, new RowMapper<Report>(){

			@Override
			public Report mapRow(ResultSet rs, int arg1) throws SQLException {
				Report report = new Report();
				
				report.setId(rs.getInt("id"));
				report.setGroupId(rs.getInt("groupId"));
				report.setGroupName(rs.getString("GroupName"));
				report.setSubGroupId(rs.getInt("subGroupId"));
				report.setSubGroupName(rs.getString("SubGroupName"));
				report.setName(rs.getString("name"));
				
				StepsDAO stepsDAO = new StepsDAOImpl(MainController.getDataSource());
				List<Steps> steps = stepsDAO.get(rs.getInt("id"));
				report.setSteps(steps);
				
				return report;
			}
			
		});
		return listReport;
		
	}

}
