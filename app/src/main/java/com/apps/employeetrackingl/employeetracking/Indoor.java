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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Indoor extends AppCompatActivity {
    TextView taskhead,taskdetailstv;
    RadioGroup rg;
    RadioButton rb;
    Button Starttask,endtask;
    String  starttime,endtime;
 //   String currenttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);
        taskhead=findViewById(R.id.taskheading);
        taskdetailstv=findViewById(R.id.taskdetails);
        SharedPreferences shared = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        String taskheading=shared.getString("taskheading",null);
        String taskdetails=shared.getString("taskdetails",null);
        String taskstarttime=shared.getString("taskstarttime",null);
        String taskendtime=shared.getString("taskendtime",null);
        String taskstartdate=shared.getString("taskstartdate",null);
        String taskid=shared.getString("taskid",null);
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
                 // get selected radio button from radioGroup
                 int selectedId = rg.getCheckedRadioButtonId();

                 // find the radiobutton by returned id
                 rb = (RadioButton) findViewById(selectedId);

                 String s= (String) rb.getText();
                 Toast.makeText(Indoor.this,"SELCTED RB"+
                         s, Toast.LENGTH_SHORT).show();
             }
         });

    }
}
