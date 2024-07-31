package ru.kalin.k8sdemoproject.properties;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Value
@ConfigurationProperties(prefix = "cursor.posts")
public class PostsProperties {
    int defaultLimit;
    int defaultOffset;
}
