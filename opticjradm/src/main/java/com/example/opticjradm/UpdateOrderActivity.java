package com.example.opticjradm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.OrderDAOImpl;
import dao.ProductDAOImpl;
import entities.Order;

public class UpdateOrderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView loginNameText;
    private Spinner productSpinner;
    private EditText customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText;
    private Button updateOrderButton;

    private String loginName;
    private String selectedProduct;

    private OrderDAOImpl orderDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        initUI();
        initListener();
        initData();
    }

    private void initUI() {
        loginNameText = findViewById(R.id.login_name_text);
        productSpinner = findViewById(R.id.product_spinner);
        customerNameEditText = findViewById(R.id.customer_name_edit_text);
        customerEmailEditText = findViewById(R.id.customer_email_edit_text);
        customerPhoneNumberEditText = findViewById(R.id.customer_phone_number_edit_text);
        customerAddressEditText = findViewById(R.id.customer_address_edit_text);

        updateOrderButton = findViewById(R.id.update_order_button);
    }

    private void initListener() {
        updateOrderButton.setOnClickListener(this);
        productSpinner.setOnItemSelectedListener(this);
    }

    private void initData() {
        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);

        ProductDAOImpl productDAO = new ProductDAOImpl(this);
        List<String> productNameList = productDAO.getProducts();
        productNameList.add(0, "Choose product");
        ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productNameList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView optionText = (TextView) view;
                if (position == 0) {
                    optionText.setTextColor(Color.GRAY);
                } else {
                    optionText.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        productSpinner.setAdapter(productNameAdapter);

        orderDAO = new OrderDAOImpl(this);
        Order order = orderDAO.getOrder(getIntent().getIntExtra("orderId", 0));
        productSpinner.setSelection(productNameAdapter.getPosition(order.getProductName()));
        selectedProduct = productSpinner.getSelectedItem().toString();
        customerNameEditText.setText(order.getCustomerName());
        customerEmailEditText.setText(order.getCustomerEmail());
        customerPhoneNumberEditText.setText(order.getCustomerPhone());
        customerAddressEditText.setText(order.getCustomerAddress());
    }

    private boolean isEditTextEmpty(EditText[] createOrderEditTexts) {
        for(EditText currentEditText: createOrderEditTexts) {
            if (currentEditText.getText().toString().trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentTimeStamp(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_order_button:
                if(isEditTextEmpty(new EditText[]{customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText}) || selectedProduct.equals("Choose product")) {
                    Toast.makeText(this, "All fields is required", Toast.LENGTH_SHORT).show();
                } else {
                    if (orderDAO.updateOrder(new Order(selectedProduct, customerNameEditText.getText().toString(), customerEmailEditText.getText().toString(), customerPhoneNumberEditText.getText().toString(), customerAddressEditText.getText().toString(), getCurrentTimeStamp()), getIntent().getIntExtra("orderId", 0)) != -1) {
                        Toast.makeText(this, "The order data updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to update the order data", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedProduct = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}