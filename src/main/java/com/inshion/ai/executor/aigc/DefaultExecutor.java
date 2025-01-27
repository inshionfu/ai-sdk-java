package com.inshion.ai.executor.aigc;

import com.alibaba.fastjson.JSON;
import com.inshion.ai.IOpenAiApi;
import com.inshion.ai.executor.Executor;
import com.inshion.ai.executor.result.ResultHandler;
import com.inshion.ai.model.ChatCompletionRequest;
import com.inshion.ai.model.ChatCompletionResponse;
import com.inshion.ai.model.ChatCompletionSyncResponse;
import com.inshion.ai.model.Constants;
import com.inshion.ai.session.Configuration;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName GLMExecutor
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2025/1/3 17:10
 * @Version
 */

@Slf4j
public class DefaultExecutor implements Executor, ResultHandler {

    private final Configuration configuration;

    private final EventSource.Factory factory;

    private IOpenAiApi openAiApi;

    private OkHttpClient okHttpClient;

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
        this.factory = configuration.createRequestFactory();
        this.openAiApi = configuration.getOpenAiApi();
        this.okHttpClient = configuration.getOkHttpClient();
    }

    @Override
    public CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception {
        // 执行异步任务并获取结果
        CompletableFuture<String> future = new CompletableFuture<>();
        StringBuffer buffer = new StringBuffer();
        Request request = new Request.Builder()
                .url(configuration.getBaseUrl().concat(IOpenAiApi.v4_completions))
                .post(RequestBody.create(MediaType.parse(Constants.APPLICATION_JSON), chatCompletionRequest.toString()))
                .build();
        factory.newEventSource(request, new EventSourceListener() {

            @Override
            public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                if ("[DONE]".equals(data)) {
                    log.info("[输出结束] Tokens {}", JSON.toJSONString(data));
                    return;
                }

                ChatCompletionResponse response = JSON.parseObject(data, ChatCompletionResponse.class);
                log.info("测试结果 {}", JSON.toJSONString(response));
                List<ChatCompletionResponse.Choice> choices = response.getChoices();
                for (ChatCompletionResponse.Choice choice : choices) {
                    if (!StringUtils.equals("stop", choice.getFinishReason())) {
                        buffer.append(choice.getDelta().getContent());
                    }
                }
            }

            @Override
            public void onClosed(EventSource eventSource) {
                future.complete(buffer.toString());
            }

            @Override
            public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                log.error("error: ", t);
                future.completeExceptionally(new RuntimeException("Request closed before completion"));
            }
        });
        return future;
    }

    @Override
    public ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception {
        return null;
    }

    @Override
    public EventSourceListener eventSourceListener(EventSourceListener eventSourceListener) {
        return null;
    }
}
