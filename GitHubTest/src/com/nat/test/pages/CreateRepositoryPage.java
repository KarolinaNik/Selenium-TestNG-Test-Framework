package com.nat.test.pages;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Class represents page with the form for creating new repository
 */
public class CreateRepositoryPage extends Page {

	@FindBy(name = "q")
	private WebElement search;

	@FindBy(id = "repository_name")
	private WebElement repNameElement;

	@FindBy(id = "repository_description")
	private WebElement repDescription;

	@FindBy(xpath = "//*[contains (text(), 'Create repository')]")
	private WebElement repSubmit;

	@FindBy(xpath = "//div[contains(@class, 'owner-container')]/span")
	private WebElement owner;

	@FindBy(xpath = "//input[@type='radio'][@id='repository_public_true']")
	private WebElement publicRep;

	@FindBy(xpath = "//input[@type='radio'][@id='repository_public_false']")
	private WebElement privateRep;

	@FindBy(id = "repository_auto_init")
	private WebElement autoInit;

	@FindBy(xpath = "//*[contains(text(), 'Add .gitignore')]")
	private WebElement addGitignore;

	@FindBy(id = "context-ignore-filter-field")
	private WebElement chooseGitignore;

	@FindBy(xpath = "//div[@class='select-menu-item js-navigation-item']/div")
	private List<WebElement> gitignoreList;

	@FindBy(xpath = "//*[contains(text(), 'Add a license')]")
	private WebElement addLicense;

	@FindBy(xpath = "//div[@class='select-menu-item js-navigation-item']/div")
	private List<WebElement> licenseList;

	@FindBy(id = "context-license-filter-field")
	private WebElement chooseLicense;

	@FindBy(xpath = "//button[contains(@class, 'sign-out-button')]")
	private WebElement logout;

	@FindBy(xpath = "//*[contains(text(), 'Choose another owner')]")
	private WebElement chooseOwner;

	private String repName;

	/**
	 * Class constructor
	 * 
	 * @param driver
	 *            The driver that will be used for navigation
	 * @throws IllegalStateException
	 *             If it's not expected page
	 */
	public CreateRepositoryPage(WebDriver driver) {
		this.driver = driver;
		// Check that we're on the right page.
		if (!"Create a New Repository".equals(driver.getTitle())) {
			throw new IllegalStateException(
					"This is not the CreateRepository page, this is "
							+ driver.getTitle());
		}
	}

	/**
	 * Checks if the form for creating new repository presents on the page
	 *
	 * @return true if all form elements present on the page
	 */
	public boolean isNewRepFormExists() {
		return isElementPresents(repNameElement)
				&& isElementPresents(repDescription)
				&& isElementPresents(repSubmit) && isElementPresents(owner)
				&& isElementPresents(publicRep)
				&& isElementPresents(privateRep) && isElementPresents(autoInit)
				&& isElementPresents(addGitignore)
				&& isElementPresents(addLicense);
	}

	/**
	 * Log out
	 *
	 * @return An instance of {@link StartPage} class
	 */
	public StartPage logout() {
		logout.click();
		return PageFactory.initElements(driver, StartPage.class);
	}

	/**
	 * Check if dropdowns to choose owner, gitignore and license appear if click
	 * it's elements
	 *
	 * @return True if dropdowns to choose owner, gitignore and license appear
	 *         if click it's elements
	 */
	public boolean isNewRepFormJSWorks() {
		owner.click();
		boolean isChooseOwnerPresents = isElementPresents(chooseOwner);
		System.out.println(isChooseOwnerPresents + "");
		addGitignore.click();
		boolean isGitignorePresents = isElementPresents(chooseGitignore);
		System.out.println(isGitignorePresents + "");
		addLicense.click();
		boolean isLicensePresents = isElementPresents(chooseLicense);
		System.out.println(isLicensePresents + "");
		return isChooseOwnerPresents && isGitignorePresents
				&& isLicensePresents;
	}

	/**
	 * Method to get the page with just created repository.
	 * 
	 * @param repName
	 *            New repository name
	 * @param repDescription
	 *            Repository description
	 * @param addReadme
	 *            If true, initialize repository with a README
	 * @param gitignore
	 *            Select gitignore from the dropdown
	 * @param license
	 *            Select license type from the dropdown
	 *
	 * @return An instance of {@link RepositoryPage} class
	 */
	public RepositoryPage createRepository(String repName,
			String repDescription, boolean addReadme, String gitignore,
			String license) {
		// GitHub replaces all spaces to "-" anyway
		this.repName = repName.trim().replaceAll(" +", "-");
		this.repNameElement.sendKeys(repName);
		this.repDescription.sendKeys(repDescription);
		if (autoInit.isSelected()) {
			if (!addReadme) {
				autoInit.click();
			}
		} else {
			if (addReadme) {
				autoInit.click();
			}
		}
		if (null != gitignore) {
			addGitignore.click();
			for (WebElement element : gitignoreList) {
				if (element.getText().contains(gitignore)) {
					element.click();
				}
			}
		}
		if (null != license) {
			addLicense.click();
			for (WebElement element : licenseList) {
				if (element.getText().contains(license)) {
					element.click();
				}
			}
		}
		repSubmit.click();
		RepositoryPage repPage = new RepositoryPage(repName, driver);
		repPage.setRepName(repName);
		return repPage;
	}

	/**
	 * The method to get the repository name
	 *
	 * @return repName
	 */
	public String getRepositoryName() {
		return repName;
	}
}
