package embedded.tomcat.spring;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan
@RestController
public class App implements WebMvcConfigurer {
    public static void main(String[] args) throws LifecycleException {
        String docBase = (System.getProperty("java.io.tmpdir"));
        Tomcat server = new Tomcat();
        server.setBaseDir(docBase);
        server.setPort(8080);
        server.getConnector();
        server.addContext("", docBase);

        AnnotationConfigWebApplicationContext spring = new AnnotationConfigWebApplicationContext();
        spring.register(App.class);
        server.addServlet("", "dispatcher", new DispatcherServlet(spring)).addMapping("/");
        
        server.start();
        server.getServer().await();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!\n";
    }
}
