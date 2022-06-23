package View;

import Controller.DateValidation;
import Controller.TableController;
import Model.FileOperations;
import Model.InvoiceHeader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class GUI extends JFrame implements ActionListener, MouseListener {

    private String headerFilePathDefault = "resoures/InvoiceHeader.csv";
    private String lineFilePathDefault = "resoures/InvoiceLine.csv";

    // Menu Bar
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadFileItem;
    private JMenuItem saveFileItem;


    private JSplitPane splitPane;
    // Left Panel
    private JPanel leftPanel;

    private DefaultTableModel invoicesTableModel;
    private JTable invoicesTable;
    private String[] invoicesTableColumns = {"No.", "Date", "Customer", "Total"};

    private ArrayList<InvoiceHeader> invoicesTableData = new ArrayList<InvoiceHeader>();
    TableController tableController = new TableController(invoicesTableData, headerFilePathDefault, lineFilePathDefault);

    private JButton newInvoiceButton;
    private JButton deleteInvoiceButton;

    // Right Panel
    private JPanel rightPanel;
    private String invoiceNumber;

    private JTextField invoiceDateTF;
    private JTextField customerNameTF;

    private int invoiceTotal;

    DefaultTableModel invoiceDetailsTableModel;
    private JTable invoiceDetailsTable;
    private String[] invoiceDetailsTableColumns = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    private JButton saveButton;
    private JButton cancelButton;
    private JButton addNewRowButton;

    public GUI(){
        super("Sales Invoice Generator");
        setLayout(null);
        setSize(870,700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Menu Bar
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");

        loadFileItem = new JMenuItem("Load File", 'L');
        loadFileItem.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
        loadFileItem.addActionListener(this);
        loadFileItem.setActionCommand("loadFile");
        fileMenu.add(loadFileItem);

        saveFileItem = new JMenuItem("Save File", 'S');
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke('S',KeyEvent.CTRL_DOWN_MASK));
        saveFileItem.addActionListener(this);
        saveFileItem.setActionCommand("saveFile");
        fileMenu.add(saveFileItem);

        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        //Left Panel
        invoicesTableModel = new DefaultTableModel(null, invoicesTableColumns);

        invoicesTableData = tableController.getTablesData();

        for(int i = 0 ; i < invoicesTableData.size() ; i++) {
            Object[] invoicesTableDataObject = new Object[]{
                    invoicesTableData.get(i).getInvoiceNumber(),
                    invoicesTableData.get(i).getInvoiceDate(),
                    invoicesTableData.get(i).getCustomerName(),
                    " ",
            };
            invoicesTableModel.addRow(invoicesTableDataObject);
        }

        invoicesTable = new JTable(invoicesTableModel);
        invoicesTable.addMouseListener(this);
        JScrollPane invoicesTableSP = new JScrollPane(invoicesTable);
        invoicesTableSP.setBounds(20,30,400,540);
        add(invoicesTableSP);

        newInvoiceButton = new JButton("Create New Invoice");
        newInvoiceButton.setBounds(70, 590, 150,20);
        newInvoiceButton.addActionListener(this);
        newInvoiceButton.setActionCommand("newInvoice");
        add(newInvoiceButton);

        deleteInvoiceButton = new JButton("Delete Invoice");
        deleteInvoiceButton.setBounds(230, 590, 150,20);
        deleteInvoiceButton.addActionListener(this);
        deleteInvoiceButton.setActionCommand("deleteInvoice");
        add(deleteInvoiceButton);

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // Right Panel
        invoiceNumber = invoicesTableData.get(0).getInvoiceNumber();
        JLabel invoiceNumberLabel = new JLabel("Invoice Number: "+ invoiceNumber);
        invoiceNumberLabel.setBounds(440,20, 400, 20);
        add(invoiceNumberLabel);

        JLabel invoiceDateLabel = new JLabel("Invoice Date");
        invoiceDateLabel.setBounds(440,60, 100, 20);
        add(invoiceDateLabel);

        invoiceDateTF = new JTextField(20);
        invoiceDateTF.setBounds(540,60, 300, 20);
        add(invoiceDateTF);

        JLabel customerNameLabel = new JLabel("Customer Name");
        customerNameLabel.setBounds(440,100, 100, 20);
        add(customerNameLabel);

        customerNameTF = new JTextField(20);
        customerNameTF.setBounds(540,100, 300, 20);
        add(customerNameTF);

        JLabel invoiceTotalLabel = new JLabel("Invoice Total: " + invoiceTotal);
        invoiceTotalLabel.setBounds(440,140, 400, 20);
        add(invoiceTotalLabel);

        rightPanel = new JPanel(null);
        rightPanel.setBounds(440,180,400,400);

        Border blacklineRight = BorderFactory.createTitledBorder("Invoice Items"); // Create border for the panel with Title
        rightPanel.setBorder(blacklineRight);
        getContentPane().add(rightPanel, BorderLayout.CENTER);
        add(rightPanel);

        invoiceDetailsTableModel = new DefaultTableModel(null, invoiceDetailsTableColumns);

        for (int i = 0 ; i < invoicesTableData.toArray().length ; i++){
            for(int j = 0 ; j < invoicesTableData.get(i).getInvoiceDetails().toArray().length ; j++){
                Object[] invoicesTableDataObject = new Object[10];

                if(Objects.equals(invoicesTableData.get(i).getInvoiceNumber(), invoicesTableData.get(i).getInvoiceDetails().get(j).getInvoiceNumber())) {
                        invoicesTableDataObject = new Object[]{
                                invoicesTableData.get(i).getInvoiceDetails().get(j).getInvoiceNumber(),
                                invoicesTableData.get(i).getInvoiceDetails().get(j).getItemName(),
                                invoicesTableData.get(i).getInvoiceDetails().get(j).getItemPrice(),
                                invoicesTableData.get(i).getInvoiceDetails().get(j).getItemCount(),
                                invoicesTableData.get(i).getInvoiceDetails().get(j).getItemTotalPrice(),
                        };
                    invoiceDetailsTableModel.addRow(invoicesTableDataObject);
                    }
                }
            }


        invoiceDetailsTable = new JTable(invoiceDetailsTableModel);
        JScrollPane invoiceDetailsTableSP = new JScrollPane(invoiceDetailsTable);
        invoiceDetailsTableSP.setBounds(10,20,380,370);
        rightPanel.add(invoiceDetailsTableSP, BorderLayout.CENTER);

        saveButton = new JButton("Save");
        saveButton.setBounds(580,590,100,20);
        saveButton.addActionListener(this);
        saveButton.setActionCommand("saveNewInvoice");
        add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(700,590,100,20);
        add(cancelButton);

        addNewRowButton = new JButton("Add Row");
        addNewRowButton.setBounds(460,590,100,20);
        addNewRowButton.addActionListener(this);
        addNewRowButton.setActionCommand("addNewRow");
        add(addNewRowButton);
    }









    @Override
    public void actionPerformed(ActionEvent e) {
        var i = invoicesTable.getSelectedRow();
//        System.out.println(i);
        switch (e.getActionCommand()) {
            case "saveFile" -> saveInvoice();
            case "loadFile" -> loadInvoice();
            case "deleteInvoice" -> deleteInvoice();
            case "newInvoice" -> createNewInvoice();
            case "addNewRow" -> addNewRow();
            case "saveNewInvoice" -> saveNewInvoice();
            default -> {}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    private void saveInvoice() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save your invoice");

        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Comma Separated Values (.csv)", "csv");
        fc.addChoosableFileFilter(restrict);

        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File csvFile = fc.getSelectedFile();
            String path = csvFile.getAbsolutePath();

            if(!path.endsWith(".csv")){
                csvFile = new File(path + ".csv");
            }
            FileWriter fw = null;
            BufferedWriter bw = null;

            try {
                fw = new FileWriter(csvFile);
                bw = new BufferedWriter(fw);

                // Iterate over each cell of the table
                for (int i = 0; i < invoiceDetailsTable.getRowCount(); i++) {
                    for (int j = 0; j < invoiceDetailsTable.getColumnCount(); j++) {
                        bw.write(invoiceDetailsTable.getValueAt(i, j).toString() + ","); // Save the value of each cell and follow it with ","
                    }
                    bw.newLine(); // Create a new line (new row)
                }
                JOptionPane.showMessageDialog(this, "Your file has been saved successfully", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    assert bw != null;
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }



    private void saveNewInvoice() {

        Boolean isDateRight = new DateValidation().dateValidation(invoiceDateTF.getText());

        if(!customerNameTF.getText().isEmpty()) {
            if (isDateRight) {
                invoicesTableModel.addRow(new Object[]{invoiceNumber, invoiceDateTF.getText(), customerNameTF.getText(), invoiceTotal});
                saveInvoice();
            } else {
                JOptionPane.showMessageDialog(this, "Enter date in the right formar (dd-MM-yyyy)", "Invalid date format", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Enter customer name", "Invalid name", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadInvoice(){
        JOptionPane.showMessageDialog(this, "Select Invoice Header.", "Invoice Header", JOptionPane.INFORMATION_MESSAGE);

        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Comma Separated Values (.csv)", "csv");
        fc.addChoosableFileFilter(restrict);

        int result = fc.showOpenDialog(this);

        String headerFilePath = "";
        if(result == JFileChooser.APPROVE_OPTION){
            headerFilePath = fc.getSelectedFile().getPath();
        }

        JOptionPane.showMessageDialog(this, "Select Invoice Line.", "Invoice Line", JOptionPane.INFORMATION_MESSAGE);

        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        restrict = new FileNameExtensionFilter("Comma Separated Values (.csv)", "csv");
        fc.addChoosableFileFilter(restrict);

        result = fc.showOpenDialog(this);

        String lineFilePath = "";
        if(result == JFileChooser.APPROVE_OPTION){
            lineFilePath = fc.getSelectedFile().getPath();
        }
        System.out.println("invoicesTableModel.getRowCount()" + invoicesTableModel.getRowCount());

        int tempRowCount = invoicesTableModel.getRowCount();
        for(int i = 0 ; i < tempRowCount ; i++){
            invoicesTableModel.removeRow(0);
        }
        tempRowCount = invoiceDetailsTableModel.getRowCount();
        for(int i = 0 ; i < tempRowCount ; i++){
            invoiceDetailsTableModel.removeRow(0);
        }

        invoicesTableData = new ArrayList<InvoiceHeader>();
        tableController = new TableController(invoicesTableData, headerFilePath, lineFilePath);
        invoicesTableData = tableController.getTablesData();


        for(int i = 0 ; i < invoicesTableData.size() ; i++) {
            Object[] invoicesTableDataObject = new Object[]{
                    invoicesTableData.get(i).getInvoiceNumber(),
                    invoicesTableData.get(i).getInvoiceDate(),
                    invoicesTableData.get(i).getCustomerName(),
                    " ",
            };
            invoicesTableModel.addRow(invoicesTableDataObject);
        }



        for (int i = 0 ; i < invoicesTableData.toArray().length ; i++){
            for(int j = 0 ; j < invoicesTableData.get(i).getInvoiceDetails().toArray().length ; j++){
                Object[] invoicesTableDataObject;

                if(Objects.equals(invoicesTableData.get(i).getInvoiceNumber(), invoicesTableData.get(i).getInvoiceDetails().get(j).getInvoiceNumber())) {
                    invoicesTableDataObject = new Object[]{
                            invoicesTableData.get(i).getInvoiceDetails().get(j).getInvoiceNumber(),
                            invoicesTableData.get(i).getInvoiceDetails().get(j).getItemName(),
                            invoicesTableData.get(i).getInvoiceDetails().get(j).getItemPrice(),
                            invoicesTableData.get(i).getInvoiceDetails().get(j).getItemCount(),
                            invoicesTableData.get(i).getInvoiceDetails().get(j).getItemTotalPrice(),
                    };
                    invoiceDetailsTableModel.addRow(invoicesTableDataObject);
                }
            }
        }


    }

    private void deleteInvoice() {
        int row = invoicesTable.getSelectedRow();
        Object deletedInvoiceNumber = invoicesTable.getModel().getValueAt(row, 0);
        invoicesTableModel.removeRow(row);
        String temp = null;

        for(int i = 0 ; i < invoicesTableData.size() ; i++){
            if(invoicesTableData.get(i).getInvoiceNumber() == deletedInvoiceNumber){
                temp = invoicesTableData.get(i).getInvoiceNumber();
            }
        }

        ArrayList<Integer> tempArray = new ArrayList<Integer>(10);
        for(int i = 0 ; i < invoiceDetailsTableModel.getRowCount() ; i++){
            Object deletedInvoiceLine = invoiceDetailsTableModel.getValueAt(i, 0);

            if (Objects.equals(deletedInvoiceLine.toString(), temp)){
                tempArray.add(i);
            }
        }
        System.out.println(tempArray);
        for(int i = tempArray.size()-1 ; i >= 0 ; i--) {
            invoiceDetailsTableModel.removeRow(tempArray.get(i));
        }

        JOptionPane.showMessageDialog(this, "Invoice has been deleted successfully. Press 'save' to save current invoices.", "Delete Invoice", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createNewInvoice(){
        invoicesTableModel.addRow(new Object[]{" ", " ", " ", " "});
    }

    private void addNewRow(){
        invoiceDetailsTableModel.addRow(new Object[]{" ", " ", " ", " ", " "});
    }
}


