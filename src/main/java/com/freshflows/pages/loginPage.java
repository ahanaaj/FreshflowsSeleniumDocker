package com.freshflows.pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class loginPage  {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By contWithG = By.xpath("//span[contains(text(), 'Continue with Google')]");
    private final By usermail = By.xpath("//input[@name='email']");
    private final By password = By.xpath("//input[@name='password']");
    private final By submit = By.xpath("//button[@type='submit']");
    private final By chooseOrgTitle = By.xpath("//div[contains(text(), 'Choose an Organization')]");
    private final By newOrg = By.xpath("//span[contains(text(), 'New Organization')]");
    private final By orgName = By.xpath("//input[@data-testid='inp_org_name']");
    private final By nextbtn = By.xpath("//span[contains(text(), 'Next')]");
    private final By skip = By.xpath("//span[contains(text(),'Skip')]");
    private final By playerCloseIcon = By.xpath("//div[@class='h-9 w-9 flex items-center justify-center rounded-full absolute']");
    private final By workspace = By.xpath("//a[contains(text(), 'Workspace')]");

    String random = RandomStringUtils.randomAlphabetic(9);

    public loginPage(WebDriver driver){
        this.driver= driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }


    public void successfulLogin(){
        driver.get("https://app.staging.freshflows.io/login?next=/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(contWithG));
        driver.findElement(usermail).sendKeys("ahanaa.j@american-technology.net");
        driver.findElement(password).sendKeys("Whitecollar30!");
        driver.findElement(submit).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(chooseOrgTitle));

}

public void createnewOrg(){
    wait.until(ExpectedConditions.visibilityOfElementLocated(newOrg));
    driver.findElement(newOrg).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(orgName));
    driver.findElement(orgName).sendKeys(random);
    driver.findElement(nextbtn).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(skip));
    driver.findElement(skip).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(playerCloseIcon));
    wait.until(ExpectedConditions.visibilityOfElementLocated(workspace));
}

}
