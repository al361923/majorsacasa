package es.uji.ei1027.majorsacasa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.uji.ei1027.majorsacasa.model.Volunteer;

@Repository
public class VolunteerDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired 
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		
	}
	
	
	//ANDREEA al insertar un nuevo voluntario , el campo isAccepted lo actualiza un trabajador social y lo mismo ocurre con el
	//campo endDate. No se deben insertar ahora
	
	public void addVolunteer(Volunteer volunteer) {
		jdbcTemplate.update("INSERT INTO VOLUNTEER VALUES(?,?,?,?,?,?,?,?,?,?,?)",
							volunteer.getName(),
							volunteer.getPhoneNumber(),
							volunteer.getEmail(),
							volunteer.getUsr(),
							volunteer.getPwd(),
							volunteer.getHobbies(),
							volunteer.getApplicationDate(),
							volunteer.getAcceptationDate(),
							volunteer.isAccepted(),
							volunteer.getBirthDate(),
							volunteer.getEndDate()
							);
			}
	
	public void deleteVolunteer(String Usr) {
		jdbcTemplate.update("UPDATE FROM VOLUNTEER SET ENDDATE = " + new Date() + " WHERE USR = ?", Usr);
	}
	
	public void updateVolunteer(Volunteer volunteer){
		jdbcTemplate.update("UPDATE VOLUNTEER SET NAME = ?, PHONENUMBER = ?, EMAIL = ?, USR = ?, PWD = ?, HOBBIES = ?,"
							+" BIRTHDATE = ? WHERE USR = ?",
							volunteer.getName(),
							volunteer.getPhoneNumber(),
							volunteer.getEmail(),
							volunteer.getUsr(),
							volunteer.getPwd(),
							volunteer.getHobbies(),
							volunteer.getBirthDate(),
							volunteer.getUsr()
							);
			
	}
	
	public Volunteer getVolunteerByUsr(String Usr) {
		try{
			return jdbcTemplate.queryForObject("SELECT * FROM VOLUNTEER WHERE USR = ?", new VolunteerRowMapper(), Usr);
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public Volunteer getVolunteerByEmail(String email) {
		try{
			return jdbcTemplate.queryForObject("SELECT * FROM VOLUNTEER WHERE EMAIL = ?", new VolunteerRowMapper(), email);
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public List<Volunteer> getVolunteers(){
		try{
			return jdbcTemplate.query("SELECT * FROM VOLUNTEER", new VolunteerRowMapper());
		}catch (EmptyResultDataAccessException e){
			return new ArrayList<Volunteer>();
		}
		
	}
}
