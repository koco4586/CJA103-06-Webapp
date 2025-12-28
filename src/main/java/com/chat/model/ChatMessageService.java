package com.chat.model;

import java.util.List;

public class ChatMessageService {
	// 使用介面型別宣告，預留DI的彈性
	// 在建構子中手動 new ChatMessageHibernateDAO()。
	// 轉 Spring Boot 後，這裡只要加上 @Autowired，Spring 就會自動注入實作。
	private ChatMessageDAO_interface dao;

	public ChatMessageService() {
		dao = new ChatMessageHibernateDAOImpl();
	}

	// 新增
	public ChatMessageVO addChatMessage(Integer chatroomId, Integer memberId, String message,
			Integer replyToMessageId) {
		ChatMessageVO chatMessageVO = new ChatMessageVO();
		chatMessageVO.setChatroomId(chatroomId);
		chatMessageVO.setMemberId(memberId);
		chatMessageVO.setMessage(message);
		chatMessageVO.setReplyToMessageId(replyToMessageId);
		// 新增時也自動填入時間 (避免前端顯示 null) 這一行時間不會新增到資料庫
	    chatMessageVO.setChatTime(java.time.LocalDateTime.now());
		dao.insert(chatMessageVO);
		return chatMessageVO;
	}

	// 修改
	public ChatMessageVO updateChatMessage(Integer messageId, String message) {
		ChatMessageVO chatMessageVO = dao.findByPrimaryKey(messageId);
		if (chatMessageVO != null) {
			chatMessageVO.setMessage(message);
			dao.update(chatMessageVO);
		}
		return chatMessageVO;
	}

	// 刪除單筆訊息 by messageId
	public void deleteChatMessage(Integer messageId) {
		dao.delete(messageId);
	}

	// 查詢單筆 by messageId
	public ChatMessageVO getOneChatMessage(Integer messageId) {
		return dao.findByPrimaryKey(messageId);
	}

	// 查詢全部訊息
	public List<ChatMessageVO> getAll() {
		return dao.getAll();
	}

	// 聊天室專用查詢 by聊天室ID查詢對話紀錄
	public List<ChatMessageVO> getMessagesByChatroom(Integer chatroomId) {
		return dao.getAllByChatroomId(chatroomId);
	}
}