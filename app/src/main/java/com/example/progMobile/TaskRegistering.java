package com.example.progMobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskRegistering extends AppCompatActivity {

    private EditText taskTitle, taskDesc, date, time;
    DBToDoHelper dbHelper;
    Tasks task, subTask;
    long ret;
    private Button altBttn;

    private Button btnCancel, btnActive;
    AlarmManager alarmManager;
    PendingIntent pedingIntent;

    boolean isRepeat=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        taskTitle =findViewById(R.id.taskTitle);
        taskDesc =findViewById(R.id.taskDesc);
        date =findViewById(R.id.date);
        time =findViewById(R.id.time);
        btnActive =findViewById(R.id.btnActive);
        btnCancel=findViewById(R.id.btnCancel);
        Intent it = getIntent();
        subTask =(Tasks)it.getSerializableExtra("ch_tarefa");
        task = new Tasks();
        dbHelper = new DBToDoHelper(TaskRegistering.this);
        altBttn = findViewById(R.id.altBttn);

        //Mudar código de acordo com activity
        if(subTask !=null){
            altBttn.setText("Alterar");
            taskTitle.setText(subTask.getTaskTitle());
            taskDesc.setText(subTask.getTaskDesc());
            date.setText(subTask.getDate());
            time.setText(subTask.getTime());
            task.setTaskID(subTask.getTaskID());
        }else{
            altBttn.setText("Salvar");
        }
        btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TaskRegistering.this,"Alarme Ativo!",Toast.LENGTH_SHORT).show();
                isRepeat=false;
                startAlarm();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TaskRegistering.this,"Alarme Cancelado =(",Toast.LENGTH_SHORT).show();
                if(alarmManager !=null){
                    alarmManager.cancel(pedingIntent);
                }
            }
        });

        altBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = taskTitle.getText().toString();
                String desc = taskDesc.getText().toString();
                String date = TaskRegistering.this.date.getText().toString();
                String hour = time.getText().toString();
                long ret;
                task.setTaskTitle(title);
                task.setTaskDesc(desc);
                task.setDate(date);
                task.setTime(hour);
                if(altBttn.getText().toString().equals("Salvar")){ //Verify do botão
                    ret=dbHelper.insertTask(task);
                    if(ret==-1){
                        Toast.makeText(TaskRegistering.this,"Não foi possível cadastrar",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TaskRegistering.this,"Atividade Cadastrada!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    dbHelper.update(task);
                    dbHelper.close();
                }
                finish();
            }
        });
    }

    private void startAlarm() {
        alarmManager=(AlarmManager)
                this.getSystemService(Context.ALARM_SERVICE);
        Intent it = new Intent(TaskRegistering.this, AlarmeToastReceiver.class);
        pedingIntent=PendingIntent.getBroadcast(this,0,it,0);
        if(!isRepeat){
            alarmManager.set(AlarmManager.RTC, SystemClock.elapsedRealtime()+60*1000,pedingIntent);
        }else{
            alarmManager.set(AlarmManager.RTC, SystemClock.elapsedRealtime()+60*1000,pedingIntent);
        }
    }
}