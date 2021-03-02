package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Button save;
    Button cancel;
    TextView txtv1,txtv2;
    TimePicker timer;
    EditText medName;
    String medicine;
    CheckBox morning,noon,night;
    ListView listview;
    String Medicine;
    TextView txtv3;

    ArrayList<String> arrayList;
    ArrayList<Integer> idList;

    ArrayAdapter<String> adapter;
    ManageAlarm manageAlarm;
    DatabaseReference DatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseRef=FirebaseDatabase.getInstance().getReference("Alarms");
        manageAlarm=new ManageAlarm();
        save=(Button)findViewById(R.id.save_butt);
        cancel=(Button)findViewById(R.id.cancel_butt);
        txtv1=(TextView)findViewById(R.id.tits);
        //timer=(TimePicker)findViewById(R.id.timer);
        medName=(EditText)findViewById(R.id.med_name);
        //med=(CheckBox)findViewById(R.id.med_text);
        txtv2=(TextView)findViewById(R.id.tits2);
        listview=(ListView)findViewById(R.id.time_list);
        medName=(EditText)findViewById(R.id.med_name);

        txtv3 = (TextView) findViewById(R.id.text1);

        arrayList=new ArrayList<String>();
        idList=new ArrayList<Integer>();
        getData(DatabaseRef);
        adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);

        listview.setAdapter(adapter);


        morning=(CheckBox)findViewById(R.id.morning_dose);
        noon=(CheckBox)findViewById(R.id.afternoon_dose);
        night=(CheckBox)findViewById(R.id.night_dose);

        TimePicker timePicker=new TimePicker(this);
        int currtHour=timePicker.getCurrentHour();
        int currMin=timePicker.getCurrentMinute();

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id2=idList.get(position);
                String str=String.valueOf(id2);
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
            }
        });

    }


    public void saveTime(View view) {
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.morning_dose:
                if (checked) {
                    TimePicker timePicker=new TimePicker(this);
                    int currtHour=timePicker.getCurrentHour();
                    int currMin=timePicker.getCurrentMinute();
                    TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            int id=manageAlarm.SetBajna(hourOfDay,minute,0,MainActivity.this);
                            String ID=String.valueOf(id);
                            medicine=medName.getText().toString()+"\n";
                            Medicine=medicine+hourOfDay+":"+minute;
                            AlarmInfo alarmInfo=new AlarmInfo(medicine,hourOfDay,minute,0,id,00);
                            DatabaseRef.child("Alarms").child(ID).setValue(alarmInfo);

                            //arrayList.add(Medicine);
                            //adapter.notifyDataSetChanged();

                        }
                    },currtHour,currMin,true);

                    timePickerDialog.show();
                }
                else{

                }
                break;
            case R.id.night_dose:
                if (checked){
                    TimePicker timePicker=new TimePicker(this);
                    int currtHour=timePicker.getCurrentHour();
                    int currMin=timePicker.getCurrentMinute();
                    TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            medicine=medName.getText().toString()+"\n";
                            Medicine=medicine+hourOfDay+":"+minute;
                            arrayList.add(Medicine);
                            adapter.notifyDataSetChanged();
                        }
                    },currtHour,currMin,true);

                    timePickerDialog.show();


                }
                else{

                }
                break;

        }
    }


    public void cancelTime(View view)
    {
        
    }


    public void getData(DatabaseReference dbf)
    {
        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dsp:snapshot.getChildren())
                {
                    String name=dsp.child("medName").getValue(String.class);
                    int hour=dsp.child("hour").getValue(Integer.class);
                    int mi=dsp.child("min").getValue(Integer.class);
                    int id2=dsp.child("id").getValue(Integer.class);

                    //Toast.makeText(MainActivity.this,id2,Toast.LENGTH_LONG).show();
                    String value=name+" "+hour+":"+mi;
                    arrayList.add(value);
                    idList.add(id2);
                    adapter.notifyDataSetChanged();
                }

                /*
                String name=snapshot.child("Alarms").child("1").child("medName").getValue(String.class);
                int hour=snapshot.child("Alarms").child("1").child("hour").getValue(Integer.class);
                int mi=snapshot.child("Alarms").child("1").child("min").getValue(Integer.class);
                int id2=snapshot.child("Alarms").child("1").child("id").getValue(Integer.class);

                //Toast.makeText(MainActivity.this,id2,Toast.LENGTH_LONG).show();
                String value=name+" "+hour+":"+mi;
                arrayList.add(value);
                idList.add(id2);
                adapter.notifyDataSetChanged();

                 */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}