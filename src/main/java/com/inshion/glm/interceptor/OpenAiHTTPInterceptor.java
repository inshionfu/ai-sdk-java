package com.inshion.glm.interceptor;

import com.inshion.glm.session.Configuration;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @ClassName OpenAiHTTPInterceptor
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2025/1/3 17:22
 * @Version
 */

public class OpenAiHTTPInterceptor implements Interceptor {

    private final Configuration configuration;

    public OpenAiHTTPInterceptor(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public @NotNull Response intercept(Chain chain) throws IOException {
        // 1.获取原始 Request
        Request original = chain.request();
        // 2.构建请求
        Request request = original.newBuilder()
                .url(original.url())
//                .header("Authorization", "Bearer " + BearerTokenUtils.getToken(configuration.getApiKey(), configuration.getApiSecret()))
                .header("Authorization", "Bearer " + configuration.getApiSecretKey())
                .header("Content-Type", Configuration.JSON_CONTENT_TYPE)
                .header("User-Agent", Configuration.DEFAULT_USER_AGENT)
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}
