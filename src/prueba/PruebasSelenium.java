package prueba;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PruebasSelenium {
	
	static WebDriver driver1;

	@BeforeAll
	static void setURL() throws IOException {
		driver1 = new FirefoxDriver();
	}


	@ParameterizedTest
    @CsvFileSource(resources = "/test/resources/test-case.csv", numLinesToSkip = 1, delimiter = ',')
	void test(String direccion, String codigoPostal) {
		driver1.get("https://elenarivero.github.io/ejercicio1/index.html");
		WebElement direccionBox = driver1.findElement(By.id("direccion"));        
        WebElement cpBox = driver1.findElement(By.id("cp"));
        
        direccionBox.sendKeys(direccion);
        cpBox.sendKeys(codigoPostal);
        
        
        WebElement submitButton = driver1.findElement(By.id("enviar"));
        submitButton.click();
        
        WebElement errorCP = driver1.findElement(By.id("errorCP"));
        boolean texto = errorCP.isDisplayed();
        assertTrue(texto);
        
        WebElement errorDireccion = driver1.findElement(By.id("errorDireccion"));
        boolean textoDireccion = errorDireccion.isDisplayed();
        assertFalse(textoDireccion);
        
	}


	@AfterAll
	static void exitDriver() {
		driver1.quit();
	}
}
