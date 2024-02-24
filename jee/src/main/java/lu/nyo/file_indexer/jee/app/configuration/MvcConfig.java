package lu.nyo.file_indexer.jee.app.configuration;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.MAX_VALUE;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.springframework.http.CacheControl.maxAge;

@EnableWebMvc
@Configuration
public  class MvcConfig implements WebMvcConfigurer {

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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        fastConverter.setSupportedMediaTypes(mediaTypeList);

        FastJsonConfig fastJsonConfig = getFastJsonConfig();
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }

    private static FastJsonConfig getFastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setFeatures(Feature.AllowArbitraryCommas, Feature.AllowUnQuotedFieldNames);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);
        fastJsonConfig.setDateFormat("dd-MM-yyyy HH:mm:ss");
        return fastJsonConfig;
    }
}

