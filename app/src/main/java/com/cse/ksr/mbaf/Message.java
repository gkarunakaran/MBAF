package com.cse.ksr.mbaf;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class Message extends AppCompatActivity {
    TextView message;
    ProgressDialog pd;
    String data;
    String getmessage;
    protected void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.message);
        message=(TextView)findViewById(R.id.message);
        Bundle data=getIntent().getExtras();
        String url=data.getString("url");
        new Messages().execute(url+"messages.php");
    }

    public class Messages extends AsyncTask<String,Void,String>{

            protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Message.this);
            pd.setCancelable(true);
            pd.setTitle("Loading");
            pd.setMessage("Please Wait");
            pd.setIndeterminate(false);
            pd.show();
        }


        protected String doInBackground(String params[]) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(params[0]);
                HttpResponse response = client.execute(post);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                }
                try{
                    JSONObject jobject=new JSONObject(data);
                    JSONArray jarray=jobject.getJSONArray("messages");
                    for(int i=0;i<jarray.length();i++){
                        JSONObject jobj=jarray.getJSONObject(i);
                        getmessage=jobj.getString("Message");
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            message.setText(""+getmessage);
            pd.cancel();
        }
    }
}
