package com.fatesg.senaigo.retrofit2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fatesg.senaigo.retrofit2.R;
import com.fatesg.senaigo.retrofit2.adapter.UserPostAdapter;
import com.fatesg.senaigo.retrofit2.bootstrap.APIClient;
import com.fatesg.senaigo.retrofit2.model.UserPost;
import com.fatesg.senaigo.retrofit2.resource.UserPostResource;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    protected EditText txtTitle;

    protected TextView lblUserId;
    protected TextView lblUserpostId;
    protected TextView lblTitle;

    protected Button btnDeleteUser;
    protected Switch swtEnableEdit;

    protected ListView lstUserPosts;

    protected UserPostResource userPostResource;
    protected UserPostAdapter userPostAdapter;

    protected List<UserPost> listUserPost = new LinkedList<>();

    protected UserPost up;
    protected Integer lstPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // Get the fields from the view
        initFields();

        // Get the post resource from ENDPOINT
        getResource();

        //Set adapter
        setUserPostAdapter();

        //ListView onClick to delete the selected user
        selectUserPost();

        //Doing slide and click event on switch
        switchState();
    }

    public void addUser(View view) {

        UserPost userPostAdd = new UserPost();


        if(!txtTitle.getText().toString().isEmpty()) {
            userPostAdd.setUserId(Integer.parseInt(lblUserId.getText().toString()
                    .replace("User ID: ", "")));
            userPostAdd.setId(Integer.parseInt(lblUserpostId.getText().toString()
                    .replace("ID: ", "")));
            userPostAdd.setTitle(txtTitle.getText().toString());

            if(swtEnableEdit.isChecked()){

                Call<UserPost> put = userPostResource.put(userPostAdd, userPostAdd.getId());

                put.enqueue(new Callback<UserPost>() {
                    @Override
                    public void onResponse(Call<UserPost> call, Response<UserPost> response) {
                        UserPost up = response.body();
                        listUserPost.set(lstPosition, up);
                        clearFields();
                        setUserPostAdapter();
                    }

                    @Override
                    public void onFailure(Call<UserPost> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }else{
                Call<UserPost> post = userPostResource.post(userPostAdd);

                post.enqueue(new Callback<UserPost>() {
                    @Override
                    public void onResponse(Call<UserPost> call, Response<UserPost> response) {
                        UserPost up = response.body();
                        listUserPost.add(up);
                        setUserPostAdapter();
                        clearFields();
                    }

                    @Override
                    public void onFailure(Call<UserPost> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
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
                    listUserPost.remove(lstPosition.intValue());
                    setUserPostAdapter();
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
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        swtEnableEdit = findViewById(R.id.swtEnableEdit);
        txtTitle = findViewById(R.id.txtTitle);
        lstUserPosts = findViewById(R.id.lstUserPosts);
    }

    public void getResource(){

        userPostResource = APIClient.getClientPost().create(UserPostResource.class);

        Call<List<UserPost>> get = userPostResource.get();

        get.enqueue(new Callback<List<UserPost>>() {
            @Override
            public void onResponse(Call<List<UserPost>> call, Response<List<UserPost>> response) {

                listUserPost = response.body();

                setUserPostAdapter();
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

    private void setUserPostAdapter(){
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
        txtTitle.setText("");
        txtTitle.setEnabled(true);
        swtEnableEdit.setChecked(false);
        btnDeleteUser.setEnabled(true);
        setLabelId();
    }

    private void selectUserPost(){

        lstUserPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstPosition = position;
                lblUserId.setText(R.string.label_user_id);
                lblUserpostId.setText(R.string.label_userpost_id);
                txtTitle.setText("");

                UserPost userPostSelected = listUserPost.get(position);

                lblUserId.setText(lblUserId.getText().toString()
                        .concat(" ").concat(String.valueOf(userPostSelected.getUserId())));

                lblUserpostId.setText(lblUserpostId.getText().toString()
                        .concat(" ").concat(String.valueOf(userPostSelected.getId())));

                txtTitle.setText(userPostSelected.getTitle());
                txtTitle.setEnabled(false);
                swtEnableEdit.setChecked(false);
            }
        });
    }

    private void switchState(){
        swtEnableEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!txtTitle.getText().toString().isEmpty()) {

                    if (isChecked) {
                        btnDeleteUser.setEnabled(false);
                        txtTitle.setEnabled(true);
                    } else {
                        Toast.makeText(AddUserActivity.this, "Entrou", Toast.LENGTH_LONG).show();
                        btnDeleteUser.setEnabled(true);
                        txtTitle.setEnabled(false);
                    }
                } else {
                    Toast.makeText(AddUserActivity.this, String.format("\"%s\" field cannot be empty!",
                            lblTitle.getText()), Toast.LENGTH_LONG).show();
                    swtEnableEdit.setChecked(false);
                }
            }
        });
    }


}
