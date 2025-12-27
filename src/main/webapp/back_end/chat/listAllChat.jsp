<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.chat.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    ChatMessageService chatSvc = new ChatMessageService();
    List<ChatMessageVO> list = chatSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有聊天資料 - listAllChat.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有聊天資料 - listAllChat.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back_end/chat/select_page.jsp"><img src="<%=request.getContextPath()%>/resources/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>訊息ID</th>
		<th>聊天室ID</th>
		<th>職位</th>
		<th>會員ID</th>
		<th>聊天時間</th>
		<th>回覆到哪則訊息ID</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="ChatMessageVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${ChatMessageVO.messageId}</td>
			<td>${ChatMessageVO.chatroomId}</td>
			<td>${ChatMessageVO.memberId}</td>
			<td>${ChatMessageVO.message}</td>
			<td>${ChatMessageVO.chatTime}</td>
			<td>${ChatMessageVO.replyToMessageId}</td> 
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/emp/emp.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="empno"  value="${ChatMessageVO.messageId}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/emp/emp.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="empno"  value="${ChatMessageVO.messageId}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>