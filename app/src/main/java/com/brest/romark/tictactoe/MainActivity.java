package com.brest.romark.tictactoe;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brest.romark.tictactoe.callback.InsertAllDbCallback;
import com.brest.romark.tictactoe.callback.SelectAllDbCallback;
import com.brest.romark.tictactoe.callback.LoadJsonUserCallback;
import com.brest.romark.tictactoe.callback.UserExistsDbCallback;
import com.brest.romark.tictactoe.dao.UserDao;
import com.brest.romark.tictactoe.database.GithubDatabase;
import com.brest.romark.tictactoe.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


public class MainActivity extends AppCompatActivity implements LoadJsonUserCallback,
        SelectAllDbCallback, InsertAllDbCallback, UserExistsDbCallback {

    private Button myBtn;
    private EditText eLogin;

    private List<User> users;
    private User currentUser;
    private UserListAdapter adapter;

    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        users = new ArrayList<>();

        GithubDatabase db = Room.databaseBuilder(getApplicationContext(),
                GithubDatabase.class, "github-db").build();

        userDao = db.userDao();

        setContentView(R.layout.activity_main);
        myBtn = findViewById(R.id.myBtn);
        eLogin = findViewById(R.id.eLogin);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadJSONTask loadJSONTask = new LoadJSONTask();
                loadJSONTask.callback = MainActivity.this;
                loadJSONTask.execute("https://api.github.com/users/" + eLogin.getText().toString());
            }
        });

        SelectAllDbTask selectAllDbTask = new SelectAllDbTask(userDao);
        selectAllDbTask.callback = MainActivity.this;
        selectAllDbTask.execute();

        RecyclerView rvUsers = findViewById(R.id.recyclerView);
        adapter = new UserListAdapter(users);
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onJsonReceived(User user) {
        currentUser = user;
        UserExistsDbTask userExistsDbTask = new UserExistsDbTask(userDao);
        userExistsDbTask.callback = this;
        userExistsDbTask.execute(user.getLogin());
    }

    @Override
    public void onUsersReceived(List<User> users) {
        this.users = users;
        adapter.setmUsers(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUsersInserted(Long result) {
        SelectAllDbTask selectAllDbTask = new SelectAllDbTask(userDao);
        selectAllDbTask.callback = MainActivity.this;
        selectAllDbTask.execute();
    }

    @Override
    public void onUserCountReceived(Boolean doesExist) {
        if (!doesExist) {
            InsertAllDbTask insertAllDbTask = new InsertAllDbTask(userDao);
            insertAllDbTask.callback = this;
            insertAllDbTask.execute(currentUser);
            currentUser = null;
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "The user is already in database.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadJSONTask extends AsyncTask<String, Void, User> {

        private LoadJsonUserCallback callback = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("task", "PreExecute");
        }

        @Override
        protected User doInBackground(String... strings) {
            User userSearchResult = null;
            Gson gson = new Gson();
            try {
                InputStream stream = (new URL(strings[0])).openConnection().getInputStream();
                Reader br = new BufferedReader(new InputStreamReader(stream));
                publishProgress();
                userSearchResult = gson.fromJson(br, new TypeToken<User>() {}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("task", "DoInBackground");
            return userSearchResult;
        }

        @Override
        protected void onPostExecute(User userSearchResult) {
            super.onPostExecute(userSearchResult);

            if (userSearchResult != null) {
                userSearchResult.setDate(System.currentTimeMillis());
                Log.d("result", userSearchResult.toString());
                callback.onJsonReceived(userSearchResult);
                callback = null;
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "The user is not found.", Toast.LENGTH_SHORT);
                toast.show();
            }
            Log.d("task", "OnPostExecute");
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SelectAllDbTask extends AsyncTask<Void, Void, List<User>> {

        private SelectAllDbCallback callback = null;

        UserDao userDao;

        SelectAllDbTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAll();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            if (users != null && users.size() != 0) {
                Log.d("users", users.toString());
                callback.onUsersReceived(users);
            }
            else {
                Log.d("users", "users == null OR empty");
                callback.onUsersReceived(new ArrayList<User>());
            }
            callback = null;
            Log.d("users", "OnPostExecute");
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertAllDbTask extends AsyncTask<User, Void, Long> {

        private InsertAllDbCallback callback = null;

        UserDao userDao;

        InsertAllDbTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Long doInBackground(User... users) {
            return userDao.insert(users[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            callback.onUsersInserted(id);
            callback = null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UserExistsDbTask extends AsyncTask<String, Void, Boolean> {

        private UserExistsDbCallback callback = null;

        UserDao userDao;

        UserExistsDbTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            int count = userDao.userCount(strings[0]);
            return count == 1;
        }

        @Override
        protected void onPostExecute(Boolean doesExist) {
            callback.onUserCountReceived(doesExist);
            callback = null;
        }
    }
}
