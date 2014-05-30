package com.lock.workorder;

import java.util.Date;

import com.base.core.ssh.l0model.BaseEntity;

/*
 * -- Create table
 create table TBL_WORKORDER
 (
 id                    VARCHAR2(32) not null,
 answerstate           NUMBER(1) default 0 not null,
 overflag              CHAR(1) default 'N' not null,
 accepttime            DATE not null,
 userid                VARCHAR2(20),
 keyid                 VARCHAR2(32),
 customerid            VARCHAR2(32),
 accuseid              VARCHAR2(32),
 openlockid            VARCHAR2(32),
 accusecallbillid      VARCHAR2(32),
 accuseopenlockid      VARCHAR2(32),
 remark                VARCHAR2(100),
 businesstype          NUMBER(2) default 5,
 switchtype            NUMBER(2) default 4,
 dtmf                  VARCHAR2(32),
 callerid              VARCHAR2(15),
 openlockname          VARCHAR2(50),
 dialstatus            NUMBER(10) default -1,
 autodialfailurereason NUMBER(10) default -1,
 billid                VARCHAR2(32),
 callback              NUMBER(2) default 0,
 calleeid              VARCHAR2(32)
 )
 -- Add comments to the columns 
 comment on column TBL_WORKORDER.businesstype
 is 'enum BusinessType
 {
 BT_NONE = 0,
 BT_CallIn = 1,
 BT_BlackList = 2,
 BT_UblCallIn = 3,
 BT_UblCallOut = 4,
 BT_StaffCallIn = 5,
 BT_StaffCallOut = 6,
 BT_Complaint = 7,
 BT_AutoDial
 };';
 comment on column TBL_WORKORDER.switchtype
 is 'enum eSwitchType
 {
 ST_Other=0,
 ST_Auto=1,
 ST_0459=2,
 ST_8308=3,
 ST_Staff=4
 };';
 comment on column TBL_WORKORDER.calleridis '客户来电';
 comment on column TBL_WORKORDER.openlocknameis '开锁公司名称';
 comment on column TBL_WORKORDER.dialstatusis '外呼状态';
 comment on column TBL_WORKORDER.autodialfailurereasonis '失败原因';
 comment on column TBL_WORKORDER.billidis '对应话单ID';
 comment on column TBL_WORKORDER.callbackis '是否回拔';
 comment on column TBL_WORKORDER.calleeidis '呼叫号码';
 */
public class WorkOrder extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -597742190552838365L;
	private String id;
	private int answerstate;
	private String overflag;
	private Date accepttime;
	private String userid;
	private String keyid;
	private String customerid;
	private String accuseid;
	private String openlockid;
	private String accusecallbillid;
	private String accuseopenlockid;
	private String remark;
	private int businesstype;
	private int switchtype;
	private String dtmf;
	private String callerid;
	private String openlockname;
	private int autodialfailurereason;
	private String billid;
	private int callback;
	private String calleeid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAnswerstate() {
		return answerstate;
	}

	public void setAnswerstate(int answerstate) {
		this.answerstate = answerstate;
	}

	public String getOverflag() {
		return overflag;
	}

	public void setOverflag(String overflag) {
		this.overflag = overflag;
	}

	public Date getAccepttime() {
		return accepttime;
	}

	public void setAccepttime(Date accepttime) {
		this.accepttime = accepttime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getAccuseid() {
		return accuseid;
	}

	public void setAccuseid(String accuseid) {
		this.accuseid = accuseid;
	}

	public String getOpenlockid() {
		return openlockid;
	}

	public void setOpenlockid(String openlockid) {
		this.openlockid = openlockid;
	}

	public String getAccusecallbillid() {
		return accusecallbillid;
	}

	public void setAccusecallbillid(String accusecallbillid) {
		this.accusecallbillid = accusecallbillid;
	}

	public String getAccuseopenlockid() {
		return accuseopenlockid;
	}

	public void setAccuseopenlockid(String accuseopenlockid) {
		this.accuseopenlockid = accuseopenlockid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(int businesstype) {
		this.businesstype = businesstype;
	}

	public int getSwitchtype() {
		return switchtype;
	}

	public void setSwitchtype(int switchtype) {
		this.switchtype = switchtype;
	}

	public String getDtmf() {
		return dtmf;
	}

	public void setDtmf(String dtmf) {
		this.dtmf = dtmf;
	}

	public String getCallerid() {
		return callerid;
	}

	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}

	public String getOpenlockname() {
		return openlockname;
	}

	public void setOpenlockname(String openlockname) {
		this.openlockname = openlockname;
	}

	public int getAutodialfailurereason() {
		return autodialfailurereason;
	}

	public void setAutodialfailurereason(int autodialfailurereason) {
		this.autodialfailurereason = autodialfailurereason;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public int getCallback() {
		return callback;
	}

	public void setCallback(int callback) {
		this.callback = callback;
	}

	public String getCalleeid() {
		return calleeid;
	}

	public void setCalleeid(String calleeid) {
		this.calleeid = calleeid;
	}

	// 以下字段在数据库没有对应列
	private Date acceptStartTime;
	private Date acceptEndTime;

	public Date getAcceptStartTime() {
		return acceptStartTime;
	}

	public void setAcceptStartTime(Date acceptStartTime) {
		this.acceptStartTime = acceptStartTime;
	}

	public Date getAcceptEndTime() {
		return acceptEndTime;
	}

	public void setAcceptEndTime(Date acceptEndTime) {
		this.acceptEndTime = acceptEndTime;
	}

	@Override
	public String toString() {
		return "WorkOrder [id=" + id + ", answerstate=" + answerstate
				+ ", overflag=" + overflag + ", accepttime=" + accepttime
				+ ", userid=" + userid + ", keyid=" + keyid + ", customerid="
				+ customerid + ", accuseid=" + accuseid + ", openlockid="
				+ openlockid + ", accusecallbillid=" + accusecallbillid
				+ ", accuseopenlockid=" + accuseopenlockid + ", remark="
				+ remark + ", businesstype=" + businesstype + ", switchtype="
				+ switchtype + ", dtmf=" + dtmf + ", callerid=" + callerid
				+ ", openlockname=" + openlockname + ", autodialfailurereason="
				+ autodialfailurereason + ", billid=" + billid + ", callback="
				+ callback + ", calleeid=" + calleeid + ", acceptStartTime="
				+ acceptStartTime + ", acceptEndTime=" + acceptEndTime + "]";
	}

}
