# game-scoring-app
Tennis Game Scoring Application, Which applies  various rules to decide the the Winner based on the scores each player attain along the Game
High Level Class Diagram 
[embed] https://github.com/Jagadeesh8/game-scoring-app/blob/main/TennisApp-HL-Class%20(1).pdf [/embed]

High Level Arroach for Single Game Winner Evaluation flow 
1. Referee Enters score for each player to process the points 
2. If no one secure any poiint and we have called the process i.e we are stating the game, we update the score cards for the game.  
3. As game progress when a player secure a point process will be called and score cards will be updated and retured to client so that client can display the data.
4. if both players reach score 40 points Deuce rule will be applied and winner be evaluated based on that.

High Level Arroach for Set Winner Evaluation flow 
1. Referee Enters score for each player to process the points 
2. If no one secure any poiint and we have called the process i.e we are stating the game, we update the score cards for the game.
3. When a point is secured by a player setmatch process will be called internally it will call individual game service as mentioned above will be called, baed on the result from single game set match score will be updated. this way we reuse the Single game process as well as we can also view the current game score and whole set match score. 
4. Tie-Break rule will be evaluated after both players get set match score of 6. 
 
Advantages
1. Since we have given Implementaion for Tennis seperately trough Interface, same project can be extended for any other game also, we just need to impletment those specific game rules here. 
2. Apart from Deuce and standard rules we can Implement new rule for single game by implementing game rule service. 
3. Deuce and standard rule Impl also can be changed.
4. Same Applies to set match also. 
