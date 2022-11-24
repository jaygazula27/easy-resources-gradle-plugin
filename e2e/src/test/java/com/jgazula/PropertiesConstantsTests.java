package com.jgazula;

import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PropertiesConstantsTests {

    @Test
    void databaseProperties() throws IOException {
        // given
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/database.properties"));

        // then
        assertThat(properties.getProperty(DatabaseProperties.SPRING_DATASOURCE_URL)).isEqualTo("jdbc:mysql://localhost/test");
        assertThat(properties.getProperty(DatabaseProperties.SPRING_DATASOURCE_USERNAME)).isEqualTo("dbuser");
        assertThat(properties.getProperty(DatabaseProperties.SPRING_DATASOURCE_PASSWORD)).isEqualTo("dbpass");
        assertThat(properties.getProperty(DatabaseProperties.SPRING_DATASOURCE_DRIVER_CLASS_NAME)).isEqualTo("com.mysql.jdbc.Driver");
        assertThat(DatabaseProperties.class.getDeclaredFields().length).isEqualTo(4);
    }

    @Test
    void mainProperties() throws IOException {
        // given
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/main.properties"));

        // then
        assertThat(properties.getProperty(MainProperties.APP_VERSION)).isEqualTo("0.0.1");
        assertThat(properties.getProperty(MainProperties.APP_AUTHOR_FIRST_NAME)).isEqualTo("Jay");
        assertThat(properties.getProperty(MainProperties.APP_AUTHOR_LAST_NAME)).isEqualTo("Gazula");
        assertThat(MainProperties.class.getDeclaredFields().length).isEqualTo(3);
    }
}