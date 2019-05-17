package com.brest.romark.tictactoe.callback;

import com.brest.romark.tictactoe.entity.Repo;

import java.util.ArrayList;

public interface LoadRepoJsonCallback {
    void onRepoJsonReceived(ArrayList<Repo> repos);
}
