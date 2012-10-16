/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.ehcache.CacheManager;

import org.esupportail.commons.exceptions.GroupNotFoundException;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.util.StringUtils;

/**
 * An implementation of LdapUserAndGroupService that delegates to
 * two instances of CachingLdapEntityServiceImpl.
 */
public class SearchableLdapUserAndGroupServiceImpl implements LdapUserAndGroupService, InitializingBean {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8708919611670209427L;

	/**
	 * The default unique attribute for users.
	 */
	private static final String DEFAULT_USER_ID_ATTRIBUTE = "uid";

	/**
	 * The default unique attribute for groups.
	 */
	private static final String DEFAULT_GROUP_ID_ATTRIBUTE = "gid";

	/**
	 * The default object class for users.
	 */
	private static final String DEFAULT_USER_OBJECT_CLASS = "Person";

	/**
	 * The default object class for groups.
	 */
	private static final String DEFAULT_GROUP_OBJECT_CLASS = "Group";

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The attribute used by method getLdapUsersFromToken().
	 */
	@Deprecated
	private String userSearchAttribute;

	/**
	 * The attribute used by method getLdapGroupsFromToken().
	 */
	@Deprecated
	private String groupSearchAttribute;

	/**
	 * The attributes that will be shown when searching for a user.
	 */
	@Deprecated
	private List<String> userSearchDisplayedAttributes;

	/**
	 * The attributes that will be shown when searching for a group.
	 */
	@Deprecated
	private List<String> groupSearchDisplayedAttributes;

	/**
	 * The attributes that is used by groups to store members.
	 */
	private String groupMemberAttribute;

	/**
	 * The real user LDAP entity service to delegate.
	 */
	private LdapUserService userService;

	/**
	 * The real group LDAP entity service to delegate.
	 */
	private LdapGroupService groupService;

	/**
	 * Bean constructor.
	 */
	public SearchableLdapUserAndGroupServiceImpl() {
		super();
	}

	@Override
	public void afterPropertiesSet() {
		//if != null because injected by spring
		if (userService == null) {
			//old method
			//the userService must be injected by spring
			userService = new SearchableLdapUserServiceImpl();
			SearchableLdapUserServiceImpl s = (SearchableLdapUserServiceImpl)userService;
			s.setIdAttribute(DEFAULT_USER_ID_ATTRIBUTE);
			s.setObjectClass(DEFAULT_USER_OBJECT_CLASS);
			if (userSearchAttribute == null) {
				logger.info("property userSearchAttribute is not set, "
						+ "method getLdapGroupsFromToken() will fail");
			} else {
				s.setSearchAttribute(userSearchAttribute);
				s.setSearchDisplayedAttributes(userSearchDisplayedAttributes);
			}
			s.afterPropertiesSet();

		}
		//if != null because injected by spring
		if (groupService == null) {
			//old method
			//the userService must be injected by spring
			groupService = new SearchableLdapGroupServiceImpl();
			SearchableLdapGroupServiceImpl s = (SearchableLdapGroupServiceImpl)groupService;
			s.setIdAttribute(DEFAULT_GROUP_ID_ATTRIBUTE);
			s.setObjectClass(DEFAULT_GROUP_OBJECT_CLASS);

			if (groupSearchAttribute == null) {
				logger.info("property groupSearchAttribute is not set, "
						+ "method getLdapGroupsFromToken() will fail");
			} else {
				s.setSearchAttribute(userSearchAttribute);
				s.setSearchDisplayedAttributes(groupSearchDisplayedAttributes);
			}
			s.afterPropertiesSet();

		}

		if (!StringUtils.hasText(groupMemberAttribute)) {
			groupMemberAttribute = null;
			logger.warn("property groupMemberAttribute is not set, "
					+ "method getMemberIds() and getMembers() will fail");
		}
	}

	@Override
	public LdapUser getLdapUser(final String id) throws LdapException, UserNotFoundException {
		return userService.getLdapUser(id);
	}

	@Override
	public List<LdapUser> getLdapUsersFromFilter(final String filterExpr) throws LdapException {
		return userService.getLdapUsersFromFilter(filterExpr);
	}

	@Override
	public List<LdapUser> getLdapUsersFromToken(final String token) throws LdapException {
		return userService.getLdapUsersFromFilter(token);
	}

	@Override
	public boolean userMatchesFilter(final String id, final String filter) throws LdapException {
		return userService.userMatchesFilter(id, filter);
	}

	@Override
	public List<String> getUserStatistics(final Locale locale) {
		return userService.getStatistics(locale);
	}

	@Override
	public void resetUserStatistics() {
		userService.resetStatistics();
	}

