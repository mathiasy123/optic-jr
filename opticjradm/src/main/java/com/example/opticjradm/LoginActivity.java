package com.example.opticjradm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dao.AdminDAOImpl;
import entities.Admin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    private AdminDAOImpl adminDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initListener();
        initData();
    }

    private void initUI() {
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
    }

    private void initListener() {
        loginButton.setOnClickListener(this);
    }

    private void initData() {
        adminDAO = new AdminDAOImpl(this);
    }

    private void clearForm() {
        emailEditText.setText("");
        passwordEditText.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.trim().equals("") && password.trim().equals("")) {
                    Toast.makeText(this, "Email or password is required", Toast.LENGTH_SHORT).show();
                } else {
                    Admin adminResult = adminDAO.getAdmin(email, password);
                    if (adminResult == null) {
                        Toast.makeText(this, "Email or password is invalid", Toast.LENGTH_SHORT).show();
                    } else {
                        clearForm();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, DashboardActivity.class).putExtra("loginName", adminResult.getName()));
                    }
                }
                break;
        }
    }
}