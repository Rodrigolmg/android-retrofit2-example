package com.fatesg.senaigo.retrofit2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fatesg.senaigo.retrofit2.R;
import com.fatesg.senaigo.retrofit2.adapter.UserPostAdapter;
import com.fatesg.senaigo.retrofit2.bootstrap.APIClient;
import com.fatesg.senaigo.retrofit2.model.UserPost;
import com.fatesg.senaigo.retrofit2.resource.UserPostResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    protected EditText txtTitle;

    protected TextView lblUserId;
    protected TextView lblUserpostId;
    protected TextView lblTitle;

    protected ListView lstUserPosts;

    protected UserPostResource userPostResource;
    protected UserPostAdapter userPostAdapter;

    protected List<UserPost> listUserPost = new ArrayList<>();

    protected UserPost up;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // Get the fields from the view
        initFields();

        // Get the post resource from ENDPOINT
        getResource();

        //Set adapter

        setUserPostadapter();

        //ListView onClick to delete the selected user
        selectUserPost();
    }

    public void addUser(View view) {

        UserPost userPostAdd = new UserPost();

        if(!txtTitle.getText().toString().isEmpty()) {
            userPostAdd.setUserId(Integer.parseInt(lblUserId.getText().toString()
                    .replace("User ID: ", "")));
            userPostAdd.setId(Integer.parseInt(lblUserpostId.getText().toString()
                    .replace("ID: ", "")));
            userPostAdd.setTitle(txtTitle.getText().toString());

            Call<UserPost> post = userPostResource.post(userPostAdd);

            post.enqueue(new Callback<UserPost>() {
                @Override
                public void onResponse(Call<UserPost> call, Response<UserPost> response) {
                    UserPost up = response.body();
                    listUserPost.add(up);
                    setUserPostadapter();
                    clearFields();
                }

                @Override
                public void onFailure(Call<UserPost> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, String.format("\"%s\" field cannot be empty!",
                    lblTitle.getText()), Toast.LENGTH_LONG).show();
        }

    }

    public void deleteUser(View view) {

        final UserPost userPostDel = new UserPost();

        if(!txtTitle.getText().toString().isEmpty()) {
            userPostDel.setUserId(Integer.parseInt(lblUserId.getText().toString()
                    .replace("User ID: ", "")));
            userPostDel.setId(Integer.parseInt(lblUserpostId.getText().toString()
                    .replace("ID: ", "")));
            userPostDel.setTitle(txtTitle.getText().toString());

            Call<Void> delete = userPostResource.delete(userPostDel.getUserId());

            delete.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    listUserPost.remove(userPostDel);
                    setUserPostadapter();
                    clearFields();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, String.format("\"%s\" field cannot be empty!",
                    lblTitle.getText()), Toast.LENGTH_LONG).show();
        }
    }

    public void clearFields(View view) {
        clearFields();
    }

    private void initFields(){
        lblTitle = findViewById(R.id.lblTitle);
        lblUserId = findViewById(R.id.lblUserId);
        lblUserpostId = findViewById(R.id.lblUserpostId);
        txtTitle = findViewById(R.id.txtTitle);
        lstUserPosts = findViewById(R.id.lstUserPosts);
    }

    private void getResource(){

        userPostResource = APIClient.getClientPost().create(UserPostResource.class);

        Call<List<UserPost>> get = userPostResource.get();

        get.enqueue(new Callback<List<UserPost>>() {
            @Override
            public void onResponse(Call<List<UserPost>> call, Response<List<UserPost>> response) {

                listUserPost = response.body();

                setUserPostadapter();
                up = (UserPost) userPostAdapter
                        .getItem(userPostAdapter.getCount() - 1);

                setLabelId();
            }

            @Override
            public void onFailure(Call<List<UserPost>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUserPostadapter(){
        userPostAdapter = new UserPostAdapter(listUserPost, this);
        lstUserPosts.setAdapter(userPostAdapter);
    }

    private void setLabelId(){

        lblUserId.setText(R.string.label_user_id);
        lblUserpostId.setText(R.string.label_userpost_id);

        String lblUserIdAdd = lblUserId.getText().toString()
                .concat(" ").concat(String.valueOf(up.getUserId() + 1));

        String lblIdAdd = lblUserpostId.getText().toString()
                .concat(" ").concat(String.valueOf(up.getId() + 1));

        lblUserId.setText(lblUserIdAdd);
        lblUserpostId.setText(lblIdAdd);
    }

    private void clearFields(){
        txtTitle.setInputType(1);
        txtTitle.setText("");
        txtTitle.setFocusable(true);
        txtTitle.setFocusableInTouchMode(true);
        setLabelId();
    }

    private void selectUserPost(){

        lstUserPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lblUserId.setText(R.string.label_user_id);
                lblUserpostId.setText(R.string.label_userpost_id);
                txtTitle.setText("");

                UserPost userPostSelected = listUserPost.get(position);

                lblUserId.setText(lblUserId.getText().toString()
                        .concat(" ").concat(String.valueOf(userPostSelected.getUserId())));

                lblUserpostId.setText(lblUserpostId.getText().toString()
                        .concat(" ").concat(String.valueOf(userPostSelected.getId())));

                txtTitle.setText(userPostSelected.getTitle());
                txtTitle.setInputType(0);
                txtTitle.setFocusable(false);
                txtTitle.setFocusableInTouchMode(false);
            }
        });
    }
}
