package com.empresadelivery.empresadelivery.modelos;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CrudUsuarios {



    private final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(UsuariosRealm.class).max("idusuario");
        int nextId;
        if(currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }


    public final static void addUsuariosRealm(final UsuariosRealm suariosRealm){
        Log.d("TAG", "idusuarioinicio: "+suariosRealm.getIdfacebook()+suariosRealm.getNombreusuario());

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                int index = CrudUsuarios.calculateIndex();

                UsuariosRealm resuariosRealm = realm.createObject(UsuariosRealm.class, index);

                resuariosRealm.setAlmacenusuario(suariosRealm.getAlmacenusuario());
                resuariosRealm.setClaveusuario(suariosRealm.getClaveusuario());
                resuariosRealm.setEstadousuario(suariosRealm.getEstadousuario());
                resuariosRealm.setImagen(suariosRealm.getImagen());
                resuariosRealm.setNombreusuario(suariosRealm.getNombreusuario());
        resuariosRealm.setIdfacebook(suariosRealm.getIdfacebook());
                realm.insertOrUpdate(resuariosRealm);

            }
        });
    }
    public final static List<UsuariosRealm> getAllUsuariosRealm(){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<UsuariosRealm> UsuariosRealm = realm.where(UsuariosRealm.class).findAll();
        for(UsuariosRealm ysuariosRealm: UsuariosRealm){
            Log.d("TAG", "idusuario: " + ysuariosRealm.getIdusuario()+ysuariosRealm.getNombreusuario()+ysuariosRealm.getIdfacebook()+ysuariosRealm.getImagen() );


        }
        return UsuariosRealm;
    }


    public final static UsuariosRealm getUsuariosRealmByidusuario(int idusuario){
        Realm realm = Realm.getDefaultInstance();
        UsuariosRealm UsuariosRealm = realm.where(UsuariosRealm.class).equalTo("idusuario", idusuario).findFirst();
        if(UsuariosRealm != null){
            Log.d("TAG", "idmesadepedidoatrare: " + UsuariosRealm.getIdusuario() );
        }
        return UsuariosRealm;
    }



    public final static UsuariosRealm buscarusuariiporidFacebook(String idfacebook){
        Realm realm = Realm.getDefaultInstance();
        UsuariosRealm UsuariosRealm = realm.where(UsuariosRealm.class).equalTo("idfacebook", idfacebook).findFirst();
        if(UsuariosRealm != null){
            Log.d("TAG", "idmesadepedidoatrare: " + UsuariosRealm.getIdusuario() );
        }
        return UsuariosRealm;
    }

    public final static void Actualizarestadousuario(int idusuario, String estado){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UsuariosRealm UsuariosRealm = realm.where(UsuariosRealm.class).equalTo("idusuario", idusuario).findFirst();
        UsuariosRealm.setEstadousuario(estado);
        realm.insertOrUpdate(UsuariosRealm);

        Log.d("TAG", "se actualiuzo estado de usuario: " + estado);
    }



    public final static void eliminarusuarioporidusuario(int idusuario){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UsuariosRealm profesor = realm.where(UsuariosRealm.class).equalTo("idusuario", idusuario).findFirst();
        profesor.deleteFromRealm();
        realm.commitTransaction();





        Log.d("TAG", "se elimino pedido con id : " + String.valueOf(idusuario) );
    }
}


