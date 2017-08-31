package com.think360.sosimply.model.adapter_items;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.IDraggable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.holder.StringHolder;
import com.think360.sosimply.R;
import com.think360.sosimply.model.getavailibility.Time;
import com.think360.sosimply.ui.fragments.AvailabilityFragment;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mikepenz on 28.12.15.
 */
public class AvailabilityItem extends AbstractItem<AvailabilityItem, AvailabilityItem.ViewHolder> implements IDraggable<AvailabilityItem, IItem> {

    public String header;
    public StringHolder name;

    public Date getDescription() {
        return description;
    }

    public Date description;
    public String time;
    public String zone;
    public int availabilityStatus;

    public Time getItem() {
        return item;
    }

    public Time item;

    private boolean mIsDraggable = true;

    public String getAvalabilityId() {
        return avalabilityId;
    }

    private String avalabilityId;

    public AvailabilityItem withHeader(String header) {
        this.header = header;
        return this;
    }

    public AvailabilityItem withItem(Time item) {
        this.item = item;
        return this;
    }

    public AvailabilityItem withTime(String time) {
        this.time = time;
        return this;
    }


    public AvailabilityItem withAvalibalityStatus(int status) {

        this.availabilityStatus = status;

        return this;
    }


    public AvailabilityItem withZone(String zone) {
        this.zone = zone;
        return this;
    }

    public AvailabilityItem withName(@StringRes int NameRes) {
        this.name = new StringHolder(NameRes);
        return this;
    }

    public AvailabilityItem withDescription(Date description) {
        this.description = description;
        return this;
    }

    public AvailabilityItem withAvalibalityId(String avalabilityId) {
        this.avalabilityId = avalabilityId;
        return this;

    }

    @Override
    public boolean isDraggable() {
        return mIsDraggable;
    }

    @Override
    public AvailabilityItem withIsDraggable(boolean draggable) {
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

        return R.layout.single_item_availabality_list;
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
        //StringHolder.applyTo(zone, viewHolder.tvZone);
        viewHolder.tvZone.setText(zone );
        viewHolder.tvTime.setText(time);

        switch (availabilityStatus) {
            case AvailabilityFragment.State.ASSIGNED:
                viewHolder.ivAvalibalityStatus.setVisibility(View.VISIBLE);
                viewHolder.randomNumber.setVisibility(View.VISIBLE);
                viewHolder.randomNumber.setText(item.getRandonno());
                viewHolder.ivAvalibalityStatus.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.assignd_state));
                break;
            case AvailabilityFragment.State.NOT_ASSIGNED:
                viewHolder.randomNumber.setVisibility(View.INVISIBLE);
                viewHolder.ivAvalibalityStatus.setVisibility(View.INVISIBLE);
                break;
            case AvailabilityFragment.State.RUNNING:
                viewHolder.randomNumber.setVisibility(View.VISIBLE);
                viewHolder.ivAvalibalityStatus.setVisibility(View.VISIBLE);
                viewHolder.randomNumber.setText(item.getRandonno());
                viewHolder.ivAvalibalityStatus.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.running));

                break;
        }
   /*     if (Sta) {
            viewHolder.ivAvalibalityStatus.setVisibility(View.VISIBLE);
            viewHolder.ivAvalibalityStatus.setImageDrawable(ContextCompat.getDrawable(viewHolder.itemView.getContext(), R.drawable.assignd_state));
            //viewHolder.randomNumber.setText();
        } else {
            viewHolder.ivAvalibalityStatus.setVisibility(View.INVISIBLE);
        }*/

        //set the text for the id or hide
        // StringHolder.applyToOrHide(id, viewHolder.id);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        ///  holder.zone.setText(null);
        //  holder.id.setText(null);
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
      /*  @BindView(R.id.material_drawer_name)
        TextView zone;
        @BindView(R.id.material_drawer_description)
        TextView id;*/

        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvZone)
        TextView tvZone;
        @BindView(R.id.randomNumber)
        TextView randomNumber;


        @BindView(R.id.ivAvalibalityStatus)
        ImageView ivAvalibalityStatus;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
