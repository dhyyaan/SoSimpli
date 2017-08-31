package com.think360.sosimply.model.adapter_items;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.IDraggable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.holder.StringHolder;
import com.think360.sosimply.R;
import com.think360.sosimply.model.approved.Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mikepenz on 28.12.15.
 */
public class ApprovedScheduleItem extends AbstractItem<ApprovedScheduleItem, ApprovedScheduleItem.ViewHolder> implements IDraggable<ApprovedScheduleItem, IItem> {

    public String header;
    public StringHolder name;
    public StringHolder description;
    public StringHolder tvScheduleName;

    public Data getDatum() {
        return datum;
    }

    private Data datum;

    private String day, month, fromTime, endTime, zone, cityName;
    private boolean mIsDraggable = true;
    private String randomNumber;

    public ApprovedScheduleItem withHeader(String header) {
        this.header = header;
        return this;
    }


    public ApprovedScheduleItem withName(String Name) {
        this.name = new StringHolder(Name);
        return this;
    }

    public ApprovedScheduleItem withName(@StringRes int NameRes) {
        this.name = new StringHolder(NameRes);
        return this;
    }

    public ApprovedScheduleItem withDescription(String description) {
        this.description = new StringHolder(description);
        return this;
    }

    public ApprovedScheduleItem withDescription(@StringRes int descriptionRes) {
        this.description = new StringHolder(descriptionRes);
        return this;
    }

    @Override
    public boolean isDraggable() {
        return mIsDraggable;
    }

    @Override
    public ApprovedScheduleItem withIsDraggable(boolean draggable) {
        this.mIsDraggable = draggable;
        return this;
    }

    /**
     * defines the type defining this item. must be unique. preferably an id
     *
     * @return the type
     */
    @Override
    public int getType() {
        return R.id.fastadapter_sample_item_id;
    }

    /**
     * defines the layout which will be used for this item in the list
     *
     * @return the layout for this item
     */
    @Override
    public int getLayoutRes() {

        return R.layout.approved_schedule_item;
    }

    /**
     * binds the data of this item onto the viewHolder
     *
     * @param viewHolder the viewHolder of this item
     */
    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        //get the context
        Context ctx = viewHolder.itemView.getContext();

        //set the background for the item
        // UIUtils.setBackground(viewHolder.view, FastAdapterUIUtils.getSelectableBackground(ctx, ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.colorAppLightGray), true));
        //set the text for the zone
        StringHolder.applyTo(tvScheduleName, viewHolder.tvScheduleName);
        //set the text for the id or hide
        //  StringHolder.applyToOrHide(description, viewHolder.description);
        viewHolder.tvDate.setText(day);
        viewHolder.tvMonth.setText(month);
        viewHolder.tvTime.setText(fromTime + " | " + endTime);
        viewHolder.tvZone.setText(zone + " | " + cityName);
        viewHolder.tvUniqueNumber.setText("$" + randomNumber);
        // viewHolder.tvUniqueNumber.setText(day);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.tvScheduleName.setText(null);

    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    public ApprovedScheduleItem withItem(Data datum) {
        this.day = datum.getDay();
        this.month = datum.getMonth();
        this.fromTime = datum.getFrom_time();
        this.endTime = datum.getTo_time();
        this.zone = datum.getZone();
        this.cityName = datum.getCityname();
        this.tvScheduleName = new StringHolder(datum.getSchdule_name());
        this.randomNumber = datum.getRandonno();
        this.datum = datum;
        return this;

    }


    /**
     * our ViewHolder
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDate)
        protected TextView tvDate;
        @BindView(R.id.tvMonth)
        protected TextView tvMonth;
        @BindView(R.id.tvTime)
        protected TextView tvTime;
        @BindView(R.id.tvZone)
        protected TextView tvZone;

        @BindView(R.id.tvUniqueNumber)
        protected TextView tvUniqueNumber;
        protected View view;
        @BindView(R.id.tvScheduleName)
        TextView tvScheduleName;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
