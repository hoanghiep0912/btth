package com.example.shopgiaythethao.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.shopgiaythethao.Domain.ItemsModel;
import com.example.shopgiaythethao.Interface.ChangeNumberItemsListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ManagmentCart(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("CartList", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void insertItem(ItemsModel item) {
        ArrayList<ItemsModel> listItems = getListCart();
        boolean existAlready = false;
        int existingPosition = -1;

        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getId().equals(item.getId()) &&
                    listItems.get(i).getSelectedSize() != null &&
                    listItems.get(i).getSelectedSize().equals(item.getSelectedSize())) {
                existAlready = true;
                existingPosition = i;
                break;
            }
        }

        if (existAlready && existingPosition != -1) {
            // Nếu sản phẩm đã tồn tại với cùng size, tăng số lượng
            listItems.get(existingPosition).setNumberinCart(
                    listItems.get(existingPosition).getNumberinCart() + item.getNumberinCart());
        } else {
            // Nếu sản phẩm chưa tồn tại hoặc có size khác, thêm mới
            listItems.add(item);
        }

        saveListCart(listItems);
        Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<ItemsModel> getListCart() {
        String cartJson = sharedPreferences.getString("CartItems", null);
        Type listType = new TypeToken<ArrayList<ItemsModel>>() {}.getType();

        ArrayList<ItemsModel> cartList = gson.fromJson(cartJson, listType);
        return cartList != null ? cartList : new ArrayList<>();
    }

    private void saveListCart(ArrayList<ItemsModel> list) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String cartJson = gson.toJson(list);
        editor.putString("CartItems", cartJson);
        editor.apply();
    }

    public void plusNumberItem(ArrayList<ItemsModel> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() + 1);
        saveListCart(listItem);
        changeNumberItemsListener.changed();
    }

    public void minusNumberItem(ArrayList<ItemsModel> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listItem.get(position).getNumberinCart() > 1) {
            listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() - 1);
        } else {
            removeItem(listItem, position);
        }
        saveListCart(listItem);
        changeNumberItemsListener.changed();
    }

    public void removeItem(ArrayList<ItemsModel> listItem, int position) {
        listItem.remove(position);
        saveListCart(listItem);
    }

    public double getTotalFee() {
        ArrayList<ItemsModel> listItem = getListCart();
        double fee = 0;
        for (int i = 0; i < listItem.size(); i++) {
            fee += (listItem.get(i).getPrice() * listItem.get(i).getNumberinCart());
        }
        return fee;
    }

    public void clearCart() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("CartItems");
        editor.apply();
    }
}