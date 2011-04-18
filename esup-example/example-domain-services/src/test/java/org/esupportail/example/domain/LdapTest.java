package org.esupportail.example.domain;

import org.esupportail.commons.services.ldap.LdapUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations="/properties/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class LdapTest {
	
	LdapUserService ldapUserService;
	
	@Test
	public void testNuxeoAutomationClient() throws Exception {
		ldapUserService.test();
	}

	/**
	 * @param ldapUserService the ldapUserService to set
	 */
	@Autowired
	public void setLdapUserService(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

}
