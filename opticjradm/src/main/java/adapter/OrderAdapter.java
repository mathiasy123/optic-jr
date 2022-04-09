package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opticjradm.R;
import com.example.opticjradm.SeeOrderActivity;

import java.util.List;

import dao.OrderDAOImpl;
import entities.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<Order> orderList;
    private Context context;
    String loginName;

    public OrderAdapter(List<Order> orderList, Context context, String loginName) {
        this.orderList = orderList;
        this.context = context;
        this.loginName = loginName;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_order_layout, parent, false);
        return new OrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        holder.orderIdText.setText(String.valueOf(orderList.get(position).getId()));
        holder.productNameText.setText(orderList.get(position).getProductName());
        holder.productPriceText.setText("$" + orderList.get(position).getProductPrice());
        holder.customerNameText.setText(orderList.get(position).getCustomerName());
        holder.orderDateText.setText(orderList.get(position).getOrderDate());

        holder.deleteOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDAOImpl orderDAO = new OrderDAOImpl(context);
                if (orderDAO.deleteOrder(Integer.parseInt(holder.orderIdText.getText().toString())) != -1) {
                    orderList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toast.makeText(context, "The order data deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete the order data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.seeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SeeOrderActivity.class).putExtra("loginName", loginName).putExtra("orderId", Integer.parseInt(holder.orderIdText.getText().toString())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button seeOrderButton, deleteOrderButton;
        private TextView orderIdText, productNameText, productPriceText, customerNameText, orderDateText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdText = itemView.findViewById(R.id.order_id_text);
            productNameText = itemView.findViewById(R.id.order_product_name_text);
            productPriceText = itemView.findViewById(R.id.order_product_price_text);
            customerNameText = itemView.findViewById(R.id.order_customer_name_text);
            orderDateText = itemView.findViewById(R.id.order_date_text);

            seeOrderButton = itemView.findViewById(R.id.see_order_button);
            deleteOrderButton = itemView.findViewById(R.id.delete_order_button);
        }
    }
}
