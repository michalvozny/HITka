package sk.michalvozny.hitka;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vozny on 24. 8. 2016.
 */
class FoodListAdapter extends BaseAdapter implements Filterable{

    private FoodListFilter filter;
    private final LayoutInflater inflater;
    private String[] foodList, foodListBase;

    public FoodListAdapter(String[] foodList, Context context){
        this.foodList = foodList;
        this.foodListBase = foodList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return foodList.length;
    }

    @Override
    public Object getItem(int position) {
        return foodList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView value = (TextView) view.findViewById(R.id.listItemValue);
        TextView name = (TextView) view.findViewById(R.id.listItemName);
        TextView note = (TextView) view.findViewById(R.id.listItemNote);

        String foodItem = foodList[position];
        String[] itemParts = foodItem.split("#");

        note.setText(itemParts[1]);
        value.setText(itemParts[2]);
        name.setText(itemParts[3]);

        int c = Color.rgb(0, 0, 0);
        int val = Integer.parseInt(itemParts[2]);
        switch (val){
            case 0: c = Color.rgb(0, 200, 0);
                break;
            case 1: c = Color.rgb(255, 180, 0);
                break;
            case 2: c = Color.rgb(255, 120, 0);
                break;
            case 3:
                c = Color.rgb(255, 0, 0);
                break;
        }

        value.setTextColor(c);

        return view;
    }

    @Override
    public Filter getFilter() {
        return filter == null ? new FoodListFilter() : filter;
    }

    private class FoodListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0){
                results.values = foodListBase;
                results.count = foodListBase.length;
            }
            else{
                ArrayList<String> newList = new ArrayList<>();
                for (String food : foodListBase) {
                    //Log.d("TAG", "add food: " + Normalizer.normalize(food.toLowerCase(), Normalizer.Form.NFD));
                    if (Normalizer.normalize(food.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").contains(prefix.toString().toLowerCase())){
                        newList.add(food);
                    }
                }

                results.count = newList.size();
                results.values = newList.toArray();
            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodList = Arrays.copyOf((Object[]) results.values, results.count, String[].class);
            notifyDataSetChanged();
        }
    }
}
