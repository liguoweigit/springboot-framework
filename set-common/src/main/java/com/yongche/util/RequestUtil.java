package com.yongche.util;

import com.google.common.base.Charsets;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * by yongche.com
 *
 * @author mma
 * @since 2017-12-06 下午5:58
 */

public class RequestUtil {

    public static String getParam(final ServletRequest request, String name, String defaultValue) {
        String v = request.getParameter(name);
        return StringUtils.isEmpty(v) ? defaultValue : v;
    }

    public static int getParamInt(final ServletRequest request, String name, int defaultValue) {
        String v = request.getParameter(name);
        if (StringUtils.isEmpty(v)) {
            return defaultValue;
        }
        return NumberUtils.toInt(v);
    }

    public static long getParamLong(final ServletRequest request, String name, int defaultValue) {
        String v = request.getParameter(name);
        if (StringUtils.isEmpty(v)) {
            return defaultValue;
        }
        return NumberUtils.toLong(v);
    }

    /**
     * 参数为空验证，只要有一个为空，即为空
     * @param request
     * @param names
     * @return
     */
    public static boolean isEmpty(final ServletRequest request, String... names) {
        for (String name : names) {
            if (StringUtils.isEmpty(request.getParameter(name))) {
                return true;
            }
        }
        return false;
    }

    public static TreeMap<String, String> getParamterMap(final String param) {
        TreeMap<String, String> m = new TreeMap<String, String>();
        if (StringUtils.isEmpty(param))
            return m;
        String[] split = param.split("&");
        for (String s : split) {
            String[] ss = s.split("=");
            if (ss.length == 2) {
                m.put(ss[0], ss[1]);
            }
        }
        return m;
    }



    /**
     * 获取参数 ，按字典排序
     * @param request
     * @return
     */
    public static TreeMap<String, String> getParameterMap(final ServletRequest request) {
        TreeMap<String, String> m = new TreeMap<String, String>();
        // Arrays.toString(a)
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            m.put(key, request.getParameter(key));
        }
        return m;
    }

    public static String toQueryString(Map<String, ?> params) {
        if (MapUtils.isEmpty(params))
            return StringUtils.EMPTY;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.substring(1);
    }

    public static String getURL(final HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String query = request.getQueryString();
        if (query != null)
            url.append("?").append(query);
        return url.toString();

    }

    public static void setCsvHeader(String name, HttpServletRequest request, HttpServletResponse response) {
        setCsvHeader(name, request, response, Charsets.UTF_8.name());
    }

    /**
     * windows 设置gbk 直接以excel 格式打开不是乱码
     * @param name
     * @param request
     * @param response
     * @param encode
     */
    public static void setCsvHeader(String name, HttpServletRequest request, HttpServletResponse response, String encode) {
        try {
            request.setCharacterEncoding(encode);
            response.setCharacterEncoding(encode);
            response.setContentType("text/csv;charset=" + encode);
            if (request.getHeader("User-Agent").toUpperCase().indexOf("FIREFOX") > 0) {
                name = new String(name.getBytes(encode), "ISO8859-1");// firefox浏览器         
            } else {
                name = URLEncoder.encode(name, encode);
            }
            response.setHeader("content-disposition", "attachment;filename=" + name + ".csv");
        } catch (Exception e) {
        }
    }

    public static void setTextHead(String name, HttpServletRequest request, HttpServletResponse response, String encode) {
        try {
            request.setCharacterEncoding(encode);
            response.setCharacterEncoding(encode);
            response.setContentType("text;charset=" + encode);
            if (request.getHeader("User-Agent").toUpperCase().indexOf("FIREFOX") > 0) {
                name = new String(name.getBytes(encode), "ISO8859-1");
            } else {
                name = URLEncoder.encode(name, encode);
            }

            response.setHeader("Content-disposition", "attachment;filename=" + name + ".txt");
        } catch (Exception e) {
        }
    }

    public static void setExcelHeader(String name, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(Charsets.UTF_8.name());
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(Charsets.UTF_8.name());
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                name = URLEncoder.encode(name, Charsets.UTF_8.name());// IE浏览器
            } else {
                name = new String(name.getBytes(Charsets.UTF_8.name()), "ISO8859-1"); // firefox浏览器 及其他问题
            }
            response.setHeader("content-disposition", "attachment;filename=" + name);
        } catch (Exception e) {
        }
    }
}
