<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Chat Messages: Home</title>

<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
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

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td><h3>Chat Messages: Home</h3>
				<h4>( MVC )</h4></td>
		</tr>
	</table>

	<p>This is the Home page for chat messages: Home</p>

	<h3>資料查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li><a
			href='<%=request.getContextPath()%>/back_end/chat/listAllChat.jsp'>List</a>
			all ChatMessages. <br>
		<br></li>


		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/chat/chat.do">
				<b>輸入訊息ID:</b> <input type="text" name="messageId">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="chatMessageSvc" scope="page"
			class="com.chat.model.ChatMessageService" />

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/chat/chat.do">
				<b>選擇訊息ID:</b> <select size="1" name="messageId">
					<c:forEach var="chatMessageVO" items="${chatMessageSvc.all}">
						<option value="${chatMessageVO.messageId}">${chatMessageVO.messageId}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>


	</ul>


	<h3>員工管理</h3>

	<ul>
		<li><a href='addChatMessage.jsp'>Add</a> a new ChatMessage.</li>
	</ul>

</body>
</html>