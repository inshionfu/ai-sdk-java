package com.inshion.ai.session.defaults;

import com.inshion.ai.executor.Executor;
import com.inshion.ai.model.ChatCompletionRequest;
import com.inshion.ai.model.ChatCompletionSyncResponse;
import com.inshion.ai.model.Constants;
import com.inshion.ai.session.Configuration;
import com.inshion.ai.session.OpenAiSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName OpenAiSession
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2025/1/3 17:38
 * @Version
 */
@Slf4j
public class DefaultOpenAiSession implements OpenAiSession {

    private final Configuration configuration;

    private final Map<String, Executor> executorGroup;

    public DefaultOpenAiSession(Configuration configuration, Map<String, Executor> executorGroup) {
        this.configuration = configuration;
        this.executorGroup = executorGroup;
    }

    @Override
    public CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception {
        Executor executor = executorGroup.getOrDefault(chatCompletionRequest.getModel(), executorGroup.get(Constants.Model.DEFAULT.getCode()));
        if (Objects.isNull(executor)) {
            throw new RuntimeException(chatCompletionRequest.getModel() + " 模型执行器尚未实现");
        }
        return executor.completions(chatCompletionRequest);
    }

    @Override
    public ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception {
        Executor executor = executorGroup.getOrDefault(chatCompletionRequest.getModel(), executorGroup.get(Constants.Model.DEFAULT.getCode()));
        if (Objects.isNull(executor)) {
            throw new RuntimeException(chatCompletionRequest.getModel() + " 模型执行器尚未实现");
        }
        return executor.completionSync(chatCompletionRequest);
    }
}
