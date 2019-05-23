# SENG201-SpaceExplorer

## Loading source code into Eclipse
1. Open Eclipse
2. click File > Import
3. select the "Existing Projects into Workspace" wizard
4. select the provided eclipse-workspace folder as the root directory
5. click "Finish"

if the above does not work:
1. click File > New > Java project
2. create a new empty project
3. right click on the created source folder and click Import
4. select the "File System" wizard
5. select the provided eclipse-workspace folder as the from directory
6. click finish and "Yes to All" if prompted to overwrite files

## Running the JAR file
navigate to the root project directory
run the terminal command "java -jar SpaceExplorer.jar"

## Running an intergration test
Run the python script with 'python3 intergration_test.py tests/<filename>'
This will automatically run through the test input and then redirect to user
input afterwards to allow for further testing.
