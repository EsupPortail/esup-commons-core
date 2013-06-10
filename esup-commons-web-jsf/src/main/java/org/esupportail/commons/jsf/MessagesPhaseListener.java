/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.jsf;

//see http://learnjsf.com/wp/2006/08/06/a-prg-phase-listener-for-jsf/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * A phase listener to pass messages through the PRG pattern.
 */
public class MessagesPhaseListener implements PhaseListener {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2560498754887415967L;

	/**
	 * A name under which to save messages between the redirect and
	 * the subsequent get.
	 */
	private final String sessionToken = getClass().getName() + ".messages";

	/**
	 * A logger.
	 */
	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * Constructor.
	 */
	private MessagesPhaseListener() {
	}

	/**
	 * a static factory method
	 */
	public static MessagesPhaseListener createInstance() {
		return new MessagesPhaseListener();
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
	protected void debugEvent(final PhaseEvent event, final String string) {
		if (logger.isDebugEnabled()) {
			debugMessages(event);
		}
	}

	/**
	 * Debug an event.
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	protected void debugMessages(final PhaseEvent event) {
		if (logger.isDebugEnabled()) {
			FacesContext facesContext = event.getFacesContext();
			for (Iterator<String> i = facesContext.getClientIdsWithMessages(); i.hasNext();) {
				String clientId = i.next();
				for (Iterator<FacesMessage> j = facesContext.getMessages(clientId); j.hasNext();) {
					FacesMessage facesMessage = j.next();
					logger.debug("CONTEXT: [" + clientId + "]=>[" + facesMessage.getDetail() + "]");
				}
			}
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object>  sessionMap = externalContext.getSessionMap();
			Map<String, List<FacesMessage>> allMessages =
				(Map<String, List<FacesMessage>>) sessionMap.get(sessionToken);
			if (allMessages == null) {
				return;
			}
			for (String clientId : allMessages.keySet()) {
				List<FacesMessage> clientMessages = allMessages.get(clientId);
				for (FacesMessage facesMessage : clientMessages) {
					logger.debug("SESSION: [" + clientId + "]=>[" + facesMessage.getDetail() + "]");
				}
			}
		}
	}

	@Override
	public void afterPhase(final PhaseEvent event) {
		  FacesContext facesContext = event.getFacesContext();
		  ExternalContext externalContext = facesContext.getExternalContext();
		  String method = getMethod(externalContext);
		  if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES
		    && "POST".equals(method)) {
		   Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		   sessionMap.put(sessionToken, new HashMap<Object, Object>());
		  }


		// Save messages in session so they'll be available on the
		// subsequent GET request
		if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES
				|| event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS
				|| event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
			if (logger.isDebugEnabled()) {
				logger.debug("SAVING MESSAGES TO SESSION...");
			}
			saveMessages(event.getFacesContext());
			debugMessages(event);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void beforePhase(final PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		ExternalContext externalContext = facesContext.getExternalContext();
		if (event.getPhaseId() != PhaseId.RENDER_RESPONSE) {
			return;
		}
		if ("POST".equals(getMethod(externalContext))) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("RESTORING MESSAGES TO CONTEXT...");
		}
		// Move saved messages from session back to request queue
		restoreMessages(facesContext);
		/*
		 * JSF normally clears input component values in the UpdateModel
		 * phase. However, this phase does not run for a GET request, so we
		 * must do it ourselves. Otherwise, the view will retain values from
		 * the first time it was loaded.
		 */
		resetComponentValues(viewRoot.getChildren());
		debugMessages(event);
	}

	/**
	* @param externalContext
	* @return the method of the current request.
	*/
	private String getMethod(final ExternalContext externalContext) {
		Object request = externalContext.getRequest();
		if (request == null) {
			return null;
		}
		if (request instanceof HttpServletRequest) {
			return ((HttpServletRequest) request).getMethod();
		}
		return null;
	}
	
	/**
	 * Remove the messages that are not associated with any particular component
	 * from the user's session and add them to the faces context.
	 * @param facesContext
	 *
	 * @return the number of removed messages.
	 */
	@SuppressWarnings("unchecked")
	protected int restoreMessages(final FacesContext facesContext) {
		// remove messages from the session
		int numRestoredMessages = 0;
		Map<String, Object>  sessionMap = facesContext.getExternalContext().getSessionMap();
		Map<String, List<FacesMessage>> allMessages =
			(Map<String, List<FacesMessage>>) sessionMap.remove(sessionToken);
		if (allMessages == null) {
			return 0;
		}
		// Move messages from session back to facesContext
		for (String clientId : allMessages.keySet()) {
			List<FacesMessage> clientMessages = allMessages.get(clientId);
			for (FacesMessage facesMessage : clientMessages) {
				facesContext.addMessage(clientId, facesMessage);
				numRestoredMessages++;
			}
		}
		return numRestoredMessages;
	}

	/**
	 * Remove the messages that are not associated with any particular component
	 * from the faces context and store them to the user's session.
	 * @param facesContext
	 *
	 * @return the number of removed messages.
	 */
	@SuppressWarnings("unchecked")
	private int saveMessages(final FacesContext facesContext) {
		// Remove messages from the context
		// Save as a map of lists so we can continue to messages with components
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		int numMessages = 0;

		if (!sessionMap.containsKey(sessionToken)) {
			sessionMap.put(sessionToken, new HashMap<Object, Object>());
		}
		Map<String, List<FacesMessage>> allMessages =
			(Map<String, List<FacesMessage>>) sessionMap.get(sessionToken);

		for (Iterator<String> i = facesContext.getClientIdsWithMessages(); i.hasNext();) {
			String clientId = i.next();
			// For each component (client ID), retrieve the messages to a list
			List<FacesMessage> messages = new ArrayList<FacesMessage>();
			for (Iterator<FacesMessage> j = facesContext.getMessages(clientId); j.hasNext();) {
				messages.add(j.next());
				j.remove();
				numMessages++;
			}
			List<FacesMessage> clientMessages = allMessages.get(clientId);
			if (clientMessages != null) {
				// There are already messages for this component
				clientMessages.addAll(messages);
			} else {
				// Not yet messages for this component
				allMessages.put(clientId, messages);
			}
		}
		return numMessages;
	}

	/**
	 * Resets UIInput component values.
	 * From http://forum.java.sun.com/thread.jspa?threadID=495087&messageID=3704164
	 * @param children
	 */
	private void resetComponentValues(final List<UIComponent> children) {
		for (UIComponent component : children) {
			if (component instanceof UIInput) {
				UIInput input = (UIInput) component;
				input.setSubmittedValue(null);
			}
			resetComponentValues(component.getChildren());
		}
	}

}