package com.inshion.ai.session.defaults;

import com.inshion.ai.IOpenAiApi;
import com.inshion.ai.executor.Executor;
import com.inshion.ai.interceptor.OpenAiHTTPInterceptor;
import com.inshion.ai.session.Configuration;
import com.inshion.ai.session.OpenAiSession;
import com.inshion.ai.session.OpenAiSessionFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DefaultOpenAiSessionFactory
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2025/1/3 16:54
 * @Version
 */

public class DefaultOpenAiSessionFactory implements OpenAiSessionFactory {
    private final Configuration configuration;

    public DefaultOpenAiSessionFactory(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public OpenAiSession openSession() {
        // 1.日志配置
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(configuration.getLevel());

        // 2.开启Http客户端
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new OpenAiHTTPInterceptor(configuration))
                .connectTimeout(configuration.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(configuration.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(configuration.getReadTimeout(), TimeUnit.SECONDS)
                .build();
        configuration.setOkHttpClient(client);

        // 3.创建api服务
        IOpenAiApi openAiApi = new Retrofit.Builder()
                .baseUrl(configuration.getBaseUrl())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(IOpenAiApi.class);
        configuration.setOpenAiApi(openAiApi);

        // 4.实例化执行器
        HashMap<String, Executor> executorGroup = configuration.newExecutorGroup();
        return new DefaultOpenAiSession(configuration, executorGroup);
    }
}
