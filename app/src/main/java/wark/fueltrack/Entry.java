package wark.fueltrack;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by wark on 2016/01/19.
 * Contains the data for a single fueling:
 * The date it was on, what station it was at, the odometer reading,
 * the fuel grade, the amount of fuel, the price of the fuel per liter,
 * and the total cost of the fueling.
 * Stored in an EntryLog
 * Displayed in MainActivity through the EntryLog
 * Data entered in CreateEntryActivity
 */
public class Entry implements Parcelable{
    private String date;
    private String station;
    private double odometer;
    private String f_grade;
    private double f_amount;
    private double f_unit;
    private double f_calculated;

    public Entry(String date, String station, double odometer, String f_grade, double f_amount, double f_unit) {
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.f_grade = f_grade;
        this.f_amount = f_amount;
        this.f_unit = f_unit;
        this.f_calculated = f_amount*f_unit*0.01;
    }

    @Override
    public String toString() {
        return  station + " - " + date +
                "\nOdometer=" + String.format("%.1f",odometer) +
                "\nFuel Grade: " + f_grade +
                "\nFuel Volume: " + String.format("%.3f",f_amount) + 'L' +
                "\nFuel Price: " + String.format("%.1f",f_unit) + "¢/L" +
                "\nFuel Cost: $" + String.format("%.2f",f_calculated);

    }

    // for Parcelable
    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() {
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    // for Parcelable
    private Entry(Parcel in){
        date = in.readString();
        station = in.readString();
        odometer = in.readDouble();
        f_grade = in.readString();
        f_amount = in.readDouble();
        f_unit = in.readDouble();
        f_calculated = in.readDouble();
    }

    // for Parcelable
    @Override
    public int describeContents(){
        return 0;
    }

    // for Parcelable
    @Override
    public void writeToParcel(Parcel out,int flags){
        out.writeString(date);
        out.writeString(station);
        out.writeDouble(odometer);
        out.writeString(f_grade);
        out.writeDouble(f_amount);
        out.writeDouble(f_unit);
        out.writeDouble(f_calculated);
    }

    public String getDate() {
        return date;
    }

    public String getStation() {
        return station;
    }

    public double getOdometer() {
        return odometer;
    }

    public String getF_grade() {
        return f_grade;
    }

    public double getF_amount() {
        return f_amount;
    }

    public double getF_unit() {
        return f_unit;
    }

    public double getF_calculated() {
        return f_calculated;
    }
}
