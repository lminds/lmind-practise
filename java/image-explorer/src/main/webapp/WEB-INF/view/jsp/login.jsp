<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/view/jsp/header.jsp"%>
<title>login</title>
</head>
<body style="overflow: hidden;">
  <div class="container-fluid">
    <form action="login" method="post">
      <input type="password" name="password">
      <input type="submit">
    </form>
  </div>
</body>
</html>