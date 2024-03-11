package com.edu.book.application.client.impl

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.edu.book.application.client.OkHttpClientManager
import com.edu.book.infrastructure.util.HttpUtil
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException


/**
 * Author ： Martin
 * Date : 17/11/28
 * Description : OkHttp客户端封装方法
 * Version : 1.0
 */
@Service(value = "okHttpClientManager")
class OkHttpClientManagerImpl : OkHttpClientManager {

    @Autowired
    private lateinit var client: OkHttpClient

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(OkHttpClientManagerImpl::class.java)
        private val JSON_TYPE = MediaType.parse("application/json;charset=utf-8")
        private val X_WWW_FORM_URLENCODED_TYPE = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8")
    }

    /**
     * 组装请求参数
     */
    private fun buildRequest(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                             urlParamMap: Map<String, Any>, method: HttpUtil.OKSupportMethodEnum,
                             requestBody: RequestBody?): Request? {
        val urlParamStr = HttpUtil.genUrlParam(urlParamMap)
        val url = HttpUtil.genTotalUrl(baseUrl, apiUrl, urlParamStr)
        val builder = Request.Builder().url(url)
        HttpUtil.addOkHttpHeader(builder, headParamMap)
        return if (method === HttpUtil.OKSupportMethodEnum.GET) {
            builder.build()
        } else if (method === HttpUtil.OKSupportMethodEnum.POST) {
            builder.post(requestBody!!).build()
        } else if (method === HttpUtil.OKSupportMethodEnum.DELETE) {
            builder.delete(requestBody).build()
        } else {
            null
        }
    }

    /**
     * 执行请求
     */
    private fun <T> executeRequest(request: Request, type: TypeReference<T>): T? {
        try {
            client.newCall(request).execute().use { response ->
                val result = response.body()!!.string()
                log.info("请求地址:{}, 响应结果：{}", request.url(), result)
                return JSON.parseObject(result, type)
            }
        } catch (e: IOException) {
            log.error("http请求读写异常,请求为 {}", e)
            return null
        }

    }

    /**
     * 执行请求
     */
    private fun <T> executeRequest(request: Request, type: Class<T>): T? {
        try {
            client.newCall(request).execute().use { response ->
                val result = response.body()!!.string()
                log.info("请求地址:{}, 响应结果：{}", request.url(), result)
                return JSONObject.parseObject(result, type)
            }
        } catch (e: IOException) {
            log.error("http请求读写异常,请求为 {}", e)
            return null
        }

    }

    override operator fun <T> get(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                                  urlParamMap: Map<String, Any>, type: TypeReference<T>): T? {
        val result = buildRequest(baseUrl, apiUrl, headParamMap,
                urlParamMap, HttpUtil.OKSupportMethodEnum.GET, null)
        return executeRequest(result!!, type)
    }

    override fun <T> post(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                          urlParamMap: Map<String, Any>, requestBody: RequestBody, type: TypeReference<T>): T? {
        val result = buildRequest(baseUrl, apiUrl, headParamMap,
                urlParamMap, HttpUtil.OKSupportMethodEnum.POST, requestBody)
        return executeRequest(result!!, type)
    }

    override fun <T> postJson(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                              urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T? {
        val requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(jsonBody))
        val result = buildRequest(baseUrl, apiUrl, headParamMap,
                urlParamMap, HttpUtil.OKSupportMethodEnum.POST, requestBody)
        return executeRequest(result!!, type)
    }

    override fun <T> postFormUrlEncoded(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                                        urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T? {
        val requestBody = RequestBody.create(X_WWW_FORM_URLENCODED_TYPE, HttpUtil.genUrlParam(jsonBody))
        val result = buildRequest(baseUrl, apiUrl, headParamMap,
                urlParamMap, HttpUtil.OKSupportMethodEnum.POST, requestBody)
        return executeRequest(result!!, type)
    }

    override fun <T> postByUrlEncoded(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                                      urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T? {
        val formBodyBuilder = FormBody.Builder()
        for ((key, value) in jsonBody) {
            formBodyBuilder.add(key, value as String)
        }
        val urlParamStr = HttpUtil.genUrlParam(urlParamMap)
        val url = HttpUtil.genTotalUrl(baseUrl, apiUrl, urlParamStr)
        val requestBuilder = Request.Builder()
        for ((key, value) in headParamMap) {
            requestBuilder.header(key, value)
        }
        val result = requestBuilder.url(url).post(formBodyBuilder.build()).build()
        return executeRequest(result, type)
    }

    override fun <T> delete(baseUrl: String, apiUrl: String, headParamMap: Map<String, String>,
                            urlParamMap: Map<String, Any>, jsonBody: Map<String, Any>, type: TypeReference<T>): T? {
        val requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(jsonBody))
        val result = buildRequest(baseUrl, apiUrl, headParamMap,
                urlParamMap, HttpUtil.OKSupportMethodEnum.DELETE, requestBody)
        return executeRequest(result!!, type)
    }
}
