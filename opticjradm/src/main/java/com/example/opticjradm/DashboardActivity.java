package com.example.opticjradm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dao.CustomerDAOImpl;
import dao.OrderDAOImpl;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button manageCustomerButton, manageOrderButton, logoutButton;
    private TextView loginNameText, countCustomerText, totalIncomeText, countOrderText;
    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
        countCustomerText = findViewById(R.id.count_customer_text);
        totalIncomeText = findViewById(R.id.total_income_text);
        countOrderText = findViewById(R.id.count_order_text);
        manageCustomerButton = findViewById(R.id.manage_customer_button);
        manageOrderButton = findViewById(R.id.manage_order_button);
        logoutButton = findViewById(R.id.logout_button);
    }

    private void initListener() {
        manageCustomerButton.setOnClickListener(this);
        manageOrderButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    private void initData() {
        CustomerDAOImpl customerDAO= new CustomerDAOImpl(this);
        OrderDAOImpl orderDAO = new OrderDAOImpl(this);

        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);

        countCustomerText.setText(String.valueOf(customerDAO.getCountCustomer()));
        totalIncomeText.setText("$ " + orderDAO.getSumOrder());
        countOrderText.setText(String.valueOf(orderDAO.getCountOrder()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.manage_customer_button:
                DashboardActivity.this.startActivity(new Intent(DashboardActivity.this, ManageCustomerActivity.class).putExtra("loginName", loginName));
                break;
            case R.id.manage_order_button:
                DashboardActivity.this.startActivity(new Intent(DashboardActivity.this, ManageOrderActivity.class).putExtra("loginName", loginName));
                break;
            case R.id.logout_button:
                DashboardActivity.this.startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                break;
        }
    }
}