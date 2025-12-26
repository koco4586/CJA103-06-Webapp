package com.chat.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.util.HibernateUtil;

public class ChatMessageHibernateDAO implements ChatMessageDAO_interface {

	// 透過 HibernateUtil 取得 SessionFactory
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	//新增
	@Override
	public void insert(ChatMessageVO chatMessageVO) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.persist(chatMessageVO);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new RuntimeException("An error occurred:" + e.getMessage(),e);
		}
	}
	//修改
	@Override
	public void update(ChatMessageVO chatMessageVO) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.merge(chatMessageVO);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new RuntimeException("An error occurred:" + e.getMessage(),e);
		}
	}
	//刪除by messageId
	@Override
	public void delete(Integer messageId) {
		Session session = getSession();
		try {
			session.beginTransaction();
			// 先查詢再刪除
			ChatMessageVO chatMessageVO = session.get(ChatMessageVO.class, messageId);
			if (chatMessageVO != null) {
				session.remove(chatMessageVO);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new RuntimeException("An error occurred:" + e.getMessage(),e);
		}
	}
	//查詢單筆 by messageId
	@Override
	public ChatMessageVO findByPrimaryKey(Integer messageId) {
		ChatMessageVO chatMessageVO = null;
		Session session = getSession();
		try {
			session.beginTransaction();
			chatMessageVO = session.get(ChatMessageVO.class, messageId);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new RuntimeException("An error occurred:" + e.getMessage(),e);
		}
		return chatMessageVO;
	}
	//查詢全部
	@Override
	public List<ChatMessageVO> getAll() {
		List<ChatMessageVO> list = null;
		Session session = getSession();
		try {
			session.beginTransaction();
			// HQL 查詢全部
			list = session.createQuery("FROM ChatMessageVO", ChatMessageVO.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new RuntimeException("An error occurred:" + e.getMessage(),e);
		}
		return list;
	}
	//聊天室專用查詢 by聊天室ID查詢對話紀錄
	@Override
	public List<ChatMessageVO> getAllByChatroomId(Integer chatroomId) {
		List<ChatMessageVO> list = null;
		Session session = getSession();
		try {
			session.beginTransaction();
			// 根據聊天室 ID 查詢，並依照舊訊息在前排序
			String hql = "FROM ChatMessageVO WHERE chatroomId = :chatroomId ORDER BY chatTime ASC";
			list = session.createQuery(hql, ChatMessageVO.class).setParameter("chatroomId", chatroomId).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new RuntimeException("An error occurred:" + e.getMessage(),e);
		}
		return list;
	}

//	// 簡單的 Main 方法測試
//	public static void main(String[] args) {
//		ChatMessageHibernateDAO dao = new ChatMessageHibernateDAO();
//		// ==========================================
//		// 測試新增
//		// ==========================================
//		ChatMessageVO msg1 = new ChatMessageVO();
//		msg1.setChatroomId(1); // 測試用的聊天室 ID 為 1
//		msg1.setMemberId(1); // 測試用的會員 ID 為 1
//		msg1.setMessage("這是一條 Hibernate 測試訊息");
//		dao.insert(msg1);
//
//		// ==========================================
//		// 測試查詢單筆 (findByPrimaryKey)
//		// ==========================================
//		Integer newId = msg1.getMessageId(); // 拿到剛剛新增的 ID
//		ChatMessageVO msgFromDB = dao.findByPrimaryKey(newId);
//		if (msgFromDB != null) {
//			System.out.println(msgFromDB);
//		} else {
//			System.out.println("查詢失敗");
//		}
//
//		// ==========================================
//		// 測試修改 (Update)
//		// ==========================================
//		if (msgFromDB != null) {
//			msgFromDB.setMessage("這是修改後的訊息內容");
//			dao.update(msgFromDB);
//			ChatMessageVO msgUpdated = dao.findByPrimaryKey(newId);
//			System.out.println("   新內容: " + msgUpdated);
//		}
//		// ==========================================
//		// 測試聊天室專用查詢 (getAllByChatroomId)
//		// ==========================================
//		System.out.println("測試查詢聊天室 ID=1 的所有訊息 (getAllByChatroomId):");
//		List<ChatMessageVO> chatList = dao.getAllByChatroomId(1);
//		System.out.println("查詢成功，共找到 " + chatList.size() + " 筆資料：");
//		for (ChatMessageVO msg : chatList) {
//			System.out.println(msg);
//		}
//
//		// ==========================================
//		// 測試查詢全部 (getAll)
//		// ==========================================
//		System.out.println("測試查詢全部資料 (getAll)");
//		List<ChatMessageVO> allList = dao.getAll();
//		for (ChatMessageVO msg : allList) {
//			System.out.println(msg);
//		}
//
//		// ==========================================
//		// 測試刪除 (Delete)
//		// ==========================================
//		System.out.println("測試刪除剛剛新增的資料");
//		dao.delete(newId);
//		// 驗證是否刪除
//		ChatMessageVO deletedMsg = dao.findByPrimaryKey(newId);
//		if (deletedMsg == null) {
//			System.out.println("刪除成功");
//		} else {
//			System.out.println("刪除失敗");
//		}
//	}
}