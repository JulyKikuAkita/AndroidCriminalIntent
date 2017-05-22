package com.bignerdranch.android.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Crime {
    private String TAG = getClass().getCanonicalName();
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;
    private boolean mRequiresPolice;

    private String mDatePattern = "E, MM dd, yyyy";
    private String mTimePattern = "hh:mm a, z";
    SimpleDateFormat mDateFormat;
    SimpleDateFormat mTimeFormat;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }
    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }

    public String getFormatDate() {
        mDateFormat = new SimpleDateFormat(mDatePattern);
        return mDateFormat.format(mDate);
    }

    public String getFormatTime() {
        mTimeFormat = new SimpleDateFormat(mTimePattern);
        return mTimeFormat.format(mDate);
    }

    public String getDatePattern() {
        return mDatePattern;
    }

    public void setDatePattern(String datePattern) {
        mDatePattern = datePattern;
    }

    public String getTimePattern() {
        return mTimePattern;
    }

    public void setTimePattern(String timePattern) {
        mTimePattern = timePattern;
    }
}
