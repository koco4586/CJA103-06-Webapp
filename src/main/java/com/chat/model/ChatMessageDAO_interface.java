package com.chat.model;

import java.util.*;

public interface ChatMessageDAO_interface {
	// 新增訊息
	public void insert(ChatMessageVO chatMessageVO);

	// 修改訊息
	public void update(ChatMessageVO chatMessageVO);

	// 刪除訊息
	public void delete(Integer messageId);

	// 查詢單筆 by messageId
	public ChatMessageVO findByPrimaryKey(Integer messageId);
	
	// 查詢全部 

	public List<ChatMessageVO> getAll();

	// 聊天室專用查詢 by聊天室ID查詢對話紀錄
	public List<ChatMessageVO> getAllByChatroomId(Integer chatroomId);
}
