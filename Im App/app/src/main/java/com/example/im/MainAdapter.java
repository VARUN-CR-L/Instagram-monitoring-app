package com.example.im;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.im.databinding.PersonListItemBinding;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Person> personArrayList;

    private MainSelectListener selectListener;


    public MainAdapter(ArrayList<Person> personArrayList, MainSelectListener selectListener) {
        this.personArrayList = personArrayList;
        this.selectListener = selectListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PersonListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.person_list_item,
                parent,
                false);


        return new ViewHolder(binding,selectListener);




    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Person currentperson =personArrayList.get(position);

        holder.binding.setName(currentperson);


    }

    @Override
    public int getItemCount() {
        if (personArrayList != null){
            return personArrayList.size();
        }else {
            return 0;
        }
    }

    public void setPersonArrayList(ArrayList<Person> personArrayList) {
        this.personArrayList = personArrayList;

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private PersonListItemBinding binding;

        MainSelectListener mainSelectListener;

        public ViewHolder(PersonListItemBinding binding, MainSelectListener mainSelectListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.mainSelectListener = mainSelectListener;

            binding.getRoot().setOnClickListener(this);




        }


        @Override
        public void onClick(View v) {
            mainSelectListener.onItemClicked(getAdapterPosition());
        }
    }
}
