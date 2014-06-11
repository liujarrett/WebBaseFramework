package com.base.core.utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SJDateUtil
{
	public static final String DEFAULT_FORMAT_STRING="yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat DEFAULT_FORMAT=new SimpleDateFormat(DEFAULT_FORMAT_STRING);
	public static final String DEFAULT_DATE_FORMAT_STRING="yyyy-MM-dd";
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT=new SimpleDateFormat(DEFAULT_DATE_FORMAT_STRING);
	public static final String DEFAULT_TIME_FORMAT_STRING="HH:mm:ss";
	public static final SimpleDateFormat DEFAULT_TIME_FORMAT=new SimpleDateFormat(DEFAULT_TIME_FORMAT_STRING);

	
	/**
	 * 获取当前时间字符串
	 */
	public static String getCurrentStr(String mask)
	{
		if(mask==null||mask.length()==0)
		{
			mask=DEFAULT_FORMAT_STRING;
		}
		SimpleDateFormat format=new SimpleDateFormat(mask);
		return format.format(new java.util.Date());
	}

	/**
	 * 获得当前日期
	 */
	public static Date getCurrentDate()
	{
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获得当前时间的时间戳
	 */
	public static Timestamp getCurrentTimeStamp()
	{
		return new Timestamp(getCurrentDate().getTime());
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * 将字符串转换成日期
	 */
	public static Date strToDate(String str,String mask)
	{
		try
		{
			if(mask==null||mask.length()==0)
			{
				mask=DEFAULT_FORMAT_STRING;
			}
			SimpleDateFormat df=new SimpleDateFormat(mask);
			return new Date((df.parse(str)).getTime());
		}
		catch(Exception Ex)
		{
			return null;
		}
	}

	/**
	 * 将日期转换成字符串
	 */
	public static String dateToStr(Date date,String mask)
	{
		if(mask==null||mask.length()==0)
		{
			mask=DEFAULT_FORMAT_STRING;
		}
		SimpleDateFormat df=new SimpleDateFormat(mask);
		return df.format(date);
	}

	/**
	 * 将字符串(yyyy-mm-dd hh:mm:ss)转换时间戳
	 */
	public static Timestamp strToTimestamp(String str)
	{
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		ts=Timestamp.valueOf(str);
		return ts;
	}

	/**
	 * 将时间戳转换成字符串
	 */
	public static String timestampToStr(Timestamp ts,String mask)
	{
		if(mask==null||mask.length()==0)
		{
			mask=DEFAULT_FORMAT_STRING;
		}
		SimpleDateFormat df=new SimpleDateFormat(mask);
		return df.format(ts);
	}

	/**
	 * 将date转换Timestamp
	 */
	public static Timestamp dateToTimestamp(Date date)
	{
		return new Timestamp(date.getTime());
	}

	/**
	 * 将Timestamp转换date
	 */
	public static Date timestampToDate(Timestamp ts)
	{
		return new Date(ts.getTime());
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * 将年月日转换成日期
	 */
	public static Date toDate(int year,int month,int day)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.set(year,month-1,day);
		return calendar.getTime();
	}

	/**
	 * 取得当前日期是多少周
	 */
	public static int weekOfYear(int year,int month,int day)
	{
		int days=daysOf(year,month,day);
		int weeks=days/7;
		if(days%7!=0)
			weeks++;
		return weeks;
	}

	/**
	 * 得到某一年周的总数
	 */
	public static int weekSumOfYear(int year)
	{
		return weekOfYear(year,12,31);
	}

	/**
	 * 替换Date.getDay();
	 */
	public static int dayOfWeek(java.util.Date date)
	{
		Calendar cl=Calendar.getInstance();
		cl.setTime(date);
		return cl.get(Calendar.DAY_OF_WEEK)-Calendar.SUNDAY;
	}

	/**
	 * 取得指定日期所在周的第几天（取周几）
	 */
	public static int dayOfWeek(int year,int month,int day)
	{
		/**/
		int dayOfWeek=-1;
		Calendar calendar=new GregorianCalendar();
		calendar.clear();
		calendar.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		calendar.set(year,month-1,day);
		switch(calendar.get(Calendar.DAY_OF_WEEK))
		{
			case Calendar.MONDAY://
				dayOfWeek=1;
				break;
			case Calendar.TUESDAY://
				dayOfWeek=2;
				break;
			case Calendar.WEDNESDAY://
				dayOfWeek=3;
				break;
			case Calendar.THURSDAY://
				dayOfWeek=4;
				break;
			case Calendar.FRIDAY://
				dayOfWeek=5;
				break;
			case Calendar.SATURDAY://
				dayOfWeek=6;
				break;
			case Calendar.SUNDAY://
				dayOfWeek=7;
				break;
		}
		return dayOfWeek;
	}

	/**
	 * 取得指定日期所在周的第一天
	 */
	public static Date firstDayOfWeek(int year,int month,int day)
	{
		int days=daysOf(year,month,day);
		int week=days%7;
		if(week==0)
			week=7;
		Calendar calendar=new GregorianCalendar();
		calendar.clear();
		calendar.set(year,month-1,day);
		calendar.add(Calendar.DATE,-(--week));
		return calendar.getTime();
	}

	/**
	 * 得到某年某周的第一天
	 */
	public static Date firstDayOfWeek(int year,int week)
	{
		Calendar calendar=new GregorianCalendar();
		calendar.clear();
		calendar.set(year,0,1);
		calendar.add(Calendar.DATE,(week-1)*7);
		return firstDayOfWeek(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE));
	}

	/**
	 * 获取本周一（周一为一周开始）
	 */
	public static Date firstDayOfWeek()
	{
		Calendar c=Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(df.format(c.getTime()));
	}

	/**
	 * 取得指定日期所在周的最后一天
	 */
	public static Date lastDayOfWeek(int year,int month,int day)
	{
		int days=daysOf(year,month,day);
		int week=days%7;
		if(week==0)
			week=7;
		Calendar calendar=new GregorianCalendar();
		calendar.clear();
		calendar.set(year,month-1,day);
		calendar.add(Calendar.DATE,7-week);
		return calendar.getTime();
	}

	/**
	 * 得到某年某周的最后一天
	 */
	public static Date lastDayOfWeek(int year,int week)
	{
		Calendar calendar=new GregorianCalendar();
		calendar.clear();
		calendar.set(year,0,1);
		calendar.add(Calendar.DATE,(week-1)*7);
		return lastDayOfWeek(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE));
	}

	/**
	 * 获取本周日（周一为一周开始）
	 */
	public static Date lastDayOfWeek()
	{
		Calendar c=Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(df.format(c.getTime()));
	}

	/**
	 * 获取所给时间点所在月的第一天。
	 */
	public static Date firstDayOfMonth(long timeInMillis)
	{
		Calendar c=Calendar.getInstance();
		c.setTime(new Date(timeInMillis));
		c.set(Calendar.DATE,1);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(df.format(c.getTime()));
	}

	/**
	 * 获取所给时间点所在月的最后一天。
	 */
	public static Date lastDayOfMonth(long timeInMillis)
	{
		Calendar c=Calendar.getInstance();
		c.setTime(new Date(timeInMillis));
		c.set(Calendar.DATE,1);
		c.roll(Calendar.DATE,-1);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(df.format(c.getTime()));
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * 得到指定日期在这一年是第几天
	 */
	public static int daysOf(int year,int month,int day)
	{
		int days=0;
		/**/
		Calendar calendar=new GregorianCalendar();
		calendar.clear();
		calendar.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		calendar.set(year,0,1);
		switch(calendar.get(Calendar.DAY_OF_WEEK))
		{
			case Calendar.MONDAY://
				days=0;
				break;
			case Calendar.TUESDAY://
				days=1;
				break;
			case Calendar.WEDNESDAY://
				days=2;
				break;
			case Calendar.THURSDAY://
				days=3;
				break;
			case Calendar.FRIDAY://
				days=4;
				break;
			case Calendar.SATURDAY://
				days=5;
				break;
			case Calendar.SUNDAY://
				days=6;
				break;
		}
		/**/
		for(int i=1;i<month;i++)
		{
			days+=days(year,i);
		}
		/**/
		days+=day;
		return days;
	}

	/**
	 * 某年某月，有多少天
	 */
	public static int days(int year,int month)
	{
		switch(month)
		{
			case 2:
				if(isLeapYear(year))
					return 29;
				else
					return 28;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			default:
				return 0;
		}
	}

	/**
	 * 是否为闰年
	 */
	public static boolean isLeapYear(int year)
	{
		/* 方法一：公式计算 */
		if((year%4==0)&&((year%100!=0)|(year%400==0)))
			return true;
		else
			return false;
		/* 方法二：系统方法 */
		// GregorianCalendar calendar=new GregorianCalendar();
		// calendar.isLeapYear(year);
	}

	/**
	 * 计算两个日期相隔的天数
	 */
	public static int daysBetween(Date formDate,Date toDate)
	{
		Calendar aCalendar=Calendar.getInstance();
		aCalendar.setTime(formDate);
		int day1=aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(toDate);
		int day2=aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2-day1;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * 最大
	 */
	public static Date maxDate(Date date1,Date date2)
	{
		if(date1==null&&date2==null)
		{
			return null;
		}
		if(date1==null)
		{
			return date2;
		}
		if(date2==null)
		{
			return date1;
		}
		if(date1.after(date2))
		{
			return date1;
		}
		else
		{
			return date2;
		}
	}

	/**
	 * 最小
	 */
	public static Date minDate(Date date1,Date date2)
	{
		if(date1==null&&date2==null)
		{
			return null;
		}
		if(date1==null)
		{
			return date2;
		}
		if(date2==null)
		{
			return date1;
		}
		if(date1.after(date2))
		{
			return date2;
		}
		else
		{
			return date1;
		}
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * 日期运算：+n天
	 */
	public static Date dateByAddingSomeDay(Date startDate,int days)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DAY_OF_MONTH,days);
		return new Date(calendar.getTimeInMillis());
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * 获取最近的一个日期
	 */
	public static java.util.Date getLatestDate(List<java.util.Date> dateList)
	{
		if(dateList==null||dateList.isEmpty())
		{
			return null;
		}
		return Collections.max(dateList,new Comparator<java.util.Date>(){
			public int compare(java.util.Date o1,java.util.Date o2)
			{
				return o1.compareTo(o2);
			}
		});
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * 求得某一天的最后时间。即23时59分59秒999毫秒.
	 */
	public static Date getLastTimeInDay(Date day)
	{
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(day);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND,999);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	/**
	 * 去除日期的时间部分
	 */
	public static Date getPureDate(Date inputDate)
	{
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(inputDate);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

}
