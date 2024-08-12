package ComparePDF.PDF01;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PDFReader {
	
	@Test
	public void readPDFFile() throws IOException {
		
		File file = new File("C:\\Users\\wbox62\\Downloads\\testing.pdf");
		FileInputStream fis = new FileInputStream(file);
		
		PDDocument document = PDDocument.load(fis);
		
		System.out.println(document.getPages().getCount());
		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		//pdfTextStripper.setStartPage(4);
		//pdfTextStripper.setEndPage(5);
		
		String documentText  = pdfTextStripper.getText(document);
		
		System.out.println(documentText + "and document text is");
		
		Assert.assertTrue(documentText.contains("pdf"));
		
		document.close();
		fis.close();
		
	}

}
