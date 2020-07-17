package com.example.contact_prakashrana_c0773839_android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository contactRepository;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        allContacts = contactRepository.getAllContacts();
    }


    public void insert(Contact contact) {
        contactRepository.insert(contact);
    }

    public void update(Contact contact) {
        contactRepository.update(contact);
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void deleteAll(){
        contactRepository.deleteAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts(){
        return contactRepository.getAllContacts();
    }


}

