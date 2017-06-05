package com.cse.ksr.mbaf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;


public class Login extends AppCompatActivity {
    TextView signup;
    String data;
    EditText ip, port, folder, username, password;
    Button login;
    String ipaddress,portnumber,filefolder,uname,pass;
    String URL;
    String protocol="http://";
    String col=":";
    String slash="/";
    ProgressDialog pd;
    Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        signup = (TextView) findViewById(R.id.signup);
        ip = (EditText) findViewById(R.id.iptext);
        port = (EditText) findViewById(R.id.porttext);
        folder = (EditText) findViewById(R.id.folder);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        ip.setText("192.168.43.160");
        port.setText("80");
        folder.setText("test");
        context=this;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipaddress=ip.getText().toString().trim();
                portnumber=port.getText().toString().trim();
                filefolder=folder.getText().toString().trim();
                uname=username.getText().toString().trim();
                pass=password.getText().toString().trim();
                URL=protocol+ipaddress+col+portnumber+slash+filefolder+slash;
                String loginurl=URL+"login.php";
                if(URL.length()!=0&&uname.length()!=0&&pass.length()!=0&&portnumber.length()!=0&&filefolder.length()!=0) {
                    new Signin().execute(loginurl);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter all the details",Toast.LENGTH_LONG).show();
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(Login.this,Signup.class);
                signup.putExtra("ip",ip.getText().toString());
                signup.putExtra("port",port.getText().toString());
                signup.putExtra("folder",folder.getText().toString());
                startActivity(signup);
            }
        });
    }

    public class Signin extends AsyncTask<String,Void,String>{

        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(context);
            pd.setTitle("Logging in!");
            pd.setMessage("Please Wait");
            pd.setCancelable(true);
            pd.setIndeterminate(false);
            pd.show();
        }


        protected String doInBackground(String params[]) {
            try {
                HttpClient client=new DefaultHttpClient();
                HttpPost post=new HttpPost(params[0]);
                ArrayList list=new ArrayList();
                list.add(new BasicNameValuePair("username",uname));
                list.add(new BasicNameValuePair("password",pass));
                post.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse response=client.execute(post);
                int status=response.getStatusLine().getStatusCode();
                if(status==200){
                    HttpEntity entity=response.getEntity();
                    data= EntityUtils.toString(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(data!=null){
                int length=data.length();
                String succ=data.substring(0,7);
                String mob=data.substring(7,length);
                if(succ.equals("success"))
                {
                    Intent map= new Intent(Login.this,MapsActivity.class);
                    map.putExtra("url",protocol+ip.getText().toString().trim()+":"+port.getText().toString().trim()+"/"+folder.getText().toString().trim()+"/");
                    map.putExtra("mob",mob);
                    startActivity(map);
                }
                else{
                    Toast.makeText(Login.this,"Check username & password  or Register",Toast.LENGTH_LONG).show();
                }


            }
                        pd.cancel();
        }
    }
}
