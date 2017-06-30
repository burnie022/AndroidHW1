package com.example.burnie.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{

    private String[] mNewsData;

    public NewsAdapter(){

    }


    public class NewsAdapterViewHolder  extends RecyclerView.ViewHolder{

        public final TextView mNewsTextView;
//        public final TextView mNewsDescView;
//        public final TextView mNewsTimeView;

        public NewsAdapterViewHolder(View view){
            super(view);
            mNewsTextView = (TextView) view.findViewById(R.id.tv_news_data);
//            mNewsDescView = (TextView) view.findViewById(R.id.tv_news_description);
//            mNewsTimeView = (TextView) view.findViewById(R.id.tv_news_time);

        }


    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        String news = mNewsData[position];
        holder.mNewsTextView.setText(news);
    }

    @Override
    public int getItemCount() {
        if (null == mNewsData) return 0;
        return mNewsData.length;
    }

    public void setNewsData(String[] data) {
        mNewsData = data;
        notifyDataSetChanged();
    }

}
