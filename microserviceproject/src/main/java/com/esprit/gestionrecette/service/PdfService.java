package com.esprit.gestionrecette.service;

import com.esprit.gestionrecette.entites.Recette;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] generatePdfForRecette(Recette recette) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);

        Document document = new Document(pdfDocument, PageSize.A4);
        document.setMargins(40, 40, 40, 40);

        PdfFont fontRegular = PdfFontFactory.createFont();
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Color primaryColor = ColorConstants.BLUE;
        Color secondaryColor = ColorConstants.DARK_GRAY;
        Color accentColor = ColorConstants.RED;

        // En-tête
        Div header = new Div()
                .setBackgroundColor(primaryColor, 0.1f)
                .setPadding(15)
                .setTextAlignment(TextAlignment.CENTER);

        Text title = new Text(recette.getTitre())
                .setFont(fontBold)
                .setFontSize(24)
                .setFontColor(primaryColor);

        header.add(new Paragraph(title));
        document.add(header);

        // Section Description
        addSectionTitle(document, "Description", fontBold, secondaryColor);
        addSectionContent(document, recette.getDescription(), fontRegular);

        // Section Ingrédients
        addSectionTitle(document, "Ingrédients", fontBold, secondaryColor);
        addSectionContent(document, formatList(recette.getIngredients()), fontRegular);

        // Section Étapes de préparation
        addSectionTitle(document, "Étapes de préparation", fontBold, secondaryColor);
        addSectionContent(document, formatList(recette.getEtape()), fontRegular);

        // Pied de page
        Paragraph footer = new Paragraph("© 2024 GestionRecette - Tous droits réservés")
                .setFont(fontRegular)
                .setFontSize(10)
                .setFontColor(secondaryColor)
                .setTextAlignment(TextAlignment.CENTER)
                .setFixedPosition(document.getLeftMargin(), 20,
                        document.getPdfDocument().getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin());

        document.add(footer);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private void addSectionTitle(Document document, String title, PdfFont font, Color color) {
        Paragraph paragraph = new Paragraph(title)
                .setFont(font)
                .setFontSize(16)
                .setFontColor(color)
                .setMarginTop(20)
                .setMarginBottom(10);
        document.add(paragraph);

        document.add(new Paragraph("")
                .setHeight(1)
                .setBackgroundColor(color)
                .setMarginBottom(15));
    }

    private void addSectionContent(Document document, String content, PdfFont font) {
        Paragraph paragraph = new Paragraph(content)
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5);
        document.add(paragraph);
    }

    private String formatList(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return "• " + text.replace("\n", "\n• ");
    }
}