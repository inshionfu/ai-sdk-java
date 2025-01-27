package com.inshion.ai.executor.result;

import okhttp3.sse.EventSourceListener;

public interface ResultHandler {
    EventSourceListener eventSourceListener(EventSourceListener eventSourceListener);
}
