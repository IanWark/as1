package wark.fueltrack;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

// Allows the user to input the data that comprises an Entry,
// creates the Entry, then sends the Entry to MainActivity

public class CreateEntryActivity extends AppCompatActivity {
    public final static String EXTRA_INTENT_TYPE = "wark.fueltrack.INTENT_TYPE";
    public final static String EXTRA_NEWENTRY = "wark.fueltrack.NEWENTRY";
    public final static String EXTRA_POSITION = "wark.fueltrack.POSITION";
    private String intent_type;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        // done button
        final Button done = (Button) findViewById(R.id.buttonDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Done(view);
            }
        });
        // cancel button
        final Button cancel = (Button) findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancel(view);
            }
        });
        // button for date picker
        final Button datePicker = (Button) findViewById(R.id.buttonDatePicker);
        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }

        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent recievedIntent = getIntent();
        intent_type = recievedIntent.getStringExtra(CreateEntryActivity.EXTRA_INTENT_TYPE);
        if (intent_type!=null) {
            if (intent_type.equals("edit")) {    // If editing a previous entry, put data in fields
                Entry recievedEntry = recievedIntent.getParcelableExtra(MainActivity.EXTRA_ENTRY);
                position = recievedIntent.getIntExtra(MainActivity.EXTRA_POSITION,-1);

                Button editDate = (Button) findViewById(R.id.buttonDatePicker);
                EditText editStation = (EditText) findViewById(R.id.editStation);
                EditText editOdometer = (EditText) findViewById(R.id.editOdometer);
                EditText editF_grade = (EditText) findViewById(R.id.editF_grade);
                EditText editF_amount = (EditText) findViewById(R.id.editF_amount);
                EditText editF_unit = (EditText) findViewById(R.id.editF_unit);

                editDate.setText(recievedEntry.getDate());
                editStation.setText(recievedEntry.getStation());
                editOdometer.setText(String.format("%.1f", recievedEntry.getOdometer()));
                editF_grade.setText(recievedEntry.getF_grade());
                editF_amount.setText(String.format("%.3f",recievedEntry.getF_amount()));
                editF_unit.setText(String.format("%.1f", recievedEntry.getF_unit()));
            }
        }
        else {    // If no intent_type, just go back to MainActivity
            Intent intentError = new Intent(this,MainActivity.class);
            startActivity(intentError);
        }
    }

    // returns to main activity with the data from the fields.
    private void Done(View view) {
        Intent intentDone = new Intent(this, MainActivity.class);

        // Get data from the EditText fields
        Button editDate = (Button) findViewById(R.id.buttonDatePicker);
        EditText editStation = (EditText) findViewById(R.id.editStation);
        EditText editOdometer = (EditText) findViewById(R.id.editOdometer);
        EditText editF_grade = (EditText) findViewById(R.id.editF_grade);
        EditText editF_amount = (EditText) findViewById(R.id.editF_amount);
        EditText editF_unit = (EditText) findViewById(R.id.editF_unit);
        // Convert data to Strings
        String stringDate = editDate.getText().toString();
        String stringStation = editStation.getText().toString();
        String stringOdometer = editOdometer.getText().toString();
        String stringF_grade = editF_grade.getText().toString();
        String stringF_amount = editF_amount.getText().toString();
        String stringF_unit = editF_unit.getText().toString();;

        // if any of the fields are not filled out, do not go to main activity, set button RED
        if (stringDate.equals("Pick Time") || stringStation.isEmpty() || stringOdometer.isEmpty() ||
                stringF_grade.isEmpty() || stringF_amount.isEmpty() || stringF_unit.isEmpty()){
            Button done = (Button) findViewById(R.id.buttonDone);
            done.setTextColor(Color.RED);
            return;
        }
        // Convert data to correct type
        Double doubleOdometer = Double.valueOf(stringOdometer);
        Double doubleF_amount = Double.valueOf(stringF_amount);
        Double doubleF_unit = Double.valueOf(stringF_unit);
        // Round to correct decimals
        doubleOdometer = Double.valueOf(String.format("%.1f",doubleOdometer));
        doubleF_amount = Double.valueOf(String.format("%.3f",doubleF_amount));
        doubleF_unit = Double.valueOf(String.format("%.1f",doubleF_unit));


        Entry newEntry = new Entry(stringDate,stringStation,doubleOdometer,
                stringF_grade,doubleF_amount,doubleF_unit);
        // Add data to intent
        intentDone.putExtra(EXTRA_INTENT_TYPE, intent_type);
        intentDone.putExtra(EXTRA_NEWENTRY, newEntry);
        if(intent_type.equals("edit")) {
            intentDone.putExtra(EXTRA_POSITION, position);
        }

        startActivity(intentDone);
    }

    // returns to main activity
    private void Cancel(View view) {
        Intent intentCancel = new Intent(this, MainActivity.class);
        startActivity(intentCancel);
    }

    // pops up the dialog picker
    // from http://www.codinguser.com/2012/06/time-and-date-inputs-in-android/
    private void showDatePickerDialog(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(ft, "datePicker");
    }
}
