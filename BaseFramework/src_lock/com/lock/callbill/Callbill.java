package com.lock.callbill;

import java.util.Date;

import com.base.core.ssh.l0model.BaseEntity;

/*数据库SQL语句
 * -- Create table
 create table CALLBILL
 (
 id                    VARCHAR2(32) not null,
 callerid              VARCHAR2(15) not null,
 calleeid              VARCHAR2(15) not null,
 cvtid                 VARCHAR2(15),
 talktyp               NUMBER(2) default 0 not null,
 switchtype            NUMBER(2) default 0 not null,
 stopon                NUMBER(2) default 1,
 recordpath            VARCHAR2(200),
 hangofftm             DATE not null,
 waittalktm            DATE not null,
 starttalktm           DATE not null,
 endtalktm             DATE not null,
 hangontm              DATE not null,
 staffid               VARCHAR2(20),
 dtmf                  VARCHAR2(20),
 workoderid            VARCHAR2(32),
 feecount              NUMBER default 0 not null,
 openlockid            VARCHAR2(32),
 openlockname          VARCHAR2(50),
 talkingsecond         NUMBER(10) default 0,
 calllong              NUMBER(10) default 0,
 businesstype          NUMBER(2) default 0,
 pendingreason         NUMBER(10) default -1,
 releasereason         NUMBER(10) default 0,
 autodialfailurereason NUMBER(10) default 0,
 talkinggroup          NUMBER(10) default -1,
 dialstatus            NUMBER(10) default 0,
 billid                VARCHAR2(32)
 )
 -- Add comments to the columns 
 comment on column CALLBILL.businesstype is 'enum BusinessType
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
 };
 ';
 comment on column CALLBILL.pendingreason is '获取通道进入S_CALL_PENDING状态的具体原因';
 comment on column CALLBILL.releasereason is '获取对端交换机发送的释放消息中的释放原因值';
 comment on column CALLBILL.autodialfailurereason is '获取去话呼叫失败的具体原因';
 comment on column CALLBILL.talkinggroup is '会议组号';
 comment on column CALLBILL.dialstatus is '外呼状态';
 comment on column CALLBILL.billid is '对应呼入话单ID';
 -- Create/Recreate primary, unique and foreign key constraints 
 alter table CALLBILL add constraint PK2_1 primary key (ID)
 */
