package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.opticjr.R;

import java.util.List;

import entities.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_product_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load("file:///android_asset/" + productList.get(position).getImage()).fitCenter().into(holder.productImage);
        holder.productNameText.setText(productList.get(position).getName());
        holder.productPriceText.setText(String.valueOf(productList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productNameText, productPriceText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productNameText = itemView.findViewById(R.id.product_name_text);
            productPriceText = itemView.findViewById(R.id.product_price_text);
        }
    }
}
