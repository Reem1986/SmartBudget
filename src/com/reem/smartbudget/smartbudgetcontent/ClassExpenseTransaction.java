
package com.reem.smartbudget.smartbudgetcontent;



//data-model for db
public class ClassExpenseTransaction
{
    public String id;
    public String id_parent;
    public String name;
    public float amount;
    public String from_income;
    public String date;
    public String note;

    public ClassExpenseTransaction()
    {
        id = "";
    	id_parent = "";
        name = "";
        amount = 0;
        from_income = "";
        date = "";
        note = "";

    }
}
