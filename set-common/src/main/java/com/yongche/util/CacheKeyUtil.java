package com.yongche.util;


import com.yongche.enumdata.CacheKeyEnum;

import java.text.MessageFormat;

/**
 * Created by mma on 17/1/18.
 */
public class CacheKeyUtil {

    /**
     * 动态key转字符串数值，替换占位符
     *
     * @param key
     *            rediskey
     * @param dynamicObject
     *            动态替换内容数组
     * @return 动态生成key
     */
    public static String dynamicFormat(String key, Object... dynamicObject) {
        if (dynamicObject == null || dynamicObject.length == 0) {
            return key;
        }
        for (int i = 0; i < dynamicObject.length; i++) {
            dynamicObject[i] = String.valueOf(dynamicObject[i]);
        }
        return MessageFormat.format(key, dynamicObject);
    }

    public static void main(String[] args) {
        System.out.println(CacheKeyEnum.LAST_ACCESS_TIME.toKey(123123));
    }
}
