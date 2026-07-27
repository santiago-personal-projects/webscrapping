package com.santiago.learning.projects.webscrapping.service;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.santiago.learning.projects.webscrapping.core.dto.request.ProductAndPriceRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebScrappingService {
    
    private final KafkaSenderService kafkaSender;

    @Value("${webscrapping.webPage}")
    private String webPage; 

    public void getPhoneScrapper()  {

        WebDriver driver = getDriver();

        try {
            // Navigate to web page
            driver.manage().window().setSize(new Dimension(1920, 1080));
            driver.get(webPage);
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));

            By cellphonesLinkLocator = By.xpath("//ul[contains(@class, 'js-main-navigation-categories')]//a[@title='Celulares']");
            WebElement cellphonesButton = longWait.until(ExpectedConditions.elementToBeClickable(cellphonesLinkLocator));

            // Click the button
            cellphonesButton.click();

            try {
                // Create selectors
                By loadMoreButtonLocator = By.cssSelector("button.js-load-more");
                By productContainersLocator = By.cssSelector("ol.ais-InfiniteHits-list.product__list li.js-product-item");

                // Create a dedicated wait for checking button presence quickly (2-3 seconds per click loop)
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

                log.info("Starting to load all products...");
                boolean elementsStillLoading = true;

                // Loop to repeatedly click the button until it's gone
                while (elementsStillLoading) {
                    try {
                        // Wait until the button is visible and ready to be clicked
                        WebElement loadMoreButton = shortWait.until(ExpectedConditions.elementToBeClickable(loadMoreButtonLocator));

                        loadMoreButton.click();
                        log.info("Clicked 'Mostrar más productos' button...");

                        Thread.sleep(1500);

                    } catch (TimeoutException | NoSuchElementException e) {
                        log.info("All items loaded successfully.");
                        elementsStillLoading = false;
                    }
                }

                // Now that everything is loaded, find ALL the compiled product titles
                longWait.until(ExpectedConditions.visibilityOfElementLocated(productContainersLocator));

                List<WebElement> productContainers = driver.findElements(productContainersLocator);
                log.info("Total products found on the full list: " + productContainers.size());

                // Iterate and print out the complete dataset
                for (WebElement container : productContainers) {
                    
                    // Search specific title in that container
                    WebElement titleElement = container.findElement(By.cssSelector("h3.js-algolia-product-title"));
                    String phoneName = titleElement.getText();
                    String dataId = titleElement.getAttribute("data-id");

                    WebElement priceElement = container.findElement(By.cssSelector(".price.price--redesign"));
                    String price = priceElement.getText();

                    WebElement linkElement = container.findElement(By.cssSelector("a.product__item__information__section"));
                    String fullUrl = linkElement.getAttribute("href");

                    String cleanUrl = fullUrl.split("\\?")[0];

                
                    log.info("xxxxxxxxxxxxxxxxxxxxxx");
                    log.info("phoneName : {}" , phoneName);
                    log.info("dataId    : {}" , dataId);
                    log.info("price     : {}" , price);
                    log.info("web page  : {}" , cleanUrl);

                    kafkaSender.createProduct(new ProductAndPriceRequestDTO(dataId, phoneName, cleanUrl,  price));
                    
                }

            } catch (Exception e) {
                log.error("An error occurred during list expansion: " + e.getMessage());
            }

            Thread.sleep(600000);

        } catch (Exception e) {
            log.error("An error occurred while loading the page: " + e.getMessage());
        } finally {
            // Always close the browser process when finished
            driver.quit();
        }

    }

    private WebDriver getDriver() {
        FirefoxOptions options = new FirefoxOptions();

        options.setBinary("/snap/firefox/current/usr/lib/firefox/firefox");
        // options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64; rv:130.0) Gecko/20100101 Firefox/130.0");

        WebDriver driver = new FirefoxDriver(options);
        return driver;
    }

}
