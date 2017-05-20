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
import com.uniper.model.Line;
import com.uniper.model.Mail;

public class MailDAOImpl implements MailDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public MailDAOImpl(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void saveOrUpdate(Mail mail) {
		if(mail.getId() > 0){
			
		}else{
			//String sql = "INSERT INTO mailmetadata (mailName, toList, ccList, subject, msgLocation, save, send) VALUES (?,?,?,?,?,?,?)";
			String sql = "INSERT INTO mailmetadata (mailName, toList, ccList, subject, msgLocation, save, send) VALUES ("
					+ "'" + mail.getMailName() + "', "
					+ "'" + mail.getToList() + "', "
					+ "'" + mail.getCcList() + "', "
					+ "'" + mail.getSubject() + "', "
					+ "'" + mail.getMsgLocation() + "', "
					+ "'" + mail.getSave() + "', "
					+ "'" + mail.getSend() + "')"
					;
			System.out.println(sql);
			try {
				Statement stmt = jdbcTemplate.getDataSource().getConnection().createStatement();
				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()){
					mail.setId(rs.getInt(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//jdbcTemplate.update(sql, mail.getMailName(), mail.getToList(), mail.getCcList(), mail.getSubject());
			
			List<Line> lines = mail.getLines();
			
			for (Line line : lines) {
				String lineSQL = "INSERT INTO mailbody (id, lineNumber, LineType, arg1, arg2, arg3, arg4) VALUES (?,?,?,?,?,?,?)";
				jdbcTemplate.update(lineSQL,mail.getId(),line.getLineNumber(), line.getLineType(), line.getArg1(), line.getArg2(), line.getArg3(), line.getArg4());
			}
		}

	}

	@Override
	public void delete(int reportId) {
		String sql = "DELETE FROM reports WHERE id=?";
		jdbcTemplate.update(sql, reportId);

	}

	@Override
	public Mail get(int id) {
		String sql = "SELECT * FROM mailmetadata WHERE id = " + id ;
		Mail selectedMail =  jdbcTemplate.query(sql, new ResultSetExtractor<Mail>(){

			@Override
			public Mail extractData(ResultSet rs) throws SQLException, DataAccessException {
				Mail mail = new Mail();
				if(rs.next()){
					mail.setId(rs.getInt("id"));
					mail.setMailName(rs.getString("mailName"));
					mail.setToList(rs.getString("toList"));
					mail.setCcList(rs.getString("ccList"));
					mail.setSubject(rs.getString("subject"));
					mail.setMsgLocation(rs.getString("msgLocation"));
					mail.setSave(rs.getString("save"));
					mail.setSend(rs.getString("send"));
					
					
					LineDAO lineDAO = new LineDAOImpl(MainController.getDataSource());
					List<Line> lines = lineDAO.get(rs.getInt("id"));
					
					mail.setLines(lines);
					
					
					
				}
				return mail;
			}
			
		});
		return selectedMail;
	}

	@Override
	public List<Mail> list() {
		String sql = "SELECT * FROM mailmetadata";
		List<Mail> listMail = jdbcTemplate.query(sql, new RowMapper<Mail>(){

			@Override
			public Mail mapRow(ResultSet rs, int arg1) throws SQLException {
				Mail mail = new Mail();
				mail.setId(rs.getInt("id"));
				mail.setMailName(rs.getString("mailName"));
				mail.setToList(rs.getString("toList"));
				mail.setCcList(rs.getString("ccList"));
				mail.setSubject(rs.getString("subject"));
				mail.setMsgLocation(rs.getString("msgLocation"));
				mail.setSave(rs.getString("save"));
				mail.setSend(rs.getString("send"));
				
				
				LineDAO lineDAO = new LineDAOImpl(MainController.getDataSource());
				List<Line> lines = lineDAO.get(rs.getInt("id"));
				
				mail.setLines(lines);
				
				return mail;
					
			}
			
		});
		return listMail;
	}

}
