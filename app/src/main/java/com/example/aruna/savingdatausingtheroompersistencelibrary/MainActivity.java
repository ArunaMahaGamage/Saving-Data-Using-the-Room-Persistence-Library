package com.example.aruna.savingdatausingtheroompersistencelibrary;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Need to do call crud operation in AsyncTask - other wise will throw exception

        createRoomObject();
    }

    public void createRoomObject() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        user = new User();
        user.setUid(51);
        user.setFirstName("Aruna");
        user.setLastName("Gamage");


        /*InsertTask insertTask = new InsertTask();
        insertTask.execute(user);*/

        RetreveTask retreveTask = new RetreveTask();
        retreveTask.execute();

        /*UpdateTask updateTask = new UpdateTask();
        updateTask.execute(user);*/

        /*DeleteTask deleteTask = new DeleteTask();
        deleteTask.execute(user);*/

    }

    class InsertTask extends AsyncTask <User,Integer,Object> {
        @Override
        protected Object doInBackground(User... users) {
            db.userDao().insertAll(users[0]);
            return users;
        }
    }

    class RetreveTask extends AsyncTask <User,Integer,List> {

        @Override
        protected List doInBackground(User... users) {
            List list = db.userDao().getAll();
            return list;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);

            for (int i = 0; i < list.size(); i++) {

                DeleteTask deleteTask = new DeleteTask();
                User user = (User) list.get(i);
                deleteTask.execute(user);
                Log.e("Result", String.valueOf(user.getFirstName()));
            }
        }
    }

    class UpdateTask extends AsyncTask <User,String ,List> {

        @Override
        protected List doInBackground(User... users) {
            db.userDao().update(users[0]);
            return null;
        }
    }

    class DeleteTask extends AsyncTask <User,Integer,List> {

        @Override
        protected List doInBackground(User... users) {
            db.userDao().delete(users[0]);
            return null;
        }
    }

}
