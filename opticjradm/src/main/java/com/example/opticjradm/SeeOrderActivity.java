package com.example.opticjradm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import dao.OrderDAOImpl;
import entities.Order;

public class SeeOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView loginNameText;
    private ImageView productImage;
    private TextView productNameText, productPriceText, customerNameText, customerEmailText, customerPhoneNumberText, customerAddressText;
    private Button updateOrderFormButton;
    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_order);
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
        productImage = findViewById(R.id.order_detail_product_image);
        productNameText = findViewById(R.id.order_detail_product_name_text);
        productPriceText = findViewById(R.id.order_detail_product_price_text);
        customerNameText = findViewById(R.id.order_detail_customer_name_text);
        customerEmailText = findViewById(R.id.order_detail_customer_email_text);
        customerPhoneNumberText = findViewById(R.id.order_detail_customer_phone_number_text);
        customerAddressText = findViewById(R.id.order_detail_customer_address_text);
        updateOrderFormButton = findViewById(R.id.update_order_form_button);
    }

    private void initListener() {
        updateOrderFormButton.setOnClickListener(this);
    }

    private void initData() {
        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);

        OrderDAOImpl orderDAO = new OrderDAOImpl(this);
        Order order = orderDAO.getOrder(getIntent().getIntExtra("orderId", 0));
        Glide.with(this).load("file:///android_asset/" + order.getProductImage()).fitCenter().into(productImage);
        productNameText.setText(order.getProductName());
        productPriceText.setText("$ " + order.getProductPrice());
        customerNameText.setText(order.getCustomerName());
        customerEmailText.setText(order.getCustomerEmail());
        customerPhoneNumberText.setText(order.getCustomerPhone());
        customerAddressText.setText(order.getCustomerAddress());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_order_form_button:
                SeeOrderActivity.this.startActivity(new Intent(this, UpdateOrderActivity.class).putExtra("loginName", loginName).putExtra("orderId", getIntent().getIntExtra("orderId", 0)));
        }
    }
}