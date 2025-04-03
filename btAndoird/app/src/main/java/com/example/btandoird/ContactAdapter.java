package com.example.btandoird;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> contactList;
    private android.content.Context context;

    public ContactAdapter(android.content.Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        android.view.View view = android.view.LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContactDetailActivity.class);
            intent.putExtra("name", contact.getName());
            intent.putExtra("phone", contact.getPhone());
            intent.putExtra("email", contact.getEmail());
            intent.putExtra("address", contact.getAddress());
            intent.putExtra("position", contact.getPosition());
            intent.putExtra("unit", contact.getUnit());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return contactList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        android.widget.TextView name, phone;
        public ViewHolder(android.view.View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            phone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
