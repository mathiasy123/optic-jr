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

public class CreateOrderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView loginNameText;
    private Spinner productSpinner;
    private EditText customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText;
    private Button createOrderButton;

    private OrderDAOImpl orderDAO;

    private String selectedProduct;
    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
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

        createOrderButton = findViewById(R.id.create_order_button);
    }

    private void initListener() {
        createOrderButton.setOnClickListener(this);
        productSpinner.setOnItemSelectedListener(this);
    }

    private void initData() {
        loginName = getIntent().getStringExtra("loginName");
        loginNameText.setText(loginName);

        orderDAO = new OrderDAOImpl(this);

        ProductDAOImpl productDAO = new ProductDAOImpl(this);
        List<String> productNameList =  productDAO.getProducts();
        productNameList.add(0, "Choose product");
        ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productNameList) {
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {
                    return false;
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView optionText = (TextView) view;
                if(position == 0) {
                    optionText.setTextColor(Color.GRAY);
                }
                else {
                    optionText.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        productSpinner.setAdapter(productNameAdapter);
    }

    private void clearForm() {
        productSpinner.setSelection(0);
        customerNameEditText.setText("");
        customerEmailEditText.setText("");
        customerPhoneNumberEditText.setText("");
        customerAddressEditText.setText("");
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
            case R.id.create_order_button:
                if(isEditTextEmpty(new EditText[]{customerNameEditText, customerEmailEditText, customerPhoneNumberEditText, customerAddressEditText}) || selectedProduct.equals("Choose product")) {
                    Toast.makeText(this, "All fields is required", Toast.LENGTH_SHORT).show();
                } else {
                    if (orderDAO.createOrder(new Order(selectedProduct, customerNameEditText.getText().toString(), customerEmailEditText.getText().toString(), customerPhoneNumberEditText.getText().toString(), customerAddressEditText.getText().toString(), getCurrentTimeStamp())) != -1) {
                        clearForm();
                        Toast.makeText(this, "The order data created successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to create the order data", Toast.LENGTH_SHORT).show();
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