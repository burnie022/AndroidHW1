package com.example.burnie.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.burnie.newsapp.data.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
Removed all instances of news item adding to arraylist.
Cursor has been implemented into NewsAdapter to have access to database for HW4

 */

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{

    //private String[] mNewsData;
    private Cursor cursor;
    final private ItemClickListener listener;
    Context context;
    //private ArrayList<NewsItem> newsItems;

    //public NewsAdapter(){}

    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;

    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    //implement onclicklistener
    public class NewsAdapterViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mNewsTextView;
        public final TextView mNewsDescView;
        public final TextView mNewsAuthorView;
        ImageView img;

        public NewsAdapterViewHolder(View view){
            super(view);


            mNewsTextView = (TextView) view.findViewById(R.id.tv_news_data);
            mNewsDescView = (TextView) view.findViewById(R.id.tv_news_description);
            mNewsAuthorView = (TextView) view.findViewById(R.id.tv_news_author);
            img = (ImageView)itemView.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        public void bind(int pos) {
//            No longer needed, cusor is now implemented
//            NewsItem newsItem = newsItems.get(pos);
//            mNewsTextView.setText(newsItem.getTitle());
//            mNewsDescView.setText(newsItem.getDescription());
//            mNewsAuthorView.setText(newsItem.getAuthor());

            cursor.moveToPosition(pos);
            mNewsTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_TITLE)));
            mNewsDescView.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION)));
            mNewsAuthorView.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URLTOIMAGE));
            Log.d("newsadapter", url);
            if(url != null){
                Picasso.with(context)
                        .load(url)
                        .into(img);
            }
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(cursor, getAdapterPosition());
        }

    }



    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
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
        //return newsItems.size();
        return cursor.getCount();
    }




//    public void setNewsData(String[] data) {
//        mNewsData = data;
//        notifyDataSetChanged();
//    }

}
