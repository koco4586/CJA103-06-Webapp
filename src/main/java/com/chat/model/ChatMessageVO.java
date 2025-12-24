package com.chat.model;

import java.util.Date;
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
    
    // @Temporal 註解指定為 TIMESTAMP 格式
    @Column(name = "CHAT_TIME", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date chatTime;
    
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
    
    public Date getChatTime() {
        return chatTime;
    }
    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }
    
    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }
    public void setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }
}