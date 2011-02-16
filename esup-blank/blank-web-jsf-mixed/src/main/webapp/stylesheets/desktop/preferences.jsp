<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<f:view locale="#{sessionController.locale}">
    <tr:document title="#{msgs['BLANK.TEXT']}">
        <jsp:include page="inc_navigation.jsp" />
        <tr:form>
            <tr:spacer height="20px" />
            <tr:selectOneChoice label="#{msgs['PREFERENCES.LANGUAGE.LABEL']}: "
                value="#{sessionController.locale}">
                <f:selectItems value="#{preferencesController.localeItems}" />
            </tr:selectOneChoice>
            <tr:spacer height="20px" />
            <tr:selectOneChoice label="#{msgs['PREFERENCES.ACCESSIBILITY.LABEL']}: "
                value="#{sessionController.accessibilityMode}">
                <f:selectItems value="#{preferencesController.accessibilityModeItems}" />
            </tr:selectOneChoice>
            <tr:spacer height="20px" />
            <tr:commandButton text="#{msgs['SUBMIT.TEXT']}" id="changePreferences"
                action="preferences" />
        </tr:form>
        <tr:spacer height="20px" />
        <tr:outputDocument value="#{msgs['PREFERENCES.ACCESSIBILITY.SCREENREADER.TEXT']}"
            rendered="#{sessionController.accessibilityMode=='screenReader'}" />
    </tr:document>
</f:view>
