# CS458-Project3

### Team Members ###
Doğukan Ertunga Kurnaz 21702331<br/>
İdil Yılmaz 21703556<br/>
Sarp Ulaş Kaya 21801992<br/>
Berk Kerem Berçin 21803190<br/>

### INSTRUCTIONS ###
# Setting up and using the web site:
The first thing that you need to do is open this directory inside Visual Studio Code, and download the Live Server extension if it is not already added using the Extensions tab on the left. Once the extension is added, you will have to click "Go live" on the bottom left, which will then display a port number. Using this port number, you can check the website that we have set up for yourself using the link as "http://localhost:port-number/index.html". This is an important step for each functionality to work as intended. Make sure that you use Chrome as your browser while testing the project.

# Setting up and using Selenium:
Make sure that JDK 11 and Eclipse IDE are installed on your computer before proceeding with the remaining steps. While the necessary files are all available inside this directory, we strongly recommend that you add the provided Java project folder, "project3Maven", inside your preferred eclipse workspace and access it through there using the Eclipse IDE. It is important that Certain Maven and Selenium tools will be installed inside your project folder, and the pom.xml file should provide that as soon as you open the project and add 'Maven Dependencies' inside your workspace under the project. If not, create a new Maven project inside Eclipse, and replace its pom.xml file and its src files with the ones inside the "project3Maven" directory. Once you locate the project inside the Eclipse IDE, right click on it and select Build Path > Configure Build Path. Then, select Libraries and click on Add External JARs. From here, locate and select the file "selenium-server-4.1.2.jar", which is also provided inside this directory. If you have followed the steps correctly, you will see it under referenced libraries under the project folder inside your Eclipse IDE.

The next step is to make a few changes inside the Java class themselves so that the tests work on your computer. First, locate the Project3Test.java file inside the test folder of src or src/test/java. First, inside the second parameter string of the first line of the main method, you will see the path "C:\\Users\\ULAS\\Downloads\\chromedriver_win32\\chromedriver.exe". The chromedriver.exe file is inside the chromedriver_win32.zip file that is provided inside the project directory. Extract the .exe file from this zip and change the path accordingly based on wherever you would like to keep it on your own computer. Next, change the port number inside the line commented with "//open URL" based on your own port number that VSCode's Live Server extension gave you (in other words, the one you used to access the website before). With all of these things done, you should be ready to run the tests by simply running this "Project3Test" script.