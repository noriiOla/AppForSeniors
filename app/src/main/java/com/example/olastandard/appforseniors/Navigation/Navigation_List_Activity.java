package com.example.olastandard.appforseniors.Navigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.olastandard.appforseniors.MainActivity;
import com.example.olastandard.appforseniors.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Navigation_List_Activity extends MainActivity {
    private ListView list ;
    private ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_navigation__list_);
        initToolbar();


        list = (ListView) findViewById(R.id.listView1);

        String cars[] = {"Mercedes", "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen"};

        ArrayList<String> carL = new ArrayList<String>();
        carL.addAll( Arrays.asList(cars) );

        adapter = new ArrayAdapter<String>(this, R.layout.navigation_list_item, carL);

        list.setAdapter(adapter);

    }

    private void initToolbar() {
        showBackButton();
        hideRightButton();
        setTitle(R.string.choose_from_contact);
    }

}



/*


package pl.listviewexample;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List extends Activity {

	private ListView list ;
	private ArrayAdapter<String> adapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		list = (ListView) findViewById(R.id.listView1);

		String cars[] = {"Mercedes", "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen"};

		ArrayList<String> carL = new ArrayList<String>();
	    carL.addAll( Arrays.asList(cars) );

	    adapter = new ArrayAdapter<String>(this, R.layout.row, carL);

	    list.setAdapter(adapter);
	}
}

 */