package com.apps.employeetrackingl.employeetracking;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Indoor extends AppCompatActivity {
    TextView taskhead,taskdetailstv;
    RadioGroup rg;
    RadioButton rb;
    Button Starttask,endtask;
    String  starttime,endtime;
    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    String updatetaskurl="http://srishti-systems.info/projects/ticketbooking/api/emp_taskcompleted.php?";
 //   String currenttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);

        client = new AsyncHttpClient();
        params = new RequestParams();

        taskhead=findViewById(R.id.taskheading);
        taskdetailstv=findViewById(R.id.taskdetails);
        SharedPreferences shared = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        String taskheading=shared.getString("taskheading",null);
        String taskdetails=shared.getString("taskdetails",null);
        String taskstarttime=shared.getString("taskstarttime",null);
        String taskendtime=shared.getString("taskendtime",null);
        String taskstartdate=shared.getString("taskstartdate",null);
        final String taskid=shared.getString("taskid",null);
        taskhead.setText(taskheading);
        taskdetailstv.setText("Details: "+taskdetails+"\nStart date:  "+taskstartdate+"\nend time:  "+taskendtime);

        Starttask=findViewById((R.id.starttask));
        endtask=findViewById(R.id.buttonendtime);
        rg = (RadioGroup) findViewById(R.id.radiogroup);



        Starttask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //current date and time
                Calendar c = Calendar.getInstance();
                System.out.println("Current time =&gt; "+c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                starttime = df.format(c.getTime());
                Toast.makeText(Indoor.this, "Starting time is "+starttime, Toast.LENGTH_SHORT).show();


            }
        });

         endtask.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Calendar c = Calendar.getInstance();
                 System.out.println("Current time =&gt; "+c.getTime());

                 SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                 endtime = df.format(c.getTime());
                 Toast.makeText(Indoor.this, "Ending time is "+endtime, Toast.LENGTH_SHORT).show();

//
//                 Date c1 = Calendar.getInstance().getTime(); System.out.println("Current date => " + c1);
//                 SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//                 String current_Date = df1.format(c);


                 // get selected radio button from radioGroup
                 int selectedId = rg.getCheckedRadioButtonId();

                 // find the radiobutton by returned id
                 rb = (RadioButton) findViewById(selectedId);

                 String status= (String) rb.getText();
                 Toast.makeText(Indoor.this,"SELCTED RB"+
                         status, Toast.LENGTH_SHORT).show();

                 params.put("task_id","5");  //current_Date
                 params.put("time","11:00:00");     //user_id
                 params.put("date","2018-10-26");
                 params.put("status","no");     //

                 client.get(updatetaskurl,params,new AsyncHttpResponseHandler(){
                     @Override
                     public void onSuccess(String content) {
                         super.onSuccess(content);
                         try{
                             jobject = new JSONObject(content);
                             String s = jobject.getString("Status");
                             if(s.equalsIgnoreCase("success"))
                             {
                                 Toast.makeText(Indoor.this, "Task Updated", Toast.LENGTH_SHORT).show();
                             }
                           else  if(s.equalsIgnoreCase("Already Updated"))
                             {
                                 Toast.makeText(Indoor.this, "Task Already Updated!", Toast.LENGTH_SHORT).show();
                             }
                             else
                             {
                                 Toast.makeText(Indoor.this, "Something Went wrong!Check your details and try again", Toast.LENGTH_SHORT).show();
                             }


                         }catch (Exception e){
                             Toast.makeText(Indoor.this, "Exception caught"+e, Toast.LENGTH_SHORT).show();
                         }


                     }
                 });



             }
         });

    }
}
