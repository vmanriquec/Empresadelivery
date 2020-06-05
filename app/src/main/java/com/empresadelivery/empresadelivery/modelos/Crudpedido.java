package com.empresadelivery.empresadelivery.modelos;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Crudpedido {




    public final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(PedidoRealm.class).max("id");
        int nextId;
        if(currentIdNum == null){
            nextId = 0;
        }else {
            nextId = currentIdNum.intValue()+1;
        }
        return nextId;
    }


    public final static void addPedidorealm(final Detallepedidorealm Detallepedidorealm){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                int index = Crudpedido.calculateIndex();
                PedidoRealm pedidoRealm = realm.createObject(PedidoRealm.class, index);
                pedidoRealm.setEstadopedido(pedidoRealm.getEstadopedido());
                pedidoRealm.setFechapedido(pedidoRealm.getFechapedido());
                pedidoRealm.setIdalmacen(pedidoRealm.getIdalmacen());
                pedidoRealm.setDescripcionpedido(pedidoRealm.getDescripcionpedido());
                pedidoRealm.setIdcliente(pedidoRealm.getIdcliente());
                pedidoRealm.setIdmesa(pedidoRealm.getIdmesa());
                pedidoRealm.setIdusuario(pedidoRealm.getIdusuario());
                pedidoRealm.setIdfacebook(pedidoRealm.getIdfacebook());
pedidoRealm.setTotalpedido(pedidoRealm.getTotalpedido());
                  }
        });
    }
    public final static List<PedidoRealm> getAllPedidorealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PedidoRealm> PedidoRealm = realm.where(PedidoRealm.class).findAll();
        for(PedidoRealm Pedidorealm: PedidoRealm){
            Log.d("TAG", "idpedido: " + Pedidorealm.getIdpedido() );


        }
        return PedidoRealm;
    }


    public final static PedidoRealm getPedidorealmByIdpedido(int idpedido){
        Realm realm = Realm.getDefaultInstance();
        PedidoRealm PedidoRealm = realm.where(PedidoRealm.class).equalTo("idpedido", idpedido).findFirst();
        if(PedidoRealm != null){
            Log.d("TAG", "idmesadepedidoatrare: " + PedidoRealm.getIdmesa() );
        }
        return PedidoRealm;
    }

    public final static void Actualizartotalpedido(int idpedido, Double totalpedido){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        PedidoRealm pedidoRealm = realm.where(PedidoRealm.class).equalTo("idpedido", idpedido).findFirst();
        pedidoRealm.setTotalpedido(totalpedido);
 realm.insertOrUpdate(pedidoRealm);
        realm.commitTransaction();
        Log.d("TAG", "se actualiuzo total pedido a: " + Double.toString(totalpedido ));
    }
    public final static void Actualizardescripciondepedido(int idpedido, String descripcionpedido){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        PedidoRealm pedidoRealm = realm.where(PedidoRealm.class).equalTo("descripcionpedido", descripcionpedido).findFirst();
        pedidoRealm.setDescripcionpedido(descripcionpedido);
        realm.insertOrUpdate(pedidoRealm);
        realm.commitTransaction();
        Log.d("TAG", "se actualizo descripcion de pedido: " + descripcionpedido );
    }

}
