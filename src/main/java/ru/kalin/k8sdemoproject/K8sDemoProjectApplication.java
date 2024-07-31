package ru.kalin.k8sdemoproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.kalin.k8sdemoproject.properties.PostsProperties;
import ru.kalin.k8sdemoproject.properties.UsersProperties;

@EnableConfigurationProperties({
    UsersProperties.class,
    PostsProperties.class
})
@SpringBootApplication
public class K8sDemoProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(K8sDemoProjectApplication.class, args);
    }
}
