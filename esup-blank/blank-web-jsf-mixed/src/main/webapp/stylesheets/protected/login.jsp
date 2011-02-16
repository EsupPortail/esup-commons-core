<%
response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
response.setHeader("Location", request.getContextPath() + "/stylesheets/desktop/welcome.jsf");
%>