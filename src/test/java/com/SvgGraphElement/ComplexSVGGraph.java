package com.SvgGraphElement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class ComplexSVGGraph {


	@Test
	public void getSvgGraph() throws InterruptedException {

		WebDriver driver = new ChromeDriver();

		driver.get("https://emicalculator.net/");

		driver.manage().window().maximize();

		Thread.sleep(5000);

		String svgElementXpath = "//*[local-name()='svg']//*[name()='g' and @class='highcharts-series-group']//*[name()='rect']";
        String textXpath = "//*[local-name()='svg']//*[name()='g' and @class='highcharts-label highcharts-tooltip highcharts-color-undefined']//*[name()='text']";		
		List<WebElement> svgElement = driver.findElements(By.xpath(svgElementXpath));

		System.out.println("Svg element size is" + svgElement.size());
		
		Actions act = new Actions(driver);
		
		for(WebElement svg : svgElement) {
			act.moveToElement(svg).perform();
			Thread.sleep(500);
			String text = driver.findElement(By.xpath(textXpath)).getText();
			System.out.println("And tooltip text is" + text);
		}
		

		


	}

}
