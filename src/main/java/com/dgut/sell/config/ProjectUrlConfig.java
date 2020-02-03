package com.dgut.sell.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author YS
 * @data 2019/12/16 20:41
 */
@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {
    private String wxMpAuthorize;
    private String wxOpenAuthorize;
    private String sellUrl;

}
