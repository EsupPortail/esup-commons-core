<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>
<f:view locale="#{sessionController.locale}">
    <tr:document title="#{msgs['BLANK.TEXT']}">
        <jsp:include page="inc_navigation.jsp" />
        <tr:spacer height="20px" />
        <tr:outputFormatted styleUsage="instruction"
            value="<b>#{msgs['BLANK.TEXT']}</b>, Version: #{sessionController.version}" />
        <tr:spacer height="20px" />
        <tr:outputFormatted styleUsage="instruction"
            value="<b>#{msgs['ABOUT.TEXT.SUMMARY']}</b>: #{msgs['ABOUT.TEXT.SUMMARY.CONTENT']}" />
        <tr:spacer height="20px" />
        <tr:outputFormatted styleUsage="instruction" value="<b>#{msgs['ABOUT.TEXT.AUTHORS']}</b>:" />
        <tr:panelList rows="1">
            <tr:outputFormatted styleUsage="instruction" value="#{msgs['ABOUT.TEXT.AUTHORS.YD']}" />
        </tr:panelList>
        <tr:spacer height="20px" />
        <tr:outputFormatted styleUsage="instruction" value="<b>#{msgs['ABOUT.TEXT.COPYRIGHT']}</b>" />
        <tr:spacer height="20px" />
        <tr:outputFormatted styleUsage="instruction" value="<b>#{msgs['ABOUT.TEXT.LICENCE']}</b>: " />
        <tr:goLink text="#{msgs['ABOUT.TEXT.LICENCE.CONTENT']}"
            destination="#{msgs['ABOUT.TEXT.LICENCE.CONTENT.LINK']}" />
        <tr:spacer height="20px" />
        <tr:outputFormatted styleUsage="instruction"
            value="<b>#{msgs['ABOUT.TEXT.OTHERS']}</b>: #{msgs['ABOUT.TEXT.OTHERS.CONTENT']}" />
    </tr:document>
</f:view>
