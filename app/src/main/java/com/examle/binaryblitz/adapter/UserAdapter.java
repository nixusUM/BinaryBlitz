package com.examle.binaryblitz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examle.binaryblitz.R;
import com.examle.binaryblitz.application.App;
import com.examle.binaryblitz.model.User;
import com.examle.binaryblitz.utils.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> usersList = new ArrayList<>();
    private final ItemClickListener listener;

    public UserAdapter(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.userName.setText(user.getFirstName() + " " + user.getLastName());
        holder.email.setText(user.getEmail());
        if (user.getAvatarUrl() == null || user.getAvatarUrl().isEmpty()) {
            holder.avatar.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Picasso.with(App.getInstance())
                    .load(user.getAvatarUrl())
                    .fit()
                    .into(holder.avatar);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.lyt_product_item)
        RelativeLayout lytProducts;
        @BindView(R.id.txt_name)
        TextView userName;
        @BindView(R.id.txt_email)
        TextView email;
        @BindView(R.id.img_avatar)
        ImageView avatar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            lytProducts.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }
}