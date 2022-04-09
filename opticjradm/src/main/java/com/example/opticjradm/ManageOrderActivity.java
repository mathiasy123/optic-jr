package com.example.opticjradm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapter.OrderAdapter;
import dao.OrderDAOImpl;

public class ManageOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView loginNameText;
    private Button createOrderFormButton;
    private RecyclerView orderRecyclerView;

    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order);
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
        orderRecyclerView = findViewById(R.id.order_recyclerview);
        createOrderFormButton = findViewById(R.id.create_order_form_button);
    }

    private void initListener() {
        createOrderFormButton.setOnClickListener(this);
    }

    private void initData() {
        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);

        OrderDAOImpl orderDAO = new OrderDAOImpl(this);
        OrderAdapter orderAdapter = new OrderAdapter(orderDAO.getOrders(), this, loginName);
        orderRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        orderRecyclerView.setAdapter(orderAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_order_form_button:
                ManageOrderActivity.this.startActivity(new Intent(ManageOrderActivity.this, CreateOrderActivity.class).putExtra("loginName", loginName));
                break;
        }
    }
}