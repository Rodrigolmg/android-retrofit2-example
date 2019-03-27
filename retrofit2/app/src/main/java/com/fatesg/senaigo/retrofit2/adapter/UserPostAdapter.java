package com.fatesg.senaigo.retrofit2.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fatesg.senaigo.retrofit2.R;
import com.fatesg.senaigo.retrofit2.model.UserPost;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPostAdapter extends BaseAdapter {

    @NonNull private final List<UserPost> listUsersPost;
    @NonNull private Activity act;

    @Override
    public int getCount() {
        return this.listUsersPost.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listUsersPost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.listUsersPost.indexOf(listUsersPost.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.listview_item_userpost, parent, false);
        UserPost userPost = (UserPost) getItem(position);

        TextView lblUserId = view.findViewById(R.id.userId);
        TextView lblId = view.findViewById(R.id.id);
        TextView lblTitle = view.findViewById(R.id.titlePost);
        TextView lblBody = view.findViewById(R.id.body);

        lblUserId.setText(String.valueOf(userPost.getUserId()));
        lblId.setText(String.valueOf(userPost.getId()));
        lblTitle.setText(userPost.getTitle());
        lblTitle.setText(userPost.getBody());


        return view;
    }
}