	/**
	 * Set the user cache name for users.
	 * @param cacheName
	 */
	@Deprecated
	public void setUserCacheName(final String cacheName) {
		((SearchableLdapUserServiceImpl)userService).setCacheName(cacheName);
	}

	/**
	 * Set the dnSubPath for users.
	 * @param dnSubPath
	 */
	@Deprecated
	public void setUserDnSubPath(final String dnSubPath) {
		((SearchableLdapUserServiceImpl)userService).setDnSubPath(dnSubPath);
	}

	/**
	 * Set the idAttribute for users.
	 * @param idAttribute
	 */
	@Deprecated
	public void setUserIdAttribute(final String idAttribute) {
		((SearchableLdapUserServiceImpl)userService).setIdAttribute(idAttribute);
	}

	/**
	 * Set the attributes for users.
	 * @param attributes
	 */
	@Deprecated
	public void setUserAttributes(final List<String> attributes) {
		((SearchableLdapUserServiceImpl)userService).setAttributes(attributes);
	}

	/**
	 * Set the objectClass for users.
	 * @param objectClass
	 */
	@Deprecated
	public void setUserObjectClass(final String objectClass) {
		((SearchableLdapUserServiceImpl)userService).setObjectClass(objectClass);
	}

	/**
	 * Set the testFilter for users.
	 * @param testFilter
	 */@Deprecated
	public void setUserTestFilter(final String testFilter) {
		((SearchableLdapUserServiceImpl)userService).setTestFilter(testFilter);
	}

	@Override
	public boolean supportUserStatistics() {
		return userService.supportStatistics();
	}

	@Override
	public boolean supportsUserTest() {
		return userService.supportsTest();
	}

	@Override
	public void userTest() {
		userService.test();
	}

	@Override
	public String testUserLdapFilter(final String filterExpr) throws LdapException {
		return userService.testLdapFilter(filterExpr);
	}

	@Override
	public List<String> getUserSearchDisplayedAttributes() {
		return userService.getSearchDisplayedAttributes();
	}

	/**
	 * @param searchDisplayedAttributes the searchDisplayedAttributes to set for users
	 */
	@Deprecated
	public void setUserSearchDisplayedAttributes(final List<String> searchDisplayedAttributes) {
		((SearchableLdapUserServiceImpl)userService).setSearchDisplayedAttributes(searchDisplayedAttributes);
	}

	/**
	 * @param searchAttribute the searchAttribute to set for users
	 */
	@Deprecated
	public void setUserSearchAttribute(final String searchAttribute) {
		((SearchableLdapUserServiceImpl)userService).setSearchAttribute(searchAttribute);
	}

	@Override
	public LdapGroup getLdapGroup(final String id) throws LdapException, GroupNotFoundException {
		return groupService.getLdapGroup(id);
	}

	@Override
	public List<LdapGroup> getLdapGroupsFromFilter(final String filterExpr) throws LdapException {
		return groupService.getLdapGroupsFromFilter(filterExpr);
	}

	@Override
	public List<LdapGroup> getLdapGroupsFromToken(final String token) throws LdapException {
		return groupService.getLdapGroupsFromFilter(token);
	}

	/**
	 * Extract the user id (e.g. paubry) from a group member attribute
	 * (e.g. uid=paubry,ou=people,dc=univ-rennes1,dc=fr)
	 * @param dn
	 * @return the id
	 */
	private String getUserIdFromMemberAttributeValue(final String dn) {
		if (dn == null) {
			return null;
		}
		String [] dnParts = dn.split(",");
		if (dnParts == null) {
			return null;
		}
		for (String dnPart : dnParts) {
			String [] dnSubparts = dnPart.split("=", 2);
			if (dnSubparts.length > 1) {
				if (userService.getIdAttribute().equals(dnSubparts[0])) {
					return dnSubparts[1];
				}
			}
		}
		return null;
	}

	@Override
	public List<String> getMemberIds(final LdapGroup group) {
		if (groupMemberAttribute == null) {
			throw new UnsupportedOperationException("property groupMemberAttribute is not set");
		}
		List<String> memberIds = new ArrayList<String>();
		for (String memberAttributeValue : group.getAttributes(groupMemberAttribute)) {
			String uid = getUserIdFromMemberAttributeValue(memberAttributeValue);
			if (uid != null) {
				memberIds.add(uid);
			} else {
				logger.info("could not get a LdapUser from DN [" + memberAttributeValue + "]");
			}
		}
		return memberIds;
	}

	@Override
	public List<LdapUser> getMembers(final LdapGroup group) {
		List<LdapUser> members = new ArrayList<LdapUser>();
		for (String uid : getMemberIds(group)) {
			try {
				members.add(getLdapUser(uid));
			} catch (UserNotFoundException e) {
				// user not found, forget it
			}
		}
		return members;
	}

