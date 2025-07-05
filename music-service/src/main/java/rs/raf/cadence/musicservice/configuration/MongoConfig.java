package rs.raf.cadence.musicservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
@Slf4j
public class MongoConfig {

    @Bean
    CommandLineRunner initIndexes(MongoTemplate mongoTemplate) {
        return args -> {
            mongoTemplate.indexOps("albums")
                    .ensureIndex(new Index().on("strAlbumThumb", org.springframework.data.domain.Sort.Direction.ASC));
            log.info("Index created on strAlbumThumb field");
        };
    }
}
