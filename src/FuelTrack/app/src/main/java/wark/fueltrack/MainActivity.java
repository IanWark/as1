package wark.fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String FILENAME = "fueltrack.sav";
    private ListView entryList;
    private TextView numberOverallFuel;

    private EntryLog entries = new EntryLog();
    private ArrayAdapter<Entry> adapterEntryLog;

    public final static String EXTRA_INTENT_TYPE = "wark.fueltrack.INTENT_TYPE";
    public final static String EXTRA_ENTRY = "wark.fueltrack.ENTRY";
    public final static String EXTRA_POSITION = "wark.fueltrack.POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberOverallFuel = (TextView) findViewById(R.id.numberOverallFuel);
        entryList = (ListView) findViewById(R.id.entryList);
        entryList.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewEntry(view);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();

        adapterEntryLog = new ArrayAdapter<Entry>(this,android.R.layout.simple_list_item_1,entries.getEntryList());
        entryList.setAdapter(adapterEntryLog);

        Intent recievedIntent = getIntent();
        String intent_type = recievedIntent.getStringExtra(CreateEntryActivity.EXTRA_INTENT_TYPE);
        if (intent_type!=null){
            if (intent_type.equals("new")) { // add new Entry to end of log
                Entry newEntry = recievedIntent.getParcelableExtra(CreateEntryActivity.EXTRA_NEWENTRY);
                entries.add(newEntry);

                saveInFile();
                getIntent().removeExtra(EXTRA_INTENT_TYPE);
            }
            if (intent_type.equals("edit")) { // replace old Entry with updated new Entry
                Entry newEntry = recievedIntent.getParcelableExtra(CreateEntryActivity.EXTRA_NEWENTRY);
                int position = recievedIntent.getIntExtra(CreateEntryActivity.EXTRA_POSITION, -1);
                entries.remove(position);
                entries.add(position, newEntry);

                saveInFile();
                getIntent().removeExtra(EXTRA_INTENT_TYPE);
            }

        }
        adapterEntryLog.notifyDataSetChanged();
        numberOverallFuel.setText(String.format("$%.2f", entries.getOverall_cost()));
    }

    public void createNewEntry(View view) {
        Intent intentCreate = new Intent(this,CreateEntryActivity.class);
        intentCreate.putExtra(EXTRA_INTENT_TYPE,"new");
        startActivity(intentCreate);
    }

    // when item in ListView EntryLog is clicked, send entry to CreateEntryActivity
    public void onItemClick(AdapterView<?> l, View view, int position, long id){
        Intent intentEdit = new Intent(this,CreateEntryActivity.class);
        Entry entry = entries.get(position);
        intentEdit.putExtra(EXTRA_INTENT_TYPE,"edit");
        intentEdit.putExtra(EXTRA_POSITION,position);
        intentEdit.putExtra(EXTRA_ENTRY,entry);
        startActivity(intentEdit);
    }

    // loads entries from file
    // from lonelyTwitter lab example
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Gson gson = new Gson();
            entries = gson.fromJson(in,EntryLog.class);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            entries = new EntryLog();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    // saves entries into a file
    // from lonelyTwitter lab example
    private void saveInFile(){
       try {
           FileOutputStream fos = openFileOutput(FILENAME,0);
           BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
           Gson gson = new Gson();
           gson.toJson(entries,out);
           out.flush();
           fos.close();
       } catch (FileNotFoundException e){
           // TODO
           throw new RuntimeException();
       } catch (Exception e) {
           // TODO
           throw new RuntimeException();
       }
    }
}