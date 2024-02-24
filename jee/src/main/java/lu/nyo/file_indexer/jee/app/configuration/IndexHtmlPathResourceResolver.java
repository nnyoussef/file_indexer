package lu.nyo.file_indexer.jee.app.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;

public final class IndexHtmlPathResourceResolver extends PathResourceResolver {

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        if (requestPath.startsWith("/")) {
            requestPath = "index.html";
        } else if (resolveUrlPath(requestPath, locations, chain) == null) {
            requestPath = requestPath.concat("/index.html");
        }
        return super.resolveResource(request, requestPath, locations, chain);
    }
}
