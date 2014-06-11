//在Oracle中 
//双引号的作用只有一种，表示其内部的字符串严格区分大小写
//单引号的作用也只有一种，表示一个字符串
//在Oracle中,类型
//字符串：字符串分为定长类型char和变长类型varchar2。
//数字：整数 number(整数位)，小数 number(总长度，小数位)，只写number，表示无限制。

CREATE TABLE "<table_name>"
 (   
 "ID" number(20,0) NOT NULL, 
 "COMPANY_ID" number(2,330) DEFAULT asdf,
  PRIMARY KEY("ID"), 
  );
  
COMMENT ON COLUMN "<table_name>"."ID" IS "角色标识";

CREATE INDEX CALLCENTER."PK_Base_role" ON CALLCENTER."<table_name>" (ID ASC);

CREATE INDEX CALLCENTER."aaaa" ON CALLCENTER."<table_name>" (ID DESC);


创建索引： 
create index F2009100000NMINFOSYS_XIANG on f2009100000nminfo( SYS_XIANG );
创建一般索引，索引名为表名+列名
 
create unique index F2009100000NMINFOSYS_ZDM on f2009100000nminfo( SYS_ZDM );
创建唯一索引
 
create BITMAP index F2009100000NMINFOSYS_XIANG on f2009100000nminfo( SYS_XIANG );
创建位图索引


完整语法如下：
CREATE (UNIQUE|BITMAP) INDEX [用户名.]索引名 ON [用户名.]表名
 (列名 [ ASC | DESC], [列名 [ ASC | DESC]]...)
[ TABLESPACE 表空间名 ]
[ PCTFREE 正整型数 ]
[ INITRANS 正整型数 ]
[ MAXTRANS 正整型数 ]
[ 存储子句 ]
[ LOGGING | NOLOGGING ]
[ NOSORT ]

 b_money number(20,10) default 0,
 

/*==============================================================*/
/* Table: base_区域表												*/
/*==============================================================*/
drop sequence "S_BASE_AREA";
create sequence "S_BASE_AREA";

