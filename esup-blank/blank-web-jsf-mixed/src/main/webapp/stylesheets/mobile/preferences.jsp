<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<f:view locale="#{sessionController.locale}">
    <tr:document title="#{msgs['BLANK.TEXT']}">
        <jsp:include page="inc_facet.jsp" />
        <tr:form id="changeLocale">
            <jsp:include page="inc_header.jsp" />
            <tr:panelList styleClass="panelList">
                <tr:outputFormatted styleUsage="instruction"
                    value="<b>&nbsp;#{msgs['PREFERENCES.LANGUAGE.LABEL']}: </b>" />
            </tr:panelList>
            <tr:table value="#{preferencesController.localeItems}" var="language" rows="5"
                width="100%" styleClass="iphoneTable" horizontalGridVisible="false">
                <tr:column inlineStyle="padding:0 !important;">
                    <tr:panelGroupLayout layout="vertical" styleClass="listing">
                        <tr:commandLink action="#{preferencesController.setLocaleAction}"
                            inlineStyle="text-decoration:none;">
                            <f:setPropertyActionListener value="#{language.value}"
                                target="#{preferencesController.languageSelected}" />
                            <h:graphicImage value="/media/images/accept.png"
                                style="float:left;border:0;margin-right:10px;"
                                alt="#{language.label}" title="#{language.label}"
                                rendered="#{sessionController.locale==language.value}" />
                            <tr:outputText value="#{language.label}" styleClass="centerMessageLink" />
                        </tr:commandLink>
                    </tr:panelGroupLayout>
                </tr:column>
            </tr:table>
        </tr:form>
    </tr:document>
</f:view>

