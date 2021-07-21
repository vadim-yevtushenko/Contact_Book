package com.example.contactbook;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.nio.channels.Selector;
import java.util.List;
import java.util.Map;

public class ContactAdapter extends SimpleAdapter {
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */

    private int resource;
    private LayoutInflater inflater;
    private List<? extends Map<String, ?>> data;
    private int count;
    private String firstCharKey;

    public ContactAdapter(Context context,
                          List<? extends Map<String, ?>> data,
                          int resource,
                          String[] from,
                          int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        View container = inflater.inflate(resource, parent, false);
        CircleTextView tvAvatar = container.findViewById(R.id.tvAvatar);
        ImageView ivAvatar = container.findViewById(R.id.ivAvatar);
        TextView tvName = container.findViewById(R.id.tvFirstName);
        TextView tvLastName = container.findViewById(R.id.tvLastName);

        Map<String, String> contactMap = (Map<String, String>) data.get(position);

        tvName.setText(contactMap.get(MainActivity.KEY_FIRST_NAME));
        tvLastName.setText(contactMap.get(MainActivity.KEY_SECOND_NAME));

        if (contactMap.get(MainActivity.KEY_AVATAR).equals("0")) {
            tvAvatar.setText(getFirstChar(contactMap));
        } else {
            tvAvatar.setVisibility(TextView.INVISIBLE);
            ivAvatar.setImageResource(Integer.parseInt(contactMap.get(MainActivity.KEY_AVATAR)));
        }

        tvAvatar.setColorBackground(Color.parseColor(getColor()));
        return container;
    }

    private String getFirstChar(Map<String, String> contactMap) {
        String firstChar = String.valueOf(contactMap.get(firstCharKey).charAt(0));
        return firstChar;
    }

    private String getColor() {

        if (count == Colors.values().length){
            count = 0;
        }
        return Colors.values()[count++].getColor();
    }

    public void setFirstCharKey(String firstCharKey) {
        this.firstCharKey = firstCharKey;
    }
}
