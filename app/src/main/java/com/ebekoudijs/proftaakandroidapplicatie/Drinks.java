package com.ebekoudijs.proftaakandroidapplicatie;

public class Drinks {

    public String Drink1;
    public String Drink2;
    public String Drink3;


    public Drinks(String Shot1, String Shot2, String Shot3, String Amount1, String Amount2, String  Amount3){
        Drink1 = Shot1 + " " + Amount1;
        Drink2 = Shot2 + " " + Amount2;
        Drink3 = Shot3 + " " + Amount3;
    }
}
