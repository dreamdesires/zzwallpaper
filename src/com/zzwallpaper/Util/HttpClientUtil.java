/**
 * Copyright 2013 Barfoo
 * 
 * All right reserved
 * 
 * Created on 2013-9-9 上午10:06:20
 * 
 * @author zxy
 */
package com.zzwallpaper.Util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpClientUtil {

	/**
	 * 参数说明：
	 * 
	 * @String url: 发送请求的链接地址、以字符串形式传入("https/http:www.xxx.com")
	 * @RequestParams params: 发送请求的参数 以数组的形式传入(RequestParams params = new RequestParams("xxx","xxx");,params.put("xxx", "xxx");)
	 * @AsyncHttpResponseHandler responseHandler: 用已有的固定的类方法(new JsonHttpResponseHandler()//JSON,new AsyncHttpResponseHandler()//String)
	 **/

	private static final String mHost = "http://image.baidu.com";

	private AsyncHttpClient httpClient = new AsyncHttpClient();

	private static HttpClientUtil httpClientUtil;

	public static HttpClientUtil getHttpClient() {
		if (httpClientUtil == null) {
			httpClientUtil = new HttpClientUtil();
			httpClientUtil.init();
		}
		return httpClientUtil;
	}

	private void init() {
		//httpClient.setTimeout(10000); //设置请求时间默认 10/S
	}

	/**有参数GET请求获取数据*/
	public void HTTP_GET(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		httpClient.get(getAbsoluteUrl(url), params, responseHandler);
	}

	/**无参数GET请求获取数据*/
	public void HTTP_GET(String url, AsyncHttpResponseHandler responseHandler) {
		httpClient.get(getAbsoluteUrl(url), responseHandler);
	}

	/**有参数POST请求获取数据*/
	public void HTTP_POST(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		httpClient.post(getAbsoluteUrl(url), params, responseHandler);
	}

	/**无参数POST请求获取数据*/
	public void HTTP_POST(String url, AsyncHttpResponseHandler responseHandler) {
		httpClient.post(getAbsoluteUrl(url), responseHandler);
	}

	/**验证HTTP*/
	private String getAbsoluteUrl(String url) {
		boolean matches = url.matches("[a-zA-z]+://[^\\s]*");
		if (matches) {
			return String.format("%s", url);
		} else {
			return String.format("%s%s", mHost, url);
		}
	}
}
