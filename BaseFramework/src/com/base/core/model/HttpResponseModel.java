package com.base.core.model;

import com.google.gson.Gson;

public class HttpResponseModel
{
	public String responseCode;
	public String responseMessage;

	public HttpResponseModel()
	{
		responseCode="";
		responseMessage="";
	}

	public byte[] toByteArray()
	{
		try
		{
			String json=toJson();
			return json.getBytes("UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			byte[] b=new byte[0];
			return b;
		}
	}

	public String toJson()
	{
		Gson gson=new Gson();
		return gson.toJson(this);
	}
}
