package com.example.exampleapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by Ayush on 2019-10-13.
 */
public class BankingTypeAdapter extends RecyclerView.Adapter {
    private List<String> bankingTypeList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banking_type_layout, parent, false);
        return new BankingTypeSelectorViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BankingTypeSelectorViewHolder) holder).bindData(position);
    }

    @Override
    public int getItemCount() {
        if (bankingTypeList != null)
            return bankingTypeList.size();
        return 0;
    }

    public void setData(List<String> bankingTypesList) {
        this.bankingTypeList = bankingTypesList;
    }

    public class BankingTypeSelectorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout parentView;
        AppCompatTextView bankingTypeTextView;
        AppCompatImageView tickIcon;

        public BankingTypeSelectorViewHolder(@NonNull View itemView) {
            super(itemView);
            parentView = itemView.findViewById(R.id.banking_type_item);
            bankingTypeTextView = itemView.findViewById(R.id.banking_type_text);
            tickIcon = itemView.findViewById(R.id.selected_banking_tick);
            parentView.setOnClickListener(this);
        }

        public void bindData(int position) {
            bankingTypeTextView.setText(bankingTypeList.get(position));
        }

        @Override
        public void onClick(View v) {
            tickIcon.setVisibility(View.VISIBLE);

        }
    }
}
