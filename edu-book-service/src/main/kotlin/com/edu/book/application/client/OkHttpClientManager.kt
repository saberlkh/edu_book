package com.edu.book.application.client

import com.alibaba.fastjson.TypeReference
import okhttp3.RequestBody


/**
 * Author: xuhaihan
 * Date : 2019/07/29
 * Description : OkHttpClient
 */
interface OkHttpClientManager {

    /**
     * get方法
     * @param baseUrl : 服务器域名
     * @param apiUrl : 服务器接口
     * @param headParamMap : 头参数
     * @param urlParamMap : url参数
     * @return  String : 返回结果
     */
    operator fun <T> get(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                         urlParamMap: Map<String, Any>, type: TypeReference<T>): T?

    /**
     * post方法
     * @param baseUrl : 服务器域名
     * @param apiUrl : 服务器接口
     * @param headParamMap : 头参数
     * @param urlParamMap : url参数
     * @param requestBody : 请求体内容
     * @return
     */
    fun <T> post(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                 urlParamMap: Map<String, Any>, requestBody: RequestBody, type: TypeReference<T>): T?

    /**
     * post方法
     * @param baseUrl : 服务器域名
     * @param apiUrl : 服务器接口
     * @param headParamMap : 头参数
     * @param urlParamMap : url参数
     * @param jsonBody : json字符串
     * @return
     */
    fun <T> postJson(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                     urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T?


    /**
     * formUrlEncoded方法
     * @param baseUrl : 服务器域名
     * @param apiUrl : 服务器接口
     * @param headParamMap : 头参数
     * @param urlParamMap : url参数
     * @param jsonBody : json字符串
     * @return
     */
    fun <T> postFormUrlEncoded(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                               urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T?

    /**
     * postByUrlEncoded方法
     *
     * @param baseUrl      : 服务器域名
     * @param apiUrl       : 服务器接口
     * @param headParamMap : 头参数
     * @param urlParamMap  : url参数
     * @param jsonBody     : json字符串
     * @return
     */
    fun <T> postByUrlEncoded(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,

                             urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T?

    /**
     * post方法
     * @param baseUrl : 服务器域名
     * @param apiUrl : 服务器接口
     * @param headParamMap : 头参数
     * @param urlParamMap : url参数
     * @param requestBody : 请求体内容
     * @return
     */
    fun <T> delete(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                   urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T?

}
