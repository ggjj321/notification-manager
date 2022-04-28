package com.example.notification_manager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LinkedList<String> packageNameList;
    private final LinkedList<String> titleList;
    private final LinkedList<String> textList;
    private final LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView packageNameView;
        public final TextView titleView;
        public final TextView textView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            packageNameView = itemView.findViewById(R.id.packageName);
            titleView = itemView.findViewById(R.id.title);
            textView = itemView.findViewById(R.id.text);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            int mPosition = getLayoutPosition();
//            String element = mWordList.get(mPosition);
//            mWordList.set(mPosition, "Clicked! " + element);
//            mAdapter.notifyDataSetChanged();
        }
    }

    public WordListAdapter(Context context, LinkedList<String> packageNameList, LinkedList<String> titleList, LinkedList<String> textList) {
        mInflater = LayoutInflater.from(context);
        this.packageNameList = packageNameList;
        this.titleList = titleList;
        this.textList = textList;
    }

    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(WordListAdapter.WordViewHolder holder,
                                 int position) {
        String packageNameCurrent = packageNameList.get(position);
        holder.packageNameView.setText(packageNameCurrent);

        String titleCurrent = titleList.get(position);
        holder.titleView.setText(titleCurrent);

        String textCurrent = textList.get(position);
        holder.textView.setText(textCurrent);
    }

    @Override
    public int getItemCount() {
        return packageNameList.size();
    }
}