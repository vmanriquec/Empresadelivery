package com.empresadelivery.empresadelivery.modelos;

import io.realm.Realm;

public class Crudetallepedido {

    public final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(Detallepedidorealm.class).max("id");
        int nextId;
        if(currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }


}
