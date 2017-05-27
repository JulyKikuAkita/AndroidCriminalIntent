package com.bignerdranch.android.criminalintent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Crime {
    private String TAG = getClass().getCanonicalName();
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;
    private boolean mRequiresPolice;

    private String mDefaultLang = "en";
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

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }

    public String getFormatDate() {
        if (Locale.getDefault().getLanguage().equals(mDefaultLang)) {
            mDateFormat = new SimpleDateFormat(mDatePattern);
            return mDateFormat.format(mDate);
        }
        //consider locale
        return DateFormat.getDateInstance().format(mDate);
    }

    public String getFormatTime() {
        if (Locale.getDefault().getLanguage().equals(mDefaultLang)) {
            mTimeFormat = new SimpleDateFormat(mTimePattern);
            return mTimeFormat.format(mDate);
        }
        return DateFormat.getTimeInstance().format(mDate);
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
