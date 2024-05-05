package ejercicio3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

class Ejercicio3 {
	
	static WebDriver driver1;
	
	@BeforeAll
	static void prepareDriver() {
		driver1 = new FirefoxDriver();
	}
	
	@BeforeEach
	void visitURL() {
		driver1.navigate().to("https://elenarivero.github.io/ejercicio3/index.html");
	}

	private static Stream<Arguments> testWeb () {
		return Stream.of(
			
			Arguments.of("Alejandra-Blas","23/03/2001"),
			Arguments.of("", "24/03/2001"),
			Arguments.of("s", "25/03/2001"),
			Arguments.of("6", "26/03/2001"),
			Arguments.of("-", "26/03/2001"),
			Arguments.of("Fran€","26/03/2001"),
			Arguments.of("Francisca Alejandra-Blas", ""),	
			Arguments.of("Francisca Alejandra-Blas", "00/04/2000"),
			Arguments.of("Francisca Alejandra-Blas", "32/04/2000"),
			Arguments.of("Francisca Alejandra-Blas", "20/00/2020"),
			Arguments.of("Francisca Alejandra-Blas", "01/04/2002"),
			Arguments.of("Francisca Alejandra-Blas", "31/04/2002"),
			Arguments.of("Francisca Alejandra-Blas", "32/04/2002"),
			Arguments.of("Francisca Alejandra-Blas", "02/01/2002"),
			Arguments.of("Francisca Alejandra-Blas", "02/11/2020"),
			Arguments.of("Francisca Alejandra-Blas", "02/12/2020"),
			Arguments.of("Francisca Alejandra-Blas", "02/13/2020"),
			Arguments.of("Francisca Alejandra-Blas", "2"),
			Arguments.of("Francisca Alejandra-Blas", "08-02-1000")			
		);
	}
	
	@ParameterizedTest
	@MethodSource("testWeb")
	void test(String nomAp, String fecha) {
		WebElement nombreApellidos = driver1.findElement(By.id("nomap"));
		WebElement fechaNac = driver1.findElement(By.id("fecha"));
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		nombreApellidos.sendKeys(nomAp);
		fechaNac.sendKeys(fecha);
		
		submit.click();
		
		/*
		WebElement errorNomAp = driver1.findElement(By.id("errorNomap"));
		boolean errorN = errorNomAp.isDisplayed();
		assertTrue(errorN);
		
		WebElement errorDirectivo = driver1.findElement(By.id("errorDirectivo"));
		boolean errorD = errorDirectivo.isDisplayed();
		assertFalse(errorD);
		*/
		WebElement todoOk = driver1.findElement(By.tagName("h3"));
		String msg = todoOk.getText();
		assertEquals(msg, "Datos correctos");
	}

}
