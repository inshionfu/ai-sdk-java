package com.inshion.ai.executor;

import com.inshion.ai.model.ChatCompletionRequest;
import com.inshion.ai.model.ChatCompletionSyncResponse;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import java.util.concurrent.CompletableFuture;

public interface Executor {

    CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception;

    ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception;

    EventSource completions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws Exception;
}
