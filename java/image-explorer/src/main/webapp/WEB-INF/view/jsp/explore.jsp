<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/view/jsp/header.jsp"%>
<script type="text/javascript" src="${contextPath}/res/js/explore.js"></script>
<title>${fn:escapeXml(name)}${list[index]}</title>
</head>
<body style="overflow: hidden;">
  <div class="container-fluid" style="position: fixed; background-color: aqua;">
    <div class="row" style="padding: 5px 5px 5px 5px;">
      <input id="comicName" type="hidden" value="${fn:escapeXml(name)}" />
      <a href="${contextPath}/action/list" style="margin-left: 5px">目录</a>
      <c:if test="${index < fn:length(list) - 1}">
        <a href="explore?name=${fn:escapeXml(x.encodeURIComponent(name))}&index=${index+1}" style="margin-left: 5px">下一页</a>
      </c:if>
      <c:if test="${index > 0}">
        <a href="explore?name=${fn:escapeXml(x.encodeURIComponent(name))}&index=${index-1}" style="margin-left: 5px">上一页</a>
      </c:if>
      <span>共${fn:length(list)}页</span>
      <span><input id="toIndex" type="text"></input><button id="navTo">跳转</button></span>
    </div>
  </div>

  <div class="container-fluid" style="position: absolute; width: 100%; height: 100%; z-index: -5; overflow: scroll;">
    <div>
      <input type="hidden" name="contextPath" value="${contextPath}">
      <input type="hidden" name="item" value="${list[index]}">
      <img name="${fn:escapeXml(name)}" src="${contextPath}/action/content?name=${fn:escapeXml(x.encodeURIComponent(name))}&item=${list[index]}">
    </div>
  </div>
</body>
</html>