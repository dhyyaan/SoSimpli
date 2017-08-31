package com.think360.sosimply.model.adapter_items;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.IDraggable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.holder.StringHolder;
import com.mikepenz.materialize.util.UIUtils;
import com.think360.sosimply.R;
import com.think360.sosimply.model.schedule.Datum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mikepenz on 28.12.15.
 */
public class CompletedScheduleItem extends AbstractItem<CompletedScheduleItem, CompletedScheduleItem.ViewHolder> implements IDraggable<CompletedScheduleItem, IItem> {

    public String header;
    public StringHolder name;
    public StringHolder description;

    public Datum getDatum() {
        return datum;
    }

    public CompletedScheduleItem setDatum(Datum datum) {
        this.datum = datum;
        return this;
    }

    public Datum datum;

    private boolean mIsDraggable = true;

    public CompletedScheduleItem withHeader(String header) {
        this.header = header;
        return this;
    }

    public CompletedScheduleItem withName(String Name) {
        this.name = new StringHolder(Name);
        return this;
    }

    public CompletedScheduleItem withName(@StringRes int NameRes) {
        this.name = new StringHolder(NameRes);
        return this;
    }

    public CompletedScheduleItem withDescription(String description) {
        this.description = new StringHolder(description);
        return this;
    }

    public CompletedScheduleItem withDescription(@StringRes int descriptionRes) {
        this.description = new StringHolder(descriptionRes);
        return this;
    }

    @Override
    public boolean isDraggable() {
        return mIsDraggable;
    }

    @Override
    public CompletedScheduleItem withIsDraggable(boolean draggable) {
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

        return R.layout.completed_schedule_item;
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
     //   UIUtils.setBackground(viewHolder.view, FastAdapterUIUtils.getSelectableBackground(ctx, ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.colorAppLightGray), true));
        //set the text for the zone
        StringHolder.applyTo(new StringHolder(datum.getSchduleZone()), viewHolder.tvZone);
        //set the text for the id or hide
        StringHolder.applyToOrHide(new StringHolder(datum.getSchduleDate()), viewHolder.tvDate);
        StringHolder.applyToOrHide(new StringHolder(datum.getTimeFrom() + " - " + datum.getTimeTo()), viewHolder.tvTime);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.tvZone.setText(null);
        holder.tvTime.setText(null);
        holder.tvDate.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    /**
     * our ViewHolder
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;
        @BindView(R.id.tvZone)
        TextView tvZone;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvDate)
        TextView tvDate;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
