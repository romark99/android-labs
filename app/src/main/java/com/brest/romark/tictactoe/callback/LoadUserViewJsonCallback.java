package com.brest.romark.tictactoe.callback;

import com.brest.romark.tictactoe.entity.User;
import com.brest.romark.tictactoe.entity.UserView;

public interface LoadUserViewJsonCallback {
    void onUserViewJsonReceived(UserView result);
}
