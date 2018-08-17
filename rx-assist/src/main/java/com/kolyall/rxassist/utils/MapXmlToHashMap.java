package com.kolyall.rxassist.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by User on 26.04.2017.
 */

public class MapXmlToHashMap implements Func1<String, Map<String, String>> {
    @Override
    public Map<String, String> call(String body) {
        Map<String, String> responseFields = new HashMap<>();
        responseFields.put("ordernumber", "");
        responseFields.put("billnumber", "");
        responseFields.put("testmode", "");
        responseFields.put("ordercomment", "");
        responseFields.put("orderamount", "");
        responseFields.put("ordercurrency", "");
        responseFields.put("amount", "");
        responseFields.put("currency", "");
        responseFields.put("rate", "");
        responseFields.put("firstname", "");
        responseFields.put("lastname", "");
        responseFields.put("middlename", "");
        responseFields.put("email", "");
        responseFields.put("ipaddress", "");
        responseFields.put("meantypename", "");
        responseFields.put("meansubtype", "");
        responseFields.put("meannumber", "");
        responseFields.put("cardholder", "");
        responseFields.put("cardexpirationdate", "");
        responseFields.put("issuebank", "");
        responseFields.put("bankcountry", "");
        responseFields.put("orderdate", "");
        responseFields.put("orderstate", "");
        responseFields.put("responsecode", "");
        responseFields.put("message", "");
        responseFields.put("customermessage", "");
        responseFields.put("recommendation", "");
        responseFields.put("approvalcode", "");
        responseFields.put("protocoltypename", "");
        responseFields.put("processingname", "");
        responseFields.put("operationtype", "");
        responseFields.put("packetdate", "");
        responseFields.put("signature", "");
        responseFields.put("pareq", "");
        responseFields.put("ascurl", "");

        responseFields.put("faultcode", "");
        responseFields.put("faultstring", "");

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new ByteArrayInputStream(body.getBytes()), null);

            int eventType = parser.getEventType();

            String currentTag = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    currentTag = parser.getName();
                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = "";
                } else if (eventType == XmlPullParser.TEXT) {
                    if (responseFields.containsKey(currentTag)) {
                        responseFields.put(currentTag, parser.getText());
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return responseFields;
    }
}
