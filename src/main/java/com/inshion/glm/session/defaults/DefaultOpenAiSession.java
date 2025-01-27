package com.inshion.glm.session.defaults;

import com.inshion.glm.executor.Executor;
import com.inshion.glm.model.ChatCompletionRequest;
import com.inshion.glm.model.ChatCompletionSyncResponse;
import com.inshion.glm.model.Model;
import com.inshion.glm.session.Configuration;
import com.inshion.glm.session.OpenAiSession;
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

    private final Map<Model, Executor> executorGroup;

    public DefaultOpenAiSession(Configuration configuration, Map<Model, Executor> executorGroup) {
        this.configuration = configuration;
        this.executorGroup = executorGroup;
    }

    @Override
    public CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception {
        Executor executor = executorGroup.get(chatCompletionRequest.getModel());
        if (Objects.isNull(executor)) {
            throw new RuntimeException(chatCompletionRequest.getModel() + " 模型执行器尚未实现");
        }
        return executor.completions(chatCompletionRequest);
    }

    @Override
    public ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception {
        Executor executor = executorGroup.get(chatCompletionRequest.getModel());
        if (Objects.isNull(executor)) {
            throw new RuntimeException(chatCompletionRequest.getModel() + " 模型执行器尚未实现");
        }
        return executor.completionSync(chatCompletionRequest);
    }
}
