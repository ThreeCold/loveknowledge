package com.love.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SelectTag extends SimpleTagSupport {
	private String id;
	private String name;
	private Object items;
	private Object selectedValue;
	private String textName;
	private String valueName;
	private String attr;
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public Object getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(Object selectedValue) {
		this.selectedValue = selectedValue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getItems() {
		return items;
	}
	public void setItems(Object items) {
		this.items = items;
	}
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out=this.getJspContext().getOut();
		out.print("<select id='");
		out.print(id);
		out.print("' name='");
		out.print(name);
		out.print("' ");
		if(attr!=null){
			out.print(attr);
		}
		out.println(">");
		Iterable iterable;
		if(items.getClass().isArray()){
			iterable=CommonUtils.toList(items);
		}else if(items instanceof Iterable){
			iterable=(Iterable) items;
		}else{
			throw new IllegalArgumentException("items必须是数组或实现Iterable接口");
		}
		for(Object item:iterable){
			Object textValue=getPropertyValue(item, textName);
			Object valueValue=getPropertyValue(item, valueName);
			out.print("<option value='");
			out.print(valueValue);
			out.print("' ");
			if(selectedValue!=null&&selectedValue.equals(valueValue)){
				out.print("selected");
			}
			out.print(">");
			out.print(textValue);
			out.println("</option>");
			
		}
		out.print("</select>");
	}
	private Object getPropertyValue(Object item,String propertyName) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(item.getClass());
			PropertyDescriptor[] pds=beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd:pds){
				if(pd.getName().equals(propertyName)){
				      return pd.getReadMethod().invoke(item);
					
				}
			}
			throw new IllegalArgumentException("无法找到属性"+propertyName+"的值");
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		
	}
}
