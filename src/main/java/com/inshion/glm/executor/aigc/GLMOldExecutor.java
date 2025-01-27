package com.inshion.glm.executor.aigc;

import com.inshion.glm.executor.Executor;
import com.inshion.glm.model.ChatCompletionRequest;
import com.inshion.glm.model.ChatCompletionSyncResponse;
import com.inshion.glm.session.Configuration;
import okhttp3.sse.EventSource;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName GLMOldExecutor
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2025/1/3 17:07
 * @Version
 */

public class GLMOldExecutor implements Executor {

    private final Configuration configuration;

    private final EventSource.Factory factory;

    public GLMOldExecutor(Configuration configuration) {
        this.configuration = configuration;
        this.factory = configuration.createRequestFactory();
    }

    @Override
    public CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception {
        return null;
    }

    @Override
    public ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception {
        return null;
    }
}
