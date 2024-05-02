package ejercicio2;

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

class Ejercicio2 {
	
	static WebDriver driver1;
	
	@BeforeAll
	static void prepareDriver() {
		driver1 = new FirefoxDriver();
	}
	
	@BeforeEach
	void visitURL() {
		driver1.navigate().to("https://elenarivero.github.io/Ejercicio2/index.html");
	}

	private static Stream<Arguments> testWeb () {
		return Stream.of(
				Arguments.of("123","-"),
				Arguments.of("","+"),
				Arguments.of("1234","-"),
				Arguments.of("1","+"),
				Arguments.of("23","-"),	
				Arguments.of("000","+"),
				Arguments.of("asd","-"),
				Arguments.of("@ç&","-"),
				Arguments.of("-12","+"),
				Arguments.of("123","--"),
				Arguments.of("123", ""),	
				Arguments.of("123", "&"),
				Arguments.of("123", "a"),
				Arguments.of("123", "3")
			);
	}
	
	@ParameterizedTest
	@MethodSource("testWeb")
	void test(String numEmp, String directivo) {
		WebElement inputEmpleado = driver1.findElement(By.id("numero"));
		WebElement inputDirectivo = driver1.findElement(By.id("directivo"));
		WebElement submit = driver1.findElement(By.xpath("//input[@type='submit']"));
		inputEmpleado.sendKeys(numEmp);
		inputDirectivo.sendKeys(directivo);
		
		submit.click();
		
		WebElement errorNumero = driver1.findElement(By.id("errorNumero"));
		boolean errorN = errorNumero.isDisplayed();
		assertTrue(errorN);
		
		WebElement errorDirectivo = driver1.findElement(By.id("errorDirectivo"));
		boolean errorD = errorDirectivo.isDisplayed();
		assertFalse(errorD);
	}

}
