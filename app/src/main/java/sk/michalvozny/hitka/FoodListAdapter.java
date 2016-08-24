package sk.michalvozny.hitka;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

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

        String foodItem = foodList[position];
        String[] itemParts = foodItem.split("#");

        value.setText(itemParts[0]);
        name.setText(itemParts[1]);

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
                    if (food.toLowerCase().contains(prefix.toString().toLowerCase())){
                        newList.add(food);
                        Log.d("TAG", "add food: " + food);
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
