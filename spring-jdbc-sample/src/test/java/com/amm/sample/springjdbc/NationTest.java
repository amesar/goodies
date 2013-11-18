package com.amm.sample.springjdbc;

import java.util.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.amm.sample.springjdbc.service.*;
import com.amm.sample.springjdbc.object.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

public class NationTest extends BaseTest {
	private Nation nationRef ;
	private Region regionRef ;

	@BeforeClass() 
	public void beforeClass() {
		deleteTables();	
	}

	@Test
	public void testInsert_AndGet() {
		Region region = new Region("America");
		region.setComment("Mexico");
		serviceService.insertRegion(region);
		regionRef = region;

		Assert.assertNotNull(region);
		Nation nation = new Nation("Guatemala");
		nation.setPopulation(636479);
		nation.setRegion(region);
		debug("insert.1: serviceService="+serviceService);
		int id = serviceService.insertNation(nation);
		debug("insert.2: nation="+nation);
		nation = serviceService.getNation(id);
		Assert.assertNotNull(nation);
		nationRef = nation;
	}

	@Test(expectedExceptions = DataIntegrityViolationException.class)
	public void testInsert_NotNullColumn() {
		Nation nation = new Nation(null);
		nation.setRegion(regionRef);
		debug("testInsert_NotNullColumn: nation="+nation);
		serviceService.insertNation(nation);
	}

	@Test(expectedExceptions = DataIntegrityViolationException.class)
	public void testInsert_NoForeignKey() {
		Nation nation = new Nation("Belize");
		serviceService.insertNation(nation);
	}

	@Test(dependsOnMethods={"testInsert_AndGet"})
	public void testUpdate() {
		Nation nation = nationRef;
		debug("update.1: nation="+nation);
		int newValue = -99;
		nation.setPopulation(newValue);

		serviceService.updateNation(nation);

		Nation nation2 = serviceService.getNation(nation.getId());
		debug("update.2: nation2="+nation2);
		Assert.assertNotNull(nation2);
		Assert.assertEquals(nation2.getPopulation(),newValue);
	}

	@Test(dependsOnMethods={"testUpdate"})
	public void testDelete() {
		Nation nation = nationRef;
		serviceService.deleteNation(nation);
		nation = serviceService.getNation(nation.getId());
		Assert.assertNull(nation);
	}

	@Test
	public void testGetNonExistentId() {
		Nation nation = serviceService.getNation(-1);
		Assert.assertNull(nation);
	}

	void debug(Object o) { System.out.println(">> NationTest: "+o) ; }
}
