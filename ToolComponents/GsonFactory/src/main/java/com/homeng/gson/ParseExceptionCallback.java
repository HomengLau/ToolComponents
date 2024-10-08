package com.homeng.gson;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/GsonFactory
 *    time   : 2021/02/02
 *    desc   : Json 解析异常回调类
 */
public interface ParseExceptionCallback {

    /**
     * 对象类型解析异常
     *
     * @param typeToken             类型 Token
     * @param fieldName             字段名称（可能为空）
     * @param jsonToken             后台给定的类型
     */
    void onParseObjectException(TypeToken<?> typeToken, String fieldName, JsonToken jsonToken);

    /**
     * List item 类型解析异常
     *
     * @param typeToken             类型 Token
     * @param fieldName             字段名称（可能为空）
     * @param listItemJsonToken     List 条目类型（可能为空）
     */
    void onParseListItemException(TypeToken<?> typeToken, String fieldName, JsonToken listItemJsonToken);

    /**
     * Map item 类型解析异常
     *
     * @param typeToken             类型 Token
     * @param fieldName             字段名称（可能为空）
     * @param mapItemKey            Map 集合中的 key 值，如果等于为 "null" 字符串，则证明后端返回了错误类型的 key 过来
     * @param mapItemJsonToken      Map 条目类型（可能为空）
     */
    void onParseMapItemException(TypeToken<?> typeToken, String fieldName, String mapItemKey, JsonToken mapItemJsonToken);
}