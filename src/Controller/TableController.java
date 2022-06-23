package Controller;

import Model.FileOperations;
import Model.InvoiceHeader;

import java.util.ArrayList;

public class TableController {

    private ArrayList<InvoiceHeader> tablesData;
    private String headerFilePath;
    private String lineFilePath;

    public TableController(ArrayList<InvoiceHeader> tablesData, String headerFilePath, String lineFilePath) {
        this.tablesData = tablesData;
        this.headerFilePath = headerFilePath;
        this.lineFilePath = lineFilePath;

        tablesData = FileOperations.readFile(headerFilePath,lineFilePath);
        setTablesData(tablesData);
    }

    public ArrayList<InvoiceHeader> getTablesData() {
        return tablesData;
    }

    public void setTablesData(ArrayList<InvoiceHeader> tablesData) {
        this.tablesData = tablesData;
    }
}
