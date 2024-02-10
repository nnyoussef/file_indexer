package lu.nyo.file_indexer.jee.comon;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.of;

public final class ProblemResponseEntityBuilder {
    private final ProblemDetail problemDetail;
    private static final String APPLICATION_PROBLEM = "application/problem+json";
    private final List<String> details = new LinkedList<>();
    private final List<Throwable> throwableList = new LinkedList<>();

    public  ProblemResponseEntityBuilder() {
        this.problemDetail = ProblemDetail.forStatus(BAD_REQUEST);
    }

    public static ProblemResponseEntityBuilder create() {
        return new ProblemResponseEntityBuilder();
    }

    public ProblemResponseEntityBuilder setHttpRequest(HttpServletRequest httpServletRequest) {
        problemDetail.setType(URI.create(httpServletRequest.getRequestURL().toString()));
        problemDetail.setInstance(URI.create(httpServletRequest.getServerName()));
        problemDetail.setProperty("request-method", httpServletRequest.getMethod());
        problemDetail.setProperty("query-params", httpServletRequest.getQueryString());
        problemDetail.setProperty("request-param-list", httpServletRequest.getParameterMap());
        try {
            problemDetail.setProperty("part-list", httpServletRequest.getParts()
                    .stream()
                    .map(p -> Map.of(p.getName(), p.getContentType()))
                    .toList());
        } catch (IOException | ServletException e) {
            problemDetail.setProperty("part-list", Collections.emptyList());
        }

        return this;
    }

    public ProblemResponseEntityBuilder addThrowable(Throwable throwable) {
        throwableList.add(throwable);
        problemDetail.setProperty("exceptions", throwable);
        return this;
    }

    public ProblemResponseEntityBuilder setTitle(String title) {
        problemDetail.setTitle(title);
        return this;
    }

    public ProblemResponseEntityBuilder setProperties(String key, Object value) {
        problemDetail.setProperty(key, value);
        return this;
    }

    public ProblemResponseEntityBuilder addDetail(String detail) {
        details.add(detail);
        problemDetail.setProperty("detail", details);
        return this;
    }

    public ProblemResponseEntityBuilder addDetails(List<String> details) {
        this.details.addAll(details);
        problemDetail.setProperty("detail", details);
        return this;
    }

    public ResponseEntity<ProblemDetail> build() {
        return of(problemDetail)
                .header(CONTENT_TYPE, APPLICATION_PROBLEM)
                .build();
    }

}
