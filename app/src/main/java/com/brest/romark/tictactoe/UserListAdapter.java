package com.brest.romark.tictactoe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brest.romark.tictactoe.entity.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<User> mUsers;

    UserListAdapter(List<User> users) {
        mUsers = users;
    }

    void setmUsers(List<User> mUsers) {
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View userView = inflater.inflate(R.layout.user_list_item, viewGroup, false);

        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder userViewHolder, int i) {
        User user = mUsers.get(i);

        TextView urlView = userViewHolder.urlItemView;
        urlView.setText(user.getHtml_url());

        TextView loginView = userViewHolder.loginItemView;
        loginView.setText(user.getLogin());

        ImageView imageView = userViewHolder.imageView;
        Picasso.get().load(user.getAvatar_url()).into(imageView);
    }

    @Override
    public int getItemCount() {
        Log.d("mine", mUsers.toString());
        return mUsers.size();
    }

    User getUserAt(int position) {
        return mUsers.get(position);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView loginItemView;

        TextView urlItemView;

        ImageView imageView;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            urlItemView = itemView.findViewById(R.id.tvUrl);
            loginItemView = itemView.findViewById(R.id.tvLogin);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
