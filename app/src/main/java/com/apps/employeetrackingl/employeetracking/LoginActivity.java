package com.apps.employeetrackingl.employeetracking;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {
    Button Login,show_password;
    EditText user_mail,user_password;
    String s_mail,s_password;

    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject,jobject1;
    RequestParams params;


    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        client = new AsyncHttpClient();
        params = new RequestParams();

        user_mail = findViewById(R.id.editText_user_mail);
        user_password = findViewById(R.id.editText_password);
        Login = findViewById(R.id.button_log_in);
        show_password = findViewById(R.id.button_show_password);


        SharedPreferences shared = getApplicationContext().getSharedPreferences("Pref", MODE_PRIVATE);
        editor = shared.edit();

        show_password.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        user_password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }
        });

        user_mail.setText("monisha.sics@gmail.com");
        user_password.setText("monisha123");







      Login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

                  /* s_mail = user_mail.getText().toString();
             s_password = user_password.getText().toString();*/
// Hardcoded
              params.put("username","monisha.sics@gmail.com");
              params.put("password","monisha123");

              client.get("http://srishti-systems.info/projects/ticketbooking/api/emp_login.php?",params,new AsyncHttpResponseHandler(){

                  @Override
                  public void onSuccess(String content) {
                      super.onSuccess(content);


                      try {

                          jobject = new JSONObject(content);
                          String s = jobject.getString("status");

                          if(s.equalsIgnoreCase("Success")){

                              jobject1 =jobject.getJSONObject("User_data");
                              String user_id=jobject1.getString("id");
                              String user_n = jobject1.getString("username");
                              String pw = jobject1.getString("password");
                              String postn = jobject1.getString("position");
                              String status = jobject1.getString("status");


                            //  Toast.makeText(LoginActivity.this, ""+user_id, Toast.LENGTH_SHORT).show();

                              editor.putString("user_id", user_id);
                              editor.putString("username", user_n);
                              editor.putString("password", pw);
                              editor.putString("position", postn);
                              editor.putString("status", status);
                              editor.commit();

                              Intent intent=new Intent(LoginActivity.this,NavActivity.class);
                              startActivity(intent);

                          }else if(s.equalsIgnoreCase("Incorrect Password")){

                              Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();

                          }


                      }catch (Exception e){
                          Toast.makeText(LoginActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                      }


                  }
              });


          }
      });
    }
}
