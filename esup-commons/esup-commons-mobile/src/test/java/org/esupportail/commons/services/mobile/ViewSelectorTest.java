package org.esupportail.commons.services.mobile;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations="/mobile.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ViewSelectorTest {

	@Autowired
	ViewSelectorService viewSelectorService;

	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testGetCalifiedViewName() {
		MockHttpServletRequest requestMobile = new MockHttpServletRequest("GET", "/main.app");
		requestMobile.addHeader("User-Agent", "je suis un iPhone !");
		Assert.assertEquals(viewSelectorService.getCalifiedViewName("TOTO", requestMobile), "MOBILE_TOTO");
		Assert.assertEquals(viewSelectorService.getCalifiedViewName("TOTO", requestMobile, true), "MOBILE_TOTO");
		Assert.assertTrue(viewSelectorService.isMobile(requestMobile));
		MockHttpServletRequest requestBrowser = new MockHttpServletRequest("GET", "/main.app");
		requestBrowser.addHeader("User-Agent", "FireFox");
		Assert.assertEquals(viewSelectorService.getCalifiedViewName("TOTO", requestBrowser), "BROWSER_TOTO");
		Assert.assertEquals(viewSelectorService.getCalifiedViewName("TOTO", requestBrowser, true), "BROWSER_WAI_TOTO");
		Assert.assertFalse(viewSelectorService.isMobile(requestBrowser));
	}


}