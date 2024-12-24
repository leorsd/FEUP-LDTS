# Dungeon Locked

## 📖 Game Overview
__Dungeon Locked__ is a local co-op game inspired by the 00's Flash classic "Fireboy and Watergirl".

In this game, two players take on the roles of our main characters, Lavena and Tergon, as they navigate through challenging dungeons filled with traps and enemies. To finally achieve freedom, they have to advance through 3 levels that require teamwork and strategy to overcome.

In each level, the difficulty increases, introducing new challenges. In the final level, you'll face all the obstacles from the previous levels, along with a surprising new challenge that will push your abilities to the limit.

## 🎮 Gameplay Functionalities and Details

Number of Players: 2 (local co-op).

### Controls

####  __Tergon__ (Player 1)
Move Left: A  
Move Right: D  
Jump: W

#### __Lavena__ (Player 2)
Move Left: ←  
Move Right: →  
Jump: ↑

## Implemented Features

### Interactive Menu 
Upon launching the game, the user is presented with several options. They can begin with the tutorial, select a specific level to play, view the credits, or exit the game.

The currently highlighted option is indicated by a key on the left side, and the user can navigate through the menu using the up and down arrow keys. To confirm their selection, they simply press the Enter key.
### Keyboard Actions Handling
Key inputs are processed and translated into actions within the game. 

The controls for the two players are handled independently: Player 1 uses the WASD keys, while Player 2 uses the Arrow Keys. Additionally, pressing the Q key allows players to exit the current level and return to the main menu.

### Colision Detection
We implemented all the collision mechanics that seemed essential for our game. 

If a player collides with a monster, a trap, or is crushed by a toggleable wall, they will die instantly. Players cannot pass through walls but can move through each other and in front of the level's exit door, as intended.

### Respawn 
If a player dies, they will respawn at their initial starting position from when the level first loaded. Upon respawning, any progress made up to that point will be reset, requiring the player to recollect all previously obtained keys.

### Different Levels
We implemented a tutorial and three distinct levels, each progressively more challenging.

### Level-Ending Logics
When both players collect all their respective keys
## 🗺️ Features and Mechanics

### Menu
Start a new game, select a specific level or exit the game.

![Menu](assets/menu_mockup.png)

### Levels

In every level, the players' primary objective is to gather two keys that unlock the gate (black line), allowing them to progress to the next stage.
Each player can only pick up the key that matches their color, which enables the creation of more entertaining and challenging puzzles.
New levels gradually introduce new features, like:
* Environmental Obstacles: Toxic Lakes, platforms and walls.

* Enemies: Skeleton and Final Boss Skeleton.

* Traps and challenges: Toxic ponds and button-coordenated walls.

### Level 1 (Tutorial)

Basic layout and terrain to familiarize players with teamwork mechanics.

![Level 1](assets/level1_mockup.png)

In Level 1, we decided to:
* Implement small parkour sections
* Introduce the players to the key feature
  All of these features allow the new players to get familiar with the controls, mechanics and objectives of the game.

### Level 2

Features new enemies, platforms and toxic ponds.

![Level 2](assets/level2_mockup.png)

This level includes:
* More challenging parkour sections;
* Toxic ponds (which can only be touched by the character of the matching color);
* A new enemy, the skeleton, which moves exclusively sideways.
* Deaths: If the wrong player touches the toxic pond or any of them comes in contact with the monster, they will die instantly and respawn at the initial position.

### Level 3
Introduced new button obstacles and a bigger boss enemy.

![Level 3](assets/level3_mockup.png)
This level introduces:
* A new button-coordenated purple wall, that disappears while one of the players is pressing the (also purple) button;
* A new enemy, the final boss, consisting of a bigger skeleton with a twist: this one stays still, only jumping vertically from time to time.

## 🚀 How to Play
Follow these steps:
* 1. Open a terminal and launch the game with `./gradlew run`
* 2. Use the menu to select a level, start from the tutorial or exit the game.
* 3. Work together to collect the keys and open the gate to complete the level.
* 4. Defeat the boss in the final level to win the game.

## 📈 UML Class Diagram

The following simplified UML diagram showcases classes and interfaces, as well as used design patters.  
![Simple UML](assets/LDTS_UML.drawio.png)
For more details refer to:
[Complete Diagram](assets/Complete_UML.drawio.png)
## 🛠️ Design Patterns

### Creational Design Patterns

#### Singleton

* Justification: We also chose to apply the Singleton design pattern, which is implemented in the Game class.
  There’s really not a point in initiating more than one instance of the Game class, since it’s the core class from which everything is created. After all, we only want a single game running at once.


* Consequences: In order to implement this pattern, we made the Game class constructor private, and created a static method that manages its instances. This method checks if a Game already exists. If it does, that instance is returned, if not, a new instance is initialized.

