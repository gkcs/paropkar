package paropkar.services;

import paropkar.dao.ComplaintDAO;
import paropkar.dao.DataAccessor;
import paropkar.model.Complaint;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ReportGenerator {
    private final HtmlToPdfConvertor htmlToPdfConvertor;
    private final String htmlTemplateFile;
    private final String cssFilePath;
    private final ComplaintDAO complaintDAO;

    public ReportGenerator(final String htmlTemplateFile,
                           final String cssFilePath) {
        this.htmlTemplateFile = htmlTemplateFile;
        this.cssFilePath = cssFilePath;
        this.complaintDAO = new ComplaintDAO(DataAccessor.getDataAccessor());
        htmlToPdfConvertor = new HtmlToPdfConvertor();
    }

    public CompletableFuture<byte[]> generate(final String complaintId) {
        return complaintDAO.getObject(complaintId).thenApply(complaint ->
                htmlToPdfConvertor.createPDF(cssFilePath,
                        htmlTemplateFile,
                        getParameters(complaint),
                        getImages(complaint)));
    }

    private Map<String, String> getImages(final Complaint complaint) {
        return new HashMap<>();
    }

    private Map<String, String> getParameters(final Complaint complaint) {
        return new HashMap<>();
    }
}