	@Override
	public boolean groupMatchesFilter(final String id, final String filter) throws LdapException {
		return groupService.groupMatchesFilter(id, filter);
	}

	@Override
	public List<String> getGroupStatistics(final Locale locale) {
		return groupService.getStatistics(locale);
	}

	@Override
	public void resetGroupStatistics() {
		groupService.resetStatistics();
	}

	/**
	 * Set the group cache name for groups.
	 * @param cacheName
	 */
	@Deprecated
	public void setGroupCacheName(final String cacheName) {
		((SearchableLdapGroupServiceImpl)groupService).setCacheName(cacheName);
	}

	/**
	 * Set the dnSubPath for groups.
	 * @param dnSubPath
	 */
	@Deprecated
	public void setGroupDnSubPath(final String dnSubPath) {
		((SearchableLdapGroupServiceImpl)groupService).setDnSubPath(dnSubPath);
	}

	/**
	 * Set the idAttribute for groups.
	 * @param idAttribute
	 */
	@Deprecated
	public void setGroupIdAttribute(final String idAttribute) {
		((SearchableLdapGroupServiceImpl)groupService).setIdAttribute(idAttribute);
	}

	/**
	 * Set the attributes for groups.
	 * @param attributes
	 */
	@Deprecated
	public void setGroupAttributes(final List<String> attributes) {
		((SearchableLdapGroupServiceImpl)groupService).setAttributes(attributes);
	}

	/**
	 * Set the objectClass for groups.
	 * @param objectClass
	 */
	@Deprecated
	public void setGroupObjectClass(final String objectClass) {
		((SearchableLdapGroupServiceImpl)groupService).setObjectClass(objectClass);
	}

	/**
	 * Set the testFilter for groups.
	 * @param testFilter
	 */
	@Deprecated
	public void setGroupTestFilter(final String testFilter) {
		((SearchableLdapGroupServiceImpl)groupService).setTestFilter(testFilter);
	}

	@Override
	public boolean supportGroupStatistics() {
		return groupService.supportStatistics();
	}

	@Override
	public boolean supportsGroupTest() {
		return groupService.supportsTest();
	}

	@Override
	public void groupTest() {
		groupService.test();
	}

	@Override
	public String testGroupLdapFilter(final String filterExpr) throws LdapException {
		return groupService.testLdapFilter(filterExpr);
	}

	@Override
	public List<String> getGroupSearchDisplayedAttributes() {
		return groupService.getSearchDisplayedAttributes();
	}

	/**
	 * @param searchDisplayedAttributes
	 */
	@Deprecated
	public void setGroupSearchDisplayedAttributes(final List<String> searchDisplayedAttributes) {
		((SearchableLdapGroupServiceImpl)groupService).setSearchDisplayedAttributes(searchDisplayedAttributes);
	}

	/**
	 * @param searchAttribute
	 */
	@Deprecated
	public void setGroupSearchAttribute(final String searchAttribute) {
		((SearchableLdapGroupServiceImpl)groupService).setSearchAttribute(searchAttribute);
	}

	/**
	 * Set the cache manager.
	 * @param cacheManager
	 */
	@Deprecated
	public void setCacheManager(final CacheManager cacheManager) {
		((SearchableLdapUserServiceImpl)userService).setCacheManager(cacheManager);
		((SearchableLdapGroupServiceImpl)groupService).setCacheManager(cacheManager);
	}

	/**
	 * Set the i18nService.
	 * @param i18nService
	 */
	@Deprecated
	public void setI18nService(final I18nService i18nService) {
		((SearchableLdapUserServiceImpl)userService).setI18nService(i18nService);
		((SearchableLdapGroupServiceImpl)groupService).setI18nService(i18nService);
	}

	/**
	 * Set the ldapTemplate.
	 * @param ldapTemplate
	 */
	@Deprecated
	public void setLdapTemplate(final LdapTemplate ldapTemplate) {
		((SearchableLdapUserServiceImpl)userService).setLdapTemplate(ldapTemplate);
		((SearchableLdapGroupServiceImpl)groupService).setLdapTemplate(ldapTemplate);
	}

	/**
	 * @param groupMemberAttribute the groupMemberAttribute to set
	 */
	public void setGroupMemberAttribute(final String groupMemberAttribute) {
		this.groupMemberAttribute = groupMemberAttribute;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(final LdapUserService userService) {
		this.userService = userService;
	}

	/**
	 * @param groupService the groupService to set
	 */
	public void setGroupService(final LdapGroupService groupService) {
		this.groupService = groupService;
	}

}
