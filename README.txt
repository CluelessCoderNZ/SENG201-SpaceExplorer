# SENG201-SpaceExplorer

## LOADING SOURCE CODE INTO ECLIPSE
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


## RUNNING THE JAR FILE
1. navigate to the root project directory
2. run the terminal command "java -jar jsv22_csm119_SpaceExplorer.jar"
 + to get the command line version of the game, use "java -jar jsv22_csm119_SpaceExplorer.jar cl"

## Running system tests
the system test files can be found in the tests/ folder in the root directory
Run the testing script with "python3 system_tester.py <jar file> <test file>"  
This will automatically run through the input in the test file and then redirect to user input afterwards to allow for further testing if required. Otherwise the game can be closed with ctrl-C.
