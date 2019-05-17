package com.brest.romark.tictactoe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brest.romark.tictactoe.R;
import com.brest.romark.tictactoe.entity.Repo;

import java.util.ArrayList;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    private Context context;

    private ArrayList<Repo> repos;

    public RepoListAdapter(Context context, ArrayList<Repo> repos) {
        this.context = context;
        this.repos = repos;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View repoView = inflater.inflate(R.layout.repo_list_item, viewGroup, false);

        RepoViewHolder viewHolder = new RepoViewHolder(repoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder repoViewHolder, int i) {
        Repo repo = repos.get(i);

        TextView tvRepoName = repoViewHolder.tvRepoName;
        tvRepoName.setText(repo.getName());
    }

    @Override
    public int getItemCount() {
        if (repos == null) {
            return 0;
        }
        return repos.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView tvRepoName;

        RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRepoName = itemView.findViewById(R.id.tvRepoName);
        }
    }
}
