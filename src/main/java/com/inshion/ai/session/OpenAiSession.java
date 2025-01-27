package com.inshion.ai.session;

import com.inshion.ai.model.ChatCompletionRequest;
import com.inshion.ai.model.ChatCompletionSyncResponse;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import java.util.concurrent.CompletableFuture;

/**
 *@ClassName OpenAiSession
 *@Description  
 *@Author kojikoji 1310402980@qq.com
 *@Date 2025/1/3 16:52
 *@Version 
 */

public interface OpenAiSession {

    CompletableFuture<String> completions(ChatCompletionRequest chatCompletionRequest) throws Exception;

    ChatCompletionSyncResponse completionSync(ChatCompletionRequest chatCompletionRequest) throws Exception;

    EventSource completions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws Exception;

    Configuration configuration();
}
