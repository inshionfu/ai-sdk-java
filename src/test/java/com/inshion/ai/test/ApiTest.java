package com.inshion.ai.test;


import com.alibaba.fastjson.JSON;
import com.inshion.ai.model.*;
import com.inshion.ai.session.Configuration;
import com.inshion.ai.session.OpenAiSession;
import com.inshion.ai.session.OpenAiSessionFactory;
import com.inshion.ai.session.defaults.DefaultOpenAiSessionFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ApiTest {

    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        Configuration configuration = new Configuration();
//        configuration.setBaseUrl(Constants.BaseUrl.Doubao.getUrl());
//        configuration.setBaseUrl(Constants.BaseUrl.DeepSeek.getUrl());
        configuration.setBaseUrl(Constants.BaseUrl.Qwen.getUrl());
//        configuration.setApiKey("sk-368e70846c9e442abc58dd0552b03092"); // deepseek
//        configuration.setApiKey("8e896d03-a8ef-4682-af89-ad68b952ea8f"); // doubao
        configuration.setApiKey("sk-29162448448d4e2aba9b1f801b317488"); // qwen
        configuration.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        // 3. 开启会话
        this.openAiSession = factory.openSession();
    }

    @Test
    public void test_completion_future() throws Exception {
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
//        request.setModel(Constants.Model.DeepSeek_V3.getCode());
//        request.setModel("ep-20250114151947-75mlc"); // doubao
        request.setModel(Constants.Model.Qwen_Max.getCode()); // qwen
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Constants.Role.user.getCode())
                        .content("先介绍你是什么大模型，再评价四川大学怎么样")
                        .build());
            }
        });

        CompletableFuture<String> future = openAiSession.completions(request);
        String response = future.get();

        log.info("测试结果：{}", response);
    }

    @Test
    public void test_completions_sync() throws Exception {
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
//        request.setModel(Constants.Model.DeepSeek_V3.getCode());
//        request.setModel("ep-20250114151947-75mlc"); // doubao
        request.setModel(Constants.Model.Qwen_Plus_Latest.getCode()); // doubao
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Constants.Role.user.getCode())
                        .content("先介绍你是什么大模型，再评价四川大学怎么样")
                        .build());
            }
        });

        ChatCompletionSyncResponse response = openAiSession.completionSync(request);

        log.info("测试结果：{}", JSON.toJSONString(response));
    }

    @Test
    public void test_completions_stream() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
//        request.setModel("ep-20250114151947-75mlc"); // GLM_3_5_TURBO、GLM_4
        request.setModel(Constants.Model.Qwen_plus_1220.getCode()); // GLM_3_5_TURBO、GLM_4
        request.setStream(true);
        request.setMessages(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                // content 对象格式
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Constants.Role.user.getCode())
                        .content(ChatCompletionRequest.Prompt.Content.builder()
                                .type(ChatCompletionRequest.Prompt.Content.Type.text.getCode())
                                .text("四川大学怎么样")
                                .build())
                        .build());
            }
        });

        openAiSession.completions(request, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                if ("[DONE]".equals(data)) {
                    log.info("[输出结束] Tokens {}", JSON.toJSONString(data));
                    return;
                }

                ChatCompletionResponse response = JSON.parseObject(data, ChatCompletionResponse.class);
                log.info("测试结果：{}", JSON.toJSONString(response));
            }

            @Override
            public void onClosed(EventSource eventSource) {
                log.info("对话完成");
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                log.error("对话失败", t);
                countDownLatch.countDown();
            }
        });

        // 等待
        countDownLatch.await();
    }
}
