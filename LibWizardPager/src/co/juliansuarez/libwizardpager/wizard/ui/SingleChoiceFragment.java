package co.juliansuarez.libwizardpager.wizard.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import co.juliansuarez.libwizardpager.R;
import co.juliansuarez.libwizardpager.wizard.model.Page;
import co.juliansuarez.libwizardpager.wizard.model.SingleFixedChoicePage;

public class SingleChoiceFragment extends ListFragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private List<String> mChoices;
    private String mKey;
    private Page mPage;

    public static SingleChoiceFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        SingleChoiceFragment fragment = new SingleChoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SingleChoiceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);

        SingleFixedChoicePage fixedChoicePage = (SingleFixedChoicePage) mPage;
        mChoices = new ArrayList<String>();
        for (int i = 0; i < fixedChoicePage.getOptionCount(); i++) {
            mChoices.add(fixedChoicePage.getOptionAt(i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        final ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_single_choice,
                android.R.id.text1,
                mChoices)
                {
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent){
        		TextView textView=(TextView)super.getView(position,convertView,parent);
        		
        		textView.setTextColor(Color.RED);
        		
        		return textView;
        	}
                });
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Pre-select currently selected item.
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String selection = mPage.getData().getString(Page.SIMPLE_DATA_KEY);
                for (int i = 0; i < mChoices.size(); i++) {
                    if (mChoices.get(i).equals(selection)) {
                        listView.setItemChecked(i, true);
                        break;
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mPage.getData().putString(Page.SIMPLE_DATA_KEY,
		getListAdapter().getItem(position).toString());
		mPage.notifyDataChanged();
		if(mPage.getTitle().equals("Order type"))
		{
			if(getListAdapter().getItem(position).toString().equals("Burrito"))
				mCallbacks.setCountText("290",0);
			else
			{
				if(getListAdapter().getItem(position).toString().equals("Burrito Bowl"))
					mCallbacks.setCountText("0",0);
				else
				{
					if(getListAdapter().getItem(position).toString().equals("Tacos"))
						mCallbacks.setCountText("180",0);
					else
					{
						if(getListAdapter().getItem(position).toString().equals("Salad"))
							mCallbacks.setCountText("10",0);
					}
				}
			}
		}
		else
		{
			if(mPage.getTitle().equals("Filling"))
			{
				if(getListAdapter().getItem(position).toString().equals("Chicken"))
					mCallbacks.setCountText("190",1);
				else
				{
					if(getListAdapter().getItem(position).toString().equals("Steak"))
						mCallbacks.setCountText("190",1);
					else
					{
						if(getListAdapter().getItem(position).toString().equals("Barbacoa"))
							mCallbacks.setCountText("170",1);
						else
						{
							if(getListAdapter().getItem(position).toString().equals("Carnitas"))
								mCallbacks.setCountText("190",1);
							else
							{
								if(getListAdapter().getItem(position).toString().equals("Veggie"))
									mCallbacks.setCountText("0",1);
							}
						}
					}
				}
			}
			else
			{
				if(mPage.getTitle().equals("Rice"))
				{
					if(getListAdapter().getItem(position).toString().equals("No Rice"))
						mCallbacks.setCountText("0",2);
					else
					{
						if(getListAdapter().getItem(position).toString().equals("White Rice"))
							mCallbacks.setCountText("170",2);
						else
						{
							if(getListAdapter().getItem(position).toString().equals("Brown Rice"))
								mCallbacks.setCountText("160",2);
						}
					}
				}
				else
				{
					if(mPage.getTitle().equals("Beans"))
					{
						if(getListAdapter().getItem(position).toString().equals("No Beans"))
							mCallbacks.setCountText("0",3);
						else
						{
							if(getListAdapter().getItem(position).toString().equals("Black Beans"))
								mCallbacks.setCountText("120",3);
							else
							{
								if(getListAdapter().getItem(position).toString().equals("Pinto Beans"))
									mCallbacks.setCountText("115",3);
							}
						}
					}
					else
					{
						if(mPage.getTitle().equals("Chips type"))
						{
							if(getListAdapter().getItem(position).toString().equals("No Chips"))
								mCallbacks.setCountText("0",5);
							else
							{
								if(getListAdapter().getItem(position).toString().equals("Chips and Guacamole"))
									mCallbacks.setCountText("720",5);
								else
								{
									if(getListAdapter().getItem(position).toString().equals("Chips"))
										mCallbacks.setCountText("570",5);
								}
							}
						}
						else
						{
							if(mPage.getTitle().equals("Salsa"))
							{
								if(getListAdapter().getItem(position).toString().equals("No Salsa"))
									mCallbacks.setCountText("0",6);
								else
								{
									if(getListAdapter().getItem(position).toString().equals("Roasted Chili-Corn Salsa"))
										mCallbacks.setCountText("80",6);
									else
									{
										if(getListAdapter().getItem(position).toString().equals("Fresh Tomato"))
											mCallbacks.setCountText("20",6);
										else
										{
											if(getListAdapter().getItem(position).toString().equals("Tomatillo-Green Chili Salsa"))
												mCallbacks.setCountText("15",6);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
	}	
}

