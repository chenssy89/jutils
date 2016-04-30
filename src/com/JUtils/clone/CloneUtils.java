package com.JUtils.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

/**
 * 克隆工具类，进行深克隆,包括对象、集合
 * @Project:JUtils
 * @file:CloneUtils.java
 * @Author:chenssy
 * @data:2014年8月9日
 */
public class CloneUtils {

	/**
	 * 采用对象的序列化完成对象的深克隆
	 * @autor:chenssy
	 * @data:2014年8月9日
	 *
	 * @param obj
	 * 			待克隆的对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T cloneObject(T obj) {
		T cloneObj = null;
		try {
			// 写入字节流
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream obs = new ObjectOutputStream(out);
			obs.writeObject(obj);
			obs.close();

			// 分配内存，写入原始对象，生成新对象
			ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(ios);
			// 返回生成的新对象
			cloneObj = (T) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloneObj;
	}
	
	/**
	 * 利用序列化完成集合的深克隆
	 * @autor:chenssy
	 * @data:2014年8月9日
	 *
	 * @param collection
	 * 					待克隆的集合
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> cloneCollection(Collection<T> collection) throws ClassNotFoundException, IOException{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(collection);
	    out.close();
	  
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    Collection<T> dest = (Collection<T>) in.readObject();  
	    in.close();
	    
	    return dest;  
	}
}
