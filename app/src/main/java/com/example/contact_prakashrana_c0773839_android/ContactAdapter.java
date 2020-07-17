package com.example.contact_prakashrana_c0773839_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> implements Filterable {


    private List<Contact> contactList = new ArrayList<>();
    private List<Contact> contactListFull = new ArrayList<>();

    private OnItemClickListner listner;

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currerntContact = contactList.get(position);
        holder.textViewName.setText(currerntContact.getFirstName() + " " + currerntContact.getLastName());
        holder.textViewEmail.setText(currerntContact.getEmail());
    }

    @Override
    public int getItemCount() {

        return contactList.size();
    }


    public void setContactList(List<Contact> contactList){
        this.contactList = contactList;
        contactListFull = new ArrayList<>(contactList);

        notifyDataSetChanged();
    }

    public Contact getContactAt(int position){
        return contactList.get(position);
    }

    @Override
    public Filter getFilter() {
        return contactListFilter;
    }

    private Filter contactListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Contact> filterList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filterList.addAll(contactListFull);
            }
            else{
                String filterPattern = charSequence.toString().trim().toLowerCase();
                for(Contact contact: contactListFull){
                    if(contact.getFirstName().toLowerCase().contains(filterPattern) ||
                    contact.getLastName().toLowerCase().contains(filterPattern) ||
                            contact.getEmail().toLowerCase().contains(filterPattern)){
                        filterList.add(contact);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {


            contactList.clear();
            contactList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ContactHolder extends RecyclerView.ViewHolder{
        private TextView textViewName;
        private TextView textViewEmail;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            textViewName =  itemView.findViewById(R.id.name);
            textViewEmail =  itemView.findViewById(R.id.email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  =  getAdapterPosition();

                    if(listner != null && position != RecyclerView.NO_POSITION){
                        listner.onItemClick(contactList.get(position));
                    }

                }
            });

        }
    }

    public  interface  OnItemClickListner {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.listner = onItemClickListner;
    }
}
