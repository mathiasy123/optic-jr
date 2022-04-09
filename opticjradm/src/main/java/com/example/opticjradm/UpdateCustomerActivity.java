package com.example.opticjradm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dao.CustomerDAOImpl;
import entities.Customer;

public class UpdateCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView loginNameText;
    private EditText customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText;
    private Button customerUpdateButton;
    private CustomerDAOImpl customerDAO;
    private int customerId;
    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        initUI();
        initListener();
        initData();
    }

    private void initUI() {
        loginNameText = findViewById(R.id.login_name_text);
        customerNameEditText = findViewById(R.id.customer_name_edit_text);
        customerEmailEditText = findViewById(R.id.customer_email_edit_text);
        customerPhoneNumberEditText = findViewById(R.id.customer_phone_number_edit_text);
        customerAddressEditText = findViewById(R.id.customer_address_edit_text);

        customerUpdateButton = findViewById(R.id.update_customer_button);
    }

    private void initListener() {
        customerUpdateButton.setOnClickListener(this);
    }

    private void initData() {
        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);

        customerId = getIntent().getIntExtra("customerId", 0);
        customerDAO = new CustomerDAOImpl(this);
        Customer customer = customerDAO.getCustomer(customerId);

        customerNameEditText.setText(customer.getName());
        customerEmailEditText.setText(customer.getEmail());
        customerPhoneNumberEditText.setText(customer.getPhone());
        customerAddressEditText.setText(customer.getAddress());
    }

    private boolean isEditTextEmpty(EditText[] updateCustomerEditTexts) {
        for(EditText currentEditText: updateCustomerEditTexts) {
            if (currentEditText.getText().toString().trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_customer_button:
                customerDAO = new CustomerDAOImpl(this);
                if(isEditTextEmpty(new EditText[]{customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText})) {
                    Toast.makeText(this, "All fields is required", Toast.LENGTH_SHORT).show();
                } else {
                    if (customerDAO.updateCustomer(new Customer(customerNameEditText.getText().toString(), customerEmailEditText.getText().toString(), customerPhoneNumberEditText.getText().toString(), customerAddressEditText.getText().toString()), customerId) != -1) {
                        Toast.makeText(this, "The customer data updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to update the customer data", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}