# 🌠 Multiplayer Tic-Tac-Toe [![License](https://img.shields.io/badge/licence-MIT-blue)](https://choosealicense.com/licenses/mit/) [![Contributions welcome](https://img.shields.io/badge/contributions-welcome-orange.svg)](https://github.com/Ukasz09/TDD-Tic-Tac-Toe) [![Status](https://img.shields.io/badge/status-finished-brightgreen)](https://github.com/Ukasz09/TDD-Tic-Tac-Toe)

 
Multiplayer Tic-Tac-Toe game in cosmic atmosphere
- Various size of game map
- Fun avatars, animation and game signs 
- 💻 All resolutions support <br/>

<br/>
- :white_check_mark: TDD <br/>
- :white_check_mark: Client-server - java socket programming <br/>

![gameplay image](https://raw.githubusercontent.com/Ukasz09/TDD-Tic-Tac-Toe/master/readmeImages/sizes.png)
<p align="center"><img width=95% src="https://raw.githubusercontent.com/Ukasz09/TDD-Tic-Tac-Toe/master/readmeImages/anim1.gif"></p>
<p align="center"><img width=95% src="https://raw.githubusercontent.com/Ukasz09/TDD-Tic-Tac-Toe/master/readmeImages/anim2.gif"></p>


## Game control
- `Mouse`: intuitively, by clicking on individual elements
- `Esc`: shortcut for end game button
- `Enter`: shortcut for confirm button

## How to use it ?
Actually I don't use any cloud hosting for my app, so it is possible to play only on localhost (LAN)  

:zero:  One of the players need to run a server:
1) open directory `Server`
2) Run server with command
```bash
java -jar Server.jar
```
You will see information about starting server and LAN server ip 

:one:	Both players need to run a client application <br/>
1) open directory `Client`
2) run `Client.jar` (just click on it)

If there is a problem with running, try to open it by console with command:

```bash
java -jar TicTacToe.jar
```
If you use Linux, you can see errors like this:

```bash
java.lang.NoClassDefFoundError: javafx/application/Application
```
it means that you don't have installed javafx libraries and you need to follow this steps: <br/>
- Download javaFx libraries [javafx-oracle.com](https://www.oracle.com/java/technologies/java-archive-javafx-downloads.html#javafx_sdk-1.3.1-oth-JPR)
- Unpack files into your java home destination, e.g. on Linux: `/usr/lib/jvm/java-14-oracle`
- Run script `runGame.sh`:
  - `-d` or `--default` flag: open with default java installation folder
  - `-p=JAVA_PATH` or `--path=JAVA_PATH` flag: open with custom java installation folder by JAVA_PATH     

Examples: <br/>

```bash
	./runGame.sh --default
	./runGame.sh -d
	./runGame.sh -p=/usr/lib/jvm/java-14-oracle
	./runGame.sh --path=/usr/lib/jvm/java-14-oracle
```
:two: Input server IP  (from server console)
:three: Enjoy the game 😎


## Software design stuff
**Used Designs Patterns:**
<br/><br/>
✅ Singleton <br/>
✅ Observer <br/>
✅ Builder <br/>

**Code overview:**
<br/><br/>
✔️ over 4000 lines of code  <br/>

## Acknowledgements
To my friends: [@Kaszti](https://github.com/wasyl078), [@Pawel](https://github.com/PRZYPRAWA), [@M1loseph](https://github.com/M1loseph)  [@Michu](https://github.com/michaltkacz) <br/>
For supporting and expressing a desire to become some of the available avatars 

___
## 📫 Contact 
Created by <br/>
<a href="https://github.com/Ukasz09" target="_blank"><img src="https://avatars0.githubusercontent.com/u/44710226?s=460&v=4"  width="100px;"></a>
<br/> gajerski.lukasz@gmail.com - feel free to contact me! ✊
