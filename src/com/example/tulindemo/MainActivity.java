package com.example.tulindemo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements HttpDataListener, OnClickListener {

	private HttpData httpData;
	private List<ListData> lists;
	private ListView lv;
	private EditText input;
	private Button send_btn;
	private String input_str;
	private TextAdapter adapter;
	private String[] welcomeArry;
	private double currentTime,oldTime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
	}

	@Override
	public void getDataUrl(String data) {
		// TODO Auto-generated method stub
		parseText(data);
	}

	public void initView() {
		lv = (ListView) findViewById(R.id.lv);
		input = (EditText) findViewById(R.id.editText);
		send_btn = (Button) findViewById(R.id.send_btn);
		lists = new ArrayList<ListData>();
		send_btn.setOnClickListener(this);
		adapter=new TextAdapter(lists, this);
		lv.setAdapter(adapter);
		ListData listData;
		listData = new ListData(getRandomWelcomeTicps(),ListData.RECEIVER,getTime());
		lists.add(listData);
	}
	public String getRandomWelcomeTicps(){
		String welcome_tips=null;
		welcomeArry=this.getResources().getStringArray(R.array.welcome_tips);
		int index=(int) (Math.random()*(welcomeArry.length-1));
		welcome_tips=welcomeArry[index];
		return welcome_tips;
		
	}
	public void parseText(String str) {

		try {
			JSONObject jb = new JSONObject(str);
			System.out.println(jb.get("code"));
			System.out.println(jb.get("text"));
			ListData listData;
			listData = new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		getTime();
		input_str=input.getText().toString();
		input.setText("");
		ListData listData;
		listData = new ListData(input_str,ListData.SEND,getTime());
		lists.add(listData);
		adapter.notifyDataSetChanged();
		
		try {
			String APIKEY = "6cc8bd0f44ba563c30ead32e6f8c668b";
			String INFO;
			INFO = URLEncoder.encode(input_str, "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
			httpData = (HttpData) new HttpData(getURL, this).execute();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getTime(){
		currentTime=System.currentTimeMillis();
		SimpleDateFormat format=new SimpleDateFormat("yyyyÄêMMÔÂddÈÕ HH:mm:ss");
		Date curData=new Date();
		String str=format.format(curData);
		if (currentTime-oldTime>=5*60*1000) {
			oldTime=currentTime;
			return str;
		}else {
			return "";
		}
		
	}
}