### Structural Design Patterns

#### Composite
* Justification: We decided to implement the composite design pattern in our game to manage the complexity of different controllers in a flexible and scalable way.
* Consequences: Due to the implementation of this design pattern we can treat individual controllers (like MonsterController, Player1Controller and Player2Controller) and the composite LevelController uniformly. By organizing controllers in this hierarchical way, we make our code cleaner and more intuitive.
  This pattern follows the Single Responsibility, the Open/Closed and the Liskov Substitution principles of SOLID, that help us make a modular system which is also easier to maintain.

#### Adapter
* Justification: The adapter pattern gives more flexibility and adaptability to the code, while helping enforce the SOLID principles.
* Consequences: This pattern forces us to have more interfaces, like GUI, and to clearly define the responsibilities of each of its subclasses. Therefore, it helps us follow the SOLID principles, namely the Single Responsibility, Open/Closed and Interface segregation principles.
* Lanterna GUI and Level Loader: In our case, the class Lanterna GUI acts as adapter for the Viewers. Lanterna GUI implements GUI, allowing for easy switching between GUI’s. In the future, we hope do a similar thing when loading levels: make an interface Level Loader, and several classes that implement it, allowing for several ways of representing levels.

### Behavioral Design Patterns

#### State
* Justification: In our game, there are only two possible states: Menu or Level. The behavior of the Controller and Visualizer class has to adapt depending on the current state.
  This design pattern also aligns with the Single Responsibility and Open/Closed principles of SOLID, which are great for our project. It organizes the code into distinct classes, each dedicated to a specific function, and enables the addition of new states without changing the existing ones.

* Consequences:This design pattern is implemented in our game through our GameManager class and Scene interface. The scene interface is implemented by the Menu and Level classes, which represent our only states. Then, everytime the game updates, the GameManager checks whether the current Scene is a Menu or a Level and adjusts its behavior accordingly.

#### Template Method

* Justification: The Template Method Pattern allows for defining a general structure for drawing scenes while enabling subclasses to customize specific steps, like drawing scene elements. This promotes code reuse, as the shared logic (clearing and refreshing the GUI) is centralized in the base class, reducing duplication and ensuring consistency across different visualizers. This aligns with the Single Responsibility, Open/Close, and Liskov substitution principles.
* Consequences: Using this pattern in our code improves code reusability and maintainability by centralizing the common drawing logic in the SceneVisualizer class while allowing customization through the drawElements method. It ensures consistent behavior across different visualizers and facilitates the addition of new scene types without modifying the core algorithm.

### Architectural Pattern

#### Model-View-Controller (MVC)
* Justification: Having a clear division of tasks among classes helps us follow the Single responsibility principle and allows dependency injection on tests. The three main responsibilities in a game are holding the state of the game, modifying it, and displaying it on an UI. Therefore, the most logical thing to do is to use the MVC pattern.
* Consequences: Using this method implies clearly separating the code in 3 parts, and holding an instance of each component in some of the classes. In our case, GameManager is the class that manages the interactions between them.

### Sequencing Pattern

#### Game Loop
* Justification: We decided to use this pattern because we don't really need to differentiate entities’ movement rates, and also to avoid synchronization errors between the threads. This pattern also ensures that the update and render processes happen in a consistent and smooth way. Additionally, it allows us to control the game's update rate, making it either smoother or lighter depending on our needs.
  This pattern can be broken down into five steps that repeat continuously. First, we initialize the game and set everything up for the start. Next, we process the user input, update the game objects, and draw all the elements. We then ensure updates occur at a steady rate using sleep, and the cycle repeats.
* Consequences: To implement this pattern, we created our Game class, which orchestrates the game's lifecycle (looping every 50ms) and maintains a controlled frame rate.
  This approach makes the game highly responsive and provides the player with a smooth and enjoyable experience, and we don't have any reason not to implement it in our project.

## 🤝 Development

To further improve and guarantee the quality of the software, we use additional tools, namely branch protection, [Github Actions](https://github.com/features/actions), and peer reviews.

To protect the master branch and ensure no code breaks it, we enforce a set of rules that, among other things requires pull requests with 2 approving reviews to be merged.
All pull requests to the branch `master` must pass a [Github Action](.github/workflows/test.yml) that compiles the code and tuns the unit tests. Those pull requests must be approved by the other group members before being merged.

We use feature and fix branches, and commonly used naming conventions on branches and commits.


## 🔜 Future work and upcoming features

* Additional collectibles for replayability (e.g., coins or artifacts).
* Power-Ups
* Check collisions with traps and keys, and act upon them
* Detect collisions with monsters when any of the characters' pixels coincide and not only when the base position is equal
* Include size in keys, traps, and walls
* Have better images for elements and background
* Add level-ending logics
* Ensure players fall while moving to the sides
