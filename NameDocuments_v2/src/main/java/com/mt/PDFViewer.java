package com.mt;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import javax.swing.*;

public class PDFViewer {

        // build a controller
        SwingController controller = new SwingController();

        // Build a SwingViewFactory configured with the controller
        SwingViewBuilder factory = new SwingViewBuilder(controller);

        // Use the factory to build a JPanel that is pre-configured
        public void viewPDF(JFrame window) {
//with a complete, active Viewer UI.
        JPanel viewerComponentPanel = factory.buildViewerPanel();

// add copy keyboard command
        ComponentKeyBinding.install(controller, viewerComponentPanel);

// add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));

        // Create a JFrame to display the panel in
        window.add(viewerComponentPanel);
        window.pack();
        window.setVisible(true);
    }
        public void openPDF(String filePath){
            // Open a PDF document to view
            controller.openDocument(filePath);
        }

        public void closeDocument(){
            controller.closeDocument();
        }
}
