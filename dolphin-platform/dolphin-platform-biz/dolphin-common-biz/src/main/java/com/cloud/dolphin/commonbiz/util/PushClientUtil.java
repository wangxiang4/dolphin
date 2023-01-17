package com.cloud.dolphin.commonbiz.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *<p>
 * 友盟推送客户端
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @since: 2022/11/15
 */
public class PushClientUtil {

    private OkHttpClient okHttpClient;

	protected final String USER_AGENT = "Mozilla/5.0";

	protected static final String host = "http://msg.umeng.com";

	protected static final String postPath = "/api/send";

    public static final String uAppKey = "******";

    public static final String uAppMasterSecret = "******";

	public static final String uMessageSecret = "******";

    public static final String uPushUserAliasType = "DOLPHIN_UID";

    public PushClientUtil(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
    }

    public boolean send(JSONObject jsonObject) throws Exception {

        // 配置消息推送参数
        jsonObject.put("appkey", uAppKey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		jsonObject.put("timestamp", timestamp);

		// 发送url处理
        String url = host + postPath;
        String postBody = jsonObject.toString();
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + uMessageSecret).getBytes("utf8"));
        url = url + "?sign=" + sign;

        // 发送post请求并获得响应
        okhttp3.Request request = new okhttp3.Request
                .Builder()
                .url(url)
                .addHeader("User-Agent", USER_AGENT)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody))
                .build();

        Response response = okHttpClient.newCall(request).execute();
        int status = response.code();
        System.out.println("Response Code : " + status);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.body().byteStream()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
        if (status == 200) {
            System.out.println("Notification sent successfully.");
        } else {
            System.out.println("Failed to send the notification!");
        }
        return true;
    }


}
