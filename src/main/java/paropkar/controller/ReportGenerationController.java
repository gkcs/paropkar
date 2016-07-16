package paropkar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paropkar.services.ReportGenerator;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;


@RestController
public class ReportGenerationController {

    private final ReportGenerator reportGenerator;

    @Autowired
    public ReportGenerationController(@Value("${htmlFormatFile}") String htmlTemplateFile,
                                      @Value("${cssFilePath}") String cssFilePath) {
        this.reportGenerator = new ReportGenerator(htmlTemplateFile, cssFilePath);
    }

    @RequestMapping("/generateReport")
    public CompletableFuture<ResponseEntity<byte[]>> getInvoice(final String complaintId) {
        return reportGenerator.generate(complaintId).handle(
                ((bytes, throwable) -> {
                    if (throwable == null) {
                        final HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.parseMediaType("application/pdf"));
                        headers.setContentDispositionFormData("report.pdf", "report.pdf");
                        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                        return new ResponseEntity<>(bytes, headers, OK);
                    } else {
                        throwable.printStackTrace();
                        return new ResponseEntity<>(throwable.getMessage().getBytes(), INTERNAL_SERVER_ERROR);
                    }
                }));
    }
}
