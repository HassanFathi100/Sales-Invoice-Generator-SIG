package Model;

public class InvoiceLines {

    private String invoiceNumber;
    private String itemName;
    private String itemPrice;
    private String itemCount;
    private String itemTotalPrice;

    public InvoiceLines(String invoiceNumber, String itemName, String itemPrice, String itemCount) {
        this.invoiceNumber = invoiceNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;

        Float itemTotalPrice= (Float.parseFloat(itemPrice) * Integer.parseInt(itemCount));
        this.itemTotalPrice = itemTotalPrice.toString();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(String itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }
}
