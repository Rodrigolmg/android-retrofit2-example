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

public class ShowUserActivity extends AppCompatActivity {

    protected Spinner spnUser;
    protected ListView lstUser;

    protected User user = new User();

    protected UserResource userResource;

    protected List<HashMap<String,String>> users = new ArrayList<>();

    protected List<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        initFields();
        getResource();
    }

    public void showUser(View view) {
        clearListView();
        lstUser.setVisibility(View.VISIBLE);

        HashMap<String, String> mUsers = new HashMap<>();

        user = (User) spnUser.getSelectedItem();

        mUsers.put("id", "Id: " + String.valueOf(user.getId()));
        mUsers.put("name", "Name: " + user.getName());
        mUsers.put("username", "Username: " + user.getUsername());
        mUsers.put("email", "Email: " + user.getEmail());
        mUsers.put("phone", "Phone: " + user.getPhone());
        mUsers.put("website", "Website: " + user.getWebsite());
        mUsers.put("street", "Street: " + user.getAddress().getStreet());
        mUsers.put("suite", "Suite: " + user.getAddress().getSuite());
        mUsers.put("city", "City: " + user.getAddress().getCity());
        mUsers.put("zipcode", "Zipcode: " + user.getAddress().getZipcode());
        mUsers.put("lat", "Latitude: " + user.getAddress().getGeo().getLat());
        mUsers.put("lng", "Longitude: " + user.getAddress().getGeo().getLng());
        mUsers.put("cName", "Company name: " + user.getCompany().getName());
        mUsers.put("catchPhrase", "Catch Phrase: " + user.getCompany().getCatchPhrase());
        mUsers.put("bs", "BS: " + user.getCompany().getBs());

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

        userResource = APIClient.getClientUsers().create(UserResource.class);

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
