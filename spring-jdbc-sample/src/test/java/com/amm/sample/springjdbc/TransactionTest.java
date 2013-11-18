package com.amm.sample.springjdbc;

import java.util.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.amm.sample.springjdbc.service.*;
import com.amm.sample.springjdbc.object.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

public class TransactionTest extends BaseTest {

	@BeforeClass() 
	public void beforeClass() {
		deleteTables();
	}

	@Test
	public void insert() {
		Nation nation = new Nation("USA");
		Region region = new Region("America");
		serviceService.insertNationAndRegion(nation,region);
	}
	
	@Test(dependsOnMethods={"insert"}, expectedExceptions = DuplicateKeyException.class)
	public void insert_RegionAlreadyExists() {
		Nation nation = new Nation("Canada");
		Region region = new Region("America"); // Already exists
		serviceService.insertNationAndRegion(nation,region);
	}

	// Since Region is added first in DomainService method, a failed Nation insert should roll back Region
	@Test
	public void insert_TransactionFails() {
		try {
			Region region = new Region("Africa"); 
			Nation nation = new Nation(null); // Bad nation - no name
			serviceService.insertNationAndRegion(nation,region);
		} catch (DataIntegrityViolationException e) {
			Region region = serviceService.getRegionByName("Africa");
			Assert.assertNull(region);
			return;
		}
		Assert.fail("should throw DataIntegrityViolationException");
	}

	// Same logic as above but without Transaction annotation
	@Test
	public void insert_WithoutTransaction() {
		try {
			Region region = new Region("Europe"); 
			Nation nation = new Nation(null); // Bad nation - no name
			serviceService.insertNationAndRegionNoTx(nation,region);
		} catch (DataIntegrityViolationException e) {
			Region region = serviceService.getRegionByName("Europe");
			Assert.assertNotNull(region);
			return;
		}
		Assert.fail("should throw DataIntegrityViolationException");
	}

	void debug(Object o) { System.out.println(">> TransTest: "+o) ; }
}
