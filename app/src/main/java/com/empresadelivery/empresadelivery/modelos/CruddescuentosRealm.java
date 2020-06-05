package com.empresadelivery.empresadelivery.modelos;

import io.realm.Realm;

public class CruddescuentosRealm {
    public final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(DescuentosRealm.class).max("iddesc");
        int nextId;
        if(currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }
}
