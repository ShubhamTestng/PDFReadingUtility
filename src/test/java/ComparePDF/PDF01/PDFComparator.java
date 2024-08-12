package ComparePDF.PDF01;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

public class PDFComparator {

    public static void main(String[] args) {
        String filePath1 = "C:\\Users\\wbox62\\eclipse-workspace\\PDF01\\target\\1pagetest.pdf";
        String filePath2 = "C:\\Users\\wbox62\\eclipse-workspace\\PDF01\\target\\testing.pdf";

        try {
            comparePDFs(filePath1, filePath2);
            System.out.println("PDF comparison completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void comparePDFs(String filePath1, String filePath2) throws IOException {
        File pdf1 = new File(filePath1);
        File pdf2 = new File(filePath2);

        try (PDDocument doc1 = PDDocument.load(pdf1); PDDocument doc2 = PDDocument.load(pdf2)) {

            // Compare page count
            int numPages1 = doc1.getNumberOfPages();
            int numPages2 = doc2.getNumberOfPages();
            if (numPages1 != numPages2) {
                System.out.println("Page count mismatch: expected " + numPages1 + " but found " + numPages2);
            } else {
                System.out.println("Page count matches: " + numPages1);
            }

            // Compare text content
            PDFTextStripper pdfStripper = new PDFTextStripper();
            for (int i = 0; i < Math.min(numPages1, numPages2); i++) {
                pdfStripper.setStartPage(i + 1);
                pdfStripper.setEndPage(i + 1);
                String text1 = normalizeText(pdfStripper.getText(doc1).trim());
                String text2 = normalizeText(pdfStripper.getText(doc2).trim());

                // Comment out the following lines to avoid printing full text
                // System.out.println("Page " + (i + 1) + " - File 1 text: " + text1);
                // System.out.println("Page " + (i + 1) + " - File 2 text: " + text2);

                if (!text1.equals(text2)) {
                    System.out.println("Text content mismatch on page " + (i + 1));
                    printTextDifferences(text1, text2);
                } else {
                    // Optional: Comment out this line if you don't want to print matches
                    System.out.println("Text content matches on page " + (i + 1));
                }

                // Compare images, charts, tables, and other elements
                PDPage page1 = doc1.getPage(i);
                PDPage page2 = doc2.getPage(i);
                compareImagesGraphicsChartsTables(page1, page2, i + 1);
                compareOtherElements(page1, page2, i + 1);
            }
        }
    }

    private static void compareImagesGraphicsChartsTables(PDPage page1, PDPage page2, int pageIndex) throws IOException {
        // Extracting images and graphics and comparing them
        compareImages(page1, page2, pageIndex);
        // Placeholder methods for charts and tables
        compareCharts(page1, page2, pageIndex);
        compareTables(page1, page2, pageIndex);
    }

    private static void compareImages(PDPage page1, PDPage page2, int pageIndex) throws IOException {
        // Logic to compare images
        List<BufferedImage> images1 = extractImagesFromPage(page1);
        List<BufferedImage> images2 = extractImagesFromPage(page2);

        if (images1.size() != images2.size()) {
            System.out.println("Image count mismatch on page " + pageIndex);
        } else {
            System.out.println("Image count matches on page " + pageIndex);
        }
        for (int i = 0; i < images1.size(); i++) {
            BufferedImage img1 = images1.get(i);
            BufferedImage img2 = images2.get(i);

            if (!compareBufferedImages(img1, img2)) {
                System.out.println("Image mismatch on page " + pageIndex + " at index " + i);
            } else {
                System.out.println("Image matches on page " + pageIndex + " at index " + i);
            }
        }
    }

    private static List<BufferedImage> extractImagesFromPage(PDPage page) {
        // Custom logic to extract images from a PDF page
        // This might involve parsing the content streams and extracting image data
        return null; // Placeholder
    }

    private static boolean compareBufferedImages(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static void compareCharts(PDPage page1, PDPage page2, int pageIndex) {
        // Logic to compare charts
        // Placeholder for actual implementation
        System.out.println("Chart comparison logic not implemented for page " + pageIndex);
    }

    private static void compareTables(PDPage page1, PDPage page2, int pageIndex) {
        // Logic to compare tables
        // Placeholder for actual implementation
        System.out.println("Table comparison logic not implemented for page " + pageIndex);
    }

    private static void compareOtherElements(PDPage page1, PDPage page2, int pageIndex) {
        // Example for comparing dimensions
        if (!page1.getMediaBox().equals(page2.getMediaBox())) {
            System.out.println("Page size mismatch on page " + pageIndex);
        } else {
            System.out.println("Page size matches on page " + pageIndex);
        }

        // Example for comparing color (header/footer text color as an example)
        Color headerColor1 = getHeaderColor(page1);
        Color headerColor2 = getHeaderColor(page2);
        if (!headerColor1.equals(headerColor2)) {
            System.out.println("Header color mismatch on page " + pageIndex);
        } else {
            System.out.println("Header color matches on page " + pageIndex);
        }
    }

    // This method would need to be implemented to extract the header color from a page
    private static Color getHeaderColor(PDPage page) {
        // Custom logic to extract header color
        return Color.BLACK; // Placeholder
    }

    private static String normalizeText(String text) {
        // Remove extra whitespace and normalize newlines
        return text.replaceAll("\\s+", " ").trim();
    }

    private static void printTextDifferences(String text1, String text2) {
        List<String> lines1 = Arrays.asList(text1.split("\\r?\\n"));
        List<String> lines2 = Arrays.asList(text2.split("\\r?\\n"));

        int maxLines = Math.max(lines1.size(), lines2.size());
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < lines1.size() ? lines1.get(i) : "";
            String line2 = i < lines2.size() ? lines2.get(i) : "";

            if (!line1.equals(line2)) {
                System.out.println("Difference on line " + (i + 1) + ":");
                System.out.println("File 1: " + line1);
                System.out.println("File 2: " + line2);
            }
        }
    }
}
