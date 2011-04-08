<%@ include file="/WEB-INF/jsp/header.jsp"%>

<h1>esup-blank-web-springmvc-servlet</h1>

<hr/>
<h2 class="alt">
  ${user.id} !
</h2>

<hr/>

<div class="span-7 colborder">

  <p id="helloPart"><spring:message code="view.helloString" arguments="${user.id}"/></p>
  
</div>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>
