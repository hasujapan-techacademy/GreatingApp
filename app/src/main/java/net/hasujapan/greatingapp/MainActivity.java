package net.hasujapan.greatingapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int MORNING_HOUR_FROM = 1;
    static final int MORNING_MIN_FROM = 59;
    static final int MORNING_HOUR_TO = 10;
    static final int MORNING_MIN_TO = 0;

    static final int AFTERNOON_HOUR_FROM = 9;
    static final int AFTERNOON_MIN_FROM = 59;
    static final int AFTERNOON_HOUR_TO = 18;
    static final int AFTERNOON_MIN_TO = 0;

    // -----------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setShowTimePickerDialogButtonListener();
    }

    // -----------------------------------------------------------------------

    private void setShowTimePickerDialogButtonListener() {
        Button showTimePickerDialogButton = (Button) findViewById(R.id.showTimePickerDialogButton);
        showTimePickerDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    // -----------------------------------------------------------------------

    private void showTimePickerDialog() {

        Calendar calendar = Calendar.getInstance();
        int presetHour = calendar.get(Calendar.HOUR);
        int presetMin = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        updateGreetingTextView(hourOfDay, minute);
                    }
                },
                presetHour,
                presetMin,
                true);
        timePickerDialog.show();
    }

    // -----------------------------------------------------------------------

    private void updateGreetingTextView(int hour, int min) {

        String text = "こんばんは"; // 日付またぎが面倒なので朝と昼でなければ夜と判定する

        if(isMorning(hour, min)) {
            text = "おはようございます";
        }
        else if (isAfternoon(hour, min)) {
            text = "こんにちは";
        }

        TextView greetingTextView = (TextView) findViewById(R.id.greetingTextView);
        greetingTextView.setText(text);
    }

    // 「おはようございます」を返すべき時刻の範囲かどうかを判定する

    private boolean isMorning(int hour, int min) {
        return isBetweenTime(
                hour,
                min,
                MORNING_HOUR_FROM,
                MORNING_MIN_FROM,
                MORNING_HOUR_TO,
                MORNING_MIN_TO
        );
    }

    // 「こんにちは」を返すべき時刻の範囲かどうかを判定する

    private boolean isAfternoon(int hour, int min) {
        return isBetweenTime(
                hour,
                min,
                AFTERNOON_HOUR_FROM,
                AFTERNOON_MIN_FROM,
                AFTERNOON_HOUR_TO,
                AFTERNOON_MIN_TO
        );
    }

    // 指定された時刻が指定された範囲に収まっているかどうかを判定する

    private boolean isBetweenTime(int hour, int min, int fromHour, int fromMin, int toHour, int toMin) {

        boolean result = false;

        Date assignedTime = getTime(hour, min);
        Date from = getTime(fromHour, fromMin);
        Date to = getTime(toHour, toMin);

        if(assignedTime.after(from) && assignedTime.before(to)) {
            result = true;
        }

        return result;
    }

    private Date getTime(int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        Date assignedTime = cal.getTime();
        return assignedTime;
    }

    // -----------------------------------------------------------------------

}
