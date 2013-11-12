package test;

import com.example.smsquickform.model.DbCriteria;

import junit.framework.TestCase;

public class CriteriaTest extends TestCase {

	public void testAddColumnCondition() {
		DbCriteria crit = new DbCriteria();
		crit.addColumnCondition("nik", "123143434343");
		crit.addColumnCondition("nama", "nurul");
		String h = crit.getConditions();
		//fail("Not yet implemented");
	}

	public void testAddConditionStringString() {
		DbCriteria crit = new DbCriteria();
		crit.addCondition("nama = nurul");
		String h = crit.getConditions();
		//fail("Not yet implemented");
	}

	public void testAddConditionString() {
		fail("Not yet implemented");
	}

	public void testGetOrder() {
		fail("Not yet implemented");
	}

	public void testSetOrder() {
		fail("Not yet implemented");
	}

	public void testGetHaving() {
		fail("Not yet implemented");
	}

	public void testSetHaving() {
		fail("Not yet implemented");
	}

	public void testGetGroup() {
		fail("Not yet implemented");
	}

	public void testSetGroup() {
		fail("Not yet implemented");
	}

	public void testAddWhere() {
		fail("Not yet implemented");
	}

	public void testGetConditions() {
		fail("Not yet implemented");
	}

}
