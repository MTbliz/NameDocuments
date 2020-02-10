import com.mt.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.init();
    }
        public void init() {
            try {
                javax.swing.SwingUtilities.invokeAndWait (
                        new Runnable() {
                            public void run() {
                                dropDownListSection();
                            }
                        });
            } catch (Exception e) {
                System.err.println("Błąd tworzenia GUI");
            }
        }

    private void dropDownListSection()
    {
        ImportCSV importCSV = new ImportCSV();
       List<RowModel> rowModels = importCSV.readCSVToRowModel("TMF.csv");

        //Create main frame
        JFrame ramka = new JFrame();
        //frame will be screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ramka.setBounds(0,0,screenSize.width, screenSize.height);
        ramka.setTitle("Document Name");
        ramka.setVisible(true);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PDFViewer pdfViewer = new PDFViewer();
        pdfViewer.viewPDF(ramka);

        TmfLvlPanel tmfLvlPanel = new TmfLvlPanel(rowModels, pdfViewer);
        ramka.add(tmfLvlPanel, BorderLayout.WEST);
    }
    }

