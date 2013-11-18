package com.amm.sample.springjdbc.dao;

import java.util.*;
import java.sql.*;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import com.amm.sample.springjdbc.object.Region;

public class RegionDao extends JdbcDaoSupport {
	private static final String TABLE = "region" ;

	// **** Get

	public Region get(int id) {
		final String sql = "select * from "+TABLE+ " where id=?" ; 
		Object[] params = new Object[] {id};
		return getByParams(sql, new Object[] {id});
	}

	public Region getByName(String name) {
		final String sql = "select * from "+TABLE+ " where name=?" ; 
		return getByParams(sql, new Object[] {name});
	}

	private Region getByParams(String sql, Object [] params) {
		try {
			return getJdbcTemplate().queryForObject(sql, params,
				new RowMapper<Region>() {
					public Region mapRow(ResultSet rs, int index) throws SQLException {
						Region obj = new Region(rs.getInt("id"));
						obj.setName(rs.getString("name"));
						obj.setComment(rs.getString("comment"));
						return obj;
					}
				})
			;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// **** Insert

	public int insert(final Region obj) {
		final String sql = "insert into "+TABLE+ " (name, comment) VALUES (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
			 new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql, new String [] {"id"});
					fillPreparedStatement(ps,obj);
					return ps;
				}
			 },
			 keyHolder);
		int key = keyHolder.getKey().intValue();
		obj.setId(key);
		return key;
	}

	private static void fillPreparedStatement(PreparedStatement ps, Region obj) throws SQLException {
   		ps.setString(1, obj.getName()); 
   		ps.setString(2, obj.getComment()); 
   	}

	// **** Update

	public void update(final Region obj) {
		final String sql = "update "+TABLE+" set name = ?,comment = ? where id = ?";	
		//getJdbcTemplate().update(sql, new Object[] { obj.getName(), obj.getComment(), obj.getId() });	
		getJdbcTemplate().update(
			 new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql);
					fillPreparedStatement(ps,obj);
   					ps.setInt(3, obj.getId()); 
					return ps;
				}
			 });
	}

	// **** Delete

	public void delete(Region obj) {
		getJdbcTemplate().execute("delete from "+TABLE+" where id="+obj.getId());
	}

	public void delete() {
		getJdbcTemplate().execute("delete from "+TABLE);
	}
}
