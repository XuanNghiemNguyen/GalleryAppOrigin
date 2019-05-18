package com.client.galleryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimelineAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<JDate> timeline;

    public TimelineAdapter(Context context, int layout, ArrayList<JDate> timeline) {
        this.context = context;
        this.layout = layout;
        this.timeline = timeline;
    }

    @Override
    public int getCount() {
        return timeline.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txtDateMonth = (TextView) convertView.findViewById(R.id.txtDateMonth);
            viewHolder.gridViewOneMonth = (ExpandableHeightGridView) convertView.findViewById(R.id.gridViewOneMonth);
            viewHolder.gridViewOneMonth.setExpanded(true);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (TimelineAdapter.ViewHolder) convertView.getTag();
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(timeline.get(position).getDate());
        int year = calendar.get(Calendar.YEAR);
        //Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH) + 1;
        String mDateMonth = "Tháng " + month + ", " + year;

        viewHolder.txtDateMonth.setText(mDateMonth);

        // Load ảnh vào gridview

        OneMonthImageAdapter imagesAdapter = new OneMonthImageAdapter(context, timeline.get(position).getFileImage());
        viewHolder.gridViewOneMonth.setAdapter(imagesAdapter);

        //

        return convertView;
    }

    private class ViewHolder {
        TextView txtDateMonth;
        ExpandableHeightGridView gridViewOneMonth;
    }

}