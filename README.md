# Package Challenge
The jar(packer-0.0.1-SNAPSHOT.jar) can be added as dependency for a maven project and the functionality for the package challenge can be invoke as : Packer.pack(filePath). The result string would give the index numbers of packages meeting the requirement as per the problem statement.

Some of the points that I would like to mention are :
1. Separation of concern is achieved via separate classes for each purpose (for instance Service and Validation)
2. The code is unit tested with a code coverage of 87.6%
3. Quality check is done using SonarLint Eclipse plugin and no possible bugs or issues are found.
