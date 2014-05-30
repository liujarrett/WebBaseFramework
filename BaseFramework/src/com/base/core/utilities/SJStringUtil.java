package com.base.core.utilities;

public class SJStringUtil
{
	/**
	 * 判断字符串是否为空 isNull
	 * 
	 * @param name str 输入的字符串
	 * @return (true:为空 false:不为空)
	 */
	public static Boolean isNull(String str)
	{
		Boolean result=false;

		if(null==str||"".equals(str))
			result=true;

		return result;
	}

	/**
	 * 判断字符串是否不为空 isNotNull
	 * 
	 * @param name str 输入的字符串
	 * @return (true:不为空 false:为空)
	 */
	public static Boolean isNotNull(String str)
	{
		return !isNull(str);
	}

	/**
	 * 判断对象是否为空 isNull
	 * 
	 * @param name obj 输入的对象
	 * @return (true:为空 false:不为空)
	 */
	public static Boolean isNull(Object obj)
	{
		Boolean result=false;

		if(null==obj||"".equals(String.valueOf(obj)))
			result=true;

		return result;
	}

	/**
	 * 判断对象是否不为空 isNotNull
	 * 
	 * @param name obj 输入的对象
	 * @return (true:不为空 false:为空)
	 */
	public static Boolean isNotNull(Object obj)
	{
		return !isNull(obj);
	}

	/**
	 * 输入一个要重复的字符串和要重复的次数,返回重复过后的字符串 repeatStr
	 * 
	 * @param name str 待重复的字符串
	 * @param name times 要重复的次数
	 * @return 重复过后的字符串
	 */
	public static String repeatStr(String str,Integer times)
	{
		return new String(new char[times]).replace("\0",str);
	}

	/**
	 * 根据输入的字符串，长度和后缀格式化字符串 <br/>
	 * 
	 * disposalString <br/>
	 * TODO 适用条件: <br/>
	 * TODO 执行流程: <br/>
	 * TODO 使用方法: <br/>
	 * System.out.println("中".getBytes().length);//3 <br/>
	 * System.out.println("a".getBytes().length);//1 <br/>
	 * System.out.println("A".getBytes().length);//1 <br/>
	 * System.out.println("1".getBytes().length);//1 <br/>
	 * System.out.println(",".getBytes().length);//1 <br/>
	 * System.out.println("，".getBytes().length);//3 <br/>
	 * 
	 * TODO 注意事项: 可处理中文汉字，标点，英文字符，标点，大小写字母，数字，空格 <br/>
	 * 
	 * @param name str 预处理的字符串
	 * @param name length 长度
	 * @param name suffix 当字符串的长度大于length时，截取字符串，后缀用suffix补充(一般为...)
	 * @param name fill 当字符串的长度小于length时，用fill填充字符串(一般为英文空格" "，网页上为&nbsp;)
	 * @return 格式化后的字符串
	 */
	public static String disposalString(String str,Integer length,String suffix,String fill)
	{

		StringBuffer sbResult=new StringBuffer();

		if(SJStringUtil.isNull(str))
			return sbResult.toString();

		if(SJStringUtil.isNull(length)||0==length)
			length=12;

		if(SJStringUtil.isNull(suffix))
			suffix="...";

		//字符串总字节数
		Integer strBytesLength=str.getBytes().length;

		String[] strArray=new String[str.length()];

		Integer sequence=0;
		Integer tempLength=length;

		length=length*3/2;//按三字节重置length的长度

		try
		{

			if(strBytesLength<=length)
			{//当字符串的长度小于等于length时
				for(int i=0;i<str.length();i++)
				{
					strArray[i]=str.substring(i,i+1);

					if(strArray[i].getBytes().length==1)
						tempLength=Math.abs(tempLength-strArray[i].getBytes().length);
					else
						tempLength=tempLength-2;
				}
				sbResult.append(str);
				sbResult.append(repeatStr(fill,tempLength+suffix.length()));
			}
			else
			{//当字符串的长度大于length时			
				for(int i=0;i<str.length();i++)
				{
					strArray[i]=str.substring(i,i+1);

					sequence+=strArray[i].getBytes().length;

					if(sequence<=length)
						sbResult.append(strArray[i]);
					else
						break;
				}

				sbResult.append(suffix);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return sbResult.toString();
	}
}
