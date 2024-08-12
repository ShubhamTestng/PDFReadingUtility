package ComparePDF.PDF01;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;

import java.io.File;
import java.io.IOException;

public class PDFTest {

    public static void main(String[] args) {
        try {
            File originalPdf = new File("C:\\Users\\wbox62\\Downloads\\PyleTestExecutionReports2Aug2024production.pdf");
            File differencesPdf = new File("C:\\Users\\wbox62\\Downloads\\PyleTestexecutionreports2aug2024.pdf");

            PDDocument document = PDDocument.load(originalPdf);
            PDPage page = document.getPage(0); // Assume one page for simplicity

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 700);
                contentStream.showText("Differences found:");
                contentStream.endText();
            }

            document.save(differencesPdf);
            document.close();

            System.out.println("Differences highlighted in " + differencesPdf.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
