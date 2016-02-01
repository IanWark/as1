package wark.fueltrack;

import android.widget.Adapter;
import android.widget.ArrayAdapter;

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
import java.util.Iterator;

import wark.fueltrack.Entry;

/**
 * Created by Ian on 27/01/2016.
 * Contains zero to many Entry objects and keeps track of the overall cost of all the Entries
 * Displayed in MainActivity
 */
public class EntryLog {
    private static final String FILENAME = "fueltrack.sav";
    private ArrayList<Entry> entryList;
    private Double overallCost;

    public EntryLog() {
        this.entryList = new ArrayList<Entry>();
        this.overallCost = new Double(0);
    }

    // computes overall fuel cost of all the entries combined
    private void updateOverallCost(){
        overallCost = 0.0;
        int size = entryList.size();
        Entry current;
        for (int i=0;i<size;i++){
            current = entryList.get(i);
            overallCost = overallCost + current.getF_calculated();
        }
        return;
    }

    public void add(Entry entry) {
        entryList.add(entry);
        updateOverallCost();
    }

    public void add(int index,Entry entry) {
        entryList.add(index, entry);
        updateOverallCost();
    }

    public void remove(int index){
        entryList.remove(index);
        updateOverallCost();
    }

    public Entry get(int index){
        return entryList.get(index);
    }

    public ArrayList<Entry> getEntryList() {
        return entryList;
    }

    public Double getOverall_cost(){
        return overallCost;
    }

}
