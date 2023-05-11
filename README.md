# CASProject3
For our third project in Complex Adaptive Systems we decided to create a program that runs a 2D Cellular Automata by generating 
rule-sets with a genetic algorithm <br>
with the goal of finding a rule-set that determines the majority rule of its starting board. 

This program mainly uses four classes for its operation, these are: 
BoardHandler, GeneticAlgorithm, Rule, and RunCA.<br>

BoardHandler as the name suggests makes boards and exports them or 
reads them to and from files. 

Genetic algorithm is contains the main function and does the generating and evolving 
of the rule-sets used in the running of the cellular automata.

The Rule class represents the individual rules that get generated and applied to each run of the 
cellular automata. Each rule has three attributes: myRule, fitness and ID. myRule is a boolean array that is the
rule-set that will directly be applied to the running cellular automata. Fitness is the fitness score this rule-set
has earned after being run in the cellular automata. ID is simply an identifying number given to a rule-set to allow
for saving it in files or on our leaderboard.

RunCA handles the actual running of the cellular automata for a given rule-set and board. This class is made to be
instantiated and run on separate threads to minimize runtimes. What it does is take the given board which is a 2D array
of boolean values and iterates through all the points on the board and applies the rule-set to them to figure out how 
they will change in the next time step. It will continue to apply the rule-set until a pre-determined number of cycles 
is reached.