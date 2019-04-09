package com.brest.romark.tictactoe.callback;

import com.brest.romark.tictactoe.entity.User;

import java.util.List;

public interface DbCallback {
    void onUserDeleted();

    void onUsersInserted(Long result);

    void onUsersReceived(List<User> result);

    void onUserCountReceived(Boolean doesExist);
}
