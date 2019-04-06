/*
 * ************************************************************
 * 文件：FormatUtils.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:42:55
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.utils;

import android.text.TextUtils;

import com.cody.http.cat.db.data.ItemMonitorData;
import com.google.gson.JsonParser;

import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.cody.http.cat.db.data.HttpHeader;

/**
 * Created by xu.yi. on 2019/4/5.
 * FormatUtils
 */
public class FormatUtils {

    private static final SimpleDateFormat TIME_SHORT = new SimpleDateFormat("HH:mm:ss SSS", Locale.CHINA);

    private static final SimpleDateFormat TIME_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINA);

    private static String formatData(Date date, SimpleDateFormat format) {
        if (date == null) {
            return "";
        }
        return format.format(date);
    }

    public static String getDateFormatShort(Date date) {
        return formatData(date, TIME_SHORT);
    }

    public static String getDateFormatLong(Date date) {
        return formatData(date, TIME_LONG);
    }

    public static String formatBytes(long bytes) {
        return FormatUtils.formatByteCount(bytes, true);
    }

    private static String formatByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format(Locale.US, "%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String formatHeaders(List<HttpHeader> httpHeaders, boolean withMarkup) {
        StringBuilder out = new StringBuilder();
        if (httpHeaders != null) {
            for (HttpHeader header : httpHeaders) {
                out.append((withMarkup) ? "<b>" : "")
                        .append(header.getName())
                        .append(": ")
                        .append((withMarkup) ? "</b>" : "")
                        .append(header.getValue())
                        .append((withMarkup) ? "<br />" : "\n");
            }
        }
        return out.toString();
    }

    public static String formatBody(String body, String contentType) {
        if (contentType != null && contentType.toLowerCase().contains("json")) {
            return FormatUtils.formatJson(body);
        } else if (contentType != null && contentType.toLowerCase().contains("xml")) {
            return FormatUtils.formatXml(body);
        } else {
            return body;
        }
    }

    private static String formatJson(String json) {
        try {
            return JsonConverter.getInstance().toJson(new JsonParser().parse(json));
        } catch (Exception e) {
            return json;
        }
    }

    private static String formatXml(String xml) {
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());
            serializer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (Exception e) {
            return xml;
        }
    }

    public static String getShareText(ItemMonitorData itemMonitorData) {
        String text = "";
        text += "Url" + ": " + check(itemMonitorData.getUrl()) + "\n";
        text += "Method" + ": " + check(itemMonitorData.getMethod()) + "\n";
        text += "Protocol" + ": " + check(itemMonitorData.getProtocol()) + "\n";
        text += "Status" + ": " + check(itemMonitorData.getStatus().toString()) + "\n";
        text += "Response" + ": " + check(itemMonitorData.getResponseSummaryText()) + "\n";
        text += "SSL" + ": " + itemMonitorData.isSsl() + "\n";
        text += "\n";
        text += "Request Time" + ": " + FormatUtils.getDateFormatLong(itemMonitorData.getRequestDate()) + "\n";
        text += "Response Time" + ": " + FormatUtils.getDateFormatLong(itemMonitorData.getResponseDate()) + "\n";
        text += "Duration" + ": " + check(itemMonitorData.getDurationFormat()) + "\n";
        text += "\n";
        text += "Request Size" + ": " + FormatUtils.formatBytes(itemMonitorData.getRequestContentLength()) + "\n";
        text += "Response Size" + ": " + FormatUtils.formatBytes(itemMonitorData.getResponseContentLength()) + "\n";
        text += "Total Size" + ": " + check(itemMonitorData.getTotalSizeString()) + "\n";
        text += "\n";
        text += "---------- " + "Request" + " ----------\n\n";
        String headers = itemMonitorData.getRequestHeadersString(false);
        if (!TextUtils.isEmpty(headers)) {
            text += headers + "\n";
        }
        text += (itemMonitorData.isRequestBodyIsPlainText()) ? check(itemMonitorData.getFormattedRequestBody()) :
                "(encoded or binary body omitted)";
        text += "\n";
        text += "---------- " + "Response" + " ----------\n\n";
        headers = itemMonitorData.getResponseHeadersString(false);
        if (!TextUtils.isEmpty(headers)) {
            text += headers + "\n";
        }
        text += (itemMonitorData.isResponseBodyIsPlainText()) ? check(itemMonitorData.getFormattedResponseBody()) :
                "(encoded or binary body omitted)";
        return text;
    }

    private static String check(String string) {
        return (string != null) ? string : "";
    }

}