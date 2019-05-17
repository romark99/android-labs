package com.brest.romark.tictactoe.adapter;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brest.romark.tictactoe.MainActivity;
import com.brest.romark.tictactoe.MyApplication;
import com.brest.romark.tictactoe.R;
import com.brest.romark.tictactoe.callback.LoadRepoJsonCallback;
import com.brest.romark.tictactoe.callback.LoadUserViewJsonCallback;
import com.brest.romark.tictactoe.entity.Repo;
import com.brest.romark.tictactoe.entity.User;
import com.brest.romark.tictactoe.entity.UserView;
import com.brest.romark.tictactoe.fragment.UserViewFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>
implements LoadUserViewJsonCallback, LoadRepoJsonCallback {

    private RecyclerView recyclerView;

    private final View.OnClickListener listItemClickListener = new ListItemClickListener();

    private List<User> mUsers;

    private Context context;

    private UserView userView;

    public UserListAdapter(Context context, List<User> users) {
        this.context = context;
        mUsers = users;
    }

    public void setmUsers(List<User> mUsers) {
        this.mUsers = mUsers;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View userView = inflater.inflate(R.layout.user_list_item, viewGroup, false);
        userView.setOnClickListener(listItemClickListener);

        UserViewHolder viewHolder = new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onAttachedToRecyclerView(@androidx.annotation.NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder userViewHolder, int i) {
        User user = mUsers.get(i);

        TextView urlView = userViewHolder.urlItemView;
        urlView.setText(user.getHtml_url());

        TextView loginView = userViewHolder.loginItemView;
        loginView.setText(user.getLogin());

        ImageView imageView = userViewHolder.imageView;
        Picasso.get().load(user.getAvatar_url()).into(imageView);
    }

    @Override
    public int getItemCount() {

        //?????????????????????????????????????????????
        if (mUsers == null) {
            return 0;
        }
        Log.d("mine", mUsers.toString());
        return mUsers.size();
    }

    public User getUserAt(int position) {
        return mUsers.get(position);
    }

    @Override
    public void onUserViewJsonReceived(UserView result) {
        setUserView(result);
        LoadRepoJSONTask loadRepoJSONTask = new LoadRepoJSONTask();
        loadRepoJSONTask.callback = UserListAdapter.this;
        loadRepoJSONTask.execute(result.getRepos_url());
    }

    @Override
    public void onRepoJsonReceived(ArrayList<Repo> repos) {
        userView.setRepos(repos);
        UserViewFragment userViewFragment = new UserViewFragment(userView);

        FragmentTransaction fragmentTransaction =
                ((MainActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.myFragment, userViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView loginItemView;

        TextView urlItemView;

        ImageView imageView;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            urlItemView = itemView.findViewById(R.id.tvUrl);
            loginItemView = itemView.findViewById(R.id.tvLogin);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    private class ListItemClickListener implements View.OnClickListener  {

        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            User item = mUsers.get(itemPosition);
            Toast toast = Toast.makeText(MyApplication.getAppContext(),
                    item.getLogin(), Toast.LENGTH_SHORT);
            toast.show();

            UserViewJSONTask userViewJSONTask = new UserViewJSONTask();
            userViewJSONTask.callback = UserListAdapter.this;
            userViewJSONTask.execute("https://api.github.com/users/" + item.getLogin());
        }
    }

    private static class UserViewJSONTask extends AsyncTask<String, Void, UserView> {

        private LoadUserViewJsonCallback callback = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("task", "PreExecute");
        }

        @Override
        protected UserView doInBackground(String... strings) {
            UserView userSearchResult = null;
            Gson gson = new Gson();
            try {
                InputStream stream = (new URL(strings[0])).openConnection().getInputStream();
                Reader br = new BufferedReader(new InputStreamReader(stream));
                publishProgress();
                userSearchResult = gson.fromJson(br, new TypeToken<UserView>() {}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("task", "DoInBackground");
            return userSearchResult;
        }

        @Override
        protected void onPostExecute(UserView userSearchResult) {
            super.onPostExecute(userSearchResult);

            if (userSearchResult != null) {
                Log.d("result", userSearchResult.toString());
                callback.onUserViewJsonReceived(userSearchResult);
                callback = null;
            }
            else {
                Toast toast = Toast.makeText(MyApplication.getAppContext(),
                        "The user is not found.", Toast.LENGTH_SHORT);
                toast.show();
            }
            Log.d("task", "OnPostExecute");
        }
    }

    private static class LoadRepoJSONTask extends AsyncTask<String, Void, ArrayList<Repo>> {

        private LoadRepoJsonCallback callback = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("task", "PreExecute");
        }

        @Override
        protected ArrayList<Repo> doInBackground(String... strings) {
            ArrayList<Repo> repos = null;
            Gson gson = new Gson();
            try {
                InputStream stream = (new URL(strings[0])).openConnection().getInputStream();
                Reader br = new BufferedReader(new InputStreamReader(stream));
                publishProgress();
                repos = gson.fromJson(br, new TypeToken<ArrayList<Repo>>() {}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("task", "DoInBackground");
            return repos;
        }

        @Override
        protected void onPostExecute(ArrayList<Repo> repos) {
            super.onPostExecute(repos);

            if (repos != null) {
                Log.d("result", repos.toString());
                callback.onRepoJsonReceived(repos);
                callback = null;
            }
            else {
                Toast toast = Toast.makeText(MyApplication.getAppContext(),
                        "The user is not found.", Toast.LENGTH_SHORT);
                toast.show();
            }
            Log.d("task", "OnPostExecute");
        }
    }
}
