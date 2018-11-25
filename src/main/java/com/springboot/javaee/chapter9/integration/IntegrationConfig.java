package com.springboot.javaee.chapter9.integration;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import sun.misc.Resource;

import java.io.IOException;

/**
 * @author: leiyulin
 * @description:
 * @date:2018/11/2511:04 AM
 */
@Configuration
public class IntegrationConfig {
    @Value("https://spring.io/blog.atom")
    private Resource resource;

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(500).get();
    }

    @Bean
    public FeedEntryMessageSource feedEntryMessageSource() {
        FeedEntryMessageSource messageSource = new FeedEntryMessageSource(resource.getURL(), "news");
        return messageSource;
    }

    @Bean
    public IntegrationFlow myFlow() throws IOException {
        return IntegrationFlows.from(feedEntryMessageSource())
                .<SyndEntry, String>route(playload -> playload.getCategories().get(0).getName(),
                        mapping -> mapping.channelMapping("releases", "releasesChannel")
                                .channelMapping("engineering", "engineeringChannel")
                                .channelMapping("news", "newsChannel")).get();
    }


}

