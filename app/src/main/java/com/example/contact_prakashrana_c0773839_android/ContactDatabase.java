package com.example.contact_prakashrana_c0773839_android;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase instance;

    public abstract ContactDao contactDao();

    public static synchronized ContactDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class,"contact")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }


    private  static  RoomDatabase.Callback roomCallback =  new RoomDatabase.Callback() {


        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDb(instance).execute();
        }
    };

    private static class PopulateDb extends AsyncTask<Void, Void, Void> {

        private ContactDao contactDao;

        private PopulateDb(ContactDatabase contactDatabase){
            contactDao = contactDatabase.contactDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.insert(new Contact("Prakash", "Rana", "rrr@gamail.cpm","123123","graydon hall"));
            contactDao.insert(new Contact("Ram", "Rana", "rrr@gamail.cpm","123123","graydon hall"));
            contactDao.insert(new Contact("Hari", "Rana", "rrr@gamail.cpm","123123","graydon hall"));
            contactDao.insert(new Contact("Shyam", "Rana", "Shyam@gamail.cpm","123123","graydon hall"));

            return null;
        }
    }
}
