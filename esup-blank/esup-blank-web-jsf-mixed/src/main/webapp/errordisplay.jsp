<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.io.PrintWriter,org.apache.myfaces.shared_tomahawk.util.ExceptionUtils" isErrorPage="true" %>
<html>
<head>
  <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8" />
  <title>Application Blanche - Erreur</title>
</head>
<body>
<style>
body {
    font-family : arial, verdana, Geneva, Arial, Helvetica, sans-serif;
    font-size : 1.1em;
}
.errorHeader {
    font-size: 1.6em;
    background-color: #6392C6;
    color: white;
    font-weight: bold;
    padding: 3px;
    margin-bottom: 10px;
}

.errorFooter {
    font-size: 0.8em;
    background-color: #6392C6;
    color: white;
    font-style: italic;
    padding: 3px;
    margin-top: 5px;
}

.errorMessage {
    color: red;
    font-weight: bold;
}
.errorExceptions {
}
.errorExceptionStack {
    margin-top: 5px;
    padding: 3px;
    border-style: solid;
    border-width: 1px;
    border-color: #9F9F9F;
    background-color: #E0E0E0;
}
.errorExceptionCause {
    font-size: 1.1em;
    padding: 3px;
    border-style: solid;
    border-width: 1px;
    border-color: #9F9F9F;
    background-color: #E0E0E0;
}
.errorException {
    font-size: 1.0em;
}
</style>
<div class="errorHeader">Application Blanche - Une erreur est survenue</div>

<%
    List exceptions = ExceptionUtils.getExceptions(exception);
    Throwable throwable = (Throwable) exceptions.get(exceptions.size()-1);
    String exceptionMessage = ExceptionUtils.getExceptionMessage(exceptions);
    if (exceptionMessage.indexOf("No saved view state") > -1) { 
        exceptionMessage="Temps de connexion dépassé...";    
    }
    %><span class="errorMessage"><%=exceptionMessage %></span>
    <%
    if (exceptionMessage.indexOf("Erreur de configuration") == -1 && exceptionMessage.indexOf("Configuration exception") == -1) { 
        %>
    	<br/>
        <a href="<%= request.getContextPath() %>">Veuillez cliquer ici...</a>   
        <%
    }
    %>
    
    <%PrintWriter pw = new PrintWriter(out); %>
    
<div class="errorFooter">Application Blanche - Rapport d'exception</div>

</body>
</html>