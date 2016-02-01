package wark.fueltrack;

import android.os.Parcel;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Ian on 30/01/2016.
 */
public class EntryTest extends ActivityInstrumentationTestCase2 {
    public EntryTest(){
        super(MainActivity.class);
    }

    public void testEntryF_calculated(){
        Entry testEntry = new Entry("1990-01-01","Costco",200,"regular",20,50);
        assertEquals(testEntry.getF_calculated(), 20*0.5);
    }

    // from http://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
    public void testParcel(){
        Entry testEntry = new Entry("1990-01-01","Costco",200,"regular",20,50);
        Parcel testParcel = Parcel.obtain();
        testEntry.writeToParcel(testParcel, 0);

        testParcel.setDataPosition(0);

        Entry parcelEntry = Entry.CREATOR.createFromParcel(testParcel);
        assertEquals(testEntry.toString(),parcelEntry.toString());
    }
}
