<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<tr:form id="navigation">
    <tr:navigationPane hint="bar" shortDesc="#{msgs['MENU.SHORTDESC']}">
        <tr:commandNavigationItem text="#{msgs['WELCOME.TEXT']}" action="welcome"
            disabled="#{empty sessionController.action
            or sessionController.action=='welcome'}">
            <f:setPropertyActionListener value="welcome" target="#{sessionController.action}" />
        </tr:commandNavigationItem>
        <tr:commandNavigationItem text="#{msgs['PREFERENCES.TEXT']}" action="preferences"
            disabled="#{not empty sessionController.action
            and sessionController.action=='preferences'}">
            <f:setPropertyActionListener value="preferences" target="#{sessionController.action}" />
        </tr:commandNavigationItem>
        <tr:commandNavigationItem text="#{msgs['ABOUT.TEXT']}" action="about"
            disabled="#{not empty sessionController.action
            and sessionController.action=='about'}">
            <f:setPropertyActionListener value="about" target="#{sessionController.action}" />
        </tr:commandNavigationItem>
        <tr:commandNavigationItem text="#{msgs['HELP.TEXT']}" action="help"
            disabled="#{not empty sessionController.action
            and sessionController.action=='help'}">
            <f:setPropertyActionListener value="help" target="#{sessionController.action}" />
        </tr:commandNavigationItem>
        <tr:commandNavigationItem text="#{msgs['LOGIN.TEXT']}"
            destination="/stylesheets/protected/login.jsf"
            rendered="#{sessionController.loginEnable}" />
        <tr:commandNavigationItem text="#{msgs['LOGOUT.TEXT']}"
            action="#{sessionController.logoutAction}" immediate="true"
            rendered="#{sessionController.logoutEnable}" />
        <tr:commandNavigationItem text="#{msgs['MOBILE.TEXT']}" action="mobileWelcome"
            immediate="true" rendered="#{not sessionController.portletMode}" />
    </tr:navigationPane>
</tr:form>
