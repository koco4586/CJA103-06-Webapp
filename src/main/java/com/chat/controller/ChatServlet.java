package com.chat.controller;

import java.io.*;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.chat.model.ChatMessageService;
import com.chat.model.ChatMessageVO;
import com.emp.model.*;

public class ChatServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("messageId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入訊息ID");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/chat/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer messageId = null;
			try {
				messageId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("訊息ID不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/chat/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			ChatMessageService chatSvc = new ChatMessageService();
			ChatMessageVO chatMessageVO = chatSvc.getOneChatMessage(messageId);
			if (chatMessageVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/chat/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("chatMessageVO", chatMessageVO); // 資料庫取出的chatMessageVO物件,存入req
			String url = "/back_end/chat/listOneChat.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneChat.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllChat.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer messageId = Integer.valueOf(req.getParameter("messageId"));

			/*************************** 2.開始查詢資料 ****************************************/
			ChatMessageService chatSvc = new ChatMessageService();
			ChatMessageVO chatMessageVO = chatSvc.getOneChatMessage(messageId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("chatMessageVO", chatMessageVO); // 資料庫取出的chatMessageVO物件,存入req
			String url = "/back_end/chat/update_chat_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_chat_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自 update_chat_input.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				// 取得訊息 ID
				String messageIdStr = req.getParameter("messageId");
				Integer messageId = Integer.valueOf(messageIdStr.trim());

				// 取得並驗證訊息內容
				String message = req.getParameter("message");
				if (message == null || message.trim().length() == 0) {
					errorMsgs.add("訊息內容: 請勿空白");
				}

				// 如果有錯誤，將控制權交還給來源頁面
				if (!errorMsgs.isEmpty()) {
					// 先從資料庫取出原始完整的資料
					ChatMessageService chatSvc = new ChatMessageService();
					ChatMessageVO chatMessageVO = chatSvc.getOneChatMessage(messageId);

					// 將使用者剛才輸入的有錯訊息內容填進去，保留其他原始欄位
					chatMessageVO.setMessage(message);

					req.setAttribute("chatMessageVO", chatMessageVO); // 此時 VO 包含了完整資訊
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/chat/update_chat_input.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/
				ChatMessageService chatSvc = new ChatMessageService();
				// 呼叫 Service 的修改方法，傳入 ID 與新訊息
				ChatMessageVO chatMessageVO = chatSvc.updateChatMessage(messageId, message);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("chatMessageVO", chatMessageVO); // 修改成功後，存入正確的 VO
				// 導向顯示單筆訊息的頁面
				String url = "/back_end/chat/listOneChat.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/chat/update_chat_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1. 接收請求參數 輸入格式驗證 *************************/
				ChatMessageService chatSvc = new ChatMessageService();

				// 驗證聊天室ID格式
				Integer chatroomId = null;
				String chatroomIdStr = req.getParameter("chatroomId");
				if (chatroomIdStr == null || chatroomIdStr.trim().isEmpty()) {
					errorMsgs.add("聊天室編號：請勿空白");
				} else {
					try {
						chatroomId = Integer.valueOf(chatroomIdStr.trim());
					} catch (NumberFormatException e) {
						errorMsgs.add("聊天室編號：請填數字");
					}
				}

				// 驗證會員ID格式
				Integer memberId = null;
				String memberIdStr = req.getParameter("memberId");
				if (memberIdStr == null || memberIdStr.trim().isEmpty()) {
					errorMsgs.add("會員ID：請勿空白");
				} else {
					try {
						memberId = Integer.valueOf(memberIdStr.trim());
					} catch (NumberFormatException e) {
						errorMsgs.add("會員ID：請填數字");
					}
				}

				// 驗證訊息內容格式
				String message = req.getParameter("message");
				if (message == null || message.trim().isEmpty()) {
					errorMsgs.add("訊息內容：請勿空白");
				}

				// 驗證回覆訊息ID格式和存在性  
				Integer replyToMessageId = null;
				String replyStr = req.getParameter("replyToMessageId");
				if (replyStr != null && !replyStr.trim().isEmpty()) {
					try {
						replyToMessageId = Integer.valueOf(replyStr.trim());
						if (chatSvc.getOneChatMessage(replyToMessageId) == null) {
							errorMsgs.add("回覆的訊息ID不存在");
						}
					} catch (NumberFormatException e) {
						errorMsgs.add("回覆訊息ID：格式錯誤，請填數字");
					}
				}

				// 封裝資料，確保 forward 回去時 addChatMessage.jsp 的 EL 抓得到值
				ChatMessageVO chatMessageVO = new ChatMessageVO();
				chatMessageVO.setChatroomId(chatroomId);
				chatMessageVO.setMemberId(memberId);
				chatMessageVO.setMessage(message);
				chatMessageVO.setReplyToMessageId(replyToMessageId);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("chatMessageVO", chatMessageVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back_end/chat/addChatMessage.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2. 開始新增資料 ***************************************/
				chatSvc.addChatMessage(chatroomId, memberId, message, replyToMessageId); //

				/*************************** 3. 新增完成, 準備轉交 ***********************************/
				res.sendRedirect(req.getContextPath() + "/back_end/chat/listAllChat.jsp");
				return;

			} catch (Exception e) {
				errorMsgs.add("新增資料失敗：" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back_end/chat/addChatMessage.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer empno = Integer.valueOf(req.getParameter("empno"));

			/*************************** 2.開始刪除資料 ***************************************/
			EmpService empSvc = new EmpService();
			empSvc.deleteEmp(empno);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/emp/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
