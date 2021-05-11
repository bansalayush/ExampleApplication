package com.example.exampleapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.exampleapplication.R;
import com.example.exampleapplication.pojo.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StaggeredExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "StaggeredExampleAdapter";

    private List<Task> mList = new ArrayList();
    private Set<String> visitedTasks = new LinkedHashSet<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.task_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position != RecyclerView.NO_POSITION) {
            visitedTasks.add(mList.get(position).getDescription());
        }
        if (position > 4) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
        ((MViewHolder) holder).bind(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<Task> newList) {
        if (newList != null) {
            mList.clear();
            mList.addAll(newList);
            notifyDataSetChanged();
        }
    }

    public Set<String> getVisitedTasks() {
        return visitedTasks;
    }

    private class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView taskText;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.task_text);
            taskText.setOnClickListener(this);
        }

        public void bind(Task task) {
            taskText.setText(task.getDescription());
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + visitedTasks);
        }
    }

}
