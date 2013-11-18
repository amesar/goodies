package com.amm.sample.springjdbc;

import java.util.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.amm.sample.springjdbc.service.*;
import com.amm.sample.springjdbc.object.*;
import org.springframework.dao.DataIntegrityViolationException;

public class RegionTest extends BaseTest {
	private Region objRef ;

	@BeforeClass() 
	public void beforeClass() {
		deleteTables();
	}

	@Test
	public void testInsertAndGet() {
		Region obj = new Region("America");
		obj.setComment("America");
		int id = serviceService.insertRegion(obj);
		debug("insert: obj="+obj);
		obj = serviceService.getRegion(id);
		Assert.assertNotNull(obj);
		objRef = obj;
	}

	@Test(expectedExceptions = DataIntegrityViolationException.class)
	public void testInsertNotNullColumn() {
		Region obj = new Region(null);
		serviceService.insertRegion(obj);
	}

	@Test(dependsOnMethods={"testInsertAndGet"})
	public void testUpdate() {
		Region obj = objRef;
		debug("update.1: obj="+obj);
		String newValue = "foo";
		obj.setComment(newValue);

		serviceService.updateRegion(obj);

		Region obj2 = serviceService.getRegion(obj.getId());
		debug("update.2: obj2="+obj2);
		Assert.assertNotNull(obj2);
		Assert.assertEquals(obj2.getComment(),newValue);
	}

	@Test(dependsOnMethods={"testUpdate"})
	public void testDelete() {
		Region obj = objRef;
		serviceService.deleteRegion(obj);
		obj = serviceService.getRegion(obj.getId());
		Assert.assertNull(obj);
	}

	@Test
	public void testGetNonExistentId() {
		//debug("testGetNonExistentId.1: serviceService="+serviceService);
		Region obj = serviceService.getRegion(-1);
		Assert.assertNull(obj);
	}

	void debug(Object o) { System.out.println(">> RegionTest: "+o) ; }
}
