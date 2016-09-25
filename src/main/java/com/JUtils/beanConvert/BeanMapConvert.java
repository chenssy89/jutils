package com.JUtils.beanConvert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean与Map的转换
 *
 * @author chenssy
 * @date 2016-09-24
 * @since 1.0.0
 */
public class BeanMapConvert {
    /**
     * Bean转换为Map
     *
     * @param object
     * @return String-Object的HashMap
     *
     * @author chenssy
     * @date 2016-09-25
     * @since v1.0.0
     */
    public static Map<String,Object> bean2MapObject(Object object){
        if(object == null){
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(object);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }

        return map;
    }

    /**
     * Map转换为Java Bean
     *
     * @param map
     *              待转换的Map
     * @param object
     *              Java Bean
     * @return java.lang.Object
     *
     * @author chenssy
     * @date 2016-09-25
     * @since v1.0.0
     */
    public static Object map2Bean(Map map,Object object){
        if(map == null || object == null){
            return null;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(object, value);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}
