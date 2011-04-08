<%@ include file="/WEB-INF/jsp/header.jsp"%>

<h1><spring:message code="exception.title"/></h1>


<div class="exception">

	<span class="exceptionMessage">
		<spring:message code="${exceptionMessage}"/>
	</span>

    <a href="#" id="exception-details-link">
    	<spring:message code="exception.details"/>
    </a>
	<div class="exception-details">
		<pre>${exceptionStackTrace}</pre>
	</div>

</div>
				
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
				