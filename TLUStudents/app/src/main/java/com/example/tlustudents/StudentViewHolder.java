package com.example.tlustudents;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class StudentViewHolder extends RecyclerView.ViewHolder {
    private ImageView imvAvatar;
    private TextView txtName;
    private TextView txtSid;
    public StudentViewHolder(@NonNull View itemView){
        super(itemView);
        imvAvatar = itemView.findViewById(R.id.imv_student_ava);
        txtName = itemView.findViewById(R.id.txt_student_name);
        txtSid = itemView.findViewById(R.id.txt_student_sid);
    }
    public void bind(Student std){
        imvAvatar.setImageResource(std.getAvatar());
        txtName.setText(std.getFullname());
        txtSid.setText(std.getSid());
    }
}
