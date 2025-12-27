package com.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

//告訴 Tomcat 這是監聽器，啟動時要執行它
@WebListener
public class HibernateListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("====== Tomcat 啟動：正在初始化 Hibernate ======");

		//呼叫一次 getSessionFactory() 觸發 Hibernate 掃描 Entity、建立連線池
		try {
			HibernateUtil.getSessionFactory();
			System.out.println("====== Hibernate 初始化完成，網站準備好了！ ======");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("====== Hibernate 初始化失敗 ======");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Tomcat 關閉時，把 Hibernate 關掉，釋放資源
		HibernateUtil.shutdown();
	}
}