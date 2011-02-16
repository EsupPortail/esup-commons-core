<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<f:view locale="#{sessionController.locale}">
    <tr:document title="#{msgs['BLANK.TEXT']}">
        <jsp:include page="inc_navigation.jsp" />
        <h:panelGroup>
            <tr:spacer height="20px" />
            <tr:outputFormatted styleUsage="instruction"
                value="<b>#{msgs['USER.TEXT']}:</b> #{sessionController.currentUser.displayName} - #{msgs['ADMIN.TRUE.TEXT']} - #{msgs['PREFERENCES.LANGUAGE.SHORT_LABEL']}#{sessionController.currentUser.displayLanguage} - #{msgs['PREFERENCES.ACCESSIBILITY.SHORT_LABEL']}#{sessionController.currentUser.displayAccessibilityMode}"
                rendered="#{not empty sessionController.currentUser and sessionController.currentUser.admin}" />
            <tr:outputFormatted styleUsage="instruction"
                value="<b>#{msgs['USER.TEXT']}:</b> #{sessionController.currentUser.displayName} - #{msgs['PREFERENCES.LANGUAGE.SHORT_LABEL']}#{sessionController.currentUser.displayLanguage} - #{msgs['PREFERENCES.ACCESSIBILITY.SHORT_LABEL']}#{sessionController.currentUser.displayAccessibilityMode}"
                rendered="#{not empty sessionController.currentUser and not sessionController.currentUser.admin}" />
        </h:panelGroup>
        <tr:spacer height="20px" />
        <tr:outputFormatted styleUsage="instruction"
            value="<b>#{msgs['WELCOME.INTRO_1']}#{msgs['BLANK.TEXT']}#{msgs['WELCOME.INTRO_2']}</b>" />
    </tr:document>
</f:view>
