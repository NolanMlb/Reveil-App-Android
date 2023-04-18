package com.example.reveil.views;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.reveil.R;
import com.example.reveil.managers.AlarmReceiver;
import com.example.reveil.managers.ReveilHandler;
import com.example.reveil.models.Reveil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static List<Reveil> reveils;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readRecords();
        countRecords();
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("channel_id", "Nom du canal", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Description du canal");
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void readRecords() {
        LinearLayout linearLayoutRecords = (LinearLayout)
                findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();
        reveils = new ReveilHandler(this).getAllReveils();
        if (reveils.size() > 0) {
            for (Reveil obj : reveils) {
                int id = obj.getId();
                String hour = obj.getHour();
                String minute = obj.getMinute();
                Boolean monday = obj.isMonday();
                Boolean tuesday = obj.isTuesday();
                Boolean wednesday = obj.isWednesday();
                Boolean thursday = obj.isThursday();
                Boolean friday = obj.isFriday();
                Boolean saturday = obj.isSaturday();
                Boolean sunday = obj.isSunday();
                Boolean isStarted = obj.isStart();
                if (isStarted) {
                    startAlarmByCheckingDays(hour, minute, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
                }
                String recurrence = "";
                if (monday == true) recurrence += "Lun ";
                if (tuesday == true) recurrence += "Mar ";
                if (wednesday == true) recurrence += "Mer ";
                if (thursday == true) recurrence += "Jeu ";
                if (friday == true) recurrence += "Ven ";
                if (saturday == true) recurrence += "Sam ";
                if (sunday == true) recurrence += "Dim";
                TextView textViewRecurrence = new TextView(this);
                textViewRecurrence.setText(recurrence);
                textViewRecurrence.setPadding(0, 10, 0, 10);
                textViewRecurrence.setTag(id);
                String textViewContents = hour + " h "
                        + minute;
                LinearLayout container = new LinearLayout(this);
                // Création des LayoutParams avec des marges
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                layoutParams.setMargins(50, 5, 25, 5);
                // Application des LayoutParams au conteneur
                container.setLayoutParams(layoutParams);
                TextView textViewReveilItem = new TextView(this);
                textViewReveilItem.setLayoutParams(new LinearLayout.LayoutParams(
                        750,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0
                ));
                textViewReveilItem.setPadding(0, 10, 0, 10);
                textViewReveilItem.setTextColor(Color.BLACK);
                textViewReveilItem.setText(textViewContents);
                textViewReveilItem.setTag(Integer.toString(id));
                textViewReveilItem.setTypeface(null, Typeface.BOLD);
                textViewReveilItem.setTextSize(30);

                // Création du Switch
                Switch toggleSwitch = new Switch(this);
                toggleSwitch.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                toggleSwitch.setChecked(isStarted);
                toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        obj.setStart(isChecked);
                        new ReveilHandler(MainActivity.this).updateStatusReveil(obj);
                        if (isChecked) {
                            startAlarm(hour, minute);
                        } else {
                            alarmManager.cancel(pendingIntent);
                            if (AlarmReceiver.ringtone != null) {
                                AlarmReceiver.stopAlarm();
                            }
                        }
                    }
                });

                // Création d'un bouton pour supprimer le conteneur
                Button buttonDelete = new Button(this);
                Drawable drawable = getResources().getDrawable(R.drawable.ic_delete);
                buttonDelete.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayoutRecords.removeView(container);
                        deleteReveil(obj);
                    }
                });

                LinearLayout containerLeft = new LinearLayout(this);
                containerLeft.setOrientation(LinearLayout.VERTICAL);
                containerLeft.addView(textViewReveilItem);
                containerLeft.addView(textViewRecurrence);

                // Création de la LinearLayout contenant le toggle et le bouton
                LinearLayout containerRight = new LinearLayout(this);
                containerRight.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                containerRight.setOrientation(LinearLayout.HORIZONTAL);

                // Ajout du toggle dans la LinearLayout
                containerRight.addView(toggleSwitch);

                // Ajout du bouton dans la LinearLayout
                containerRight.addView(buttonDelete);

                // Ajout de la LinearLayout dans le container
                container.addView(containerLeft);
                container.addView(containerRight);

                linearLayoutRecords.addView(container);
            }
        }
        else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("Aucun réveil pour le moment.");
            linearLayoutRecords.addView(locationItem);
        }
    }

    public void startAlarmByCheckingDays (String hour, String minute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        Calendar calendar = Calendar.getInstance();
        String currentDay = new SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.getTime());
        // Vérifier si le jour actuel correspond à l'un des jours choisis pour le réveil
        if ((currentDay.equals("Mon") && monday) ||
                (currentDay.equals("Tue") && tuesday) ||
                (currentDay.equals("Wed") && wednesday) ||
                (currentDay.equals("Thu") && thursday) ||
                (currentDay.equals("Fri") && friday) ||
                (currentDay.equals("Sat") && saturday) ||
                (currentDay.equals("Sun") && sunday) || (!monday && !tuesday && !wednesday && !thursday && !friday && !saturday && !sunday)) {
            startAlarm(hour, minute);
        }
    }
    public void startAlarm (String hour, String minute) {
        // Créer une instance de AlarmManager
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Créer un PendingIntent pour lancer le BroadcastReceiver
        intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // Programmer l'alarme à une heure précise
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        if (parseInt(hour) < currentHour || (parseInt(hour) == currentHour && parseInt(minute) <= currentMinute)) {
            // L'heure spécifiée est antérieure à l'heure actuelle, ajouter un jour pour planifier l'alarme pour le lendemain
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, parseInt(hour)); // Heure de déclenchement de l'alarme
        calendar.set(Calendar.MINUTE, parseInt(minute));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        long triggerTime = calendar.getTimeInMillis() + 1000;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }
    public void deleteReveil(Reveil reveil) {
        new ReveilHandler(this).deleteReveil(reveil);
    }
    public void countRecords() {
        int reveilCount = new ReveilHandler(this).count();
        System.out.println(reveilCount + "réveil(s) trouvé(s)");
    }

    public void onClickSwitchActivity(View view) {
        Intent intent = new Intent(this, CreateReveil.class);
        startActivity(intent);
    }
}