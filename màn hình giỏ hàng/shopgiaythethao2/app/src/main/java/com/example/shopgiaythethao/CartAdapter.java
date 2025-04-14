package com.example.shopgiaythethao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopgiaythethao.Domain.ItemsModel;
import com.example.shopgiaythethao.Helper.ManagmentCart;
import com.example.shopgiaythethao.Interface.ChangeNumberItemsListener;
import com.example.shopgiaythethao.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<ItemsModel> listItemsSelected;
    private ManagmentCart managmentCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<ItemsModel> listItemsSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemsSelected = listItemsSelected;
        this.managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsModel item = listItemsSelected.get(position);

        // Định dạng giá tiền theo Việt Nam
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());

        holder.title.setText(item.getTitle());
        holder.size.setText("Size: " + item.getSelectedSize());
        holder.numberItemTxt.setText(String.valueOf(item.getNumberinCart()));

        double totalPriceItem = Math.round((item.getNumberinCart() * item.getPrice()) * 100) / 100;
        holder.totalPriceTxt.setText(nf.format(totalPriceItem) + "₫");
        holder.priceTxt.setText(nf.format(item.getPrice()) + "₫");

        // Hiển thị hình ảnh sản phẩm
        if (!item.getPicUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getPicUrl().get(0))
                    .into(holder.pic);
        }

        // Xử lý các sự kiện nút + và -
        holder.plusBtn.setOnClickListener(v -> {
            managmentCart.plusNumberItem(listItemsSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            });
        });

        holder.minusBtn.setOnClickListener(v -> {
            managmentCart.minusNumberItem(listItemsSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            });
        });

        // Xử lý sự kiện xóa sản phẩm
        holder.deleteBtn.setOnClickListener(v -> {
            managmentCart.removeItem(listItemsSelected, position);
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        });
    }

    @Override
    public int getItemCount() {
        return listItemsSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, priceTxt, totalPriceTxt, numberItemTxt, size;
        ImageView pic, plusBtn, minusBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            size = itemView.findViewById(R.id.sizeTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            totalPriceTxt = itemView.findViewById(R.id.totalPriceTxt);
            numberItemTxt = itemView.findViewById(R.id.numberItemTxt);
            pic = itemView.findViewById(R.id.pic);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}