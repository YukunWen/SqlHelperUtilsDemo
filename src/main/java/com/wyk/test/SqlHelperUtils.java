package com.wyk.test;

import lombok.Data;
import org.apache.commons.collections.MapUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyk on 2018/11/8
 */
public class SqlHelperUtils {
    /**
     * 传入相应的查询结果和欲想顺序进行顺序重排
     *
     * @param result 传入的查询结果
     * @param resultClass 传入查询结果的类
     * @param order 传入的顺序List
     * @param orderClass 传入的顺序List的类型
     * @param orderName 传入的要排序的字段名
     * @param <resultClass>
     * @param <orderClass>
     * @return
     * @throws Exception
     */
    public static <resultClass, orderClass> List<resultClass> changeResultOrder(Object result, Class<?> resultClass,
                                                                                Object order, Class<?> orderClass, String orderName) {
        List<resultClass> returnList = new ArrayList<>();
        Map<orderClass, resultClass> tempMap = new HashMap<>();

        for (resultClass aoo : (List<resultClass>) result) {
            if (resultClass.isAssignableFrom(Map.class)) {
                Object b = MapUtils.getObject((Map) aoo, orderName);
                orderClass cb = (orderClass) b;
                if (orderClass.isAssignableFrom(Long.class)) {
                    cb = (orderClass) Long.valueOf(b + "");
                }
                tempMap.put(cb, aoo);
            } else {
                try {
                    Method method = aoo.getClass().getMethod("get" + toUpperFirstChar(orderName));
                    orderClass b = (orderClass) method.invoke(aoo);
                    tempMap.put(b, aoo);
                }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
                    throw new RuntimeException("调用sqlHelpUtils出错，反射获取方法有误",e);
                }
            }
        }

        for (orderClass boo : (List<orderClass>) order) {
            resultClass getResult = tempMap.get(boo);
            if (null != getResult){
                returnList.add(getResult);
            }
        }
        return returnList;
    }

    private static String toUpperFirstChar(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }

    public static void main(String[] args) {
        System.out.println("testList");
        testList();

        System.out.println("testMapLongId");
        testMapLongId();

        System.out.println("testMapIntegerId");
        testMapIntegerId();

    }

    private static void testMapIntegerId() {
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1);
        map1.put("name", "第一个");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2);
        map2.put("name", "第二个");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 3);
        map3.put("name", "第三个");
        Map<String, Object> map4 = new HashMap<>();
        map4.put("id", 4);
        map4.put("name", "第四个");
        Map<String, Object> map5 = new HashMap<>();
        map5.put("id", 5);
        map5.put("name", "第五个");
        Map<String, Object> map6 = new HashMap<>();
        map6.put("id", 6);
        map6.put("name", "第六个");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);

        List<Integer> uids = new ArrayList<Integer>() {{
            this.add(3);
            this.add(1);
            this.add(5);
            this.add(2);
            this.add(4);
            this.add(14);
        }};

        List<Map<String,Object>> c = null;

        try {
            c = changeResultOrder(list,Map.class,uids,Integer.class,"id");
            c.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testMapLongId() {
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1);
        map1.put("name", "第一个");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2);
        map2.put("name", "第二个");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 3);
        map3.put("name", "第三个");
        Map<String, Object> map4 = new HashMap<>();
        map4.put("id", 4);
        map4.put("name", "第四个");
        Map<String, Object> map5 = new HashMap<>();
        map5.put("id", 5);
        map5.put("name", "第五个");
        Map<String, Object> map6 = new HashMap<>();
        map6.put("id", 6);
        map6.put("name", "第六个");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);

        List<Long> uids = new ArrayList<Long>() {{
            this.add(6L);
            this.add(3L);
            this.add(2L);
            this.add(4L);
            this.add(17L);
        }};

        List<Map<String,Object>> c = null;

        try {
            c = changeResultOrder(list,Map.class,uids,Long.class,"id");
            c.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testList() {
        TestBO testBO1 = new TestBO();
        TestBO testBO2 = new TestBO();
        TestBO testBO3 = new TestBO();
        TestBO testBO4 = new TestBO();
        TestBO testBO5 = new TestBO();
        TestBO testBO6 = new TestBO();
        TestBO testBO7 = new TestBO();
        testBO1.setId(1L);
        testBO1.setName("我是第一个");
        testBO2.setId(2L);
        testBO2.setName("我是第二个");
        testBO3.setId(3L);
        testBO3.setName("我是第三个");
        testBO4.setId(4L);
        testBO4.setName("我是第四个");
        testBO5.setId(5L);
        testBO5.setName("我是第五个");

        testBO6.setId(7L);
        testBO6.setName("我是第六个");
        testBO7.setId(6L);
        testBO7.setName("我是第七个");


        List<TestBO> testBOS = new ArrayList<>(5);
        testBOS.add(testBO1);
        testBOS.add(testBO2);
        testBOS.add(testBO3);
        testBOS.add(testBO4);
        testBOS.add(testBO5);
        testBOS.add(testBO6);
        testBOS.add(testBO7);

        List<Long> ids = new ArrayList<Long>() {
            {
                this.add(5L);
                this.add(4L);
                this.add(2L);
                this.add(1L);
                this.add(6L);
                this.add(7L);
                this.add(16L);
            }
        };
        List<TestBO> d = null;
        try {
            d = changeResultOrder(testBOS, TestBO.class, ids, Long.class, "id");
            d.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Data
    public static class TestBO {

        private Long id;

        private String name;
    }
}
