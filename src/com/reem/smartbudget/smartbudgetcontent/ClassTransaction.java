
package com.reem.smartbudget.smartbudgetcontent;



//data-model for db
public class ClassTransaction
{
    public String id;
    public String name;
    public float amount;
    public boolean creditCard;
    public String cycle;
    public int icon;
    public String date;
    public String note;

    public ClassTransaction()
    {
        id = "12345";
        name = "hello name";
        amount = 0;
        creditCard = false;
        cycle = "yes";
        icon = 0;
        date = "3 sep";
        note = "no note";

    }
}
