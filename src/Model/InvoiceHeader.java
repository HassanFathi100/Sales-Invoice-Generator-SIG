package Model;

import java.util.ArrayList;

public class InvoiceHeader {

    private String invoiceNumber;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLines> invoiceDetails;
    private int invoiceItemsCount;


    public InvoiceHeader(String invoiceNumber, String invoiceDate, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<InvoiceLines> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(ArrayList<InvoiceLines> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public int getInvoiceItemsCount() {
        return invoiceItemsCount;
    }

    public void setInvoiceItemsCount(int invoiceItemsCount) {
        this.invoiceItemsCount = invoiceItemsCount;
    }
}
