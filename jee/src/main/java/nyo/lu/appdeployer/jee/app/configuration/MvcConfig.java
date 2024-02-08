package nyo.lu.appdeployer.jee.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

import static java.lang.Long.MAX_VALUE;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.springframework.http.CacheControl.maxAge;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${app.web.basepath}")
    private String appWebBasePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/file_repo/**", "/file_repo")
                .addResourceLocations(format("file:/%s//", appWebBasePath))
                .setCacheControl(maxAge(30L, MINUTES))
                .resourceChain(true)
                .addResolver(new IndexHtmlPathResourceResolver());

        registry.addResourceHandler("/console", "/console/**")
                .addResourceLocations(new ClassPathResource("static/"))
                .setCacheControl(maxAge(MAX_VALUE, MINUTES))
                .resourceChain(true)
                .addResolver(new IndexHtmlPathResourceResolver());
    }


}
