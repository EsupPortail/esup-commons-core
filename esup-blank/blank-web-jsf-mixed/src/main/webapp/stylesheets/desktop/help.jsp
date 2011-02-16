<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<f:view locale="#{sessionController.locale}">
    <tr:document title="#{msgs['BLANK.TEXT']}">
        <jsp:include page="inc_navigation.jsp" />
        <tr:form>
            <tr:spacer height="20px" />
            <tr:outputFormatted styleUsage="instruction" value=" <b>#{msgs['HELP.TEXT']}</b>" />
            <tr:spacer height="20px" />
            <tr:outputFormatted styleUsage="instruction" value="<b>#{msgs['HELP.TEXT.SUMMARY']}</b>" />
        </tr:form>
    </tr:document>
</f:view>
