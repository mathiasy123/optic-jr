package com.example.opticjradm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapter.CustomerAdapter;
import dao.CustomerDAOImpl;

public class ManageCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView loginNameText;
    private Button createCustomerFormButton;
    private RecyclerView customerRecyclerView;

    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer);
        initUI();
        initListener();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initUI() {
        loginNameText = findViewById(R.id.login_name_text);
        customerRecyclerView = findViewById(R.id.customer_recyclerview);
        createCustomerFormButton = findViewById(R.id.create_customer_form_button);
    }

    private void initListener() {
        createCustomerFormButton.setOnClickListener(this);
    }

    private void initData() {
        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);

        CustomerDAOImpl customerDAO = new CustomerDAOImpl(this);
        CustomerAdapter customerAdapter = new CustomerAdapter(customerDAO.getCustomers(), this, loginName);
        customerRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        customerRecyclerView.setAdapter(customerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_customer_form_button:
                ManageCustomerActivity.this.startActivity(new Intent(ManageCustomerActivity.this, CreateCustomerActivity.class).putExtra("loginName", loginName));
                break;
        }
    }
}