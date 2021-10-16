package com.qa.pageObjects;

import com.qa.util.WebInteractUtil;
import org.openqa.selenium.WebElement;

public class PointsVilleLoginPage extends WebInteractUtil {

    /** Method to enter username */
    public void enterUserCredentials() {
        WebElement usernameInput =
                (WebElement)
                        getJavascriptExecutor()
                                .executeScript(
                                        "return document.querySelector('amplify-authenticator>amplify-sign-in').shadowRoot.querySelector('amplify-form-section>amplify-auth-fields').shadowRoot.querySelector('div>amplify-username-field').shadowRoot.querySelector('amplify-form-field').shadowRoot.querySelector('input')");
        WebElement passwordInput =
                (WebElement)
                        getJavascriptExecutor()
                                .executeScript(
                                        "return document.querySelector('amplify-authenticator>amplify-sign-in').shadowRoot.querySelector('amplify-form-section>amplify-auth-fields').shadowRoot.querySelector('div>amplify-password-field').shadowRoot.querySelector('amplify-form-field').shadowRoot.querySelector('input')");

        String jsForUsername = "arguments[0].setAttribute('value','username')";
        String jsForPassword = "arguments[0].setAttribute('value','password')";

        executeUsingJavaScript(jsForUsername, usernameInput);
        executeUsingJavaScript(jsForPassword, passwordInput);
    }

    /** Method to enter password */
    public void clickOnResetPwdLink() {
        WebElement resetPwdLink =
                (WebElement)
                        getJavascriptExecutor()
                                .executeScript(
                                        "return document.querySelector('amplify-authenticator > amplify-sign-in').shadowRoot.querySelector('amplify-form-section > amplify-auth-fields').shadowRoot.querySelector('div > amplify-password-field').shadowRoot.querySelector('amplify-form-field').shadowRoot.querySelector('#password-hint > div > amplify-button')");
        String js = "arguments[0].click();";
        executeUsingJavaScript(js, resetPwdLink);
    }
}
