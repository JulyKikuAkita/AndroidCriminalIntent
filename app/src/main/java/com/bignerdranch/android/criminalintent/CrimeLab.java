package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * singleton class
 * The CrimeLab is a singleton, which means that once it is created, it will not be destroyed
 * until your entire application process is destroyed. Also, the CrimeLab maintains a
 * reference to its mContext object. If you store an activity as
 * the mContext object, that activity will never be cleaned up by the garbage collector
 * because the CrimeLab has a reference to it. Even if the user has navigated away from
 * that activity, it will never be cleaned up.
 * To avoid this wasteful situation, you use the application context so that your activities
 * can come and go and the CrimeLab can maintain a reference to a Context object. Always think
 * about the lifetime of your activities as you keep a reference to them.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        for (int i = 0; i< 7; i++) {
            Crime c = new Crime();
            c.setTitle(Integer.toString(i + 1));
        }
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void deleteCrime(Crime c) {
        String uuidString = c.getId().toString();

        mDatabase.delete(CrimeTable.NAME,
                CrimeTable.Cols.UUID + "= ?",
                //use ? + String[] instead of where + String to avoid SQL injection
                new String[]{uuidString}
        );
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally { //beware run out of open file handles and crash your app
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] {id.toString() }
        );

        try{
            if (cursor.getCount() == 0) {
                return  null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        }finally{
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + "= ?",
                //use ? + String[] instead of where + String to avoid SQL injection
                new String[]{uuidString});
    }

    /**
     * mapping
     * public Cursor query(
        String table,
        String[] columns,
        String where,
        String[] whereArgs,
        String groupBy,
        String having,
        String orderBy,
        String limit)
     */
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //columns - null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null //orderBy
        );
        return  new CrimeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE,
                crime.getTitle() == null ? "" : crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.TIME, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved()?1 : 0);
        return values;
    }
}
