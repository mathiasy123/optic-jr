package com.example.opticjr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adapter.ProductAdapter;
import dao.ProductDAOImpl;
import entities.Product;

public class CatalogueActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        initUI();
        initData();
    }

    private void initUI() {
        productRecyclerView = findViewById(R.id.product_recyclerview);
        productRecyclerView.setNestedScrollingEnabled(false);
    }
    private void initData() {
        ProductDAOImpl productDAO = new ProductDAOImpl(this);
        ProductAdapter productAdapter = new ProductAdapter(productDAO.getProducts(), this);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productRecyclerView.setAdapter(productAdapter);
    }
}