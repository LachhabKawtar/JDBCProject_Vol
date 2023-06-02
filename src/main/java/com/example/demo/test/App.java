package com.example.demo.test;

import com.example.demo.services.VolService;

/**
 * Hello world!
 *
 */
public class App {


    public static void main(String[] args) throws Exception {
        //  new VolService().importXL().forEach(v-> System.out.println(v));
        //Création d'un objet de type fichier Excel
          new VolService().findAll().forEach(v->{
              System.out.println(v);
          });
    }
}