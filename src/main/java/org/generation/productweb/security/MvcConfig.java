package org.generation.productweb.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.*;

import java.nio.file.*;

//https://spring.io/guides/gs/securing-web/

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        //Map the browser's URL to a specific View (HTML) inside resources/templates directory
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/aboutus").setViewName("aboutus");
        registry.addViewController("/products").setViewName("products");
        registry.addViewController("/productform").setViewName("productform");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images")
                .addResourceLocations("classpath:/static/","classpath:/images/")
                .setCachePeriod(0);

        exposeDirectory("productImages/images", registry);
    }

    //In case of uploaded files are images, we can display the images in browser with a little configuration.
    // We need to expose the directory containing the uploaded files so the clients (web browsers) can access.
    // Create a configuration file with the following code:

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {

        //Here, we configure Spring MVC to allow access to the directory user-photos in the file system,
        // with mapping to the application’s context path as /user-photos.


        //Paths.get(url) : Converts a path string, or a sequence of strings that when joined form a path string, to a Path.
        Path uploadDir = Paths.get(dirName);

        //This function returns the absolute pathname of the given file object.
        //https://www.geeksforgeeks.org/file-getabsolutepath-method-in-java-with-examples/
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        System.out.println(uploadPath);

        //if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        System.out.println("/" + dirName + "/**");

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + uploadPath + "/")
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());


        //Assists with the registration of resource resolvers and transformers.
        //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/ResourceChainRegistration.html
        // setting this to "true" is recommended for production (and "false" for development,
        // especially when applying a version strategy

        /*PathResourceResolver
        A simple ResourceResolver that tries to find a resource under the given locations matching to the request path.
        https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/resource/class-use/ResourceResolver.html
         */

    }

}

