# Cephalon

Cephalon is a discord plugin bot where you can easily  write, install and run your own plugins.

## Table of Contents
- [Setup](#setup)
  - [Requirements](#requirements)
  - [Installation](#installation)
- [Launch](#launch)

## Setup

### Requirements
First of all, you need your own Discord application and a bot. You can get a bot [here](https://discord.com/developers/docs/getting-started#creating-an-app).

JRE 17 or higher must be installed.
You can download it [here](https://www.oracle.com/java/technologies/downloads/#java17).
Alternatively, you can install it under Linux using the following command:
~~~console
sudo apt install openjdk-11-jre
~~~

If you want to build your own Jar-Files, Maven should be installed.
You can download it [here](https://maven.apache.org/download.cgi).
Alternatively, you can install it under Linux using the following command:
~~~console
sudo apt install maven
~~~
### Installation
Clone the repository, then use Maven to install the dependencies.
~~~console
git clone https://github.com/seekers-dev/seekers-api.git
cd seekers-api
mvn install
~~~
Create a file called `token.txt`, then paste the token of your bot there.

To install plugins, just drag them into the `plugins` folder. Please note that you can only use the plugins as a zip file. Do not unzip them!
## Launch
Launch it using the Exec Maven Plugin.
~~~console
mvn exec:java
~~~

You will see that a `server.properties` file is created. You can customise them as you like. You must restart the server for the changes to take effect.