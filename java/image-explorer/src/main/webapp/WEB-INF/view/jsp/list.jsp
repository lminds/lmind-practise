<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/view/jsp/header.jsp"%>
</head>
<body>
  <div class="container-fluid">
    <ul>
      <c:forEach items="${list}" var="item">
        <li><a href="${contextPath}/action/explore?name=${fn:escapeXml(x.encodeURIComponent(item))}">${fn:escapeXml(item)}</a></li>
      </c:forEach>
    </ul>
  </div>
</body>
</html>