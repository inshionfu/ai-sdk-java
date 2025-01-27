package com.inshion.ai.executor;

import com.inshion.ai.model.ChatCompletionRequest;
import com.inshion.ai.model.ChatCompletionSyncResponse;

import java.util.concurrent.CompletableFuture;

public interface Executor {

    CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception;

    ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception;
}
