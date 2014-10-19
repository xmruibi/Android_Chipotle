package com.example.android.wizardpager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.ModelCallbacks;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.model.ReviewItem;
import co.juliansuarez.libwizardpager.wizard.ui.PageFragmentCallbacks;
import co.juliansuarez.libwizardpager.wizard.ui.ReviewFragment;
import co.juliansuarez.libwizardpager.wizard.ui.StepPagerStrip;
import database.connectDB;
public class MainActivity extends FragmentActivity implements
		PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks {
	private ViewPager mPager;
	private MyPagerAdapter mPagerAdapter;

	private boolean mEditingAfterReview;

	private AbstractWizardModel mWizardModel = new SandwichWizardModel(this);

	private boolean mConsumePageSelectedEvent;
	private Button mNextButton;
	private Button mPrevButton;
	connectDB db;
	private List<Page> mCurrentPageSequence;
	private StepPagerStrip mStepPagerStrip;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db=new connectDB(this);db.open();
		if (savedInstanceState != null) {
			mWizardModel.load(savedInstanceState.getBundle("model"));
		}

		mWizardModel.registerListener(this);

		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mPagerAdapter);
		mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
		mStepPagerStrip
				.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
					@Override
					public void onPageStripSelected(int position) {
						position = Math.min(mPagerAdapter.getCount() - 1,
								position);
						if (mPager.getCurrentItem() != position) {
							mPager.setCurrentItem(position);
						}
					}
				});
		mNextButton = (Button) findViewById(R.id.next_button);
		mPrevButton = (Button) findViewById(R.id.prev_button);

		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				mStepPagerStrip.setCurrentPage(position);

				if (mConsumePageSelectedEvent) {
					mConsumePageSelectedEvent = false;
					return;
				}

				mEditingAfterReview = false;
				updateBottomBar();
			}
		});
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
					DialogFragment dg = new DialogFragment() {
						@Override
						public Dialog onCreateDialog(Bundle savedInstanceState) {
							return new AlertDialog.Builder(getActivity())
									.setMessage(R.string.submit_confirm_message)
									.setPositiveButton(
											R.string.submit_confirm_button,
											new android.content.DialogInterface.OnClickListener() {
											    public void onClick(DialogInterface dialog, int which) {
											    	ArrayList<ReviewItem> reviewItems = new ArrayList<ReviewItem>();
											        for (Page page : mWizardModel.getCurrentPageSequence()) {
											            page.getReviewItems(reviewItems);
											        }
											        Collections.sort(reviewItems, new Comparator<ReviewItem>() {
											            @Override
											            public int compare(ReviewItem a, ReviewItem b) {
											                return a.getWeight() > b.getWeight() ? +1 : a.getWeight() < b.getWeight() ? -1 : 0;
											            }
											        });
											        db.insert(reviewItems.get(3).getDisplayValue(), reviewItems.get(4).getDisplayValue(), reviewItems.get(5).getDisplayValue(), reviewItems.get(6).getDisplayValue(), reviewItems.get(7).getDisplayValue(), reviewItems.get(8).getDisplayValue(), reviewItems.get(9).getDisplayValue(),reviewItems.get(10).getDisplayValue());
											        Toast.makeText(MainActivity.this, "Place Successfully!", Toast.LENGTH_LONG).show();
											        
											        
												}
											}).setNegativeButton(android.R.string.cancel,
											null).create();   
						}	
					};
					
					dg.show(getSupportFragmentManager(), "place_order_dialog");
					
				    
					
				} else {
					if (mEditingAfterReview) {
						mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
					} else {
						mPager.setCurrentItem(mPager.getCurrentItem() + 1);
					}
				}
			}
		});

		mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			}
		});

		onPageTreeChanged();
		updateBottomBar();
	}
	

	@Override
	public void onPageTreeChanged() {
		mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
		recalculateCutOffPage();
		mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 =
																		// review
																		// step
		mPagerAdapter.notifyDataSetChanged();
		updateBottomBar();
	}

	private void updateBottomBar() {
		int position = mPager.getCurrentItem();
		if (position == mCurrentPageSequence.size()) {
			mNextButton.setText(R.string.finish);
			mNextButton.setBackgroundResource(R.drawable.finish_background);
		//	mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
		} else {
			mNextButton.setText(mEditingAfterReview ? R.string.review
					: R.string.next);
			mNextButton
					.setBackgroundResource(R.drawable.selectable_item_background);
			TypedValue v = new TypedValue();
			getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v,
					true);
			//mNextButton.setTextAppearance(this, v.resourceId);
			mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
		}

		mPrevButton
				.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mWizardModel.unregisterListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBundle("model", mWizardModel.save());
	}

	@Override
	public AbstractWizardModel onGetModel() {
		return mWizardModel;
	}
	@Override
	public void onEditScreenAfterReview(String key) {
		for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
			if (mCurrentPageSequence.get(i).getKey().equals(key)) {
				mConsumePageSelectedEvent = true;
				mEditingAfterReview = true;
				mPager.setCurrentItem(i);
				updateBottomBar();
				break;
			}
		}
	}

	@Override
	public void onPageDataChanged(Page page) {
		if (page.isRequired()) {
			if (recalculateCutOffPage()) {
				mPagerAdapter.notifyDataSetChanged();
				updateBottomBar();
			}
		}
	}

	@Override
	public Page onGetPage(String key) {
		return mWizardModel.findByKey(key);
	}

	private boolean recalculateCutOffPage() {
		// Cut off the pager adapter at first required page that isn't completed
		int cutOffPage = mCurrentPageSequence.size() + 1;
		for (int i = 0; i < mCurrentPageSequence.size(); i++) {
			Page page = mCurrentPageSequence.get(i);
			if (page.isRequired() && !page.isCompleted()) {
				cutOffPage = i;
				break;
			}
		}

		if (mPagerAdapter.getCutOffPage() != cutOffPage) {
			mPagerAdapter.setCutOffPage(cutOffPage);
			return true;
		}

		return false;
	}
	@Override
	public void setCountText(String text,int index){
	    TextView CountItem = (TextView) findViewById(R.id.caloriesCaculate);
	    TextView CountTotal = (TextView) findViewById(R.id.caloriesTotal);
	    if((CountItem.getText()).equals(""))
	    {
	    	CountItem.setText(CountItem.getText()+text);
	    	CountTotal.setText("="+CountItem.getText());
	    }
	    else
	    {
			String[] stringArray=new String[20];
			stringArray=((String)CountItem.getText()).split("\\+");
			if(index>(stringArray.length-1))
			{
				String tempText=stringArray[0];int total=Integer.parseInt(stringArray[0]);
				for(int i=1;i<(stringArray.length);i++)
				{
					tempText=tempText+"+"+stringArray[i];
					total+=Integer.parseInt(stringArray[i]);
				}
				CountItem.setText(tempText+"+"+text);
				total+=Integer.parseInt(text);
				CountTotal.setText("="+total);
			}
			else
			{
				if(index<=(stringArray.length-1))
				{
					stringArray[index]=text;
					String tempText=stringArray[0];int total=Integer.parseInt(stringArray[0]);
					for(int i=1;i<(stringArray.length);i++)
					{
						tempText=tempText+"+"+stringArray[i];
						total+=Integer.parseInt(stringArray[i]);
					}
					CountItem.setText(tempText);
					CountTotal.setText("="+total);
				}
			}
	    }
	}
	public void setAddCountText(int page)
	{
		TextView CountItem = (TextView) findViewById(R.id.caloriesCaculate);
		String text=(String)CountItem.getText();
		String[] stringArray=new String[20];
		stringArray=text.split("\\+");
		if(page!=(stringArray.length-1))
		{
			String tempText="";
			for(int i=0;i<(stringArray.length-1);i++)
			{
				tempText=tempText+"+"+stringArray[i];
			}
			CountItem.setText(tempText);
		}
	}
	public class MyPagerAdapter extends FragmentStatePagerAdapter {
		private int mCutOffPage;
		private Fragment mPrimaryItem;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			if (i >= mCurrentPageSequence.size()) {
				return new ReviewFragment();
			}

			return mCurrentPageSequence.get(i).createFragment();
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO: be smarter about this
			if (object == mPrimaryItem) {
				// Re-use the current fragment (its position never changes)
				return POSITION_UNCHANGED;
			}

			return POSITION_NONE;
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			super.setPrimaryItem(container, position, object);
			mPrimaryItem = (Fragment) object;
		}

		@Override
		public int getCount() {
			return Math.min(mCutOffPage + 1, mCurrentPageSequence == null ? 1
					: mCurrentPageSequence.size() + 1);
		}

		public void setCutOffPage(int cutOffPage) {
			if (cutOffPage < 0) {
				cutOffPage = Integer.MAX_VALUE;
			}
			mCutOffPage = cutOffPage;
		}

		public int getCutOffPage() {
			return mCutOffPage;
		}
	}
}
