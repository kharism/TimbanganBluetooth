package com.example.smsquickform;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ngohung.form.HBaseFormActivity;
import com.ngohung.form.el.HDatePickerElement;
import com.ngohung.form.el.HElement;
import com.ngohung.form.el.HNumericElement;
import com.ngohung.form.el.HPickerElement;
import com.ngohung.form.el.HRootElement;
import com.ngohung.form.el.HSection;
import com.ngohung.form.el.HTextAreaEntryElement;
import com.ngohung.form.el.listener.HValueChangedListener;

public class MainActivity extends HBaseFormActivity {
	private MainActivity activity;
	private List<Runnable> readThreads;
	private ArrayList<BluetoothDevice> devices;
	private boolean exitOnDisconect = true;
	private UUID mDeviceUUID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i("HBaseFormActivity", "Demo started!!" );
		Intent intent = getIntent();
		readThreads = new ArrayList<Runnable>();
		Bundle b = intent.getExtras();
		mDeviceUUID = UUID.fromString(b.getString(Homescreen.DEVICE_UUID));
		devices = b.getParcelableArrayList(Homescreen.DEVICES_LISTS);
		this.setInstructions("* fields are mandatory");
		activity = this;
		IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter3);
		for(int i=0;i<devices.size();i++){
			new ConnectBT(devices.get(i)).execute();
		}
	}
	private final BroadcastReceiver mReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
	        String action = arg1.getAction();
			final BluetoothDevice device = arg1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	        if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
	        	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	        	builder.setMessage("device "+device.getName()+" disconected");
	        	AlertDialog dialog = builder.create();
	        	dialog.show();
	        }
		}
		
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_demo, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		 Log.i("MENU",String.valueOf(item.getItemId())+" "+String.valueOf(R.id.saveForm));
		 
		 switch (item.getItemId()) {
		 	case R.id.validateAllFields:
		 	
		 		for(int i=readThreads.size()-1;i>=0;i--){
					ReadInput u = (ReadInput)readThreads.get(i);
					u.stop();
					readThreads.remove(u);
				}
		 		//Toast.makeText(this, this.rootElement.getSections().get(0).getElements().get(0).getValue(), Toast.LENGTH_SHORT).show();
		 		//this.displayFormErrorMsg("Nama Ibu", this.rootElement.getSections().get(0).getElements().get(0).getValue());
		 		/*if(!this.checkFormData())
		 			this.displayFormErrorMsg("Error", "There are errors in the form");
		 		else
		 			this.displayFormErrorMsg("Success", "There are no errors in the form");*/
		 			
		 	return true;
		 	
		 	case R.id.viewSaved:
				Toast.makeText(getApplicationContext(), "buka halaman sms", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(getApplicationContext(),SavedSMS.class);
				startActivity(i);
				return true;
		 	default:
		 		return super.onOptionsItemSelected(item);
		 }		 
		 
	}
	public HRootElement getRootElement(){
		return this.rootElement;
	}
	public HElement findElementByKey(String key){
		if(key == null)
			return null;
		
		int numOfItems = formAdapter.getCount();
		for(int position = 0; position < numOfItems; position++){
			HElement el = (HElement) formAdapter.getItem(position);
			if(el.getKey()!=null && el.getKey().equals(key))
			{
				return el;
			}
		}
		return null;
	}
	public View findViewByKey(String key){
		if(key == null)
			return null;
		
		int numOfItems = formAdapter.getCount();
		for(int position = 0; position < numOfItems; position++){
			HElement el = (HElement) formAdapter.getItem(position);
			View validationView = this.formViews.get(position);
			if(el.getKey()!=null && el.getKey().equals(key))
			{
				return validationView;
			}
		}
		return null;
	}
	@Override
	protected HRootElement createRootElement() {
		// TODO Auto-generated method stub
		
		ArrayList<HSection> sections = new ArrayList<HSection>();
		HSection ibu = new HSection("Ibu");
		HTextAreaEntryElement namaIbu = new HTextAreaEntryElement("nama_ibu", "Nama Ibu", "Mmasukan Nama Ibu", true);
		namaIbu.addValueChangedListener(new HValueChangedListener() {
			
			@Override
			public void onValueChanged(HElement el) {
				// TODO Auto-generated method stub
				HElement e = activity.getRootElement().getSections().get(1).getElements().get(0);
				e.setValue(activity.getRootElement().getSections().get(0).getElements().get(0).getValue());
				//activity.refreshAndValidateView(e.getKey());
				
			}
		});
		//namaIbu.setKeyboardType(InputType.TYPE_CLASS_NUMBER);
		HTextAreaEntryElement nikIbu = new HTextAreaEntryElement("NIK_ibu", "NIK", "NIK Ibu", true);
		nikIbu.setKeyboardType(InputType.TYPE_CLASS_NUMBER);
		HTextAreaEntryElement kkIbu = new HTextAreaEntryElement("NO_KK", "No KK", "No KK Ibu", true);
		kkIbu.setKeyboardType(InputType.TYPE_CLASS_NUMBER);
		ibu.addEl(namaIbu);
		ibu.addEl(nikIbu);
		ibu.addEl(kkIbu);
		
		HSection anak = new HSection("Anak");
		anak.addEl(new HTextAreaEntryElement("nama_anak", "Nama Anak", "Masukan nama anak", true));
		anak.addEl(new HTextAreaEntryElement("NIK_anak", "NIK Anak", "Masukan NIK ANAK", true));
		anak.addEl(new HPickerElement("kelamin_anak", "Jenis Kelamin", "Pilih Jenis kelamin", true, "Laki-laki|Perempuan"));
		anak.addEl(new HDatePickerElement("tgl_lahir", "Tanggal Lahir", "Masukan tanggal lahir", true) );
		anak.addEl(new HTextAreaEntryElement("berat_anak", "Berat Anak", "Masukan Berat Anak", true));
		HDatePickerElement tgl = new HDatePickerElement("tgl_ambil", "Tanggal Ambil", "", false);
		tgl.setDateValue(new Date());
		tgl.setHidden(true);
		anak.addEl(tgl);
		sections.add(ibu);
		sections.add(anak);
		HRootElement rootEl = new HRootElement("Simple Form" , sections);
		return rootEl;
	}
	private class ReadInput implements Runnable {

		private boolean bStop = false;
		private Thread t;
		BluetoothSocket mBTSocket;
		Map<String, String> maps;
		StringHandler sh;
		public ReadInput(BluetoothSocket sock) {
			mBTSocket = sock;
			sh = new StringHandler();
			maps = new HashMap<String, String>();
			maps.put("BB", "berat_anak");
			t = new Thread(this, "Input Thread");
			t.start();
		}

		public boolean isRunning() {
			return t.isAlive();
		}

		@Override
		public void run() {
			InputStream inputStream;

			try {
				inputStream = mBTSocket.getInputStream();
				while (!bStop) {
					byte[] buffer = new byte[256];
					if (inputStream.available() > 0) {
						inputStream.read(buffer);
						int i = 0;
						/*
						 * This is needed because new String(buffer) is taking the entire buffer i.e. 256 chars on Android 2.3.4 http://stackoverflow.com/a/8843462/1287554
						 */
						for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
						}
						final String strInput = new String(buffer, 0, i);

						/*
						 * If checked then receive text, better design would probably be to stop thread if unchecked and free resources, but this is a quick fix
						 */
						//TODO:Olah bacaan
						String[] lines=strInput.split("\r\n");
						int g=0;
						try{
							if(lines[g].isEmpty()|| !maps.containsKey(lines[g].split(" ")[0])){
							g++;
						}
						String pp = new String(lines[g].split(" ")[0]);
						String curr = maps.get(pp);
						
						String j = sh.Handle(strInput);
						if(!j.isEmpty()){
							HElement el = activity.findElementByKey(curr);
							el.setValue(j);
							activity.refreshAndValidateView(el.getKey());
						}
						}
						catch(Exception ex){
							
						}

					}
					Thread.sleep(500);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void stop() {
			bStop = true;
			try {
				mBTSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		for(int i=readThreads.size()-1;i>=0;i--){
			ReadInput u = (ReadInput)readThreads.get(i);
			u.stop();
			readThreads.remove(u);
			this.unregisterReceiver(mReceiver);
		}
		super.onBackPressed();
	}
	@SuppressLint("NewApi")
	private class ConnectBT extends AsyncTask<Void, Void, Void> {
		private boolean mConnectSuccessful = true;
		BluetoothSocket mBTSocket;
		BluetoothDevice mDevice;
		String mes;
		private boolean mIsBluetoothConnected = false;
		Runnable mReadThread;
		public ConnectBT(BluetoothDevice dev){
			super();
			mDevice = dev;
		}
		@Override
		protected void onPreExecute() {
			//progressDialog = ProgressDialog.show(IbuActivity.this, "Hold on", "Connecting");// http://stackoverflow.com/a/11130220/1287554
		}
		
		@Override
		protected Void doInBackground(Void... devices) {

			try {
				if (mBTSocket == null) {
					Method m = mDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
					mBTSocket = (BluetoothSocket) m.invoke(mDevice, 1);
					BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
					mBTSocket.connect();
				}
			} catch (IOException e) {
				// Unable to connect to device
				e.printStackTrace();
				mes = e.getMessage();
				mConnectSuccessful = false;
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mes = e.getMessage();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mes = e.getMessage();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mes = e.getMessage();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mes = e.getMessage();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (!mConnectSuccessful) {
				Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();
				finish();
			} else {
				mIsBluetoothConnected = true;
				mReadThread = new ReadInput(mBTSocket); // Kick off input reader
				readThreads.add(mReadThread);
			}

			//progressDialog.dismiss();
		}

	}
	private class DisConnectBT extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {
			for(int i=readThreads.size();i>=0;i--){
				ReadInput r = (ReadInput) readThreads.get(i);
				r.stop();
				while (r.isRunning())
					;
				readThreads.remove(r);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			//mIsBluetoothConnected = false;
			if (exitOnDisconect) {
				finish();
			}
		}

	}
}
