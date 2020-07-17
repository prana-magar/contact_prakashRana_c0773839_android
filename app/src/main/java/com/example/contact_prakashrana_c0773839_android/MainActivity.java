package com.example.contact_prakashrana_c0773839_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        RecyclerView recyclerView = findViewById(R.id.contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        contactAdapter = new ContactAdapter();
        recyclerView.setAdapter(contactAdapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {

                contactAdapter.setContactList(contacts);

                setTitle(" Contacts ("+contacts.size() + ") ");

            }
        });


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                setTitle(" Contacts ("+contactViewModel.getRowCount() + ") ");
            }
        });
        t.setPriority(10);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // action btn
        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this,AddEditContact.class);
                startActivityForResult(intent,123);
            }
        });


        // swipe delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                contactViewModel.delete(contactAdapter.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,"Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        contactAdapter.setOnItemClickListner(new ContactAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(MainActivity.this, AddEditContact.class);
                intent.putExtra("firstName", contact.getFirstName());
                intent.putExtra("lastName", contact.getFirstName());
                intent.putExtra("email", contact.getFirstName());
                intent.putExtra("phoneNumber", contact.getFirstName());
                intent.putExtra("address", contact.getFirstName());
                intent.putExtra("id", contact.getId());
                startActivityForResult(intent,200);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        SearchView searchView = (SearchView) menu.findItem( R.id.action_search).getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==  123 && resultCode  ==  RESULT_OK){
            String firstName =  data.getStringExtra("firstName");
            String lastName =  data.getStringExtra("lastName");
            String email =  data.getStringExtra("email");
            String phoneNumber =  data.getStringExtra("phoneNumber");
            String address =  data.getStringExtra("address");

            Contact contact = new Contact(firstName,lastName,email,phoneNumber,address);
            contactViewModel.insert(contact);

            Toast.makeText(MainActivity.this,"Contact saved",Toast.LENGTH_SHORT).show();
        }
        else if(requestCode ==  200 && resultCode  ==  RESULT_OK){
            String firstName =  data.getStringExtra("firstName");
            String lastName =  data.getStringExtra("lastName");
            String email =  data.getStringExtra("email");
            String phoneNumber =  data.getStringExtra("phoneNumber");
            String address =  data.getStringExtra("address");
            int id = data.getIntExtra("id",-1);

            Contact contact = new Contact(firstName,lastName,email,phoneNumber,address);
            contact.setId(id);
            contactViewModel.update(contact);

            Toast.makeText(MainActivity.this,"Contact Edited",Toast.LENGTH_SHORT).show();

        }

    }
}