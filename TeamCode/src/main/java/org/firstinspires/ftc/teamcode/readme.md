# 22557 Rambots Teamcode

## System
1. OpModes are ran by the user
2. The OpMode is given a built bot
3. The bot is built from components


## Components
Components are the parts of the robot that are build together into bot(). An example of a component is found [here](components/ExampleComponent.java)
 - physical components (eg. lifts, claws) go into [here](components/hardware)
 - software components (eg. cameras, gyros) go into [here](components/device)
 - management components (eg. component builds, tools) go into [here](components/meta)

In order to add components into the bot, put them in the appropriate folder then add them to the [AutoBotComponents](components/meta/AutoBotComponents.java), [TeleOpBotComponents](components/meta/TeleOpBotComponents.java), or [BotComponents](components/meta/BotComponents.java)