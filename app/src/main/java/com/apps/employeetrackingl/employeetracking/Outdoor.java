package com.apps.employeetrackingl.employeetracking;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Outdoor extends AppCompatActivity {
    TextView taskhead,taskdetailstv;
    RadioGroup rg;
    RadioButton rb;
    Button Starttask,endtask;
    String  starttime,endtime;
    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    //final Context context = this;
    //forloc
    String s_longitude,s_latitude;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    //forloc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        //for loc perm
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        //for loc perm
        client = new AsyncHttpClient();
        params = new RequestParams();
        taskhead=findViewById(R.id.taskheadingoutdoor);
        taskdetailstv=findViewById(R.id.taskdetailsoutdoor);
        SharedPreferences shared = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        String taskheading=shared.getString("taskheading",null);
        String taskdetails=shared.getString("taskdetails",null);
        String taskstarttime=shared.getString("taskstarttime",null);
        String taskendtime=shared.getString("taskendtime",null);
        String taskstartdate=shared.getString("taskstartdate",null);
        final String taskid=shared.getString("taskid",null);
        taskhead.setText(taskheading);
        taskdetailstv.setText("Details: "+taskdetails+"\nStart date:  "+taskstartdate+"\nend time:  "+taskendtime);
        Starttask=findViewById((R.id.starttaskoutdoor));
        endtask=findViewById(R.id.buttonendtimeoutdoor);
        rg = (RadioGroup) findViewById(R.id.radiogroupoutdoor);
        Starttask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //current date and time
                Calendar c = Calendar.getInstance();
                System.out.println("Current time =&gt; "+c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                starttime = df.format(c.getTime());
                Toast.makeText(Outdoor.this, "Starting time is "+starttime, Toast.LENGTH_SHORT).show();


            }
        });
        endtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get current location

                locationTrack = new LocationTrack(Outdoor.this);


                if (locationTrack.canGetLocation()) {


                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();
                    s_longitude=Double.toString(longitude);
                     s_latitude=Double.toString(latitude);

                   // Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                } else {

                    locationTrack.showSettingsAlert();
                }
                 Toast.makeText(getApplicationContext(), "Longitude:" + s_longitude + "\nLatitude:" + s_latitude, Toast.LENGTH_SHORT).show();


                //get current loc
//alert
                final Dialog dialog = new Dialog(Outdoor.this);
                dialog.setContentView(R.layout.customalert);
                Button dialogButton = (Button) dialog.findViewById(R.id.submitinalert);
                final EditText emailalert=(EditText)dialog.findViewById(R.id.emailinalert);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                 String emailalert_s=emailalert.getText().toString();
                        Toast.makeText(Outdoor.this, ""+emailalert_s, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                //alert

            }
        });




    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Outdoor.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }
}
