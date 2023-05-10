# Purpose?
It's very simple. At the very beginning, each user gradually fills out a small player profile, 
indicating their favorite games, their age group, a short description, maybe even a photo, 
after which the bot builds a list of people with similar interests over time, after which they can look through each other's profiles, 
and finally, if both people are satisfied with the allies they have found, 
they can write to each other to arrange to play together. If not, it doesn't matter, 
there are countless people in the world!

# Development
The project is entirely made using the Java Telegram API Wrapper Library from rubenlagus <a href="https://github.com/rubenlagus/TelegramBots"> TelegramBots</a>, using the Chain of Responsibility pattern which delegates specific updates to different Handlers based on their features and unique content. The general structure also uses the Spring Framework (Spring Core, Spring Data, Spring Testing, Spring AOP, and Spring Boot) as out-of-the-box solutions for many possible problems and vulnerabilities in the code. Additionally, the project supports any of the existing SQL thanks to the Spring Data flexibility.

# How to ... ?
In order to successfully set up the application and launch it asap, you should add two properties files to the project resources. 
First of them (application.properties) is related to Spring settings, more like database connection and internalization, and should look like that: 

```properties
#datasource
spring.datasource.url= #DATABASE URL
spring.datasource.password= #USER PASSWORD
spring.datasource.username= #USERNAME
spring.datasource.driver-class-name= #CONNECTION DRIVER
spring.jpa.hibernate.ddl-auto=update
#i18n
spring.messages.basename=i18n/messages
spring.messages.encoding=UTF-8
```

The second one (thirdparty.properties) should contain credentials for your Telegram bot and media info:

```properties
#credentials
telegram.bot.token= #BOT TOKEN
telegram.bot.username= #BOT USERNAME
#images
img.menu= #MENU PICTURE
img.profile= #PROFILE PICTURE
img.settings= #SETTINGS PICTURE
img.info= #INFO PICTURE
img.discover= #DISCOVER MENU PICTURE
img.explore= #EXPLORE MENU PICTURE
img.matches= #MATCHES PICTURE
```

Telegram allows us to optimize media in such a way that we only have to upload a full picture or video once when it is not stored on Telegram's servers. We can send by its file_id the time it was sent and is now stored on Telegram's vaults. Team Up Now uses media almost every time a user does something with it, so it justifies the way the bot sends media. Remember, the file_id is different for each bot, and you have to get it again every time credentials are changed!

# OSI
An official online contribution can be found <a href="https://t.me/teamupnow_bot"> here </a>
