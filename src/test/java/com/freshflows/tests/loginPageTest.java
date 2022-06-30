package com.freshflows.tests;

import com.freshflows.pages.loginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class loginPageTest extends BaseTest{


    @Test
    public void login(){
        extentTest= extent.startTest("login");
    loginPage lpg = new loginPage(driver);
    lpg.successfulLogin();
}

    @Test(dependsOnMethods = "login")
    public void newOrgCreation(){
        loginPage lpg = new loginPage(driver);
        lpg.successfulLogin();
        lpg.createnewOrg();
    }
}
