package com.brest.romark.tictactoe;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.brest.romark.tictactoe.database.UserDatabase;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


public class MainActivity extends AppCompatActivity implements AsyncTaskCallback {

    private Button myBtn;

    private ArrayList<User> users;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        users = new ArrayList<>();

//        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
//                UserDatabase.class, "github-db").build();

//        RecyclerView rvUsers = findViewById(R.id.recyclerView);
//        UserListAdapter adapter = new UserListAdapter(users);
//        rvUsers.setAdapter(adapter);
//        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        setContentView(R.layout.activity_main);
        myBtn = findViewById(R.id.myBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadJSONTask loadJSONTask = new LoadJSONTask();
                loadJSONTask.resultCallback = MainActivity.this;
                loadJSONTask.execute("https://api.github.com/users");
            }
        });
        RecyclerView rvUsers = findViewById(R.id.recyclerView);
        adapter = new UserListAdapter(users);
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResultReceived(ArrayList<User> result) {
        adapter.setmUsers(result);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadJSONTask extends AsyncTask<String, Void, ArrayList<User>> {

        AsyncTaskCallback resultCallback = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("task", "PreExecute");
        }

        @Override
        protected ArrayList<User> doInBackground(String... strings) {

            ArrayList<User> usersSearchResults = new ArrayList<>();
            Gson gson = new Gson();
            try {
                InputStream stream = (new URL(strings[0])).openConnection().getInputStream();
                Reader br = new BufferedReader(new InputStreamReader(stream));
                publishProgress();
                usersSearchResults = gson.fromJson(br, new TypeToken<ArrayList<User>>() {}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("task", "DoInBackground");
            return usersSearchResults;
        }

        @Override
        protected void onPostExecute(ArrayList<User> usersSearchResults) {
            super.onPostExecute(usersSearchResults);

            Log.d("result", usersSearchResults.toString());
            resultCallback.onResultReceived(usersSearchResults);
            resultCallback = null;

            Log.d("task", "OnPostExecute");
        }
    }
}
