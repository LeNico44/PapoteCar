package org.ln.autopartagedata.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class GenericService<T extends CrudRepository<E, Long>,E> {

    protected T repository;

    public List<E> findAll(){
        return (List<E>) repository.findAll();
    }

    public T getRepository() {
        return repository;
    }

    public void setRepository(T repository) {
        this.repository=repository;
    }

    //Convertisseur de String en Double
    public Double stringToDouble(String string){
        String stringPoint = string.replaceAll(",",".");
        //Conversion de la string en integer
        return Double.parseDouble(stringPoint);
    }

    //Convertisseur de String en Date sql
    public java.sql.Date stringToDateSql(String string) throws ParseException {
        //déclaration du format de la date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        //Conversation de la string en simpleDateFormat
        Date date = sdf.parse(string);
        //Retour de la date en sql date
        return new java.sql.Date(date.getTime());
    }

    //Convertisseur de String en boolean
    public boolean stringToBoolean(String string){
        boolean theBoolean = false;
        if(string.equals("on") || string.equals("true") || string.equals("1")){
            theBoolean = true;
        }
        return theBoolean;
    }
}
