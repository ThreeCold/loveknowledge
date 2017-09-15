package com.love.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {
     public static List toList(Object values){
    	 if(!values.getClass().isArray()){
    		 return null;
    	 }
    	 List<Object> list=new ArrayList<Object>();
    	 for(int i=0;i<Array.getLength(values);i++){
    		 Object obj=Array.get(values, i);
    		 list.add(obj);
    	 }
    	 return list;
     }
     
 	public static boolean isEmail(String email)
 	{
 		if (isNullOrWhiteSpace(email))
 		{
 			return false;
 		}
 		Pattern p = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");// 复杂匹配
 		Matcher m = p.matcher(email);
 		return m.matches();
 	}

 	public static boolean isPhoneNum(String phoneNum)
 	{
 		if (isNullOrWhiteSpace(phoneNum))
 		{
 			return false;
 		}
 		Pattern p = Pattern.compile("^1(\\d{10})$");// 复杂匹配
 		Matcher m = p.matcher(phoneNum);
 		return m.matches();
 	}


 	private static boolean isNullOrWhiteSpace(String str) {
		if(str==null){
			return true;
		}
		return str.trim().length()==0;
	}

	public static Date parseDate(String s)
 	{
 		try
 		{
 			return new SimpleDateFormat("yyyy-MM-dd").parse(s);
 		} catch (ParseException e)
 		{
 			throw new IllegalArgumentException("日期格式错误：" + s, e);
 		}
 	}
	
	public static String DateToStr(Date date) {
		  
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   String str = format.format(date);
		   return str;
		}


 	public static String urlEncodeUTF8(String str)
 	{
 		try
 		{
 			return URLEncoder.encode(str, "UTF-8");
 		} catch (UnsupportedEncodingException e)
 		{
 			throw new RuntimeException(e);
 		}
 	}
 	
 	public static Gson getGson(){
 		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
 		return gson;
 	}
 	
 	public final static String calcMD5(String s)
 	{
 	    char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
 	    try
 	    {
 	        byte[] btInput = s.getBytes();
 	        // 获得MD5摘要算法的 MessageDigest 对象
 	        MessageDigest mdInst = MessageDigest.getInstance("MD5");
 	        // 使用指定的字节更新摘要
 	        mdInst.update(btInput);
 	        // 获得密文
 	        byte[] md = mdInst.digest();
 	        // 把密文转换成十六进制的字符串形式
 	        int j = md.length;
 	        char str[] = new char[j * 2];
 	        int k = 0;
 	        for (int i = 0; i < j; i++)
 	        {
 	            byte byte0 = md[i];
 	            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
 	            str[k++] = hexDigits[byte0 & 0xf];
 	        }
 	        return new String(str);
 	    } catch (Exception e)
 	    {
 	        e.printStackTrace();
 	        return null;
 	    }
 	}
 	 
 	public final static String calcMD5(InputStream inStream)
 	{
 	    MessageDigest digest = null;
 	    byte buffer[] = new byte[1024];
 	    int len;
 	    try
 	    {
 	        digest = MessageDigest.getInstance("MD5");
 	        while ((len = inStream.read(buffer, 0, 1024)) != -1)
 	        {
 	            digest.update(buffer, 0, len);
 	        }
 	    } catch (NoSuchAlgorithmException | IOException e)
 	    {
 	        throw new RuntimeException(e);
 	    }
 	    BigInteger bigInt = new BigInteger(1, digest.digest());
 	    return bigInt.toString(16);
 	}
 	
 	public static long[] toLongArray(String[] strs){
 		long[] nums=new long[strs.length];
 		for(int i=0;i<strs.length;i++){
 			nums[i]=Long.parseLong(strs[i]);
 		}
 		return nums;
 	}
 	
 	public static boolean hasEmpty(Object...  objs){
 		String str=null;
 		for(int i=0;i<objs.length;i++){
 			str=(String) objs[i];
 			if(str==null||str.trim().length()==0){
 				return true;
 			}
 		}
 		return false;
 		
 	}
 	
 	public static void sendJson(HttpServletResponse response,AjaxResult result) throws IOException{
 		response.setContentType("application/json;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");
 		Gson gson=getGson();
 		response.getWriter().print(gson.toJson(result));
 	}
 	
 	
     
}
