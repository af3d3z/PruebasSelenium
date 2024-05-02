package ejercicio1;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

class Ejercicio1 {
	
	static WebDriver driver1;

	@BeforeAll
	static void setURL() {
		System.setProperty("webdriver.edge.driver", "C:\\chromedriver\\msedgedriver.exe");
		EdgeOptions options = new EdgeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--start-maximized"); 
		options.addArguments("--ignore-certificate-errors"); 
		options.addArguments("--disable-popup-blocking");
		driver1 = new EdgeDriver(options);
	}
	

	@BeforeEach
	void visitURL() {
		driver1.navigate().to("https://elenarivero.github.io/ejercicio1/index.html");
	}
	
	private static Stream<Arguments> testDirecciones () throws IOException {
		List<Arguments> pruebas = new LinkedList<>();		
		try {
			char fileChar;
			String aux = "";
			FileReader fr = new FileReader("test-cases.csv");
			
			while((fileChar = (char) fr.read()) != -1) {
				if(fileChar == '\n') {
					aux = aux.replace("\n", "");
					String[] array = aux.split(",");
					pruebas.add(Arguments.of(array[0], array[1]));
					aux = "";
				}
				aux += fileChar;
			}
			fr.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pruebas.stream();
	}

	private static Stream<Arguments> testWeb () {
		return Stream.of(
				Arguments.of("Calle Ramon y Cajal", "41710"),
				Arguments.of("", "41710"),
				Arguments.of("$Calle Ramon y Cajal", "41710"),
				Arguments.of("0Calle Ramon y Cajal","41710"),
				Arguments.of("\\/-.","41710"),
				Arguments.of("2384012834","41710"),
				Arguments.of("Calle Ramon y Cajal","a"),
				Arguments.of("Calle Ramon y Cajal",""),
				Arguments.of("Calle Ramon y Cajal","\\.~|\\|@#~"),
				Arguments.of("Calle Ramon y Cajal","9999"),
				Arguments.of("Calle Ramon y Cajal","10000"),
				Arguments.of("Calle Ramon y Cajal","99999"),
				Arguments.of("Calle Ramon y Cajal","100000")
			);
	}


	@ParameterizedTest
	@MethodSource("testWeb")
	void test(String direccion, String codigoPostal) {
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
		System.out.println("Finished!");
		driver1.quit();
	}
}
