# Wallet test bed app
A simple wallet application that allows the following attacks and gives an insight into the level of impact of these attacks.
* SQL Injection.
* Web parameter tampering.
* Path traversal.
* Command injection.

## SQL Injection


https://user-images.githubusercontent.com/16650007/147136164-ccc29666-0116-4aaa-9343-319b412f3011.mp4


## Web parameter tampering.


https://user-images.githubusercontent.com/16650007/147136184-fe0bdf8e-c291-4861-a4d4-09eb56b53a3f.mp4


## Path traversal.


https://user-images.githubusercontent.com/16650007/147136200-827f4c01-f210-4ba3-8805-b39d78998aaf.mp4


## Command injection.

https://user-images.githubusercontent.com/16650007/147136231-46f4b799-d31f-4c40-ad67-bf5c5445e7d8.mp4

## What can this App do?
* New users can sign up and existing users can login with username and password.
* Debit cards can be added to or deleted from the wallet.
* The wallet can be loaded with money using cards.
* The wallet can be used to transfer money to bank accounts.
* The total transaction value can be calculated for yearly, monthly and daily periodicities.
**Note:** This app is not intended to be a full-fledged digital wallet. This app implements only the basic features to allow the attacks mentioned in the previous section.

## How to run the app ?

* Install docker desktop. Follow the instructions [here](https://docs.docker.com/desktop/#download-and-install).
* Clone this repository. 
* ‘cd’ into the ‘wallet’ directory. All the docker commands given in the next steps should be executed from the ‘wallet’ directory.
* Execute the following the command to start a multi container docker for the app.
 ``````
 docker-compose up -d
 ``````
* The app should be running at ‘http://localhost:8082/wallet’.

## How to make changes and rebuild the app locally?
* Download and setup  java jdk  <= 17.0.1 and Apache Maven 3.8.3.
* Clone this repository.
* ‘cd’ into the ‘wallet’ directory.
* Run the following command.
````````
mvn package
````````
* If the build is successful, you will find ‘wallet.war’ in ‘source/wallet/target’ directory.
* Copy ‘wallet.war’ from the ‘target’ directory and paste in the project's root directory.
* Follow the steps from the previous section to start the docker multi container.
