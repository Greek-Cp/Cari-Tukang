package com.example.caritukang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caritukang.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterKategoriButton  extends RecyclerView.Adapter<AdapterKategoriButton.ViewHolder> {
    List<String> listKategoryTukang = new ArrayList<>();
    AdapterKategoriButton.AdapterKategoriInterfaceListener adapterKategoriInterfaceListener;

    public interface AdapterKategoriInterfaceListener{
        void clickButtonListener(int positionOfKategory);
    }

    public AdapterKategoriButton(List<String> listKategoryTukang, AdapterKategoriInterfaceListener adapterKategoriInterfaceListener) {
        this.listKategoryTukang = listKategoryTukang;
        this.adapterKategoriInterfaceListener = adapterKategoriInterfaceListener;
    }

    @NonNull
    @Override
    public AdapterKategoriButton.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_kategory_tukang,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKategoriButton.ViewHolder holder, int position) {
        holder.textViewNama.setText(listKategoryTukang.get(position));
    }

    @Override
    public int getItemCount() {
        return listKategoryTukang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewNama;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.id_button_kategori);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            adapterKategoriInterfaceListener.clickButtonListener(getAdapterPosition());
        }
    }
}
