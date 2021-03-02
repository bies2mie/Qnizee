package com.badrul.qnitibox;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {


    private Context mCtx;
    private List<Order> orderList;
    private OnItemClicked onClick;


    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public OrderAdapter(Context mCtx, List<Order> orderList) {
        this.mCtx = mCtx;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.order_list, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {
        Order order = orderList.get(position);

        RequestOptions options = new RequestOptions().centerCrop().dontAnimate().placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher);
        Glide
                .with(mCtx)
                .load(order.getFoodLink()).apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                return false;
            }
        })
                .into(holder.imageView);


        holder.textViewTitle.setText(order.getOrderType()); //getName
        holder.textViewShortDesc.setText(order.getOrderDay()); //GetICnum
        holder.textViewRating.setText(order.getOrderDate()); //getStatus
        holder.textViewPrice.setText("Pickup: "+order.getPuLocation()+"  "+order.getPuTime()); //getTotalPrice

        holder.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        ProgressBar progressBar;
        RelativeLayout test;

        public OrderViewHolder(View itemView) {
            super(itemView);

            test=itemView.findViewById(R.id.testing);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
    //This method will filter the list
//here we are passing the filtered data
//and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<Order> filterdNames) {
        this.orderList = filterdNames;
        notifyDataSetChanged();
    }
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}

