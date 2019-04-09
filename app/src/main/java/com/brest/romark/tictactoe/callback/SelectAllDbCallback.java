package com.brest.romark.tictactoe.callback;

import com.brest.romark.tictactoe.entity.User;

import java.util.List;

public interface SelectAllDbCallback {
    void onUsersReceived(List<User> result);
}
