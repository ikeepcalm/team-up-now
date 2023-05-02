# Team Up Now
Telegram bot designed to connect different gamers with each other based on their interests

# How does it work?
It's very simple. At the very beginning, each user gradually fills out a small player profile, 
indicating their favorite games, their age group, a short description, maybe even a photo, 
after which the bot builds a list of people with similar interests over time, after which they can look through each other's profiles, 
and finally, if both people are satisfied with the allies they have found, 
they can write to each other to arrange to play together. If not, it doesn't matter, 
there are countless people in the world!

# Backside
The project is entirely made using the Java Telegram API Wrapper Library from rubenlagus <a href="https://github.com/rubenlagus/TelegramBots"> TelegramBots</a>, using the Chain of Responsibility pattern that delegates specific updates to different teams based on their features and unique content. The persistent part also uses the Spring Framework (Spring Core, Spring Data, Spring Testing, Spring AOP, and Spring Boot) as out-of-the-box solutions for many possible problems and vulnerabilities in the code. And finally, Project Lombok is used as a very convenient choice to enhance the annotation-oriented wrapper for persistent layer views.

# OSI
An official online contribution can be found <a href="https://t.me/teamupnow_bot"> here </a>
