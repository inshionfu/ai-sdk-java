package com.inshion.glm.executor;

import com.inshion.glm.model.ChatCompletionRequest;
import com.inshion.glm.model.ChatCompletionSyncResponse;

import java.util.concurrent.CompletableFuture;

public interface Executor {

    CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception;

    ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception;
}
