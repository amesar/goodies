package com.amm.sample.springjdbc.service;

import java.util.*;
import org.apache.log4j.Logger;
import com.amm.sample.springjdbc.object.*;
import com.amm.sample.springjdbc.dao.*;
import com.amm.sample.springjdbc.util.*;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Domain service implementation
 */
public class DomainService {
	private static final Logger log = Logger.getLogger(DomainService.class);
	private RegionDao regionDao ;
	private NationDao nationDao ;

	public DomainService() {
	}

	public DomainService(RegionDao regionDao, NationDao nationDao) {
		this.regionDao = regionDao ;
		this.nationDao = nationDao ;
	}


	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public Region getRegion(int id) {
		return regionDao.get(id);
	}
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public Region getRegionByName(String name) {
		return regionDao.getByName(name);
	}

	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public int insertRegion(Region obj) {
		return regionDao.insert(obj);
	}

	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public void updateRegion(Region obj) {
		regionDao.update(obj);
	}

	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public void deleteRegion(Region obj) {
		regionDao.delete(obj);
	}
	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public void deleteRegion() {
		regionDao.delete();
	}


	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public Nation getNation(int id) {
		Nation nation = nationDao.get(id);
		if (nation == null)
			return null;
		Region region = regionDao.get(nation.getRegion().getId());
		nation.setRegion(region);
		return nation ;
	}

	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public int insertNation(Nation obj) {
		return nationDao.insert(obj);
	}

	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public void updateNation(Nation obj) {
		nationDao.update(obj);
	}

	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public void deleteNation(Nation obj) {
		nationDao.delete(obj);
	}
	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public void deleteNation() {
		nationDao.delete();
	}

	@Transactional(propagation=Propagation.REQUIRED ,rollbackFor=RuntimeAppException.class)
	public int insertNationAndRegion(Nation nation, Region region) {
		regionDao.insert(region);
		nation.setRegion(region);
		return nationDao.insert(nation);
	}

	public int insertNationAndRegionNoTx(Nation nation, Region region) {
		regionDao.insert(region);
		nation.setRegion(region);
		return nationDao.insert(nation);
	}
}
