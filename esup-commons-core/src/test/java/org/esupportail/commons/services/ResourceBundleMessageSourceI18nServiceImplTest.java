package org.esupportail.commons.services;

import java.util.Map;

import javax.annotation.Resource;

import org.esupportail.commons.services.i18n.I18nService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@ContextConfiguration(locations="/i18n.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ResourceBundleMessageSourceI18nServiceImplTest {

	@Resource(name="i18nService")
	I18nService i18nService;

	@Test
	public void testGetStrings(){
		Map<String, String> map = i18nService.getStrings();
		Assert.isTrue(map.containsKey("markAllAsRead"));
	}

}
