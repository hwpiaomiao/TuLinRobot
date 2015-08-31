package com.example.tulindemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class HttpData extends AsyncTask<String, Void, String> {

	private URL url;
	private HttpURLConnection connection;
	private InputStream in;
	private String urlS;
	private HttpDataListener httpDataListener;


	public HttpData(String url,HttpDataListener httpDataListener) {
		this.urlS=url;
		this.httpDataListener=httpDataListener;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			url = new URL(urlS);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			in=connection.getInputStream();
			// 取得输入流，并使用Reader读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			return sb.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		httpDataListener.getDataUrl(result);
		super.onPostExecute(result);
	}
}
