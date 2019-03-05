package com.fatesg.senaigo.retrofit2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fatesg.senaigo.retrofit2.R;
import com.fatesg.senaigo.retrofit2.bootstrap.APIClient;
import com.fatesg.senaigo.retrofit2.model.User;
import com.fatesg.senaigo.retrofit2.resource.UserResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    protected Spinner spnUser;
    protected ListView lstUser;

    protected User user = new User();

    protected UserResource userResource;

    protected List<HashMap<String,String>> users = new ArrayList<>();

    protected List<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initFields();
        getResource();
    }

    public void showUser(View view) {
        clearListView();
        lstUser.setVisibility(View.VISIBLE);

        HashMap<String, String> mUsers = new HashMap<>();

        user = (User) spnUser.getSelectedItem();

        mUsers.put("id", String.valueOf(user.getId()));
        mUsers.put("name", user.getName());
        mUsers.put("username", user.getUsername());
        mUsers.put("email", user.getEmail());
        mUsers.put("phone", user.getPhone());
        mUsers.put("website", user.getWebsite());
        mUsers.put("street", user.getAddress().getStreet());
        mUsers.put("suite", user.getAddress().getSuite());
        mUsers.put("city", user.getAddress().getCity());
        mUsers.put("zipcode", user.getAddress().getZipcode());
        mUsers.put("lat", user.getAddress().getGeo().getLat());
        mUsers.put("lng", user.getAddress().getGeo().getLng());
        mUsers.put("cName", user.getCompany().getName());
        mUsers.put("catchPhrase", user.getCompany().getCatchPhrase());
        mUsers.put("bs", user.getCompany().getBs());

        users.add(mUsers);

        String[] from = {"id", "name", "username", "email", "phone", "website",
                "street", "suite", "city", "zipcode", "lat", "lng", "cName",
                "catchPhrase", "bs"};

        int[] to = {R.id.id, R.id.name, R.id.username, R.id.email, R.id.phone,
                R.id.website, R.id.aStreet, R.id.aSuite, R.id.aCity,
                R.id.aZipcode, R.id.aGeoLat, R.id.aGeoLng, R.id.cName, R.id.cCatchPhrase,
                R.id.cBs};

        SimpleAdapter sa = new SimpleAdapter(this, users,
                R.layout.listview_item_user, from, to);

        lstUser.setAdapter(sa);

    }

    private void getResource(){
        final ArrayList<User> spnUsers = new ArrayList<>();

        userResource = APIClient.getClient().create(UserResource.class);

        Call<List<User>> get = userResource.get();

        get.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                listUser = response.body();

                spnUsers.addAll(listUser);

                ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, spnUsers);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnUser.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initFields(){
        spnUser = findViewById(R.id.spnUser);
        lstUser = findViewById(R.id.lstUser);
    }

    private void clearListView(){
        lstUser.setAdapter(null);
        users.clear();
    }
}
