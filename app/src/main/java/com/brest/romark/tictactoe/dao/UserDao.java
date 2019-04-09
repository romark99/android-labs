package com.brest.romark.tictactoe.dao;

import com.brest.romark.tictactoe.entity.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user ORDER BY date DESC")
    List<User> getAll();

    @Query("SELECT COUNT(*) FROM user WHERE login = :login")
    Integer userCount(String login);

    @Insert
    long insert(User user);

    @Delete
    void delete(User user);
}
