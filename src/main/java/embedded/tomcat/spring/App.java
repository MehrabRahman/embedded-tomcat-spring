package embedded.tomcat.spring;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan
@EnableWebMvc
@RestController
public class App implements WebApplicationInitializer {
    public static void main(String[] args) throws LifecycleException {
        Tomcat server = new Tomcat();
        server.setPort(8080);
        server.getConnector();
        server.addWebapp("", new File("./").getAbsolutePath());
        server.start();
        server.getServer().await();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext spring = new AnnotationConfigWebApplicationContext();
        spring.register(App.class);
        servletContext.addListener(new ContextLoaderListener(spring));

        Dynamic dispatcherServlet = servletContext.addServlet("dispatcherServlet",
                new DispatcherServlet(new GenericWebApplicationContext()));
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!\n";
    }
}
