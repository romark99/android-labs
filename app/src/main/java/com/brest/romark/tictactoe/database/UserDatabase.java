package com.brest.romark.tictactoe.database;

import com.brest.romark.tictactoe.dao.UserDao;
import com.brest.romark.tictactoe.entity.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
//    public abstract UserDao userDao();
}
