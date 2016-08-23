package sk.michalvozny.hitka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.nio.charset.Charset;

public class MainListActivity extends AppCompatActivity {

    ListView foodListLv;
    ArrayAdapter<String> foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        String[] foodList = getResources().getStringArray(R.array.food_list);

        foodListLv = (ListView) findViewById(R.id.foodList);
        foodListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, foodList);
        foodListLv.setAdapter(foodListAdapter);

        EditText searchField = (EditText) findViewById(R.id.searchField);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateFoodList(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateFoodList(CharSequence searchWord) {
        foodListAdapter.getFilter().filter(searchWord);
    }
}
