package com.e2e;

import com.jgazula.AppResourceBundle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class EnhanceResourceBundleTests {

    private static final String APP_RESOURCE_BUNDLE = "AppBundle";

    @Test
    void simpleMessageWithNoFormatting() {
        // given
        var resourceBundle = ResourceBundle.getBundle(APP_RESOURCE_BUNDLE, Locale.US);

        // when
        var appResourceBundle = new AppResourceBundle(resourceBundle);

        // then
        assertThat(appResourceBundle.buttonContinue()).isEqualTo("Continue");
    }

    @Test
    void integerMessageFormatting() {
        // given
        var resourceBundle = ResourceBundle.getBundle(APP_RESOURCE_BUNDLE, Locale.FRANCE);

        // when
        var appResourceBundle = new AppResourceBundle(resourceBundle);

        // then
        assertThat(appResourceBundle.planetQuantity(8)).isEqualTo("There are 8 planets.");
    }

    @Test
    void currencyMessageFormatting() {
        // given
        var resourceBundle = ResourceBundle.getBundle(APP_RESOURCE_BUNDLE, Locale.JAPAN);

        // when
        var appResourceBundle = new AppResourceBundle(resourceBundle);
        var str = appResourceBundle.monthlyPayment(new BigDecimal("56.75"), "electric");

        // then
        assertThat(str).isEqualTo("The monthly electric bill is typically over ￥57.");
    }

    @Test
    void percentClassFinalGrade() {
        // given
        var resourceBundle = ResourceBundle.getBundle(APP_RESOURCE_BUNDLE, Locale.CANADA);

        // when
        var appResourceBundle = new AppResourceBundle(resourceBundle);
        var str = appResourceBundle.classFinalGrade("English", new BigDecimal(".95"));

        // then
        assertThat(str).isEqualTo("His final grade for English class was 95%.");
    }

    @Test
    void dateOfBirth() {
        // given
        var resourceBundle = ResourceBundle.getBundle(APP_RESOURCE_BUNDLE, Locale.US);
        var localDate = LocalDate.of(1990, 1, 1);
        var date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // when
        var appResourceBundle = new AppResourceBundle(resourceBundle);
        var str = appResourceBundle.dateOfBirth(date);

        // then
        assertThat(str).isEqualTo("The applicant's DOB is Jan 1, 1990.");
    }

    @Test
    void timeStoreOpening() {
        // given
        var resourceBundle = ResourceBundle.getBundle(APP_RESOURCE_BUNDLE, Locale.FRANCE);
        var localDate = LocalTime.of(9, 0);
        var instant = localDate.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        var date = Date.from(instant);

        // when
        var appResourceBundle = new AppResourceBundle(resourceBundle);
        var str = appResourceBundle.storeOpeningTime("grocery", date);

        // then
        assertThat(str).isEqualTo("The grocery store opens every day at 09:00:00.");
    }

    @Test
    void choiceFilesQuantity() {
        // given
        var resourceBundle = ResourceBundle.getBundle(APP_RESOURCE_BUNDLE, Locale.US);

        // when
        var appResourceBundle = new AppResourceBundle(resourceBundle);

        // then
        assertThat(appResourceBundle.filesQuantity(0)).isEqualTo("There are no files.");
        assertThat(appResourceBundle.filesQuantity(1)).isEqualTo("There is one file.");
        assertThat(appResourceBundle.filesQuantity(2)).isEqualTo("There are 2 files.");
    }
}
