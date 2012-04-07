/**
* Esup-portail - esup-anciens - 2009
* http://subversion.univ-rennes1.fr/repos/57si-anciens
* 
*/

/**
 * 
 */
package org.esupportail.example.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cleprous
 *
 */
@ContextConfiguration(locations="/properties/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoServiceTest {
	
//	DaoService daoService;
//
//	/**
//	 * @param ldapUserService the ldapUserService to set
//	 */
//	@Autowired
//	public void setLdapUserService(DaoService daoService) {
//		this.daoService = daoService;
//	}
//	
	/**
	 * test getUsers().
	 */
	@Test
	public void getUsers() {
		//TODO CL V2 : a faire avec l'utilisation de DBUNIT avec UNITILS (à voir)
		Assert.assertTrue(true);
	}
	
}
