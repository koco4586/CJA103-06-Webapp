<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chat.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
ChatMessageVO chatMessageVO = (ChatMessageVO) request.getAttribute("chatMessageVO"); //EmpServlet.java(Concroller), 存入req的chatMessageVO物件
%>

<html>
<head>
<title>聊天資料 - listOneChat.jsp</title>

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
	width: 600px;
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

	<h4>此頁暫練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>聊天資料 - listOneChat.jsp</h3>
				<h4>
					<a
						href="<%=request.getContextPath()%>/back_end/chat/select_page.jsp"><img
						src="<%=request.getContextPath()%>/resources/images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
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
		<tr>
			<td>${chatMessageVO.messageId}</td>
			<td>${chatMessageVO.chatroomId}</td>
			<td>${chatMessageVO.memberId}</td>
			<td>${chatMessageVO.message}</td>
			<td>${chatMessageVO.chatTime}</td>
			<td>${chatMessageVO.replyToMessageId}</td>
		</tr>
	</table>

</body>
</html>