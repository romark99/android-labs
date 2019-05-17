package com.brest.romark.tictactoe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brest.romark.tictactoe.MyApplication;
import com.brest.romark.tictactoe.R;
import com.brest.romark.tictactoe.adapter.UserListAdapter;

public class UserListFragment extends Fragment {

    private RecyclerView rvUsers;

    public RecyclerView getRvUsers() {
        return rvUsers;
    }

    private UserListAdapter adapter;

    private LinearLayoutManager layout;

    private static int whatTime = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list, container, false);
        rvUsers = view.findViewById(R.id.recyclerView);
        rvUsers.setAdapter(adapter);
//        if (whatTime == 0) {
            rvUsers.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
//            whatTime++;
//        }
        return view;
    }

    public UserListFragment(UserListAdapter adapter, LinearLayoutManager layout) {
        this.adapter = adapter;
        this.layout = layout;
    }
}
