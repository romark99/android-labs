package com.brest.romark.tictactoe.callback;

import com.brest.romark.tictactoe.entity.User;
import com.brest.romark.tictactoe.entity.UserView;

import java.util.ArrayList;

public interface LoadUserJsonCallback {
    void onUserJsonReceived(User result);
}
