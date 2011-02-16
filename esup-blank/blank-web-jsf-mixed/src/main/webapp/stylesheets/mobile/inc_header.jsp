<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<tr:panelHeader styleClass="toolbar" text="#{msgs['M_BLANK.TEXT']}">
    <tr:commandLink styleClass="backButton" text="#{msgs['BACK.TEXT']}" action="welcome"
        rendered="#{not empty sessionController.action and sessionController.action!='welcome'}">
        <f:setPropertyActionListener value="welcome" target="#{sessionController.action}" />
    </tr:commandLink>
    <tr:commandLink styleClass="button" text="#{msgs['WELCOME.TEXT']}" action="welcome"
        rendered="#{not empty sessionController.action 
            and sessionController.action!='welcome' 
            and sessionController.action!='preferences' 
            and sessionController.action!='about' 
            and sessionController.action!='help'}">
        <f:setPropertyActionListener value="welcome" target="#{sessionController.action}" />
    </tr:commandLink>
</tr:panelHeader>
