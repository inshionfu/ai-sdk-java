package com.inshion.ai.session;

import com.inshion.ai.IOpenAiApi;
import com.inshion.ai.executor.Executor;
import com.inshion.ai.executor.aigc.DefaultExecutor;
import com.inshion.ai.model.Constants;
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
    private String baseUrl;

    @Getter
    @Setter
    private String apiKey;

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

    private HashMap<String, Executor> executorGroup;

    public HashMap<String, Executor> newExecutorGroup() {
        this.executorGroup = new HashMap<>();
        // TODO 旧版模型，待兼容
        Executor oldExecutor = new DefaultExecutor(this);
        this.executorGroup.put(Constants.Model.CHATGLM_6B_SSE.getCode(), oldExecutor);
        this.executorGroup.put(Constants.Model.CHATGLM_LITE.getCode(), oldExecutor);
        this.executorGroup.put(Constants.Model.CHATGLM_LITE_32K.getCode(), oldExecutor);
        this.executorGroup.put(Constants.Model.CHATGLM_STD.getCode(), oldExecutor);
        this.executorGroup.put(Constants.Model.CHATGLM_PRO.getCode(), oldExecutor);
        this.executorGroup.put(Constants.Model.CHATGLM_TURBO.getCode(), oldExecutor);
        // 新版模型，配置
        Executor defaultExecutor = new DefaultExecutor(this);
        this.executorGroup.put(Constants.Model.GLM_3_5_TURBO.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4V.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_Plus.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_0520.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_Lng.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_AirX.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_Air.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_FlashX.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_Flash.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.GLM_4_AllTools.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.COGVIEW_3.getCode(), defaultExecutor);
        // DeepSeek
        this.executorGroup.put(Constants.Model.DeepSeek_R1.getCode(), defaultExecutor);
        this.executorGroup.put(Constants.Model.DeepSeek_V3.getCode(), defaultExecutor);
        return this.executorGroup;
    }
}
