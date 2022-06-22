package Controller;

import Model.InvoiceHeader;
import Model.InvoiceLines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileOperations {

    public static void main(String[] args) throws IOException {

        ArrayList<InvoiceHeader> invoicesData;

        invoicesData = readFile("resoures/InvoiceHeader.csv","resoures/InvoiceLine.csv");

        for(int i = 0 ; i < invoicesData.toArray().length ; i++){
            System.out.println("Invoice Number: " + invoicesData.get(i).getInvoiceNumber());
            System.out.println("{");
            System.out.println("Invoice Date: " + invoicesData.get(i).getInvoiceDate() +
                    " , " + "Customer Name: " + invoicesData.get(i).getCustomerName()
            );

            for(int j = 0; j < invoicesData.get(i).getInvoiceDetails().toArray().length ; j++){

                System.out.println(invoicesData.get(i).getInvoiceDetails().get(j).getItemName() + " " +
                        invoicesData.get(i).getInvoiceDetails().get(j).getItemPrice() + " " +
                        invoicesData.get(i).getInvoiceDetails().get(j).getItemCount() + " " +
                        invoicesData.get(i).getInvoiceDetails().get(j).getItemTotalPrice()
                );
            }

            System.out.println("}");
        }

    }


    public static ArrayList<InvoiceHeader> readFile(String headerFilePath, String lineFilePath) {
        String delimiter = ",";
        ArrayList<InvoiceHeader> data = new ArrayList<InvoiceHeader>();

        ArrayList<InvoiceLines> invoiceDetails = new ArrayList<InvoiceLines>();
        invoiceDetails = readLineFile(lineFilePath);

        try {
            File file = new File(headerFilePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] csvDataArray;
            String[] tempArray = new String[3];
            int i =0;
            while ((line = br.readLine()) != null) {

                    csvDataArray = line.split(delimiter);
                    InvoiceHeader invoiceHeader = new InvoiceHeader(csvDataArray[0], csvDataArray[1], csvDataArray[2]);
                    invoiceHeader.setInvoiceDetails(invoiceDetails);
                    data.add(invoiceHeader);

            }
            br.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return data;
    }

    public void writeFile(ArrayList<InvoiceHeader> tableData){

    }


    public static ArrayList<InvoiceLines> readLineFile(String lineFilePath) {
        String delimiter = ",";
        ArrayList<InvoiceLines> data = new ArrayList<InvoiceLines>();


        try {
            File file = new File(lineFilePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] csvDataArray;
            String[] tempArray = new String[3];
            int i =0;
            while ((line = br.readLine()) != null) {

                csvDataArray = line.split(delimiter);
                InvoiceLines invoiceLines = new InvoiceLines(csvDataArray[0], csvDataArray[1], csvDataArray[2], csvDataArray[3]);
                data.add(invoiceLines);


            }
            br.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return data;
    }
}
