package wark.fueltrack;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Ian on 28/01/2016.
 */
public class EntryLogTest extends ActivityInstrumentationTestCase2 {
    public EntryLogTest(){
        super(MainActivity.class);
    }

    public void testAddEntry(){
        Entry testEntry1 = new Entry("1990-01-01","Costco",200,"regular",20,50);
        Entry testEntry2 = new Entry("1990-02-02","Esso",250,"regular",30,30);
        EntryLog testLog = new EntryLog();

        testLog.add(testEntry1);
        assertEquals(testEntry1, testLog.get(0));
        testLog.add(0, testEntry2);
        assertEquals(testEntry2, testLog.get(0));
    }

    public void testRemoveEntry(){
        Entry testEntry1 = new Entry("1990-01-01","Costco",200,"regular",20,50);
        Entry testEntry2 = new Entry("1990-02-02","Esso",250,"regular",30,30);
        EntryLog testLog = new EntryLog();

        testLog.add(testEntry1);
        testLog.add(testEntry2);
        assertEquals(testEntry1, testLog.get(0));
        assertEquals(testEntry2, testLog.get(1));
        testLog.remove(0);
        assertEquals(testEntry2,testLog.get(0));
        try{
            testLog.remove(0);
            testLog.get(0);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        fail();
    }

    public void testOverallCost(){
        Entry testEntry1 = new Entry("1990-01-01","Costco",200,"regular",20,50);
        Entry testEntry2 = new Entry("1990-02-02","Esso",250,"regular",30,30);
        EntryLog testLog = new EntryLog();

        testLog.add(testEntry1);
        assertEquals(testLog.getOverall_cost(), 20 * 0.5);
        testLog.add(0, testEntry2);
        assertEquals(testLog.getOverall_cost(),(20*0.5)+(30*0.3));
        testLog.remove(1);
        assertEquals(testLog.getOverall_cost(),30*0.3);
    }
}
