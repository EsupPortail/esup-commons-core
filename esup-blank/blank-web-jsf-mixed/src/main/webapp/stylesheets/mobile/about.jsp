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
            <div class="row"><tr:outputText styleClass="centerMessageText"
                value="#{msgs['BLANK.TEXT']}, Version: #{sessionController.version}" /></div>
        </tr:panelCaptionGroup><tr:panelCaptionGroup>
            <div class="row"><tr:outputText styleClass="centerLabelText"
                value="#{msgs['ABOUT.TEXT.SUMMARY']}" /></div>
            <div class="row" style="height: 78px;"><tr:outputText
                value="#{msgs['ABOUT.TEXT.SUMMARY.CONTENT']}" styleClass="centerMessageText" /></div>
        </tr:panelCaptionGroup><tr:panelCaptionGroup>
            <div class="row"><tr:outputText styleClass="centerLabelText"
                value="#{msgs['ABOUT.TEXT.AUTHORS']}" /></div>
            <div class="row"><tr:outputText styleClass="centerMessageText"
                value="#{msgs['ABOUT.TEXT.AUTHORS.YD']}" /></div>
        </tr:panelCaptionGroup><tr:panelCaptionGroup>
            <div class="row"><tr:outputText styleClass="centerLabelText"
                value="#{msgs['ABOUT.TEXT.M_COPYRIGHT']}" /></div>
            <div class="row"><tr:outputText
                styleClass="centerMessageText"
                value="#{msgs['ABOUT.TEXT.M_COPYRIGHT.CONTENT']}" /></div>
        </tr:panelCaptionGroup><tr:panelCaptionGroup>
            <div class="row"><tr:outputText styleClass="centerLabelText"
                value="#{msgs['ABOUT.TEXT.LICENCE']}" /></div>
            <div class="row"><tr:goLink text="#{msgs['ABOUT.TEXT.LICENCE.CONTENT']}"
                    destination="#{msgs['ABOUT.TEXT.LICENCE.CONTENT.LINK']}" styleClass="centerMessageLink" /></div>
        </tr:panelCaptionGroup><tr:panelCaptionGroup>
            <div class="row"><tr:outputText styleClass="centerLabelText"
                value="#{msgs['ABOUT.TEXT.OTHERS']}" /></div>
            <div class="row" style="height: 78px;"><tr:outputText
                styleClass="centerMessageText" value="#{msgs['ABOUT.TEXT.OTHERS.CONTENT']}" /></div>
        </tr:panelCaptionGroup></div>
    </tr:document>
</f:view>
