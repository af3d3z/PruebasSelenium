package prueba;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PruebasSelenium {
	
	static WebDriver driver1;

	@BeforeAll
	static void setURL() throws IOException {
		driver1 = new FirefoxDriver();
	}

	private static Stream<Arguments> testDirecciones () throws IOException {
		List<Arguments> pruebas = new LinkedList<>();		
		try {
			char fileChar;
			String aux = "";
			String direccion = "";
			String codigoPostal = "";
			FileReader fr = new FileReader("test-cases.csv");
			
			while((fileChar = (char) fr.read()) != -1) {
				if(fileChar == '\n') {
					aux = aux.replace("\n", "");
					String[] array = aux.split(",");
					System.out.println(Arrays.toString(array));
					pruebas.add(Arguments.of(array[0], array[1]));
					aux = "";
				}
				aux += fileChar;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pruebas.stream();
	}



	@ParameterizedTest
	@MethodSource("testDirecciones")
	void test(String direccion, String codigoPostal) {
		System.out.println("new test");
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
