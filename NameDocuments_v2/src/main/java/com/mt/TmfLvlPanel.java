package com.mt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TmfLvlPanel extends JPanel {

    private JComboBox sectionBox;
    private JComboBox subSectionBox;
    private JComboBox classificationBox;
    private JComboBox documentBox;
    private JButton checkFieldsButton;
    private String row;
    private JTextField expirationDateField;
    private JTextField documentDateField;
    private JTextField submissionDateField;
    private JTextField versionField;

    private JLabel expirationDateLabel;
    private JLabel documentDateLabel;
    private JLabel submissionDateLabel;
    private JLabel versionLabel;

    private JButton nameButton;
    private JButton locationButton;
    RowModel rowModel;
    String filePath;
    String direcotry;
    String fileChooserPath = (System.getProperty("user.home") + "/Desktop");

    // Create JPannel
    public TmfLvlPanel(List<RowModel> rowModels, PDFViewer pdfViewer) {
        setLayout(new GridLayout(12, 1));
        setBorder(BorderFactory.createLineBorder(Color.black));

        Set<String> sectionsSet = rowModels.stream().map(rowModel -> rowModel.getSection()).collect(Collectors.toSet());
        java.util.List<String> sections = new ArrayList<>(sectionsSet);
        Collections.sort(sections);

        sectionBox = new JComboBox(sections.toArray());
        subSectionBox = new JComboBox();
        classificationBox = new JComboBox();
        documentBox = new JComboBox();
        checkFieldsButton = new JButton("Check Fields");
        expirationDateField = new JTextField();
        documentDateField = new JTextField();
        submissionDateField = new JTextField();
        versionField = new JTextField();
        expirationDateLabel = new JLabel("Expiration Date");
        documentDateLabel = new JLabel("Document Date");
        submissionDateLabel = new JLabel("Submission Date");
        versionLabel = new JLabel("Version");
        nameButton = new JButton("Name Document");
        locationButton = new JButton();
        locationButton.setText("Source");;
        locationButton.setVisible(true);

        //click on sectionBox will display list
        ActionListener sectionActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deactivateFields();
                documentBox.removeAllItems();
                classificationBox.removeAllItems();
                subSectionBox.removeAllItems();
                String s = (String) sectionBox.getSelectedItem();
                Set<String> subSectionsSet = rowModels.stream().filter(rowModel -> rowModel.getSection().equals(s)).map(rowModel -> rowModel.getSubsection()).collect(Collectors.toSet());
                java.util.List<String> subSections = new ArrayList<>(subSectionsSet);
                Collections.sort(subSections);
                for (String sub : subSections) {
                    subSectionBox.addItem(sub);
                }
                classificationBox.removeAllItems();
                nameButton.setEnabled(false);
            }
        };

        //click on subsectionBox will display list
        ActionListener subSectionActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                classificationBox.removeAllItems();
                documentBox.removeAllItems();
                deactivateFields();
                String s = (String) subSectionBox.getSelectedItem();
                Set<String> classificationSet = rowModels.stream().filter(rowModel -> rowModel.getSubsection().equals(s)).map(rowModel -> rowModel.getClassification()).collect(Collectors.toSet());
                List<String> classifications = new ArrayList<>(classificationSet);
                Collections.sort(classifications);
                for (String clas : classifications) {
                    classificationBox.addItem(clas);
                }
                documentBox.removeAllItems();
                nameButton.setEnabled(false);
            }
        };

        //click on classificationBox will display list
        ActionListener classificationActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                documentBox.removeAllItems();
                deactivateFields();
                String s = (String) classificationBox.getSelectedItem();
                Set<String> documentSet = rowModels.stream().filter(rowModel -> rowModel.getClassification().equals(s)).map(rowModel -> rowModel.getDocument()).collect(Collectors.toSet());
                List<String> documents = new ArrayList<>(documentSet);
                Collections.sort(documents);
                for (String doc : documents) {
                    documentBox.addItem(doc);
                }
                nameButton.setEnabled(false);
            }
        };

        //click on documentBox will display list
        ActionListener documentActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    deactivateFields();
                    row = (String) documentBox.getSelectedItem();
                }
        };

        //click on checkButton will display appropriate fields
        ActionListener checkButtonActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deactivateFields();
                String row1 = (String)classificationBox.getSelectedItem();
                String row2 = (String)documentBox.getSelectedItem();
                if(row1!= null && filePath!=null) {
                    RowModelFilter rowModelFilter = new RowModelFilter();
                    rowModel = rowModelFilter.getRowModel(rowModels, row1, row2);
                    activateFields();
                nameButton.setEnabled(true);
                revalidate();
                repaint();
                }
                if(filePath==null){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Select document to be named");
                }
            }
        };


        ActionListener locActionListener = new ActionListener()
        {
        public void actionPerformed(ActionEvent e) throws NullPointerException {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(fileChooserPath));
            int result = chooser.showDialog(locationButton, "Run Aplication");
            if(result==JFileChooser.APPROVE_OPTION) {
                filePath = chooser.getSelectedFile().toString();
                direcotry = chooser.getCurrentDirectory().toString();
                pdfViewer.openPDF(filePath);
                fileChooserPath = direcotry;
            } else if(result == JFileChooser.CANCEL_OPTION){
                System.out.println("User choose cancel option");
            }
        }catch(NullPointerException exception) {
        exception.printStackTrace();
        }
        }
    };

        //Click on button will name document
        ActionListener nameActionListener = new ActionListener(){
            public void actionPerformed(ActionEvent e) throws NullPointerException {
                try {
                    String documentName = nameDocument();
                    System.out.println(documentName);
                    pdfViewer.closeDocument();
                    File file = new File(filePath);
                    file.renameTo(new File(direcotry + "\\" + documentName + ".pdf"));
                    classificationBox.removeAllItems();
                    subSectionBox.removeAllItems();
                    expirationDateField.setText("");
                    documentDateField.setText("");
                    submissionDateField.setText("");
                    versionField.setText("");
                    nameButton.setEnabled(false);
                    filePath=null;
                }catch(NullPointerException exception) {
                    exception.printStackTrace();
                }
            }
        };

        add(locationButton);
        add(sectionBox);
        add(subSectionBox);
        add(classificationBox);
        add(documentBox);
        add(checkFieldsButton);
        add(nameButton);
        nameButton.setEnabled(false);

        deactivateFields();

        locationButton.addActionListener(locActionListener);
        sectionBox.addActionListener(sectionActionListener);
        subSectionBox.addActionListener(subSectionActionListener);
        classificationBox.addActionListener(classificationActionListener);
        documentBox.addActionListener(documentActionListener);
        checkFieldsButton.addActionListener(checkButtonActionListener);
        nameButton.addActionListener(nameActionListener);
    }

    public String getRow() {
        return row;
    }

    public RowModel getRowModel() {
        return rowModel;
    }

    private String nameDocument(){

        List<String> fieldList = new ArrayList<>();
        fieldList.add("ExpirationDate");
        fieldList.add("DocumentDate");
        fieldList.add("SubmissionDate");
        fieldList.add("Version");
        fieldList.add("0");

        StringBuilder stringBuilder = new StringBuilder();

        if (fieldList.contains(rowModel.getTitle2())) {
            switch (rowModel.getTitle2()) {
                case "0":
                    break;
                case "ExpirationDate":
                   stringBuilder.append(expirationDateField.getText());
                    break;
                case "DocumentDate":
                    stringBuilder.append(documentDateField.getText());
                    break;
                case "SubmissionDate":
                    stringBuilder.append(submissionDateField.getText());
                    break;
                case "Version":
                    stringBuilder.append(versionField.getText());
                    break;
            }
        } else {
            stringBuilder.append(rowModel.getTitle2());
        }

        if (fieldList.contains(rowModel.getTitle3())) {
            switch (rowModel.getTitle3()) {
                case "0":
                    break;
                case "ExpirationDate":
                    stringBuilder.append(expirationDateField.getText());
                    break;
                case "DocumentDate":
                    stringBuilder.append(documentDateField.getText());
                    break;
                case "SubmissionDate":
                    stringBuilder.append(submissionDateField.getText());
                    break;
                case "Version":
                    stringBuilder.append(versionField.getText());
                    break;
            }
        } else {
            stringBuilder.append(rowModel.getTitle3());
        }

        if (fieldList.contains(rowModel.getTitle4())) {
            switch (rowModel.getTitle4()) {
                case "0":
                    break;
                case "ExpirationDate":
                    stringBuilder.append(expirationDateField.getText());
                    break;
                case "DocumentDate":
                    stringBuilder.append(documentDateField.getText());
                    break;
                case "SubmissionDate":
                    stringBuilder.append(submissionDateField.getText());
                    break;
                case "Version":
                    stringBuilder.append(versionField.getText());
                    break;
            }
        } else {
            stringBuilder.append(rowModel.getTitle4());
        }

        if (fieldList.contains(rowModel.getTitle5())) {
            switch (rowModel.getTitle5()) {
                case "0":
                    break;
                case "ExpirationDate":
                    stringBuilder.append(expirationDateField.getText());
                    break;
                case "DocumentDate":
                    stringBuilder.append(documentDateField.getText());
                    break;
                case "SubmissionDate":
                    stringBuilder.append(submissionDateField.getText());
                    break;
                case "Version":
                    stringBuilder.append(versionField.getText());
                    break;
            }
        } else {
            stringBuilder.append(rowModel.getTitle5());
        }

        if (fieldList.contains(rowModel.getTitle6())) {
            switch (rowModel.getTitle6()) {
                case "0":
                    break;
                case "ExpirationDate":
                    stringBuilder.append(expirationDateField.getText());
                    break;
                case "DocumentDate":
                    stringBuilder.append(documentDateField.getText());
                    break;
                case "SubmissionDate":
                    stringBuilder.append(submissionDateField.getText());
                    break;
                case "Version":
                    stringBuilder.append(versionField.getText());
                    break;
            }
        } else {
            stringBuilder.append(rowModel.getTitle6());
        }

        if (fieldList.contains(rowModel.getTitle7())) {
            switch (rowModel.getTitle7()) {
                case "0":
                    break;
                case "ExpirationDate":
                    stringBuilder.append(expirationDateField.getText());
                    break;
                case "DocumentDate":
                    stringBuilder.append(documentDateField.getText());
                    break;
                case "SubmissionDate":
                    stringBuilder.append(submissionDateField.getText());
                    break;
                case "Version":
                    stringBuilder.append(versionField.getText());
                    break;
            }
        } else {
            stringBuilder.append(rowModel.getTitle7());
        }

        if (fieldList.contains(rowModel.getTitle8())) {
            switch (rowModel.getTitle8()) {
                case "0":
                    break;
                case "ExpirationDate":
                    stringBuilder.append(expirationDateField.getText());
                    break;
                case "DocumentDate":
                    stringBuilder.append(documentDateField.getText());
                    break;
                case "SubmissionDate":
                    stringBuilder.append(submissionDateField.getText());
                    break;
                case "Version":
                    stringBuilder.append(versionField.getText());
                    break;
            }
        } else {
            stringBuilder.append(rowModel.getTitle8());
        }
        return  stringBuilder.toString();
    }

    public void activateFields() {

        List<String> fieldList = new ArrayList<>();
        fieldList.add("ExpirationDate");
        fieldList.add("DocumentDate");
        fieldList.add("SubmissionDate");
        fieldList.add("Version");

        if (fieldList.contains(rowModel.getTitle2())) {
            switch (rowModel.getTitle2()) {
                case "ExpirationDate":
                    add(expirationDateLabel);
                    add(expirationDateField);
                    break;
                case "DocumentDate":
                    add(documentDateLabel);
                    add(documentDateField);
                    break;
                case "SubmissionDate":
                    add(submissionDateLabel);
                    add(submissionDateField);
                    break;
                case "Version":
                    add(versionLabel);
                    add(versionField);
                    break;
            }
        }
        if (fieldList.contains(rowModel.getTitle3())) {
            switch (rowModel.getTitle3()) {
                case "ExpirationDate":
                    add(expirationDateLabel);
                    add(expirationDateField);
                    break;
                case "DocumentDate":
                    add(documentDateLabel);
                    add(documentDateField);
                    break;
                case "SubmissionDate":
                    add(submissionDateLabel);
                    add(submissionDateField);
                    break;
                case "Version":
                    add(versionLabel);
                    add(versionField);
                    break;
            }
        }
        if (fieldList.contains(rowModel.getTitle4())) {
            switch (rowModel.getTitle4()) {
                case "ExpirationDate":
                    add(expirationDateLabel);
                    add(expirationDateField);
                    break;
                case "DocumentDate":
                    add(documentDateLabel);
                    add(documentDateField);
                    break;
                case "SubmissionDate":
                    add(submissionDateLabel);
                    add(submissionDateField);
                    break;
                case "Version":
                    add(versionLabel);
                    add(versionField);
                    break;
            }
        }
        if (fieldList.contains(rowModel.getTitle5())) {
            switch (rowModel.getTitle5()) {
                case "ExpirationDate":
                    add(expirationDateLabel);
                    add(expirationDateField);
                    break;
                case "DocumentDate":
                    add(documentDateLabel);
                    add(documentDateField);
                    break;
                case "SubmissionDate":
                    add(submissionDateLabel);
                    add(submissionDateField);
                    break;
                case "Version":
                    add(versionLabel);
                    add(versionField);
                    break;
            }
        }
        if (fieldList.contains(rowModel.getTitle6())) {
            switch (rowModel.getTitle6()) {
                case "ExpirationDate":
                    add(expirationDateLabel);
                    add(expirationDateField);
                    break;
                case "DocumentDate":
                    add(documentDateLabel);
                    add(documentDateField);
                    break;
                case "SubmissionDate":
                    add(submissionDateLabel);
                    add(submissionDateField);
                    break;
                case "Version":
                    add(versionLabel);
                    add(versionField);
                    break;
            }
        }
        if (fieldList.contains(rowModel.getTitle7())) {
            switch (rowModel.getTitle7()) {
                case "ExpirationDate":
                    add(expirationDateLabel);
                    add(expirationDateField);
                    break;
                case "DocumentDate":
                    add(documentDateLabel);
                    add(documentDateField);
                    break;
                case "SubmissionDate":
                    add(submissionDateLabel);
                    add(submissionDateField);
                    break;
                case "Version":
                    add(versionLabel);
                    add(versionField);
                    break;
            }
        }
        if (fieldList.contains(rowModel.getTitle8())) {
            switch (rowModel.getTitle8()) {
                case "ExpirationDate":
                    add(expirationDateLabel);
                    add(expirationDateField);
                    break;
                case "DocumentDate":
                    add(documentDateLabel);
                    add(documentDateField);
                    break;
                case "SubmissionDate":
                    add(submissionDateLabel);
                    add(submissionDateField);
                    break;
                case "Version":
                    add(versionLabel);
                    add(versionField);
                    break;
            }
        }
    }

    public void deactivateFields() {
        remove(documentDateField);
        remove(documentDateLabel);
        remove(expirationDateField);
        remove(expirationDateLabel);
        remove(submissionDateField);
        remove(submissionDateLabel);
        remove(versionField);
        remove(versionLabel);

    }
}
