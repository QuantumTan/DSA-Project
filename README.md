# Knapsack Problem Solver

A dynamic programming solution for the grouped knapsack problem with time-limited capacities.

## Project Structure

```
DSAProject/
├── src/
│   ├── Main.java              # Console application entry point
│   ├── model/                 # Data models
│   │   ├── Item.java          # Item representation
│   │   ├── GroupResult.java   # Single group solution result
│   │   └── SolverResult.java  # Overall solution result
│   ├── solver/                # Algorithm implementation
│   │   └── KnapsackSolver.java # DP knapsack solver
│   ├── gui/                   # GUI components
│   │   └── KnapsackGUI.java   # Swing-based GUI
│   └── util/                  # Utilities
│       ├── ResultFormatter.java # Result formatting
│       └── UIConstants.java    # GUI styling constants
└── README.md

```

## Building and Running

### Console Application

```powershell
# Compile
cd C:\DSAProject
javac -d bin src\Main.java src\model\*.java src\solver\*.java

# Run
java -cp bin Main
```

### GUI Application

```powershell
# Compile
cd C:\DSAProject
javac -d bin src\gui\KnapsackGUI.java src\model\*.java src\solver\*.java src\util\*.java

# Run
java -cp bin gui.KnapsackGUI
```

## Algorithm

- **Approach**: Dynamic Programming (0/1 Knapsack per group)
- **Time Complexity**: O(G × m × T) where G is groups, m is avg items per group, T is capacity
- **Space Complexity**: O(T) using 1D DP optimization

## Input Format

```
N G T R
v[1] w[1] g[1]
v[2] w[2] g[2]
...
v[N] w[N] g[N]
```

Where:
- N = number of items
- G = number of groups
- T = time/capacity limit
- R = rate parameter (stored but not used in classic mode)
- v[i] = value of item i
- w[i] = weight of item i
- g[i] = group of item i

## Output

Single integer representing the maximum total value achievable.
