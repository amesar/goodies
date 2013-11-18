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
import com.amm.sample.springjdbc.object.Nation;
import com.amm.sample.springjdbc.object.Region;

public class NationDao extends JdbcDaoSupport {
	private static final String TABLE = "nation" ;

	// **** Get

	public Nation get(int id) {
		final String sql = "select * from "+TABLE+ " where id=?" ; 
		try {
			Object[] params = new Object[] {id };
			return getJdbcTemplate().queryForObject(sql, params,
				new RowMapper<Nation>() {
					public Nation mapRow(ResultSet rs, int index) throws SQLException {
						Nation nation = new Nation(rs.getInt("id"));
						nation.setName(rs.getString("name"));
						nation.setPopulation(rs.getInt("population"));
						Region region = new Region(rs.getInt("region_id"));
						nation.setRegion(region);
						return nation;
					}
				})
			;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// **** Insert

	public int insert(final Nation nation) {
		final String sql = "insert into "+TABLE+ " (name, population,region_id) VALUES (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(
			 new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql, new String [] {"id"});
					fillPreparedStatement(ps,nation);
					return ps;
				}
			 },
			 keyHolder);
		int key = keyHolder.getKey().intValue();
		nation.setId(key);
		return key;
	}

	private static int fillPreparedStatement(PreparedStatement ps, Nation nation) throws SQLException {
		int col=1;
   		ps.setString(col++, nation.getName()); 
   		ps.setInt(col++, nation.getPopulation()); 
		Region region = nation.getRegion();
		if (region != null)
   			ps.setInt(col++, region.getId()); 
		return col;
   	}

	// **** Update

	public void update(final Nation nation) {
		final String sql1 = "update "+TABLE+" set name=?, population=?, region_id=? where id = ?";	
		final String sql2 = "update "+TABLE+" set name=?, population=? where id = ?";	
		getJdbcTemplate().update(
			 new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String sql = nation.getRegion()==null?sql2:sql1;
					PreparedStatement ps = connection.prepareStatement(sql);
					int col = fillPreparedStatement(ps,nation);
   					ps.setInt(col, nation.getId()); 
					return ps;
				}
			 });
	}

	// **** Delete

	public void delete(Nation nation) {
		getJdbcTemplate().execute("delete from "+TABLE+" where id="+nation.getId());
	}

	public void delete() {
		getJdbcTemplate().execute("delete from "+TABLE);
	}
}
