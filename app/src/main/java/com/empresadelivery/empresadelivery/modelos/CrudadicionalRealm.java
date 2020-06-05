package com.empresadelivery.empresadelivery.modelos;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CrudadicionalRealm {

    public final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(AdicionalRealm.class).max("idadicional");
        int nextId;
        if(currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }


    public final static void addAdicionalRealm(final AdicionalRealm suariosRealm){
       // Log.d("TAG", "idusuarioinicio: "+suariosRealm.getIdfacebook()+suariosRealm.getNombreusuario());

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                int index = CrudadicionalRealm.calculateIndex();

                AdicionalRealm adicional = realm.createObject(AdicionalRealm.class, index);

                adicional.setEstadoadicional(suariosRealm.getEstadoadicional());
                adicional.setId(suariosRealm.getId());
                //adicional.setIdadicional(index);
                adicional.setNombreadicional(suariosRealm.getNombreadicional());
                adicional.setPrecioadicional(suariosRealm.getPrecioadicional());
                realm.insertOrUpdate(adicional);

            }
        });
    }
    public final static List<AdicionalRealm> getAllAdicionalRealm(){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<AdicionalRealm> AdicionalRealm = realm.where(AdicionalRealm.class).findAll();
        for(AdicionalRealm ysuariosRealm: AdicionalRealm){
            Log.d("TAG", "idusuario: " + ysuariosRealm.getNombreadicional()+ysuariosRealm.getPrecioadicional()+ysuariosRealm.getId()+ysuariosRealm.getIdadicional() +ysuariosRealm.getEstadoadicional());


        }
        return AdicionalRealm;
    }


    public final static AdicionalRealm getAdicionalRealmByidusuario(int idadicional){
        Realm realm = Realm.getDefaultInstance();
        AdicionalRealm AdicionalRealm = realm.where(AdicionalRealm.class).equalTo("idadicional", idadicional).findFirst();
        if(AdicionalRealm != null){
            Log.d("TAG", "idmesadepedidoatrare: " + AdicionalRealm.getIdadicional() );
        }
        return AdicionalRealm;
    }



    public final static AdicionalRealm buscarporid(String id){
        Realm realm = Realm.getDefaultInstance();
        AdicionalRealm AdicionalRealm = realm.where(AdicionalRealm.class).equalTo("id", id).findFirst();
        if(AdicionalRealm != null){
            Log.d("TAG", "idmesadepedidoatrare: " + AdicionalRealm.getNombreadicional() );
        }
        return AdicionalRealm;
    }

    public final static void Actualizarestadousuario(int idadicional, String estado){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        AdicionalRealm AdicionalRealm = realm.where(AdicionalRealm.class).equalTo("idadicional", idadicional).findFirst();
        AdicionalRealm.setEstadoadicional(estado);
        realm.insertOrUpdate(AdicionalRealm);

        Log.d("TAG", "se actualiuzo estado de adicional: " + estado);
    }



    public final static void eliminaradicional(int idadicional){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        AdicionalRealm profesor = realm.where(AdicionalRealm.class).equalTo("idadicional", idadicional).findFirst();
        profesor.deleteFromRealm();
        realm.commitTransaction();





        Log.d("TAG", "se elimino pedido con id : " + String.valueOf(idadicional) );
    }
}



