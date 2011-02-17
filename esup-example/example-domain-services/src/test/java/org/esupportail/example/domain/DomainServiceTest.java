/**
 * 
 */
package org.esupportail.example.domain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.esupportail.example.dao.DaoService;
import org.esupportail.example.domain.beans.User;
import org.junit.Before;
import org.junit.Test;

/**
 * @author cleprous
 */
public class DomainServiceTest {

	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * see {@link DaoService}.
	 */
	private DaoService daoService;

	/**
	 * see {@link DomainServiceImpl}.
	 */
	private DomainServiceImpl domainServiceImpl;

	/*
	 *************************** INIT ************************************** */

	/**
	 * Constructors.
	 */
	public DomainServiceTest() {
		super();
	}

	/**
	 * 
	 */
	@Before
	public void setUp() {
		daoService = EasyMock.createMock(DaoService.class);
		domainServiceImpl = new DomainServiceImpl();
		domainServiceImpl.setDaoService(daoService);
	}

	/*
	 *************************** METHODS *********************************** */
	/**
	 * test getUsers().
	 */
	@Test
	public void getUsers() {

		List<User> list = new ArrayList<User>();
		User user = new User();
		user.setId("cleprous");
		user.setDisplayName("Leproust Cédric");
		User user1 = new User();
		user.setId("bourges");
		user.setDisplayName("Bourges Raymond");
		list.add(user1);
		list.add(user);
		
		
		EasyMock.expect(daoService.getUsers()).andReturn(list);
		EasyMock.replay(daoService);

		List<User> result = domainServiceImpl.getUsers();
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() == 2);

		EasyMock.verify(daoService);
	}

	/*
	 *************************** ACCESSORS ********************************* */

}
