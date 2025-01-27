package com.inshion.glm.session;

import com.inshion.glm.IOpenAiApi;
import com.inshion.glm.executor.Executor;
import com.inshion.glm.executor.aigc.GLMExecutor;
import com.inshion.glm.executor.aigc.GLMOldExecutor;
import com.inshion.glm.model.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;

import java.util.HashMap;

/**
 *@ClassName Configuration
 *@Description  
 *@Author kojikoji 1310402980@qq.com
 *@Date 2025/1/3 16:55
 *@Version 
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

    @Getter
    @Setter
    private String apiHost;

    @Getter
    private String apiSecretKey;

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
        String[] arrStr = apiSecretKey.split("\\.");
        if (arrStr.length != 2) {
            log.error("Invalid apiSecretKey");
        }

    }

    @Getter
    private String apiKey;
    @Getter
    private String apiSecret;

    @Setter
    @Getter
    private IOpenAiApi openAiApi;

    @Getter
    @Setter
    private OkHttpClient okHttpClient;


    public EventSource.Factory createRequestFactory() {
        return EventSources.createFactory(okHttpClient);
    }

    @Setter
    @Getter
    private HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;

    @Setter
    @Getter
    private long connectTimeout = 450;

    @Setter
    @Getter
    private long writeTimeout = 450;

    @Setter
    @Getter
    private long readTimeout = 450;

    private HashMap<Model, Executor> executorGroup;

    // http keywords
    public static final String SSE_CONTENT_TYPE = "text/event-stream";
    public static final String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)";
    public static final String APPLICATION_JSON = "application/json";
    public static final String JSON_CONTENT_TYPE = APPLICATION_JSON + "; charset=utf-8";

    public HashMap<Model, Executor> newExecutorGroup() {
        this.executorGroup = new HashMap<>();
        // 旧版模型，兼容
        Executor glmOldExecutor = new GLMOldExecutor(this);
        this.executorGroup.put(Model.CHATGLM_6B_SSE, glmOldExecutor);
        this.executorGroup.put(Model.CHATGLM_LITE, glmOldExecutor);
        this.executorGroup.put(Model.CHATGLM_LITE_32K, glmOldExecutor);
        this.executorGroup.put(Model.CHATGLM_STD, glmOldExecutor);
        this.executorGroup.put(Model.CHATGLM_PRO, glmOldExecutor);
        this.executorGroup.put(Model.CHATGLM_TURBO, glmOldExecutor);
        // 新版模型，配置
        Executor glmExecutor = new GLMExecutor(this);
        this.executorGroup.put(Model.GLM_3_5_TURBO, glmExecutor);
        this.executorGroup.put(Model.GLM_4, glmExecutor);
        this.executorGroup.put(Model.GLM_4V, glmExecutor);
        this.executorGroup.put(Model.GLM_4_Plus, glmExecutor);
        this.executorGroup.put(Model.GLM_4_0520, glmExecutor);
        this.executorGroup.put(Model.GLM_4_Lng, glmExecutor);
        this.executorGroup.put(Model.GLM_4_AirX, glmExecutor);
        this.executorGroup.put(Model.GLM_4_Air, glmExecutor);
        this.executorGroup.put(Model.GLM_4_FlashX, glmExecutor);
        this.executorGroup.put(Model.GLM_4_Flash, glmExecutor);
        this.executorGroup.put(Model.GLM_4_AllTools, glmExecutor);
        this.executorGroup.put(Model.COGVIEW_3, glmExecutor);
        // DeepSeek
        this.executorGroup.put(Model.DeepSeek_R1, glmExecutor);
        this.executorGroup.put(Model.DeepSeek_V3, glmExecutor);
        return this.executorGroup;
    }
}
