package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
//import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfGeneration {

    private GrayColor baseColor = new GrayColor(0.95f);
    public void pdfGeneration(String fileName, ObservableList<Person> data){
        Document document = new Document();

        try {
            //CÉGES LOGÓ
            PdfWriter.getInstance(document,new FileOutputStream(fileName + ".pdf"));
            document.open();
            Image image1 = Image.getInstance(getClass().getResource("/logo.jpg"));
            image1.scaleToFit(400,172);
            image1.setAbsolutePosition(170f,650f);
            document.add(image1);

            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n"));
            //TÁBLÁZAT
            float[] columnWidths = {2,4,4,6};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase(("Kontaktlista")));
            cell.setBackgroundColor(baseColor);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);

            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sorszám");
            table.addCell("Vezetéknév");
            table.addCell("Keresztnév");
            table.addCell("Email");
            table.setHeaderRows(1);

            table.getDefaultCell().setBackgroundColor(baseColor);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            for(int i = 1; i <= data.size(); i++){
                Person actualPerson = data.get(i - 1 );
                table.addCell(String.valueOf(i));
                table.addCell(actualPerson.getLastname());
                table.addCell(actualPerson.getFirstname());
                table.addCell(actualPerson.getEmail());
            }
            document.add(table);
            //ALÁÍRÁS
            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" , FontFactory.getFont("betutipus",BaseFont.IDENTITY_H,BaseFont.EMBEDDED)));
            Chunk signature = new Chunk("\n\n Generálva a Telefonkönyv alkalmazás segítségével");
            Paragraph base = new Paragraph(signature);
            document.add(base);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
