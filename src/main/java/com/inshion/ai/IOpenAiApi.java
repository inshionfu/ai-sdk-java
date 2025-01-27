package com.inshion.ai;

import com.inshion.ai.model.ChatCompletionRequest;
import com.inshion.ai.model.ChatCompletionResponse;
import com.inshion.ai.model.ChatCompletionSyncResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @ClassName IOpenAiApi
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2024/11/14 17:42
 * @Version
 */

public interface IOpenAiApi {

    @Deprecated
    String v3_completions = "api/paas/v3/model-api/{model}/sse-invoke";
    @Deprecated
    String v3_completions_sync = "api/paas/v3/model-api/{model}/invoke";

    String v4_completions = "chat/completions";

    @Deprecated
    @POST(v3_completions)
    Single<ChatCompletionResponse> completions(@Path("model") String model, @Body ChatCompletionRequest chatCompletionRequest);

    @Deprecated
    @POST(v3_completions_sync)
    Single<ChatCompletionSyncResponse> completions(@Body ChatCompletionRequest chatCompletionRequest);
}
