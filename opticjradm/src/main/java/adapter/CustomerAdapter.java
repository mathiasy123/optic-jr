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
import com.example.opticjradm.UpdateCustomerActivity;

import java.util.List;

import dao.CustomerDAOImpl;
import entities.Customer;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private List<Customer> customerList;
    private Context context;
    private String loginName;

    public CustomerAdapter(List<Customer> customerList, Context context, String loginName) {
        this.customerList = customerList;
        this.context = context;
        this.loginName = loginName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_customer_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.customerIdText.setText(String.valueOf(customerList.get(position).getId()));
        holder.customerNameText.setText(customerList.get(position).getName());
        holder.customerEmailText.setText(customerList.get(position).getEmail());
        holder.customerPhoneNumberText.setText(customerList.get(position).getPhone());
        holder.customerAddressText.setText(customerList.get(position).getAddress());

        holder.updateCustomerFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, UpdateCustomerActivity.class).putExtra("loginName", loginName).putExtra("customerId", Integer.parseInt(holder.customerIdText.getText().toString())));
            }
        });

        holder.deleteCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerDAOImpl customerDAO = new CustomerDAOImpl(context);
                if (customerDAO.deleteCustomer(Integer.parseInt(holder.customerIdText.getText().toString())) != -1) {
                    customerList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toast.makeText(context, "The customer data deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete the customer data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button updateCustomerFormButton, deleteCustomerButton;
        private TextView customerIdText, customerNameText, customerEmailText, customerPhoneNumberText, customerAddressText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerIdText = itemView.findViewById(R.id.customer_id_text);
            customerNameText = itemView.findViewById(R.id.customer_name_text);
            customerEmailText = itemView.findViewById(R.id.customer_email_text);
            customerPhoneNumberText = itemView.findViewById(R.id.customer_phone_number_text);
            customerAddressText = itemView.findViewById(R.id.customer_address_text);
            updateCustomerFormButton = itemView.findViewById(R.id.update_customer_form_button);
            deleteCustomerButton = itemView.findViewById(R.id.delete_customer_button);
        }
    }
}
