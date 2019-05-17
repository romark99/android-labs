package com.brest.romark.tictactoe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brest.romark.tictactoe.MyApplication;
import com.brest.romark.tictactoe.R;
import com.brest.romark.tictactoe.adapter.RepoListAdapter;
import com.brest.romark.tictactoe.entity.UserView;
import com.squareup.picasso.Picasso;

public class UserViewFragment extends Fragment {

    private UserView userView;

    private RecyclerView rvRepos;

    public RecyclerView getRvRepos() {
        return rvRepos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_view, container, false);
        rvRepos = view.findViewById(R.id.rvRepos);

        TextView tvLogin = view.findViewById(R.id.tvLogin);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvBlog = view.findViewById(R.id.tvBlog);
        TextView tvLocation = view.findViewById(R.id.tvLocation);
        TextView tvCompany = view.findViewById(R.id.tvCompany);
        TextView tvFollowers = view.findViewById(R.id.tvFollowers);
        TextView tvFollowing = view.findViewById(R.id.tvFollowing);
        ImageView imageView = view.findViewById(R.id.bigImageView);

        Picasso.get().load(userView.getAvatar_url()).into(imageView);
        tvLogin.setText(userView.getLogin());
        tvName.setText(userView.getName());
        tvEmail.setText(userView.getEmail());
        tvBlog.setText(userView.getBlog());
        tvLocation.setText(userView.getLocation());
        tvCompany.setText(userView.getCompany());
        tvFollowers.setText(String.valueOf(userView.getFollowers()));
        tvFollowing.setText(String.valueOf(userView.getFollowing()));


        RepoListAdapter adapter = new RepoListAdapter(MyApplication.getAppContext(), userView.getRepos());
        rvRepos.setAdapter(adapter);
        rvRepos.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        return view;
    }

    public UserViewFragment(UserView userView) {
        this.userView = userView;
    }
}
