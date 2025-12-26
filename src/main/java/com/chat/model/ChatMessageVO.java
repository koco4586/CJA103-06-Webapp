package com.chat.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessageVO implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Integer messageId;
    
    @Column(name = "CHATROOM_ID")
    private Integer chatroomId;
    
    @Column(name = "MEMBER_ID")
    private Integer memberId;
    
    @Column(name = "MESSAGE")
    private String message;
    
    @Column(name = "CHAT_TIME", insertable = false, updatable = false)
    private LocalDateTime chatTime;
    
    @Column(name = "REPLY_TO_MESSAGE_ID")
    private Integer replyToMessageId;

    public ChatMessageVO() {
    	super();
    }

    // Getter 與 Setter
    public Integer getMessageId() {
        return messageId;
    }
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public Integer getChatroomId() {
        return chatroomId;
    }
    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public LocalDateTime getChatTime() {
        return chatTime;
    }
    public void setChatTime(LocalDateTime chatTime) {
        this.chatTime = chatTime;
    }
    
    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }
    public void setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }
    //以下方便測試
    @Override
	public String toString() {
		return "ChatMessageVO ["
				+ "messageId=" + messageId 
				+ ", chatroomId=" + chatroomId 
				+ ", memberId=" + memberId 
				+ ", message=" + message 
				+ ", chatTime=" + chatTime 
				+ ", replyToMessageId=" + replyToMessageId 
				+ "]";
	}
}