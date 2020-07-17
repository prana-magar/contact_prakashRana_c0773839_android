package com.example.contact_prakashrana_c0773839_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class AddEditContact extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phoneNumber;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEmail);
        phoneNumber = findViewById(R.id.editTextPhoneNumber);
        address = findViewById(R.id.editTextAddress);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            setTitle("Edit Contact");
            firstName.setText(intent.getStringExtra("firstName"));
            lastName.setText(intent.getStringExtra("lastName"));
            email.setText(intent.getStringExtra("email"));
            phoneNumber.setText(intent.getStringExtra("phoneNumber"));
            address.setText(intent.getStringExtra("address"));

        }
        else{
            setTitle("Add Contact");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.save_contact:
                saveContact();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveContact(){
        String firstName = this.firstName.getText().toString();
        String lastName = this.lastName.getText().toString();
        String email = this.email.getText().toString();
        String phoneNumber = this.phoneNumber.getText().toString();
        String address = this.address.getText().toString();


        Intent data = new Intent();
        data.putExtra("firstName", firstName);
        data.putExtra("lastName", lastName);
        data.putExtra("email", email);
        data.putExtra("phoneNumber", phoneNumber);
        data.putExtra("address", address);

        int id = getIntent().getIntExtra("id", -1);

        if(id != -1){
            data.putExtra("id", id);
        }

        setResult(RESULT_OK, data);
        finish();


    }
}