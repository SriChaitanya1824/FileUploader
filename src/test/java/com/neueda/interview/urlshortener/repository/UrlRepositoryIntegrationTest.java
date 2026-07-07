package com.neueda.interview.urlshortener.repository;

import com.neueda.interview.urlshortener.model.UrlEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UrlRepositoryIntegrationTest {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void shouldInsertAndGetFullurl() {
        UrlEntity urlEntity = new UrlEntity("http://example.com");
        urlRepository.save(urlEntity);

        assertNotNull(urlEntity.getId());

        UrlEntity urlEntityFromDb = urlRepository.findById(urlEntity.getId()).get();
        assertEquals(urlEntity.getId(), urlEntityFromDb.getId());
    }

}
