/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.portal;

import java.io.Serializable;
import java.util.List;

import org.esupportail.portal.ws.client.PortalGroup;
import org.esupportail.portal.ws.client.PortalGroupHierarchy;
import org.esupportail.portal.ws.client.PortalUser;
import org.esupportail.portal.ws.client.exceptions.PortalErrorException;
import org.esupportail.portal.ws.client.exceptions.PortalGroupNotFoundException;
import org.esupportail.portal.ws.client.exceptions.PortalUserNotFoundException;
import org.esupportail.portal.ws.client.support.AbstractPortalService;

/**
 * A void implementation of PortalService (for application that do not
 * use portal services, for instance portlet installations).
 */
public class NotSupportedPortalServiceImpl extends AbstractPortalService implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7399929419364841341L;

	/**
	 * Bean constructor.
	 */
	public NotSupportedPortalServiceImpl() {
		super();
	}

	/**
	 * @return an exception.
	 */
	private UnsupportedOperationException notSupported() {
		return new UnsupportedOperationException("do not call the methods of class [" + getClass() + "]");
	}

	//////////////////////////////////////////////////////////
	// user methods
	//////////////////////////////////////////////////////////

	@Override
	public PortalUser getUser(final String userId) {
		throw notSupported();
	}

	@Override
	public List<PortalUser> searchUsers(final String token)
	throws PortalErrorException, PortalUserNotFoundException {
		throw notSupported();
	}

	//////////////////////////////////////////////////////////
	// group methods
	//////////////////////////////////////////////////////////

	@Override
	public PortalGroup getGroupById(final String groupId) {
		throw notSupported();
	}

	@Override
	public PortalGroup getGroupByName(final String groupName) {
		throw notSupported();
	}

	@Override
	public List<PortalGroup> searchGroupsByName(final String token) {
		throw notSupported();
	}

	@Override
	public List<PortalGroup> getSubGroupsById(final String arg0)
			throws PortalErrorException, PortalGroupNotFoundException {
		throw notSupported();
	}

	@Override
	public List<PortalGroup> getSubGroupsByName(final String arg0)
			throws PortalErrorException, PortalGroupNotFoundException {
		throw notSupported();
	}

	//////////////////////////////////////////////////////////
	// group hierarchy methods
	//////////////////////////////////////////////////////////

	@Override
	public PortalGroupHierarchy getGroupHierarchyById(final String arg0)
	throws PortalErrorException, PortalGroupNotFoundException {
		throw notSupported();
	}

	@Override
	public PortalGroupHierarchy getGroupHierarchyByName(final String arg0)
	throws PortalErrorException, PortalGroupNotFoundException {
		throw notSupported();
	}

	@Override
	public PortalGroup getRootGroup() {
		throw notSupported();
	}

	@Override
	public PortalGroupHierarchy getGroupHierarchy() {
		throw notSupported();
	}

	//////////////////////////////////////////////////////////
	// group membership methods
	//////////////////////////////////////////////////////////

	@Override
	public List<PortalGroup> getUserGroups(final String userId) {
		throw notSupported();
	}

	@Override
	public List<PortalUser> getGroupUsers(final String groupId) {
		throw notSupported();
	}

	@Override
	public boolean isUserMemberOfGroup(final String userId, final String groupId) {
		throw notSupported();
	}

	@Override
	public List<PortalGroup> getContainingGroupsById(final String groupId)
			throws PortalErrorException, PortalGroupNotFoundException {
		throw notSupported();
	}

	@Override
	public List<PortalGroup> getContainingGroupsByName(final String groupName)
			throws PortalErrorException, PortalGroupNotFoundException {
		throw notSupported();
	}

}
