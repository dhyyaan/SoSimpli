package com.think360.sosimpli;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.fastadapter.AbstractAdapter;
import com.mikepenz.fastadapter.IItem;
import com.think360.sosimpli.model.adapter_items.SimpleItem;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mikepenz on 30.12.15.
 * This is a FastAdapter adapter implementation for the awesome Sticky-Headers lib by timehop
 * https://github.com/timehop/sticky-headers-recyclerview
 */
public class StickyHeaderAdapter extends AbstractAdapter implements StickyRecyclerHeadersAdapter {
    @Override
    public long getHeaderId(int position) {
        IItem item = getItem(position);

        //in our sample we want a separate header per first letter of our items
        //this if is not necessary for your code, we only use it as this sticky header is reused for different item implementations
        if (item instanceof SimpleItem && ((SimpleItem) item).header != null) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/dd/MM");

            Date date = null;
            try {
                date = formatter.parse(((SimpleItem) item).header);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String strDate = formatter.format(date);
            System.out.println("Date Format with MM/dd/yyyy : " + strDate);

            return date.getTime();
        }
       /* else if (item instanceof SimpleSubItem && ((SimpleSubItem) item).header != null) {
            return ((SimpleSubItem) item).header.charAt(0);
        } else if (item instanceof SimpleSubExpandableItem && ((SimpleSubExpandableItem) item).header != null) {
            return ((SimpleSubExpandableItem) item).header.charAt(0);
        }*/
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        //we create the view for the header
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        LinearLayout linearLayout = (LinearLayout) holder.itemView;

        TextView textView = (TextView) linearLayout.getChildAt(1);


        IItem item = getItem(position);
        if (item instanceof SimpleItem && ((SimpleItem) item).header != null) {
            //based on the position we set the headers text
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/dd/MM");

            Date date = null;
            try {
                date = formatter.parse(((SimpleItem) item).header);
            } catch (ParseException e) {
                e.printStackTrace();
            }


          String s=  DateUtils.formatDateTime(holder.itemView.getContext(), date.getTime(),  DateUtils.FORMAT_SHOW_DATE |
                    DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_ALL);


            textView.setText(s);
        }
       /* else if (item instanceof SimpleSubItem && ((SimpleSubItem) item).header != null) {
            //based on the position we set the headers text
            textView.setText(String.valueOf(((SimpleSubItem) item).header.charAt(0)));
        } else if (item instanceof SimpleSubExpandableItem && ((SimpleSubExpandableItem) item).header != null) {
            //based on the position we set the headers text
            textView.setText(String.valueOf(((SimpleSubExpandableItem) item).header.charAt(0)));
        }*/
       // holder.itemView.setBackgroundColor(getRandomColor());
    }

    //just to prettify things a bit
    private int getRandomColor() {

      return   R.color.colorAppVeryLightGrey;
     /*   SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });
        */
    }

    /**
     * REQUIRED FOR THE FastAdapter. Set order to < 0 to tell the FastAdapter he can ignore this one.
     **/

    /**
     * @return
     */
    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public List<IItem> getAdapterItems() {
        return null;
    }

    @Override
    public IItem getAdapterItem(int position) {
        return null;
    }

    @Override
    public int getAdapterPosition(IItem item) {
        return -1;
    }

    @Override
    public int getAdapterPosition(long identifier) {
        return -1;
    }

    @Override
    public int getGlobalPosition(int position) {
        return -1;
    }

}
