package com.example.contact_prakashrana_c0773839_android;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact")
    void deleteAllContacts();

    @Query("SELECT COUNT(*) FROM contact")
    int getRowCount();


    @Query("SELECT * FROM contact")
    LiveData<List<Contact>> getAllContacts();

}
