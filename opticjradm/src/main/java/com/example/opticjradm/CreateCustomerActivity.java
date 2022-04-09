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

public class CreateCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText;
    private TextView loginNameText;
    private Button createCustomerButton;
    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
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

        createCustomerButton = findViewById(R.id.create_customer_button);
    }

    private void initListener() {
        createCustomerButton.setOnClickListener(this);
    }

    private void initData() {
        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);
    }

    private void clearForm() {
        customerNameEditText.setText("");
        customerEmailEditText.setText("");
        customerPhoneNumberEditText.setText("");
        customerAddressEditText.setText("");
    }

    private boolean isEditTextEmpty(EditText[] createCustomerEditTexts) {
        for (EditText currentEditText : createCustomerEditTexts) {
            if (currentEditText.getText().toString().trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_customer_button:
                CustomerDAOImpl customerDAO = new CustomerDAOImpl(this);
                if (isEditTextEmpty(new EditText[]{customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText})) {
                    Toast.makeText(this, "All fields is required", Toast.LENGTH_SHORT).show();
                } else {
                    if (customerDAO.createCustomer(new Customer(customerNameEditText.getText().toString(), customerEmailEditText.getText().toString(), customerPhoneNumberEditText.getText().toString(), customerAddressEditText.getText().toString())) != -1) {
                        clearForm();
                        Toast.makeText(this, "The customer data created successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to create the customer data", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}