package com.stepbystep.bossapp.home;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stepbystep.bossapp.DO.Food;
import com.stepbystep.bossapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    ArrayList<Food> items = new ArrayList<>();
    Context context;
    static MenuEditFragment menuEditFragment;

    public MenuAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_menu,parent,false);

        menuEditFragment = new MenuEditFragment();
        context = parent.getContext();

        return new MenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = items.get(position);
        holder.setItem(food);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView1, textView2, textView3;
        LinearLayout menuInfo;
        Button menuEditBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.M_menuImg);
            textView1 = itemView.findViewById(R.id.M_menuName);
            textView2 = itemView.findViewById(R.id.M_menuCnt);
            textView3 = itemView.findViewById(R.id.M_menuPrice);
            menuInfo = itemView.findViewById(R.id.M_menuInfo);
            menuEditBtn = itemView.findViewById(R.id.M_menuEditButton);

        }

        public void setItem(Food food){
            Glide.with(context).load(food.getImage()).into(imageView);
            textView1.setText(food.getName());
            textView2.setText(food.getContent());
            textView3.setText(food.getCost()+"원");
        }

    }

    public void addItem(Food food){
        items.add(food);
    }

    public Food getItem(int position){
        return items.get(position);
    }

    public void setItems(ArrayList<Food> items){
        this.items = items;
    }

    public ArrayList<Food> getItems(){
        return  items;
    }

}