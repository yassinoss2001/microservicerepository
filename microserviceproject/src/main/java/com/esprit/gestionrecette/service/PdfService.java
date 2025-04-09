package com.esprit.gestionrecette.service;

import com.esprit.gestionrecette.entites.Recette;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

@Service
public class PdfService {

    // Styling constants
    private static final float PAGE_MARGIN = 40f;
    private static final int TITLE_FONT_SIZE = 26;
    private static final int SECTION_TITLE_FONT_SIZE = 16;
    private static final int CONTENT_FONT_SIZE = 12;
    private static final int FOOTER_FONT_SIZE = 10;

    private static final String DEFAULT_FONT = StandardFonts.HELVETICA;
    private static final String BOLD_FONT = StandardFonts.HELVETICA_BOLD;
    private static final String FOOTER_TEXT = "© 2025 GestionRecette - Tous droits réservés";

    public byte[] generatePdfForRecette(Recette recette) throws IOException {
        if (recette == null) throw new IllegalArgumentException("Recette cannot be null");

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, PageSize.A4);
            doc.setMargins(PAGE_MARGIN, PAGE_MARGIN, PAGE_MARGIN + 20, PAGE_MARGIN);

            PdfFont fontRegular = PdfFontFactory.createFont(DEFAULT_FONT);
            PdfFont fontBold = PdfFontFactory.createFont(BOLD_FONT);

            // Add image if exists
            addImageHeader(doc, recette.getImage());

            // Titre
            Paragraph title = new Paragraph(safeGet(recette.getTitre()))
                    .setFont(fontBold)
                    .setFontSize(TITLE_FONT_SIZE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE)
                    .setMarginBottom(20);
            doc.add(title);

            // Sections stylées
            addSectionCard(doc, "Description", safeGet(recette.getDescription()), fontBold, fontRegular);
            addSectionCard(doc, "Ingrédients", formatList(safeGet(recette.getIngredients())), fontBold, fontRegular);
            addSectionCard(doc, "Étapes de préparation", formatList(safeGet(recette.getEtape())), fontBold, fontRegular);

            // Footer
            addFooter(doc, fontRegular);

            doc.close();
            return byteArrayOutputStream.toByteArray();
        }
    }

    private void addImageHeader(Document document, String imagePath) {
        try {
            Image image;

            if (imagePath.startsWith("http")) {
                // Si c'est un lien en ligne
                image = new Image(ImageDataFactory.create(imagePath));
            } else {
                // Lire depuis src/main/resources/static/...
                String resourcePath = "static/" + imagePath; // juste le nom du fichier ex: "pizza.jpg"
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
                if (inputStream == null) {
                    System.err.println("Image introuvable dans resources: " + resourcePath);
                    return;
                }
                byte[] imageBytes = inputStream.readAllBytes();
                image = new Image(ImageDataFactory.create(imageBytes));
            }

            image.setWidth(UnitValue.createPercentValue(100));
            image.setAutoScaleHeight(true);
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);
            image.setMarginBottom(15);
            document.add(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void addSectionCard(Document doc, String title, String content, PdfFont fontBold, PdfFont fontRegular) {
        Div card = new Div()
                .setPadding(12)
                .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 0.8f))
                .setBackgroundColor(ColorConstants.LIGHT_GRAY, 0.08f)
                .setMarginBottom(15);

        Paragraph sectionTitle = new Paragraph(title)
                .setFont(fontBold)
                .setFontSize(SECTION_TITLE_FONT_SIZE)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginBottom(5);

        Paragraph sectionContent = new Paragraph(content)
                .setFont(fontRegular)
                .setFontSize(CONTENT_FONT_SIZE)
                .setFontColor(ColorConstants.BLACK)
                .setMultipliedLeading(1.3f);

        card.add(sectionTitle);
        card.add(sectionContent);
        doc.add(card);
    }

    private void addFooter(Document document, PdfFont fontRegular) {
        float pageWidth = document.getPdfDocument().getDefaultPageSize().getWidth();
        Paragraph footer = new Paragraph(FOOTER_TEXT)
                .setFont(fontRegular)
                .setFontSize(FOOTER_FONT_SIZE)
                .setFontColor(ColorConstants.GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setFixedPosition(PAGE_MARGIN, 20, pageWidth - 2 * PAGE_MARGIN)
                .setOpacity(0.6f);
        document.add(footer);
    }

    private String safeGet(String value) {
        return value != null && !value.isBlank() ? value : "Non spécifié";
    }

    private String formatList(String text) {
        if (text == null || text.isEmpty()) return "Aucune information disponible";
        return "• " + text.replace("\n", "\n• ");
    }
}
