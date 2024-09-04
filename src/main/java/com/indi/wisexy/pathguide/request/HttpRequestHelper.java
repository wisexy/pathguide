package com.indi.wisexy.pathguide.request;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpRequestHelper {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestHelper.class);

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private volatile static okhttp3.OkHttpClient client;

    /**
     * 单例模式(双重检查模式) 获取类实例
     *
     * @return client
     */
    private static okhttp3.OkHttpClient getInstance() {
        if (client == null) {
            synchronized (okhttp3.OkHttpClient.class) {
                if (client == null) {
                    client = new okhttp3.OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .connectionPool(new ConnectionPool(10, 30,
                                    TimeUnit.MINUTES))
                            .build();
                }
            }
        }
        return client;
    }

    public static String syncPost(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = HttpRequestHelper.getInstance().newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                log.info("syncPost response = {}, responseBody= {}", response, result);
                return result;
            }
            String result = response.body().string();
            log.info("syncPost response = {}, responseBody= {}", response, result);
            throw new IOException("三方接口返回http状态码为" + response.code());
        } catch (Exception e) {
            log.error("syncPost() url:{} have a exception", url, e);
            throw new RuntimeException("syncPost() have a exception {}" + e.getMessage());
        }
    }

    public static String syncGet(String url, Map<String, Object> headParamsMap) throws IOException {
        Request request;
        final Request.Builder builder = new Request.Builder().url(url);
        try {
            if (!CollectionUtils.isEmpty(headParamsMap)) {
                for (Map.Entry<String, Object> entry : headParamsMap.entrySet()) {
                    builder.addHeader(entry.getKey(), (String) entry.getValue());
                }
            }
            request = builder.build();
            Response response = HttpRequestHelper.getInstance().newCall(request).execute();
            String result = response.body().string();
            log.info("syncGet response = {},responseBody= {}", response, result);
            if (!response.isSuccessful()) {
                throw new IOException("三方接口返回http状态码为" + response.code());
            }
            return result;
        } catch (Exception e) {
            log.error("remote interface url:{} have a exception", url, e);
            throw new RuntimeException("三方接口返回异常");
        }
    }

}
