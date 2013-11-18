package com.example.smsquickform;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.smsquickform.model.Sms;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SavedSMS extends Activity {
	public static String phoneNumber="085646201002";
	DB datasource;
	ListView lv;
	MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_sms);
		datasource = new DB(this);
		List<Sms> values = datasource.findAllSMS();
		adapter = new MyAdapter(this, R.layout.list_sms, values);
		lv = (ListView)findViewById(R.id.listSms);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				adapter.setSelectedIndex(position);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	private class MyAdapter extends ArrayAdapter<Sms>{
		private int selectedIndex;
		private Context context;
		private int selectedColor = Color.parseColor("#abcdef");
		private List<Sms> myList;
		
		public MyAdapter(Context context, int resource, 
				List<Sms> objects) {
			super(context, resource,  objects);
			myList = objects;
			this.context = context;
		}
		public void setSelectedIndex(int selectedIndex) {
			this.selectedIndex = selectedIndex;
		}
		public int getSelectedIndex(){
			return selectedIndex;
		}
		public int getCount() {
			return myList.size();
		}

		@Override
		public Sms getItem(int position) {
			return myList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		public void replaceItems(List<Sms> list) {
			myList = list;
			notifyDataSetChanged();
		}
		public List<Sms> getEntireList() {
			return myList;
		}
		public View getView(int position, View convertView, ViewGroup parent){
			View vi = convertView;
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.list_item, null);
			TextView tv = (TextView)vi.findViewById(R.id.lstContent);
			Sms jj = myList.get(position);
			if(position==getSelectedIndex()){
				vi.setBackgroundColor(selectedColor);
			}
			tv.setText(jj.getValue());
			return vi;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_sm, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_sendsms:
			SmsManager smsManager = SmsManager.getDefault();
			Sms message = adapter.getItem(adapter.getSelectedIndex());
			smsManager.sendTextMessage(phoneNumber, null, message.getValue(), null, null);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
