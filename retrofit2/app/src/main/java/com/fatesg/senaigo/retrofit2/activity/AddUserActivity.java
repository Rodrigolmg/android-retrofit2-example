package com.fatesg.senaigo.retrofit2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.fatesg.senaigo.retrofit2.R;
import com.fatesg.senaigo.retrofit2.bootstrap.APIClient;
import com.fatesg.senaigo.retrofit2.model.Address;
import com.fatesg.senaigo.retrofit2.model.Company;
import com.fatesg.senaigo.retrofit2.model.Geo;
import com.fatesg.senaigo.retrofit2.model.User;
import com.fatesg.senaigo.retrofit2.resource.UserResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    protected EditText txtUserName;
    protected EditText txtUsername;
    protected EditText txtEmail;
    protected EditText txtPhone;
    protected EditText txtWebsite;

    protected EditText txtStreet;
    protected EditText txtSuite;
    protected EditText txtCity;
    protected EditText txtZipcode;
    protected EditText txtLat;
    protected EditText txtLng;

    protected EditText txtCompName;
    protected EditText txtCatchPhrase;
    protected EditText txtBs;

    protected User user;
    protected Address address;
    protected Geo geo;
    protected Company company;
    protected UserResource userResource;

    protected Long userId = 1L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
    }

    public void addUser(View view) {

        user = new User();
        address = new Address();
        geo = new Geo();
        company = new Company();

        userResource = APIClient.getClient().create(UserResource.class);

        this.initFields();

        user.setId(userId);
        user.setName(txtUserName.getText().toString());
        user.setUsername(txtUsername.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setPhone(txtPhone.getText().toString());
        user.setWebsite(txtWebsite.getText().toString());
        address.setStreet(txtStreet.getText().toString());
        address.setSuite(txtSuite.getText().toString());
        address.setCity(txtCity.getText().toString());
        address.setZipcode(txtZipcode.getText().toString());
        geo.setLat(txtLat.getText().toString());
        geo.setLng(txtLng.getText().toString());
        address.setGeo(geo);
        company.setName(txtCompName.getText().toString());
        company.setCatchPhrase(txtCatchPhrase.getText().toString());
        company.setBs(txtBs.getText().toString());
        user.setAddress(address);
        user.setCompany(company);

        Call<User> post = userResource.post(user);


    }

    private void initFields(){
        txtUserName = findViewById(R.id.txtUserName);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);;
        txtPhone = findViewById(R.id.txtPhone);;
        txtWebsite = findViewById(R.id.txtWebsite);;

        txtStreet = findViewById(R.id.txtStreet);;
        txtSuite = findViewById(R.id.txtSuite);;
        txtCity = findViewById(R.id.txtCity);;
        txtZipcode = findViewById(R.id.txtZipcode);;
        txtLat = findViewById(R.id.txtLat);;
        txtLng = findViewById(R.id.txtLng);;

        txtCompName = findViewById(R.id.txtCompName);
        txtCatchPhrase = findViewById(R.id.txtCatchPhrase);
        txtBs = findViewById(R.id.txtBs);
    }
}
