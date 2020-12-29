package com.cinemaster.backend.pdf.impl;

import com.cinemaster.backend.controller.booking.Ticket;
import com.cinemaster.backend.pdf.PdfService;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public String createPdf(Ticket ticket) {
        Document document = new Document(PageSize.A5.rotate());
        try {
            EAN13Bean barcodeGenerator = new EAN13Bean();
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            barcodeGenerator.generateBarcode(canvas, ticket.generateBarcodeEAN13());
            BufferedImage temp = canvas.getBufferedImage();
            BufferedImage barcodeImage = new BufferedImage(200, 60, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = barcodeImage.createGraphics();
            g2d.drawImage(temp, 5, -1, 200, 60, null);
            g2d.dispose();

            PdfWriter.getInstance(document, new FileOutputStream("project" + File.separator + "tickets" + File.separator + ticket.getBookingId() + ".pdf"));
            document.open();

            PdfPTable table = new PdfPTable(2);

            // User name
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, BaseColor.BLACK);
            Paragraph userNameParagraph = new Paragraph(ticket.getUserName(), font);
            PdfPCell userNameCell = new PdfPCell(userNameParagraph);
            userNameCell.setPadding(10);
            userNameCell.setBorderWidthBottom(0);
            table.addCell(userNameCell);

            // Show name
            font = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, BaseColor.BLACK);
            Paragraph showNameParagraph = new Paragraph(ticket.getShowName(), font);
            PdfPCell showNameCell = new PdfPCell(showNameParagraph);
            showNameCell.setPadding(10);
            showNameCell.setBorderWidthBottom(0);
            table.addCell(showNameCell);

            // Booking id
            font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
            Paragraph bookingParagraph = new Paragraph("ID prenotazione: " + ticket.getBookingId(), font);
            PdfPCell bookingCell = new PdfPCell(bookingParagraph);
            bookingCell.setPaddingLeft(10);
            bookingCell.setBorderWidthTop(0);
            bookingCell.setBorderWidthBottom(0);
            table.addCell(bookingCell);

            // Room name
            font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph roomNameParagraph = new Paragraph(ticket.getRoomName(), font);
            PdfPCell roomNameCell = new PdfPCell(roomNameParagraph);
            roomNameCell.setPaddingLeft(10);
            roomNameCell.setBorderWidthTop(0);
            roomNameCell.setBorderWidthBottom(0);
            table.addCell(roomNameCell);

            // Blank cell
            font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            PdfPCell blankCell = new PdfPCell();
            blankCell.setBorderWidthTop(0);
            blankCell.setBorderWidthBottom(0);
            table.addCell(blankCell);

            // Seat name + Price
            font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph seatParagraph = new Paragraph(ticket.getSeat() + " €" + String.format("%.2f", ticket.getPrice()), font);
            PdfPCell seatCell = new PdfPCell(seatParagraph);
            seatCell.setPaddingLeft(10);
            seatCell.setBorderWidthTop(0);
            seatCell.setBorderWidthBottom(0);
            table.addCell(seatCell);

            // Blank cell
            font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            blankCell = new PdfPCell();
            blankCell.setBorderWidthTop(0);
            blankCell.setBorderWidthBottom(0);
            table.addCell(blankCell);

            // Blank cell
            font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            blankCell = new PdfPCell();
            blankCell.setBorderWidthTop(0);
            blankCell.setBorderWidthBottom(0);
            table.addCell(blankCell);

            // Barcode
            font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(barcodeImage, "png", stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.setScaleToFitHeight(true);
            PdfPCell barcodeCell = new PdfPCell(image);
            barcodeCell.setBorderWidthTop(0);
            barcodeCell.setBorderWidthBottom(0);
            table.addCell(barcodeCell);

            // Date + Time
            font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph dateParagraph = new Paragraph(ticket.getDate().toString() + " - " + ticket.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")), font);
            PdfPCell dateCell = new PdfPCell(dateParagraph);
            dateCell.setPadding(10);
            dateCell.setBorderWidthTop(0);
            dateCell.setBorderWidthBottom(0);
            table.addCell(dateCell);

            // Blank cell
            font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            blankCell = new PdfPCell();
            blankCell.setBorderWidthTop(0);
            table.addCell(blankCell);

            // Cinemaster
            font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
            Paragraph cinemasterParagraph = new Paragraph("Cinemaster", font);
            PdfPCell cinemasterCell = new PdfPCell(cinemasterParagraph);
            cinemasterCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cinemasterCell.setBorderWidthTop(0);
            table.addCell(cinemasterCell);

            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "project" + File.separator + "tickets" + File.separator + ticket.getBookingId() + ".pdf";
    }
}
