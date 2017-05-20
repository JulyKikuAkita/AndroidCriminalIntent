package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

public class CrimePagerActivity extends AppCompatActivity {
    private String TAG = getClass().getCanonicalName();
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";
    private Button mFirstButton;
    private Button mLastButton;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mFirstButton = (Button) findViewById(R.id.crime_page_first_button);
        mLastButton = (Button) findViewById(R.id.crime_page_last_button);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        //With FragmentStatePagerAdapter, your unneeded fragment is destroyed
        //With FragmentPagerAdapter, the fragments are never destroyed
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                // do not show button when at the same position
                if (position == 0) {
                    mFirstButton.setEnabled(false);
                } else if(position == mCrimes.size() - 1) {
                    mLastButton.setEnabled(false);
                } else {
                    mFirstButton.setEnabled(true);
                    mLastButton.setEnabled(true);
                }
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        /**
         * Challenge, click first turn to the first item
         */
        mFirstButton.setOnClickListener(new ViewPager.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });

        /**
         * Challenge, click last turn to the last item
         */
        mLastButton.setOnClickListener(new ViewPager.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size() - 1, true);
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
