# testexercise_bank2


# UI Automation Example Project
> Example project developed to perform automated tests on the website [bigbank](https://taotlus.bigbank.ee/)
>> To collaborate with bigbank (TA): https://github.com/tbogdel


## How to use:

- [Installation](#installation)
- [Technologies](#technologies)
- [Reports](#reports)

---

## Installation
- Put ChromeDriver to directory (to be compatible with your browser):
```
	C:\Program Files\WebDriver\chromedriver.exe
```

- Clone this repository to your local machine using the command below:
```
	$ git clone https://github.com/tbogdel/testexercise_bank2.git
```

---

### Execution

> Access project root

```
	$ cd /testexercise_bigbank
```
> Execute the command to run all tests in the project

```
	$ mvn clean test
```

---
## Technologies:
- Selenium WebDriver
- Java
- Maven
- Allure report
- TestNG

---


## Reports

* To view report of test, access the files from: */target/allure-results*

> Run maven goal to generate report.

```
	$ mvn allure:serve
```

Note: if your browser can not open report then replace generated address by localhost

Report example:

<img src="https://github.com/tbogdel/testexercise_bank2/blob/main/src/test/resources/Allure%20report.png"/>

