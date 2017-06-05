package com.cse.ksr.mbaf;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Signup extends AppCompatActivity {
    EditText username,mobile,pass,repass;
    Button singup;
    String uname,mobilenumber,password,repassword;
    ProgressDialog pd;
    Context context;
    String data;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        username = (EditText) findViewById(R.id.uname);
        mobile = (EditText) findViewById(R.id.mobile);
        pass = (EditText) findViewById(R.id.password);
        repass = (EditText) findViewById(R.id.repassword);
        singup=(Button)findViewById(R.id.signup);
        Bundle data=getIntent().getExtras();
        String ip=data.getString("ip");
        String port=data.getString("port");
        String folder=data.getString("folder");
        final String URL="http://"+ip+":"+port+"/"+folder+"/";
        context=this;
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=username.getText().toString().trim();
                mobilenumber=mobile.getText().toString().trim();
                password=pass.getText().toString().trim();
                repassword=repass.getText().toString().trim();
                if(uname.length()!=0&&mobilenumber.length()!=0&&password.length()!=0&&repassword.length()!=0) {
                    if(password.equals(repassword)){
                        new Signupprocess().execute(URL+"signup.php");
                    }
                    else {
                        Toast.makeText(Signup.this,"Enter the password correctly",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(Signup.this,"Please fill all the values",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class Signupprocess extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(context);
            pd.setCancelable(true);
            pd.setTitle("Signning up");
            pd.setMessage("Please Wait");
            pd.setIndeterminate(false);
            pd.show();
        }


        protected String doInBackground(String params[]) {
            try {
                HttpClient client=new DefaultHttpClient();
                HttpPost post=new HttpPost(params[0]);
                ArrayList list=new ArrayList();
                list.add(new BasicNameValuePair("username",uname));
                list.add(new BasicNameValuePair("mobile",mobilenumber));
                list.add(new BasicNameValuePair("password",password));
                list.add(new BasicNameValuePair("repassword",repassword));
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
            if(data==null)
            {
                Toast.makeText(Signup.this,"Cannot connect to server!",Toast.LENGTH_LONG).show();
            }
            Toast.makeText(Signup.this,""+data,Toast.LENGTH_LONG).show();
            pd.cancel();
        }
    }
}
