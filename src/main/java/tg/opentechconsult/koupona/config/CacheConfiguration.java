package tg.opentechconsult.koupona.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(tg.opentechconsult.koupona.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Pays.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Pays.class.getName() + ".villes", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Ville.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Ville.class.getName() + ".quartiers", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Quartier.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Topcategorie.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Topcategorie.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Categorie.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Categorie.class.getName() + ".souscategories", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Souscategorie.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Menu.class.getName(), jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Menu.class.getName() + ".sousmenus", jcacheConfiguration);
            cm.createCache(tg.opentechconsult.koupona.domain.Sousmenu.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
