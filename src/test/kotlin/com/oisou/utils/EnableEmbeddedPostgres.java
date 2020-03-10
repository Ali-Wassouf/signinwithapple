package com.oisou.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DbConfig.class})
@EnableTransactionManagement
@SpringBootTest
@EnableJpaRepositories
public @interface EnableEmbeddedPostgres
{
}
