package Controller;

import Model.FileOperations;
import Model.InvoiceHeader;

import java.util.ArrayList;

public class TableController {

    private ArrayList<InvoiceHeader> tablesData;

    public TableController(ArrayList<InvoiceHeader> tablesData) {
        this.tablesData = tablesData;

    }

    public ArrayList<InvoiceHeader> getTablesData() {
        return tablesData;
    }

    public void setTablesData(ArrayList<InvoiceHeader> tablesData) {
        this.tablesData = tablesData;
    }
}