drop table "T_BASE_AREA" cascade constraints;
create table "T_BASE_AREA"  (
	/*区域标识*/
	"ID" number(20,0) NOT NULL, 
	/*上级*/
	"PARENT_ID" number(20,0),
	/*区域层级*/
	"AREA_LEVEL" number(2,0),
	/*区域编号*/
	"AREA_CODE" varchar2(128 byte) default '',
	/*区域类型*/
	"AREA_TYPE" varchar2(128 byte) default '',
	/*区域名称*/
	"AREA_NAME" varchar2(255 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_AREA" primary key ("ID"),
	/*外键*/
	foreign key ("PARENT_ID") references T_BASE_AREA("ID")
);


/*==============================================================*/
/* Table: base_公司表												*/
/*==============================================================*/
drop sequence "S_BASE_COMPANY";
create sequence "S_BASE_COMPANY";

drop table "T_BASE_COMPANY" cascade constraints;
create table "T_BASE_COMPANY"  (
	/*公司标识*/
	"ID" number(20,0) NOT NULL, 
	/*所属区域*/
	"AREA_ID" number(20,0), 
	/*公司编号*/
	"COMPANY_CODE" varchar2(128 byte) default '',
	/*公司类型*/
	"COMPANY_TYPE" varchar2(128 byte) default '',
	/*公司名称(可以存json)*/
	"COMPANY_NAME" varchar2(255 byte) default '',
	/*简介，描述*/
	"DESCRIBES" varchar2(1024 byte) default '',
	/*法人标识*/
	"CORPORATION_ID" varchar2(128 byte) default '',
	/*法人名称*/
	"CORPORATION_NAME" varchar2(128 byte) default '',
	/*负责人标识*/
	"PRINCIPAL_ID" varchar2(128 byte) default '',
	/*负责人名称*/
	"PRINCIPAL_NAME" varchar2(128 byte) default '',
	/*联系人标识*/
	"LINKMAN_ID" varchar2(128 byte) default '',
	/*联系人姓名*/
	"LINKMAN_NAME" varchar2(128 byte) default '',
	/*电话*/
	"TELEPHONE" varchar2(64 byte) default '',
	/*电子邮件*/
	"E_MAIL" varchar2(128 byte) default '',
	/*省*/
	"PROVINCE" varchar2(64 byte) default '',
	/*市*/
	"CITY" varchar2(64 byte) default '',
	/*区*/
	"DISTRICT" varchar2(64 byte) default '',
	/*地址*/
	"ADDRESS" varchar2(128 byte) default '',
	/*邮编*/
	"POST_CODE" varchar2(64 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_COMPANY" primary key ("ID"),
	/*外键*/
	foreign key ("AREA_ID") references T_BASE_AREA("ID")
);



/*==============================================================*/
/* Table: base_组织结构表											*/
/*==============================================================*/
drop sequence "S_BASE_ORGANIZATION";
create sequence "S_BASE_ORGANIZATION";

drop table "T_BASE_ORGANIZATION" cascade constraints;
create table "T_BASE_ORGANIZATION"  (
	/*组织结构标识*/
	"ID" number(20,0) NOT NULL,
	/*上级组织结构*/
	"PARENT_ID" number(20,0),
	/*组织结构所属公司*/
	"COMPANY_ID" number(20,0),
    /*组织结构层级*/
	"ORGANIZATION_LEVEL" number(2,0),
	/*组织结构编号*/
	"ORGANIZATION_CODE" varchar2(128 byte) default '',
	/*组织结构类型*/
	"ORGANIZATION_TYPE" varchar2(128 byte) default '',
	/*组织结构名称*/
	"ORGANIZATION_NAME" varchar2(128 byte) default '',
	/*简介，描述*/
	"DESCRIBES" varchar2(1024 byte) default '',
	/*管理者标识（经理）*/
	"MANAGER_ID" varchar2(128 byte) default '',
	/*管理者名称（经理）*/
	"MANAGER_NAME" varchar2(128 byte) default '',
	/*电话*/
	"TELEPHONE" varchar2(64 byte) default '',
	/*电子邮件*/
	"E_MAIL" varchar2(128 byte) default '',
	/*省*/
	"PROVINCE" varchar2(64 byte) default '',
	/*市*/
	"CITY" varchar2(64 byte) default '',
	/*地址*/
	"ADDRESS" varchar2(128 byte) default '',
	/*邮编*/
	"POST_CODE" varchar2(64 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_ORGANIZATION" primary key ("ID"),
	/*外键*/
	foreign key ("PARENT_ID") references T_BASE_ORGANIZATION("ID"),
	foreign key ("COMPANY_ID") references T_BASE_COMPANY("ID")
);


/*==============================================================*/
/* Table: base_角色表												*/
/*==============================================================*/
drop sequence "S_BASE_ROLE";
create sequence "S_BASE_ROLE";

drop table "T_BASE_ROLE" cascade constraints;
create table "T_BASE_ROLE" (
	/*角色标识*/
	"ID" number(20,0) not null,
	/*公司标识（公司可以建立独立的角色管理），不需要外键*/
	"COMPANY_ID" number(20,0),
	/*角色编号*/
	"ROLE_CODE" varchar2(128 byte) default '',
	/*角色类型*/
	"ROLE_TYPE" varchar2(128 byte) default '',
	/*角色名称（用于前台显示）*/
	"NAME" varchar2(128 byte) default '',
	/*角色等级标识（eg:高级，中级，低级）*/
	"GRADE_ID" varchar2(128 byte) default '',
	/*角色等级名称（eg:高级，中级，低级）*/
	"GRADE_NAME" varchar2(128 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_ROLE" primary key ("ID")
);


/*==============================================================*/
/* Table: base_功能表												*/
/*==============================================================*/

drop sequence "S_BASE_FUNCTION";
create sequence "S_BASE_FUNCTION";

drop table "T_BASE_FUNCTION" cascade constraints;
create table "T_BASE_FUNCTION"  (
	/*功能标识*/
	"ID" number(20,0) NOT NULL, 
	/*上级功能*/
	"PARENT_ID" number(20,0),
	/*功能层级*/
	"FUNCTION_LEVEL" number(20,0),
	/*功能编码*/
	"FUNCTION_CODE" varchar2(128 byte) default '',
	/*功能类型(客户端，浏览器)*/
	"FUNCTION_TYPE" varchar2(128 byte) default '',
	/*功能显示的名称*/
	"NAME" varchar2(128 byte) default '',
	/*功能显示图标*/
	"ICON" varchar2(255 byte) default '',
	/*资源*/
	"URL" varchar2(255 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_FUNCTION" primary key ("ID"),
	/*外键*/
	foreign key ("PARENT_ID") references "T_BASE_FUNCTION" ("ID")
);



/*==============================================================*/
/* Table: base_资源表												*/
/*==============================================================*/
drop sequence "S_BASE_RESOURCE";
create sequence "S_BASE_RESOURCE";

drop table "T_BASE_RESOURCE" cascade constraints;
create table "T_BASE_RESOURCE"  (
	/*资源标识*/
	"ID" number(20,0) NOT NULL, 
	/*功能标识*/
	"FUNCTION_ID" number(20,0),
	/*资源编码*/
	"RESOURCE_CODE" varchar2(128 byte) default '',
	/*资源类型(客户端，浏览器)*/
	"RESOURCE_TYPE" varchar2(128 byte) default '',
	/*资源显示的名称*/
	"NAME" varchar2(128 byte) default '',
	/*URL*/
	"URL" varchar2(255 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_RESOURCE" primary key ("ID"),
	/*外键*/
	foreign key ("FUNCTION_ID") references "T_BASE_FUNCTION" ("ID")
);


/*==============================================================*/
/* Table: base_权限表（中间表：角色表，功能表，资源表）					*/
/*==============================================================*/
drop sequence "S_BASE_PERMISSION";
create sequence "S_BASE_PERMISSION";

drop table "T_BASE_PERMISSION" cascade constraints;
create table "T_BASE_PERMISSION"  (
	/*权限标识*/
	"ID" number(20,0) NOT NULL, 
	/*角色标识（外键关联）*/
	"ROLE_ID" number(20,0), 
	/*功能标识（外键关联）*/
	"FUNCTION_ID" number(20,0), 
	/*资源标识（外键关联）*/
	"RESOURCE_ID" number(20,0), 
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_PERMISSION" primary key ("ID"),
	/*外键*/
	foreign key ("ROLE_ID") references "T_BASE_ROLE" ("ID"),
	foreign key ("FUNCTION_ID") references "T_BASE_FUNCTION" ("ID"),
	foreign key ("RESOURCE_ID") references "T_BASE_RESOURCE" ("ID")
);


/*==============================================================*/
/* Table: base_用户表												*/
/*==============================================================*/
drop sequence S_BASE_USER;
create sequence S_BASE_USER;

drop table "T_BASE_USER" cascade constraints;
create table "T_BASE_USER"  (
	/*用户标识*/
	"ID" number(20,0) NOT NULL, 
	/*用户所属公司*/
	"COMPANY_ID" number(20,0),
	/*用户所属组织机构*/
	"ORGANIZATION_ID" number(20,0),
	/*用户角色*/
	"ROLE_ID" number(20,0),
	/*用户编号*/
	"USER_CODE" varchar2(128 byte) default '',
	/*用户类型*/
	"USER_TYPE" varchar2(128 byte) default '',
	/*登录名称*/
	"USER_NAME" varchar2(128 byte) default '',
	/*登录密码*/
	"PASSWORD" varchar2(128 byte) default '',
	/*名称*/
	"FULL_NAME" varchar2(128 byte) default '',
	/*昵称*/
	"SHORT_NAME" varchar2(128 byte) default '',
	/*全拼*/
	"FULL_SPELLING" varchar2(128 byte) default '',
	/*简拼*/
	"SHORT_SPELLING" varchar2(128 byte) default '',
	/*完整英文名*/
	"FULL_ENGLISH" varchar2(128 byte) default '',
	/*简写英文名*/
	"SHORT_ENGLISH" varchar2(128 byte) default '',
	/*简介，描述*/
	"DESCRIBES" varchar2(1024 byte) default '',
	/*移动电话*/
	"MOBILE_PHONE" varchar2(64 byte) default '',
	/*办公电话*/
	"OFFICE_PHONE" varchar2(64 byte) default '',
	/*电子邮件*/
	"E_MAIL" varchar2(128 byte) default '',
	/*身份证*/
	"ID_CARD" varchar2(32 byte) default '',
	/*性别*/
	"SEX" varchar2(1 byte) default '',
	/*职位*/
	"JOB_TITLE" varchar2(64 byte) default '',
	/*职务*/
	"JOB_DUTY" varchar2(64 byte) default '',
	/*学历*/
	"DEGREE" varchar2(64 byte) default '',
	/*身高*/
	"HEIGHT" varchar2(64 byte) default '',
	/*体重*/
	"WEIGHT" varchar2(64 byte) default '',
	/*血型*/
	"BLOOD_TYPE" varchar2(64 byte) default '',
	/*生日*/
	"BIRTHDAY" varchar2(64 byte) default '',
	/*出生地*/
	"BIRTH_PLACE" varchar2(255 byte) default '',
	/*家电话*/
	"HOME_PHONE" varchar2(64 byte) default '',
	/*家地址*/
	"HOME_ADDRESS" varchar2(255 byte) default '',
	/*家邮编*/
 	"HOME_POST_CODE" varchar2(64 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_USER" primary key ("ID"),
	/*外键*/
	foreign key ("COMPANY_ID") references T_BASE_COMPANY("ID"),
	foreign key ("ORGANIZATION_ID") references T_BASE_ORGANIZATION("ID"),
	foreign key ("ROLE_ID") references T_BASE_ROLE("ID")
);


/*==============================================================*/
/* Table: base_登录日志表											*/
/*==============================================================*/

drop sequence S_BASE_LOG_LOGIN;
create sequence S_BASE_LOG_LOGIN;

drop table T_BASE_LOG_LOGIN cascade constraints;
create table T_BASE_LOG_LOGIN  (
	
	/** 登录日志Id */
	"ID" number(20,0) not null,
	/** 登录用户(不关联外键) */
	"USER_ID" number(20,0),

	/** 登录方式（浏览器，客户端） */
	"LOGIN_TYPE" varchar2(32,0), 
	/** 登录时间 */
	"LOGIN_TIME" varchar2(32 byte),
	/** 登出时间 */
	"LOGOUT_TIME" varchar2(32 byte),
	/** 登录IP */
	"LOGIN_IP" varchar2(32 byte),
	/** 登录结果 */
	"RESULT" varchar2(255 byte),

    /*国际移动设备识别码，手机串号，*#06#*/
	"USER_IMEI" varchar2(128 byte) default '',
	/*国际移动用户识别码，手机卡串号*/
	"USER_IMSI" varchar2(128 byte) default '',
	/*手机号*/
	"USER_MOBILE_PHONE" varchar2(128 byte) default '',
	/*手机品牌*/
	"USER_DEVICE_BRAND" varchar2(128 byte) default '',
	/*手机型号*/
	"USER_DEVICE_MODEL" varchar2(128 byte) default '',
	/*手机版本号*/
	"USER_DEVICE_VERSION" varchar2(128 byte) default '',
	/*手机操作系统(Android,iOS)*/
	"USER_DEVICE_OS" varchar2(128 byte) default '',
	/*手机操作系统版本*/
	"USER_DEVICE_OS_VERSION" varchar2(128 byte) default '',

	/*主键*/
	constraint "PK_BASE_LOGIN_LOG" primary key ("ID"),
	/*外键*/
	foreign key ("USER_ID") references T_BASE_USER("ID")
);



/*==============================================================*/
/* Table: base_登录后，操作日志表									*/
/*==============================================================*/
drop sequence S_BASE_LOG_OPERATE;
create sequence S_BASE_LOG_OPERATE;

drop table T_BASE_LOG_OPERATE cascade constraints;
create table T_BASE_LOG_OPERATE  (
	/** Id */
	"ID" number(20,0) not null,
	/** 登录用户(不关联外键) */
	"USER_ID" number(20,0),
	/** 客户端IP */
	"CLIENT_IP" varchar2(32 byte),
	/** 客户端类型 */
	"CLIENT_TYPE" varchar2(32 byte),
	/** 操作类型 */
	"OPERATE_TYPE" varchar2(32 byte),
	/** 操作时间 */
	"OPERATE_TIME" varchar2(32 byte),
	/** 操作方法 */
	"OPERATE_Function" varchar2(256 byte),
	/** 操作内容 */
	"OPERATE_CONTENTS" varchar2(512 byte),
	/** 操作表 */
	"TABLE_NAME" varchar2(128 byte),
	/** 影响的记录数 */
	"AFFECTED_ROWS" number(20,0),
	/*主键*/
	constraint PK_BASE_LOG_OPERATE primary key ("ID"),
	/*外键*/
	foreign key ("USER_ID") references T_BASE_USER("ID")
);



/*==============================================================*/
/* Table: base_配置						*/
/*==============================================================*/
drop sequence S_BASE_CONFIG;
create sequence S_BASE_CONFIG;

drop table T_BASE_CONFIG cascade constraints;
create table T_BASE_CONFIG  (
	/** 配置Id */
	"ID" number(20,0) not null,
	/** 公司ID */
	"COMPANY_ID" number(20,0),
	/** 配置编码 */
	"CONFIG_CODE" varchar2(128 byte),
	/** 配置类型(浏览器，客户端) */
	"CONFIG_TYPE" varchar2(128 byte),
	/** 配置名称 */
	"CONFIG_NAME" varchar2(128 byte),
	/** 配置值 */
	"CONFIG_VALUE" varchar2(128 byte),


	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	constraint PK_BASE_CONFIG primary key ("CONFIG_ID"),
);



/*==============================================================*/
/* Table: base_通知（发给指定人群，可以回复）				*/
/*==============================================================*/
	
/*==============================================================*/
/* Table: base_消息						*/
/*==============================================================*/
/*消息类型枚举值*/
public static final String TYPE_Text="text";
public static final String TYPE_Text_Location="text/location";
public static final String TYPE_File="file";
public static final String TYPE_File_Audio="file/audio";
public static final String TYPE_File_Image="file/image";
public static final String TYPE_File_Video="file/video";
/*==============================================================*/
/* Table: base_邮件						*/
/*==============================================================*/




/*==============================================================*/
/* Table: base_mobile_版本更新                                   */
/*==============================================================*/
drop sequence "S_BASE_MOBILE_VERSION";
create sequence "S_BASE_MOBILE_VERSION";

drop table "T_BASE_MOBILE_VERSION" cascade constraints;
create table "T_BASE_MOBILE_VERSION" (
	/*角色标识*/
	"ID" number(20,0) not null,
	/*移动设备类型（iOS和Android）*/
	"MOBILE" varchar2(32 byte) default '',
	/*版本号(1.0.0.0)*/
	"VERSION_NAME" varchar2(32 byte) default '1.0.0.0',
    /*版本号(1)*/
	"VERSION_CODE" number(20,0) default 1,
    /*版本说明*/
    "DESCRIPTION" varchar2(512 byte) default '',
    /*发布时间*/
    "RELEASE_TIME" varchar2(32 byte) default '',
    /*下载地址*/
    "FILE_URL" varchar2(256 byte) default '',
    /*文件大小*/
    "FILE_SIZE" varchar2(32 byte) default '',
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*主键*/
	constraint "PK_BASE_MOBILE_VERSION" primary key ("ID")
);





/*==============================================================*/
/* Table: 开锁信息表												*/
/*==============================================================*/
drop sequence S_LOCK_UNLOCK_INFO;
create sequence S_LOCK_UNLOCK_INFO;

drop table T_LOCK_UNLOCK_INFO cascade constraints;
create table T_LOCK_UNLOCK_INFO  (
	/*标识*/
	"ID" number(20,0) not null,
	/*备注*/
	"REMARKS" varchar2(255 byte) default '',
	/*序号*/
	"ORDER_NUMBER" number(20,0),
	/*当前状态*/
	"CURRENT_STATE" char(1 byte) default '0',
	/*是否被删除标记*/
	"IS_DELETE" char(1 byte) default '0',
	/*创建时间*/
	"CREATE_TIME" varchar2(20 byte) default '',
	/*更新时间*/
	"UPDATE_TIME" varchar2(20 byte) default '',
	/*开锁人员*/
	"USER_ID" number(20,0),
	/*国际移动设备识别码，手机串号，*#06#*/
	"USER_IMEI" varchar2(128 byte) default '',
	/*国际移动用户识别码，手机卡串号*/
	"USER_IMSI" varchar2(128 byte) default '',
	/*手机号*/
	"USER_MOBILE_PHONE" varchar2(128 byte) default '',
	/*手机品牌*/
	"USER_DEVICE_BRAND" varchar2(128 byte) default '',
	/*手机型号*/
	"USER_DEVICE_MODEL" varchar2(128 byte) default '',
	/*手机版本号*/
	"USER_DEVICE_VERSION" varchar2(128 byte) default '',
	/*手机操作系统(Android,iOS)*/
	"USER_DEVICE_OS" varchar2(128 byte) default '',
	/*手机操作系统版本*/
	"USER_DEVICE_OS_VERSION" varchar2(128 byte) default '',
	/*开锁类型*/
	"UNLOCK_TYPE" varchar2(128 byte),
	/*开锁时间*/
	"UNLOCK_TIME" varchar2(128 byte) default '',
	/*开锁地点*/
	"UNLOCK_LOCATION" varchar2(255 byte) default '',
	/*服务单*/
	"UNLOCK_WORK_ORDER" varchar2(255 byte) default '',	
	/*客户姓名*/
	"CUSTOMER_NAME" varchar2(128 byte) default '',
	/*客户身份证号*/
	"CUSTOMER_ID_NO" varchar2(128 byte) default '',
	/*客户身份证图片*/
	"CUSTOMER_ID_IMG" varchar2(255 byte) default '',
	/*客户驾驶证图片*/
	"DRIVING_LICENSE_IMG" varchar2(255 byte) default '',
	/*客户行驶证图片*/
	"VEHICLE_LICENSE_IMG" varchar2(255 byte) default '',
	/*客户执照图片*/
	"BUSINESS_LICENSE_IMG" varchar2(255 byte) default '',
	/*客户介绍信图片*/
	"INTRODUCTION_LETTER_IMG" varchar2(255 byte) default '',

	/*主键*/
	constraint PK_LOCK_UNLOCK_INFO primary key ("ID"),
	/*外键*/
	foreign key ("USER_ID") references T_BASE_USER("ID")
);


//	开民用锁
	//	1.身份证，
	//	2.96110服务单

	//	开汽车锁
	//	驾驶证照片	（拍照）
	//	行驶证照片（拍照）

	//	开保险箱
	//	个人：身份证
	//	单位：执照，介绍信，

	//	开ATM取款机

	//	开金库


开锁服务  信息系统

UnlockInfo


1.id
2.公司id，
2.用户ID（锁匠）
3.类型（中文）：
4.客户：身份证图片（姓名，生份证），服务单图片，驾驶证图片，行驶证图片，执照图片，介绍信图片，
5.手机取的：地点
6.手机信息：手机串号，



业务单：
1.满意度：
2.投诉：
3.拨打录音：
4.回访录音：

通话单：




查询：
1.时间，
2.公司，
3.用户，


DYLD_LIBRARY_PATH="/opt/oracle/oracle_instantclient_11_2" 

export DYLD_LIBRARY_PATH 

export ORACLE_HOME=$DYLD_LIBRARY_PATH 

TNS_ADMIN=/opt/oracle/tns 

export TNS_ADMIN

export NLS_LANG="AMERICAN_AMERICA.UTF8"  


