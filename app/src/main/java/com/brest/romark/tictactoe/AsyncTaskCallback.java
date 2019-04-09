package com.brest.romark.tictactoe;

import com.brest.romark.tictactoe.entity.User;

import java.util.ArrayList;

public interface AsyncTaskCallback {
    void onResultReceived(ArrayList<User> result);
}
