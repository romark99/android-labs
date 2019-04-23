package com.brest.romark.tictactoe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class UserListFragment extends Fragment {

    private RecyclerView rvUsers;

    public RecyclerView getRvUsers() {
        return rvUsers;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list, container, false);
        rvUsers = view.findViewById(R.id.recyclerView);
        return view;
    }
}
