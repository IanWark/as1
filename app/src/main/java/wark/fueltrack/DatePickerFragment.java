package wark.fueltrack;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Ian on 27/01/2016.
 * from http://developer.android.com/guide/topics/ui/controls/pickers.html
 * Used for the date picker in CreateEntryActivity.
 * Controls what data appears in the date picker and what happens to the date after it is chosen.
 * If editing, sets date on the date picker to be the Entry being edited, otherwise current date.
 * When date is chosen, the buttonDatePicker's text is set to the date.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        Activity activity = getActivity();
        Intent intent = activity.getIntent();
        String intent_type = intent.getStringExtra(CreateEntryActivity.EXTRA_INTENT_TYPE);

        if (intent_type!=null) {
            if (intent_type.equals("edit")) {
                // if editing an entry, set the date on the picker as the date in the entry
                Entry entry = intent.getParcelableExtra(MainActivity.EXTRA_ENTRY);
                String stringDate = entry.getDate();
                Date dateDate;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
                try {
                    dateDate = sdf.parse(stringDate);
                }  catch (ParseException e) {
                    // If parsing fails just do current date as default
                    return createDatePickerDialog(cal);
                }

                cal.setTime(dateDate);
                return createDatePickerDialog(cal);
            }
        }
        // Use the current date as the default date in the picker
        return createDatePickerDialog(cal);
    }

    // for use in onCreateDialog
    private Dialog createDatePickerDialog(Calendar cal){
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Activity currentActivity = getActivity();
        Button buttonDatePicker = (Button) currentActivity.findViewById(R.id.buttonDatePicker);
        Calendar cal = new GregorianCalendar(year,month,day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        buttonDatePicker.setText(sdf.format(cal.getTime()));
    }
}
