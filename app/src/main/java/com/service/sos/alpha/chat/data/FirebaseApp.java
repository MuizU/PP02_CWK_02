package com.service.sos.alpha.chat.data;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseApp extends android.support.multidex.MultiDexApplication {

    @Override
    public void onCreate(){
        super.onCreate();
        /*Enable disk persistence*/
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
