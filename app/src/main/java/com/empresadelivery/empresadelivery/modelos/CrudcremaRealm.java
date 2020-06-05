
package com.empresadelivery.empresadelivery.modelos;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CrudcremaRealm {
    public final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(CremaRealm.class).max("id");
        int nextId;
        if(currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }


    public final static void addCremaRealm(final CremaRealm suariosRealm){
        // Log.d("TAG", "idusuarioinicio: "+suariosRealm.getIdfacebook()+suariosRealm.getNombreusuario());

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                int index = CrudcremaRealm.calculateIndex();

                CremaRealm adicional = realm.createObject(CremaRealm.class, index);

                adicional.setEstadocrema(suariosRealm.getEstadocrema());
                adicional.setId(suariosRealm.getId());
                //adicional.setIdadicional(index);
                adicional.setNombrecrema(suariosRealm.getNombrecrema());

                realm.insertOrUpdate(adicional);

            }
        });
    }
    public final static List<CremaRealm> getAllCremaRealm(){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<CremaRealm> CremaRealm = realm.where(CremaRealm.class).findAll();
        for(CremaRealm ysuariosRealm: CremaRealm){
            Log.d("TAG", "idcremarealm: " + ysuariosRealm.getEstadocrema()+ysuariosRealm.getNombrecrema()+ysuariosRealm.getId()+ysuariosRealm.getIdcrema() );


        }
        return CremaRealm;
    }


    public final static CremaRealm getCremaRealmByidcrema(int idcrema){
        Realm realm = Realm.getDefaultInstance();
        CremaRealm CremaRealm = realm.where(CremaRealm.class).equalTo("idcrema", idcrema).findFirst();
        if(CremaRealm != null){
            Log.d("TAG", "idmesadepedidoatrare: " + CremaRealm.getIdcrema() );
        }
        return CremaRealm;
    }



    public final static CremaRealm buscarporid(String id){
        Realm realm = Realm.getDefaultInstance();
        CremaRealm CremaRealm = realm.where(CremaRealm.class).equalTo("idcrema", id).findFirst();
        if(CremaRealm != null){
            Log.d("TAG", "idmesadepedidoatrare: " + CremaRealm.getNombrecrema() );
        }
        return CremaRealm;
    }

    public final static void actualizarestadodecrema(int idcrema, String estado){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CremaRealm CremaRealm = realm.where(CremaRealm.class).equalTo("idcrema", idcrema).findFirst();
        CremaRealm.setEstadocrema(estado);
        realm.insertOrUpdate(CremaRealm);

        Log.d("TAG", "se actualiuzo estado de adicional: " + estado);
    }



    public final static void eliminarcrema(int idcrema){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CremaRealm profesor = realm.where(CremaRealm.class).equalTo("idcrema", idcrema).findFirst();
        profesor.deleteFromRealm();
        realm.commitTransaction();





        Log.d("TAG", "se elimino crema con id : " + String.valueOf(idcrema) );
    }
}