public class Callbill extends BaseEntity
{
	private static final long serialVersionUID=-6934363841120520378L;
	private String id;
	private String callerid;
	private String calleeid;
	private String cvtid;
	private int talktyp;
	private int switchtype;
	private int stopon;
	private String recordpath;
	private Date hangofftm;
	private Date waittalktm;
	private Date starttalktm;
	private Date endtalktm;
	private Date hangontm;
	private String staffid;
	private String dtmf;
	private String workoderid;
	private int feecount;
	private String openlockid;
	private String openlockname;
	private int talkingsecond;
	private int calllong;
	private int businesstype;
	private int pendingreason;
	private int releasereason;
	private int autodialfailurereason;
	private int talkinggroup;
	private int dialstatus;
	private String billid;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id=id;
	}

	public String getCallerid()
	{
		return callerid;
	}

	public void setCallerid(String callerid)
	{
		this.callerid=callerid;
	}

	public String getCalleeid()
	{
		return calleeid;
	}

	public void setCalleeid(String calleeid)
	{
		this.calleeid=calleeid;
	}

	public String getCvtid()
	{
		return cvtid;
	}

	public void setCvtid(String cvtid)
	{
		this.cvtid=cvtid;
	}

	public int getTalktyp()
	{
		return talktyp;
	}

	public void setTalktyp(int talktyp)
	{
		this.talktyp=talktyp;
	}

	public int getSwitchtype()
	{
		return switchtype;
	}

	public void setSwitchtype(int switchtype)
	{
		this.switchtype=switchtype;
	}

	public int getStopon()
	{
		return stopon;
	}

	public void setStopon(int stopon)
	{
		this.stopon=stopon;
	}

	public String getRecordpath()
	{
		return recordpath;
	}

	public void setRecordpath(String recordpath)
	{
		this.recordpath=recordpath;
	}

	public Date getHangofftm()
	{
		return hangofftm;
	}

	public void setHangofftm(Date hangofftm)
	{
		this.hangofftm=hangofftm;
	}

	public Date getWaittalktm()
	{
		return waittalktm;
	}

	public void setWaittalktm(Date waittalktm)
	{
		this.waittalktm=waittalktm;
	}

	public Date getStarttalktm()
	{
		return starttalktm;
	}

	public void setStarttalktm(Date starttalktm)
	{
		this.starttalktm=starttalktm;
	}

	public Date getEndtalktm()
	{
		return endtalktm;
	}

	public void setEndtalktm(Date endtalktm)
	{
		this.endtalktm=endtalktm;
	}

	public Date getHangontm()
	{
		return hangontm;
	}

	public void setHangontm(Date hangontm)
	{
		this.hangontm=hangontm;
	}

	public String getStaffid()
	{
		return staffid;
	}

	public void setStaffid(String staffid)
	{
		this.staffid=staffid;
	}

	public String getDtmf()
	{
		return dtmf;
	}

	public void setDtmf(String dtmf)
	{
		this.dtmf=dtmf;
	}

	public String getWorkoderid()
	{
		return workoderid;
	}

	public void setWorkoderid(String workoderid)
	{
		this.workoderid=workoderid;
	}

	public int getFeecount()
	{
		return feecount;
	}

	public void setFeecount(int feecount)
	{
		this.feecount=feecount;
	}

	public String getOpenlockid()
	{
		return openlockid;
	}

	public void setOpenlockid(String openlockid)
	{
		this.openlockid=openlockid;
	}

	public String getOpenlockname()
	{
		return openlockname;
	}

	public void setOpenlockname(String openlockname)
	{
		this.openlockname=openlockname;
	}

	public int getTalkingsecond()
	{
		return talkingsecond;
	}

	public void setTalkingsecond(int talkingsecond)
	{
		this.talkingsecond=talkingsecond;
	}

	public int getCalllong()
	{
		return calllong;
	}

	public void setCalllong(int calllong)
	{
		this.calllong=calllong;
	}

	public int getBusinesstype()
	{
		return businesstype;
	}

	public void setBusinesstype(int businesstype)
	{
		this.businesstype=businesstype;
	}

	public int getPendingreason()
	{
		return pendingreason;
	}

	public void setPendingreason(int pendingreason)
	{
		this.pendingreason=pendingreason;
	}

	public int getReleasereason()
	{
		return releasereason;
	}

	public void setReleasereason(int releasereason)
	{
		this.releasereason=releasereason;
	}

	public int getAutodialfailurereason()
	{
		return autodialfailurereason;
	}

	public void setAutodialfailurereason(int autodialfailurereason)
	{
		this.autodialfailurereason=autodialfailurereason;
	}

	public int getTalkinggroup()
	{
		return talkinggroup;
	}

	public void setTalkinggroup(int talkinggroup)
	{
		this.talkinggroup=talkinggroup;
	}

	public int getDialstatus()
	{
		return dialstatus;
	}

	public void setDialstatus(int dialstatus)
	{
		this.dialstatus=dialstatus;
	}

	public String getBillid()
	{
		return billid;
	}

	public void setBillid(String billid)
	{
		this.billid=billid;
	}

	/*
	 * 以下字段数据库没有对应列
	 */
	private String openlocktel;
	private Date hangontime;
	private Date hangofftime;

	public String getOpenlocktel()
	{
		return openlocktel;
	}

	public void setOpenlocktel(String openlocktel)
	{
		this.openlocktel=openlocktel;
	}

	public Date getHangontime()
	{
		return hangontime;
	}

	public void setHangontime(Date hangontime)
	{
		this.hangontime=hangontime;
	}

	public Date getHangofftime()
	{
		return hangofftime;
	}

	public void setHangofftime(Date hangofftime)
	{
		this.hangofftime=hangofftime;
	}

	@Override
	public String toString()
	{
		return "Callbill [id="+id+", callerid="+callerid+", calleeid="+calleeid+", cvtid="+cvtid+", talktyp="+talktyp+", switchtype="+switchtype+", stopon="+stopon+", recordpath="+recordpath+", hangofftm="+hangofftm+", waittalktm="+waittalktm+", starttalktm="+starttalktm+", endtalktm="+endtalktm+", hangontm="+hangontm+", staffid="+staffid+", dtmf="+dtmf+", workoderid="+workoderid+", feecount="+feecount+", openlockid="+openlockid+", openlockname="+openlockname+", talkingsecond="+talkingsecond+", calllong="+calllong+", businesstype="+businesstype+", pendingreason="+pendingreason+", releasereason="+releasereason+", autodialfailurereason="+autodialfailurereason+", talkinggroup="+talkinggroup+", dialstatus="+dialstatus+", billid="+billid+", openlocktel="+openlocktel+", hangontime="+hangontime
				+", hangofftime="+hangofftime+"]";
	}

}
