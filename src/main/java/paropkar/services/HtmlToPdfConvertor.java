package paropkar.services;

import com.lowagie.text.DocumentException;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.resource.CSSResource;

import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.Base64;
import java.util.Map;

public class HtmlToPdfConvertor {
    private static final float DEFAULT_DOTS_PER_POINT = 20f * 4f / 3f;
    private static final int DEFAULT_DOTS_PER_PIXEL = 20;

    public byte[] createPDF(final String cssFilePath,
                            final String htmlFilePath,
                            final Map<String, String> parameters,
                            final Map<String, String> images) {
        try {
            return Base64.getEncoder().encode(convertToPdf(getFileStreamInResources(cssFilePath),
                    generateHtmlDoc(htmlFilePath, parameters, images))
                    .toByteArray());
        } catch (DocumentException | TransformerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream convertToPdf(final InputStream cssResource, final org.w3c.dom.Document document)
            throws
            com.lowagie.text.DocumentException, TransformerException, IOException {
        final ITextOutputDevice outputDevice = new ITextOutputDevice(DEFAULT_DOTS_PER_POINT);
        final ITextRenderer textRenderer = new ITextRenderer(DEFAULT_DOTS_PER_POINT,
                DEFAULT_DOTS_PER_PIXEL,
                outputDevice,
                new ITextUserAgent(outputDevice) {
                    @Override
                    public CSSResource getCSSResource(String uri) {
                        return new CSSResource(cssResource);
                    }
                });
        textRenderer.setDocument(document, "");
        textRenderer.layout();
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        textRenderer.createPDF(stream);
        textRenderer.finishPDF();
        return stream;
    }

    private org.w3c.dom.Document generateHtmlDoc(final String htmlFilePath,
                                                 final Map<String, String> parameters,
                                                 final Map<String, String> images) throws IOException {
        final org.jsoup.nodes.Document document = Jsoup.parse(stream2file(getFileStreamInResources(htmlFilePath)),
                "UTF-8");
        for (final String key : parameters.keySet()) {
            document.getElementById(key).empty().append(parameters.get(key));
        }
        for (final String key : images.keySet()) {
            document.getElementById(key).empty().attr("src", images.get(key));
        }
        document.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        return new W3CDom().fromJsoup(document);
    }

    private InputStream getFileStreamInResources(String htmlFilePath) {
        return this.getClass().getClassLoader().getResourceAsStream(htmlFilePath);
    }

    private File stream2file(final InputStream in) throws IOException {
        final File tempFile = File.createTempFile("stream2file", ".tmp");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }
}

