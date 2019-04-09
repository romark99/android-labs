package com.brest.romark.tictactoe.callback;

import com.brest.romark.tictactoe.entity.User;

import java.util.ArrayList;

public interface LoadJsonUserCallback {
    void onJsonReceived(User result);
}
