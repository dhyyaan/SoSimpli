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
import com.think360.sosimply.model.sos.Datum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mikepenz on 28.12.15.
 */
public class SoSItem extends AbstractItem<SoSItem, SoSItem.ViewHolder> implements IDraggable<SoSItem, IItem> {

    public String header;
    public StringHolder zone;
    public StringHolder id;
    public String date;

    public Datum getDatum() {
        return datum;
    }

    public SoSItem setDatum(Datum datum) {
        this.datum = datum;
        return this;
    }

    public Datum datum;

    private boolean mIsDraggable = true;

    public SoSItem withHeader(String header) {
        this.header = header;
        return this;
    }

    public SoSItem withZone(String zone) {
        this.zone = new StringHolder(zone);
        return this;
    }

    public SoSItem withZone(@StringRes int NameRes) {
        this.zone = new StringHolder(NameRes);
        return this;
    }

    public SoSItem withid(String id) {
        this.id = new StringHolder(id);
        return this;
    }

    public SoSItem withid(@StringRes int descriptionRes) {
        this.id = new StringHolder(descriptionRes);
        return this;
    }

    @Override
    public boolean isDraggable() {
        return mIsDraggable;
    }

    @Override
    public SoSItem withIsDraggable(boolean draggable) {
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

        return R.layout.sos_item;
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
        // StringHolder.applyTo(zone, viewHolder.name);
        //set the text for the id or hide
        // StringHolder.applyToOrHide(id, viewHolder.description);

        viewHolder.tvZone.setText(datum.getZones());
        viewHolder.tvDate.setText(datum.getSos_time());
        viewHolder.tvTime.setText(datum.getTimeFrom() + " | " + datum.getTimeTo());
        // StringHolder.applyTo(datum.getZones(), viewHolder.tvZone);
        // StringHolder.applyTo(zone, viewHolder.tvDate);
        //StringHolder.applyTo(zone, viewHolder.tvTime);

    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.tvZone.setText(null);
        holder.tvDate.setText(null);
        holder.tvTime.setText(null);
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
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTime)
        TextView tvTime;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
