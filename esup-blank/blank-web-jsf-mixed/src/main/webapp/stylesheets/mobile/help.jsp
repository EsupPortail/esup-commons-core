<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<f:view locale="#{sessionController.locale}">
    <tr:document title="#{msgs['BLANK.TEXT']}">
        <jsp:include page="inc_facet.jsp" />
        <tr:form>
            <jsp:include page="inc_header.jsp" />
        </tr:form>
        <div class="panelBase"><tr:panelCaptionGroup>
            <div class="row"><tr:outputText styleClass="centerLabelText"
                value="#{msgs['HELP.TEXT']}" /></div>
        </tr:panelCaptionGroup>
        <tr:panelCaptionGroup>
            <div class="row"><tr:outputText styleClass="centerMessageText"
                value="#{msgs['HELP.TEXT.SUMMARY']}" /></div>
        </tr:panelCaptionGroup>
        </div>
    </tr:document>
</f:view>
