package com.boomhope.tms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "msg_flow")
@SuppressWarnings("all")
public class MsgFlow implements Serializable{
	private String msgFlow;
	private TransFlow transFlow;
    private int msgSerialNo;
    private String msgId;
    private String msgName;
    private String msgType;
    private String msgSavePath;
    private String msgDate;
    private String msgTime;

    @Id
    @Column(name = "msg_flow", nullable = false, length = 16)
    public String getMsgFlow() {
        return msgFlow;
    }

    public void setMsgFlow(String msgFlow) {
        this.msgFlow = msgFlow;
    }
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trans_flow_no")
	public TransFlow getTransFlow(){
		return this.transFlow;
	}
	
	public void setTransFlow(TransFlow transFlow){
		this.transFlow = transFlow;
	}


    @Column(name = "msg_serial_no", nullable = false)
    public int getMsgSerialNo() {
        return msgSerialNo;
    }

    public void setMsgSerialNo(int msgSerialNo) {
        this.msgSerialNo = msgSerialNo;
    }

    @Column(name = "msg_id", nullable = false, length = 32)
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Column(name = "msg_name", nullable = false, length = 64)
    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    @Column(name = "msg_type", nullable = false, length = 8)
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Column(name = "msg_save_path", nullable = false, length = 512)
    public String getMsgSavePath() {
        return msgSavePath;
    }

    public void setMsgSavePath(String msgSavePath) {
        this.msgSavePath = msgSavePath;
    }

    @Column(name = "msg_date", nullable = false, length = 8)
    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    @Column(name = "msg_time", nullable = false, length = 6)
    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

}
