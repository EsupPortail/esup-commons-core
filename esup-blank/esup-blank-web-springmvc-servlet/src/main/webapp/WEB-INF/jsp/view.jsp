<%@ include file="/WEB-INF/jsp/header.jsp"%>

<h1>esup-blank-web-springmvc-servlet</h1>

<hr/>
<h2 class="alt">
  ${user.login} !
</h2>

<hr/>

<div class="span-7 colborder">

  <p id="helloPart"><spring:message code="view.helloString" arguments="${user.login}"/></p>
  
</div>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>
