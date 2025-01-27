package com.inshion.ai.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constants {

    // http keywords
    public static final String SSE_CONTENT_TYPE = "text/event-stream";
    public static final String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)";
    public static final String APPLICATION_JSON = "application/json";
    public static final String JSON_CONTENT_TYPE = APPLICATION_JSON + "; charset=utf-8";

    @Getter
    @AllArgsConstructor
    public enum BaseUrl {
        Doubao("https://ark.cn-beijing.volces.com/api/v3/", "豆包"),
        DeepSeek("https://api.deepseek.com/", "deepseek"),
        Qwen("https://dashscope.aliyuncs.com/compatible-mode/v1/", "通义千问"),
        ;
        private String url;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum EventType {

        add("add", "增量"),
        finish("finish", "结束"),
        error("error", "错误"),
        interrupted("interrupted", "中断"),

        ;
        private final String code;
        private final String info;

    }

    @Getter
    @AllArgsConstructor
    public enum Model {
        /**
         * 默认模型
         */
        DEFAULT("default", "默认模型占位符，不存在匹配的执行器时使用"),
        /**
         * GLM old
         */
        @Deprecated
        CHATGLM_6B_SSE("chatGLM_6b_SSE", "ChatGLM-6B 测试模型"),
        @Deprecated
        CHATGLM_LITE("chatglm_lite", "轻量版模型，适用对推理速度和成本敏感的场景"),
        @Deprecated
        CHATGLM_LITE_32K("chatglm_lite_32k", "标准版模型，适用兼顾效果和成本的场景"),
        @Deprecated
        CHATGLM_STD("chatglm_std", "适用于对知识量、推理能力、创造力要求较高的场景"),
        @Deprecated
        CHATGLM_PRO("chatglm_pro", "适用于对知识量、推理能力、创造力要求较高的场景"),
        /**
         * 智谱AI 23年06月发布
         */
        CHATGLM_TURBO("chatglm_turbo", "适用于对知识量、推理能力、创造力要求较高的场景"),
        /**
         * 智谱AI 24年01月发布
         */
        GLM_3_5_TURBO("glm-3-turbo", "适用于对知识量、推理能力、创造力要求较高的场景"),
        GLM_4("glm-4", "适用于复杂的对话交互和深度内容创作设计的场景"),
        GLM_4V("glm-4v", "根据输入的自然语言指令和图像信息完成任务，推荐使用 SSE 或同步调用方式请求接口"),
        GLM_4_Plus("glm-4-plus", "高智能旗舰: 性能全面提升，长文本和复杂任务能力显著增强"),
        GLM_4_0520("glm-4-0520", "高智能模型：适用于处理高度复杂和多样化的任务"),
        GLM_4_Lng("glm-4-long", "超长输入：专为处理超长文本和记忆型任务设计"),
        GLM_4_AirX("glm-4-airx", "极速推理：具有超快的推理速度和强大的推理效果"),
        GLM_4_Air("glm-4-air", "高性价比：推理能力和价格之间最平衡的模型"),
        GLM_4_FlashX("glm-4-flashx", "高速低价：Flash增强版本，超快推理速度。"),
        GLM_4_Flash("glm-4-flash", "免费调用：智谱AI首个免费API，零成本调用大模型。"),
        GLM_4_AllTools("glm-4-alltools", "Agent模型：自主规划和执行复杂任务"),
        COGVIEW_3("cogview-3", "根据用户的文字描述生成图像,使用同步调用方式请求接口"),

        /**
         * DeepSeek
         */
        DeepSeek_V3("deepseek-chat", "deep-seek-v3"),
        DeepSeek_R1("deepseek-reasoner", "deep-seek-r1"),
        /**
         * Qwen
         */
        Qwen_Max("qwen-max", "通义千问2.5系列千亿级别超大规模语言模型，支持中文、英文等不同语言输入。随着模型的升级，qwen-max将滚动更新升级。如果希望使用固定版本，请使用历史快照版本。"),
        Qwen_Max_Latest("qwen-max-latest", "通义千问系列效果最好的模型，本模型是动态更新版本，模型更新不会提前通知，适合复杂、多步骤的任务，模型中英文综合能力显著提升，模型人类偏好显著提升，模型推理能力和复杂指令理解能力显著增强，困难任务上的表现更优，数学、代码能力显著提升，提升对Table、JSON等结构化数据的理解和生成能力。"),
        Qwen_Max_2024_09_19("qwen-max-2024-09-19", "通义千问系列效果最好的模型，适合复杂、多步骤的任务，模型中英文综合能力显著提升，模型人类偏好显著提升，模型推理能力和复杂指令理解能力显著增强，困难任务上的表现更优，数学、代码能力显著提升，提升对Table、JSON等结构化数据的理解和生成能力。"),
        Qwen_Plus("qwen-plus", "通义千问超大规模语言模型的增强版，支持中文英文等不同语言输入。"),
        Qwen_Plus_Latest("qwen-plus-latest", "通义千问系列能力均衡的模型，推理效果和速度介于通义千问-Max和通义千问-Turbo之间，适合中等复杂任务。本模型是动态更新版本，模型更新不会提前通知。"),
        Qwen_plus_1220("qwen-plus-1220", "通义千问系列能力均衡的模型，推理效果和速度介于通义千问-Max和通义千问-Turbo之间，适合中等复杂任务。相对于通义千问-Plus-1125版本，中英文整体能力有提升，中英常识知识类、阅读理解能力提升较为显著，codeswtich现象相比上一版有显著改善，中文指令遵循能力显著提升。"),
        Qwen_plus_2025_01_12("qwen-plus-0112", "通义千问系列能力均衡的模型，推理效果和速度介于通义千问-Max和通义千问-Turbo之间，适合中等复杂任务。相对于通义千问-Plus-2024-1220版本，中英文整体能力有提升，中英常识知识类、阅读理解能力提升较为显著，codeswtich现象相比上一版有显著改善，中文指令遵循能力显著提升。"),
        Qwen_Turbo("qwen-turbo", "通义千问超大规模语言模型，支持中文英文等不同语言输入。"),
        Qwen_Turbo_Latest("qwen-turbo-latest", "通义千问系列速度最快、成本很低的模型，适合简单任务。本模型是动态更新版本，模型更新不会提前通知，模型中英文综合能力显著提升，模型人类偏好显著提升，模型推理能力和复杂指令理解能力显著增强，困难任务上的表现更优，数学、代码能力显著提升。"),
        ;

        private final String code;
        private final String info;
    }

    @Getter
    @AllArgsConstructor
    public enum Role {
        /**
         * user 用户输入的内容，role位user
         */
        user("user"),
        /**
         * 模型生成的内容，role位assistant
         */
        assistant("assistant"),

        /**
         * 系统
         */
        system("system"),

        ;
        private final String code;

    }
}
