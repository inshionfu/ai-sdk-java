package com.inshion.ai.test;


import com.alibaba.fastjson.JSON;
import com.inshion.ai.model.*;
import com.inshion.ai.session.Configuration;
import com.inshion.ai.session.OpenAiSession;
import com.inshion.ai.session.OpenAiSessionFactory;
import com.inshion.ai.session.defaults.DefaultOpenAiSessionFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class ApiTest {

    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        Configuration configuration = new Configuration();
//        configuration.setBaseUrl(Constants.BaseUrl.Doubao.getUrl());
        configuration.setBaseUrl(Constants.BaseUrl.DeepSeek.getUrl());
        configuration.setApiKey("sk-368e70846c9e442abc58dd0552b03092"); // deepseek
//        configuration.setApiKey("8e896d03-a8ef-4682-af89-ad68b952ea8f");
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
        request.setModel(Constants.Model.DeepSeek_V3.getCode());
//        request.setModel("ep-20250114151947-75mlc"); // doubao
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Constants.Role.user.getCode())
                        .content("四川大学在哪里")
                        .build());
            }
        });

        CompletableFuture<String> future = openAiSession.completions(request);
        String response = future.get();

        log.info("测试结果：{}", response);
    }

    @Test
    public void test_completions_sync_01() throws Exception {
        // 入参；模型、请求信息
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setModel(Constants.Model.GLM_3_5_TURBO.getCode()); // chatGLM_6b_SSE、chatglm_lite、chatglm_lite_32k、chatglm_std、chatglm_pro
        request.setPrompt(new ArrayList<ChatCompletionRequest.Prompt>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Prompt.builder()
                        .role(Constants.Role.user.getCode())
                        .content("四川大学在哪里")
                        .build());
            }
        });

        // 24年1月发布的 glm-3-turbo、glm-4 支持函数、知识库、联网功能
        request.setTools(new ArrayList<ChatCompletionRequest.Tool>() {
            private static final long serialVersionUID = -7988151926241837899L;

            {
                add(ChatCompletionRequest.Tool.builder()
                        .type(ChatCompletionRequest.Tool.Type.web_search)
                        .webSearch(ChatCompletionRequest.Tool.WebSearch.builder().enable(true).searchQuery("小傅哥").build())
                        .build());
            }
        });

        ChatCompletionSyncResponse response = openAiSession.completionSync(request);

        log.info("测试结果：{}", JSON.toJSONString(response));
    }
}
