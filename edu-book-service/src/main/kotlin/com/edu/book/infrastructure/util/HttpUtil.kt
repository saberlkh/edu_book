package com.edu.book.infrastructure.util


import okhttp3.Request
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest

object HttpUtil {

	private val log = LoggerFactory.getLogger(HttpUtil::class.java)

	private const val AND = "&"
	private const val EQ = "="
	private const val CODE = "code"
	private const val MESSAGE = "message"
	private const val STATUS_CODE = "statusCode"

	enum class OKSupportMethodEnum(val methodOrder: Int, val methodName: String) {
		GET(0, "OkHttpClient Get Method"),
		POST(1, "OkHttpClient Post Method"),
		DELETE(2, "OkHttpClient Delete Method")
	}


	/**
	 * 添加okHttp客户端的header参数
	 * @param builder : 请求体
	 * @param headParamMap : 参数
	 */
	fun addOkHttpHeader(
		builder: Request.Builder,
		headParamMap: Map<String, String>?
	) {
		if (headParamMap != null && !headParamMap.isEmpty()) {
			for ((key, value) in headParamMap) {
				builder.addHeader(key, value)
			}
		}
	}

	/**
	 * 通过map装配url上的请求参数
	 * @param paramMap : 参数
	 * @return String : 参数字符串 例: a={a}&b={b}
	 */
	fun genUrlParam(paramMap: Map<String, Any>?): String {
		val urlParam = StringBuilder()
		if (paramMap != null && !paramMap.isEmpty()) {
			for ((key, value) in paramMap) {
				if (StringUtils.isNotEmpty(urlParam)) {
					urlParam.append(AND)
				}
				urlParam.append(key)
					.append(EQ)
					.append(value)
			}
		}
		return urlParam.toString()
	}

	/**
	 * 组装完整的url
	 * @param domain : 域名
	 * @param url : 请求url
	 * @param param 请求参数
	 * @return String : 完整的请url
	 * 例 /api/in/school/grades?1=1&cid={1}&uid={2}
	 */
	fun genTotalUrl(domain: String, url: String, param: String): String {
		assert(StringUtils.isNotEmpty(domain))
		assert(StringUtils.isNotEmpty(url))
		return if (StringUtils.isNotBlank(param)) {
			String.format("%s%s?1=1&%s", domain, url, param)
		} else String.format("%s%s", domain, url)
	}

	/**
	 * 从request获取真实用户IP
	 * @param request
	 */
	fun getIpAddr(request: HttpServletRequest): String {
		var ip: String? = request.getHeader("x-forwarded-for")
		if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.getHeader("Proxy-Client-IP")
		}
		if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.getHeader("X-Forwarded-For")
		}
		if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.getHeader("WL-Proxy-Client-IP")
		}
		if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.getHeader("X-Real-IP")
		}
		if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
			ip = request.remoteAddr
		}
		return ip.toString()
	}

	fun ipCheck(text: String?): Boolean {
		if (text.isNullOrBlank()) {
			return false
		}
		// 定义正则表达式
		val regex = ("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$")
		// 判断ip地址是否与正则表达式匹配
		return text.matches(Regex(regex))
	}

}