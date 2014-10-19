package co.juliansuarez.libwizardpager.wizard.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import co.juliansuarez.libwizardpager.R;
import co.juliansuarez.libwizardpager.wizard.model.MultipleFixedChoicePage;
import co.juliansuarez.libwizardpager.wizard.model.Page;

public class MultipleChoiceFragment extends ListFragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private List<String> mChoices;
    private Page mPage;

    public static MultipleChoiceFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MultipleChoiceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);

        MultipleFixedChoicePage fixedChoicePage = (MultipleFixedChoicePage) mPage;
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
                android.R.layout.simple_list_item_multiple_choice,
                android.R.id.text1,
                mChoices)   {
                	@Override
                	public View getView(int position, View convertView, ViewGroup parent){
                		TextView textView=(TextView)super.getView(position,convertView,parent);
                		
                		textView.setTextColor(Color.RED);
                		
                		return textView;
                	}
                        });
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Pre-select currently selected items.
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> selectedItems = mPage.getData().getStringArrayList(
                        Page.SIMPLE_DATA_KEY);
                if (selectedItems == null || selectedItems.size() == 0) {
                    return;
                }

                Set<String> selectedSet = new HashSet<String>(selectedItems);

                for (int i = 0; i < mChoices.size(); i++) {
                    if (selectedSet.contains(mChoices.get(i))) {
                        listView.setItemChecked(i, true);
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
        SparseBooleanArray checkedPositions = getListView().getCheckedItemPositions();
        ArrayList<String> selections = new ArrayList<String>();
        for (int i = 0; i < checkedPositions.size(); i++) {
            if (checkedPositions.valueAt(i)) {
                selections.add(getListAdapter().getItem(checkedPositions.keyAt(i)).toString());
            }
        }
        mPage.getData().putStringArrayList(Page.SIMPLE_DATA_KEY, selections);
        mPage.notifyDataChanged();
        if(mPage.getTitle().equals("Toppings"))
		{
        	int temp=0;
        	for (int i = 0; i < checkedPositions.size(); i++) {
                if (checkedPositions.valueAt(i)) {
                    if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Fajita Veggies"))
                    {
                    	temp+=20;
                    }
                    else
                    {
                    	if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Sour Cream"))
                        {
                        	temp+=120;
                        }
                    	else
                    	{
                    		if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Cheese"))
                            {
                            	temp+=100;
                            }
                    		else
                    		{
                    			if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Guacamole"))
                                {
                                	temp+=150;
                                }
                    			else
                    			{
                    				if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Lettuce"))
                                    {
                                    	temp+=0;
                                    }
                    			}
                    		}
                    	}
                    }
                } 
            }
        	mCallbacks.setCountText(""+temp,4);
		}
        else
        {
        	 if(mPage.getTitle().equals("Drinks"))
     		{
             	int temp=0;
             	for (int i = 0; i < checkedPositions.size(); i++) {
                     if (checkedPositions.valueAt(i)) {
                         if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("No Drink"))
                         {
                         	temp+=0;
                         }
                         else
                         {
                         	if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Fountain Soda Large"))
                             {
                             	temp+=180;
                             }
                         	else
                         	{
                         		if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Fountain Soda Small"))
                                 {
                                 	temp+=100;
                                 }
                         		else
                         		{
                         			if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Bottled Water"))
                                     {
                                     	temp+=0;
                                     }
                         			else
                         			{
                         				if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Nantucket Nectar-Apple"))
                                         {
                                         	temp+=50;
                                         }
                         				else
                             			{
                             				if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Nantucket Nectar-Peach Orange"))
                                             {
                                             	temp+=50;
                                             }
                             				else
                                 			{
                                 				if((getListAdapter().getItem(checkedPositions.keyAt(i)).toString()).equals("Nantucket Nectar-Pomegranate Cherry"))
                                                 {
                                                 	temp+=50;
                                                 }
                                 			}
                             			}
                         			}
                         		}
                         	}
                         }
                     } 
                 }
             	mCallbacks.setCountText(""+temp,7);
     		}
        }
    }
}
