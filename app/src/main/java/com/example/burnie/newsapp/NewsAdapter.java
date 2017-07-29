package com.example.burnie.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{

    private String[] mNewsData;
    final private ItemClickListener listener;
    private ArrayList<NewsItem> newsItems;

    //public NewsAdapter(){}

    public NewsAdapter(ArrayList<NewsItem> newsItems, ItemClickListener listener){
        this.newsItems = newsItems;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    //implement onclicklistener
    public class NewsAdapterViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mNewsTextView;
        public final TextView mNewsDescView;
        public final TextView mNewsAuthorView;

        public NewsAdapterViewHolder(View view){
            super(view);
            mNewsTextView = (TextView) view.findViewById(R.id.tv_news_data);
            mNewsDescView = (TextView) view.findViewById(R.id.tv_news_description);
            mNewsAuthorView = (TextView) view.findViewById(R.id.tv_news_author);

            view.setOnClickListener(this);
        }

        public void bind(int pos) {
            NewsItem newsItem = newsItems.get(pos);
            mNewsTextView.setText(newsItem.getTitle());
            mNewsDescView.setText(newsItem.getDescription());
            mNewsAuthorView.setText(newsItem.getAuthor());
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
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
        //String news = mNewsData[position];
        //holder.mNewsTextView.setText(news);
        holder.bind(position);
    }



    @Override
    public int getItemCount() {
        //if (null == mNewsData) return 0;
        //return mNewsData.length;
        return newsItems.size();
    }




    public void setNewsData(String[] data) {
        mNewsData = data;
        notifyDataSetChanged();
    }

}
