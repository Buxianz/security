package com.rbi.security.tool;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static final int MAX_RETRY_TIMES = 3;

	/**
	 * http post请求 json格式
	 *
	 * @param strURL
	 * @param params
	 * @return
	 */
	public static String doPost(String strURL, Map<String, String> params) {
		int WAIT_INTEVAL = 10000;
		String result = "";
		for (int i = 0; i < MAX_RETRY_TIMES; i++) {
			String param = JSON.toJSON(params).toString();
			try {
				result = doPost(strURL, param);
				if (StringUtils.isNotBlank(result)) {
					return result;
				} else if (StringUtils.isBlank(result)) {
					throw new Exception();
				}
			} catch (Exception e) {
				logger.error("httpPost请求失败,url={}", strURL, e);
				if (i < MAX_RETRY_TIMES - 1) {
					try {
						Thread.sleep(WAIT_INTEVAL);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					WAIT_INTEVAL += 30000;
				}
			}

		}
		return null;
	}

	/**
	 * http post请求 json格式
	 *
	 * @param strURL
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String strURL, String param) throws IOException {
		logger.info("post请求url: {}" + strURL);
		URL url = new URL(strURL);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json;charset=UTF-8"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 设置发送数据的格式
		connection.connect();
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		out.append(param);
		out.flush();
		out.close();
		// 读取响应
		int code=connection.getResponseCode();
		// 读取响应
		if (code==200) {
			InputStream inputStream=connection.getInputStream();
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			String line;
			StringBuffer buffer=new StringBuffer();
			while ((line=bufferedReader.readLine())!=null) {
				buffer.append(line);

			}
			bufferedReader.close();
			return buffer.toString();
		}
		return "";
	}
	/**
	 *
	 * @param strURL
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String strURL) throws IOException {
		URL url = new URL(strURL);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestMethod("GET");
		connection.connect();
		int code=connection.getResponseCode();
		// 读取响应
		if (code==200) {
			InputStream inputStream=connection.getInputStream();
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			String line;
			StringBuffer buffer=new StringBuffer();
			while ((line=bufferedReader.readLine())!=null) {
				buffer.append(line);
			}
			/**
			 *
			 */
			bufferedReader.close();
			return buffer.toString();
		}
		return "";
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currentPage", "1");
		params.put("pageSize", "10");
		params.put("companyId", "1");
		params.put("name", "孙汝琴");
		/*params.put("appid", "20161219001");
		params.put("id", "320882198108282628");
		params.put("name", "孙汝琴");
		params.put("merchantname", "guiyanghuoche");
		params.put("merchantpew", "guiyanghuoche");*/
		// RatingResult result = new RatingResult();
		// result.setApplyId("123");
		System.out.println(JSON.toJSON(params).toString());
		// System.out.println(JSON.toJSON(result).toString());
		System.out.println("********************************************************");
		/*System.out.println(
				HttpUtil.doGet("http://localhost:8080/highway-management/common/config/cacheMapVehiclePassger?vehicleOriginalType=" + URLEncoder.encode("小型轿车", "UTF-8")));*/
		System.out.println(HttpUtil.doPost("http://localhost:8080/expressway-administration/BayonetManager/paingQueryBayonet",params));
	}
}