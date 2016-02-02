import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Solver
{

    //Our data sets, all 2D arrays of strings
    protected static String[][] startValue = new String[4][4];
    protected static String[][] endValue = new String[4][4];
    protected static String[][] stepValue = new String[4][4];
    protected static Tree myDataTree;
    protected static ArrayList<Tree> solutionPath = new ArrayList<>();
    private static int depth = 0;
    private static int depthLimit = 16;
    
    public static void main(String[] args)
    {
        //To prompt the user for the start and end
        //getStart();
        //getEnd();

        //Optional if I just want to set them manually internally
        setStart();
        setEnd();

        //We do this no matter what
        copyArrayOfSameSize(startValue, stepValue);

        //Initializing our data tree
        myDataTree = new Tree(startValue);

        //This is how we add the valid moves. It should be replaced by a recursive implementation
        addMovesNester(myDataTree);

        //This is our recursive version of addMovesNester. It adds all valid moves to the entire tree
        //recursivePathGenerator(myDataTree);

        //Since getSolutionPath wants to find the shortest solution, we want to have the ability to compare what
        //was the shortest path so far. Therefore, we 'reset' the size to 16 each time.
        resetSolutionPath();

        //This is how we find the solution path, AKA where it exists in the tree. It is a recursive implementation
        getSolutionPath(myDataTree, new ArrayList<>());

        //myDataTree.printAllRootsToLeafPaths(myDataTree, new ArrayList<>());

        //This is pretty basic, it just prints the solution path we found
        printSolution();

        //For testing our Tree's functions
        //treeTest(myDataTree);

        //Testing the Queue to make sure it throws errors properly
        //queueTest();

        //For debugging
        //print2DArray(startValue, "Starting Array");
        //print2DArray(stepValue, "Stepping Array");
    }

    //TODO: Comment this
    private static void resetSolutionPath()
    {
        solutionPath = new ArrayList<>();
        String[][] tempArray = new String[1][1];
        for(int i = 0; i < 16; i++)
        {
            solutionPath.add(new Tree(tempArray));
        }
    }

    protected static void addMovesNester(Tree startingRoot)
    {
        Tree myData = startingRoot;

        //Level 0
        addValidMoves(myData);

        //Level 1
        for(int levelOne = 0; levelOne < myData.numberOfRoots; levelOne++)
        {
            myData = startingRoot.getRoot(levelOne);
            addValidMoves(myData);
        }

        //Level 2
        for(int levelOne = 0; levelOne < myData.numberOfRoots; levelOne++)
        {
            for(int levelTwo = 0; levelTwo < myData.numberOfRoots; levelTwo++)
            {
                myData = startingRoot.getRoot(levelOne).getRoot(levelTwo);
                addValidMoves(myData);
            }
        }

        //Level 3
        for(int levelOne = 0; levelOne < myData.numberOfRoots; levelOne++)
        {
            for(int levelTwo = 0; levelTwo < myData.numberOfRoots; levelTwo++)
            {
                for(int levelThree = 0; levelThree < myData.numberOfRoots; levelThree++)
                {
                    myData = startingRoot.getRoot(levelOne).getRoot(levelTwo).getRoot(levelThree);
                    addValidMoves(myData);
                }
            }
        }

        //Level 4

        for(int levelOne = 0; levelOne < myData.numberOfRoots; levelOne++)
        {
            for(int levelTwo = 0; levelTwo < myData.numberOfRoots; levelTwo++)
            {
                for(int levelThree = 0; levelThree < myData.numberOfRoots; levelThree++)
                {
                    for(int levelFour = 0; levelFour < myData.numberOfRoots; levelFour++)
                    {
                        myData = startingRoot.getRoot(levelOne).getRoot(levelTwo).getRoot(levelThree).getRoot(levelFour);
                        addValidMoves(myData);
                    }
                }
            }
        }


        //Level 5

        //Level 6

        //Level 7

        //Level 8

        //Level 9

        //Level 10

        //Level 11

        //Level 12

        //Level 13

        //Level 14

        //Level 15

        //Level 16

    }

    //Our tree tests
    private static void treeTest(Tree testTree)
    {
        //Testing toString
        System.out.println(testTree);

        //Populating our tree with roots and subroots
        //Ten subroots for our Starting Root
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        testTree.add(startValue);
        //Three subroots for node 0
        testTree.getRoot(0).add(startValue);
        testTree.getRoot(0).add(startValue);
        testTree.getRoot(0).add(startValue);
        //5 subroots for node 1
        testTree.getRoot(1).add(startValue);
        testTree.getRoot(1).add(startValue);
        testTree.getRoot(1).add(startValue);
        testTree.getRoot(1).add(startValue);
        testTree.getRoot(1).add(startValue);
        //4 subroots for node 2
        testTree.getRoot(2).add(startValue);
        testTree.getRoot(2).add(startValue);
        testTree.getRoot(2).add(startValue);
        testTree.getRoot(9).add(startValue);
        //One subroot for Node 2, Subroot 0
        testTree.getRoot(9).getRoot(0).add(endValue);

        //Testing our recursiveSearch
        System.out.println(testTree.recursiveContains(startValue));
        System.out.println(testTree.recursiveContains(endValue));

        //A test of printing all the paths in the tree
        testTree.printAllRootsToLeafPaths(testTree, new ArrayList<>());
    }

    //Our rotation for a row. It doesn't matter if you rotate right or left, as long as there's no additional
    //repetitions inside the overall tree
    protected static void rotateRowOnceRight(String[][] data, int row)
    {
        //Start by copying our 4th cell's value
        String tempVal = data[row][3];
        //Rotate our 3rd cell into the 4th cell
        data[row][3] = data[row][2];
        //Rotate our 2nd cell into our 3rd cell (already stored elsewhere)
        data[row][2] = data[row][1];
        //Rotate our 1st cell into our 2nd cell (already stored elsewhere)
        data[row][1] = data[row][0];
        //And now we can re-write our 4th cell into our 1st cell
        data[row][0] = tempVal;
    }

    //Rotating a column
    protected static void rotateColumnOnceDown(String[][] data, int column)
    {
        //Start by storing the bottom most value into a temp storage
        String tempVal = data[3][column];
        //Now we put our 3rd cell into the bottom
        data[3][column] = data[2][column];
        //And our 2nd cell into our 3rd
        data[2][column] = data[1][column];
        //and the 1st cell into the 2nd
        data[1][column] = data[0][column];
        //And the topmost cell is now what was the bottom
        data[0][column] = tempVal;
    }

    //Adds all the valid moves given a tree's current node
    //A move is considered valid if it is unique, AKA it hasn't been made anywhere else before
    protected static void addValidMoves(Tree currentStep)
    {
        //First thing we do is re-write our step value to the current root's data
        copyArrayOfSameSize(currentStep.getMyValue(), stepValue);
        //Then, for every column from 0-3
        for(int i = 0; i < 4; i++)
        {
            //We do this 3 times, returning stepValue to its original state at the end of the loop
            for(int j = 0; j < 3; j++)
            {
                //We rotate the column
                rotateColumnOnceDown(stepValue, i);
                //And if it doesn't exist yet in the entire data tree, it's a valid move
                //if(!myDataTree.recursiveContains(stepValue))
                {
                    //So we add it
                    currentStep.add(stepValue);
                }
            }
            //This is the 4th rotation, which returns it to its original state. Since this is the orignal state
            // we don't need to check if it's in the data tree, because we already know it is, as the root
            rotateColumnOnceDown(stepValue, i);
        }

        //Now our step value hasn't changed (rotating a column 4 times = same position as starting
        for(int i = 0; i < 4; i++)
        {
            //So we just do the same thing as before
            for(int j = 0; j < 3; j++)
            {
                //Only this time we rotate the rows, instead of columns
                rotateRowOnceRight(stepValue, i);
                //Again, we only add unique values
                //if(!myDataTree.recursiveContains(stepValue))
                {
                    //Adding it to the tree
                    currentStep.add(stepValue);
                }
            }
            //And returning our row back to the original starting point
            rotateRowOnceRight(stepValue, i);
        }
    }

    //TODO Comment this
    protected static void printSolution()
    {
        String[][] tempArray = new String[1][1];
        if(solutionPath.get(0).getMyValue()[0][0] == null)
        {
            System.out.println("No solution found.");
        }

        else
        {
            for(int i=0; i < solutionPath.size(); i++)
            {
                System.out.println("Move #" + i);
                System.out.print(solutionPath.get(i).toString());
                System.out.println("\n");
            }
            System.out.println("\n");
        }
    }


    protected static void recursivePathGenerator(Tree myData)
    {
        addValidMoves(myData);
        if(myData.numberOfRoots > 0 && depth < depthLimit)
        {
            depth++;
            for(int i = 0; i < myData.numberOfRoots; i++)
            {
                recursivePathGenerator(myData.getRoot(i));
            }
        }
        else
        {
            depth--;
        }
    }

    //TODO Comment this
    protected static void getSolutionPath(Tree myData, ArrayList<Tree> path)
    {
        if(myData.getMyValue() != null)
        {
            path.add(myData);
            if(myData.numberOfRoots > 0)
            {
                for(int i = 0; i < myData.numberOfRoots; i++)
                {
                    getSolutionPath(myData.getRoot(i), path);
                }
            }
            else if(arrayComparison(myData.getMyValue(), endValue) && path.size() <= solutionPath.size())
            {
                solutionPath = new ArrayList<>(path);
            }
            path.remove(myData);
        }
    }

    //Returns true if the two arrays passed OF THE SAME SIZE are identical
    protected static boolean arrayComparison(String[][] arrayOne, String[][] arrayTwo)
    {
        //Starting at the first row, until the end of it
        for (int i = 0; i < arrayOne.length; i++)
        {
            //For every single column in there
            for (int j = 0; j < arrayOne[i].length; j++)
            {
                //If any of the values do NOT match, ignoring case
                if(!arrayTwo[i][j].equalsIgnoreCase(arrayOne[i][j]))
                {
                    //Then they're not identical arrays
                    return false;
                }
            }
        }
        //If all the values matched (the return false was never reached) obviousdly they're the same array
        return true;
    }

    //Expanded tester of queue function & its exceptions
    private static void queueTest()
    {
        //First, we make a new Queue
        Queue myQueue = new Queue();
        //We make sure it's empty
        System.out.println(myQueue.getSize());
        //Then, we test at a random invalid index
        //It should return null, and throw an error
        System.out.println(myQueue.remove(5));
        //Then we add something
        myQueue.insert("This is an object test");
        //Make sure it's inserted properly
        System.out.println(myQueue.getSize());
        //And test the same invalid index
        //This should return our head, AKA the only thing we've added, as well as an error
        System.out.println(myQueue.remove(5));
        //Adding in some items to populate the list for our last test
        myQueue.insert("Head");
        myQueue.insert("Knees");
        myQueue.insert("Toes");
        myQueue.insert("Tail");
        //Make sure we populated the list properly
        System.out.println(myQueue.getSize());
        //Again, this is an invalid index, so it should return the closest thing which is tail
        //Along with an error
        System.out.println(myQueue.remove(5));
        //This should return "Head", no errors
        System.out.println(myQueue.remove(0));
    }

    //TODO Comment this
    private static void setStart()
    {
        startValue[0][0] = "W";
        startValue[0][1] = "W";
        startValue[0][2] = "W";
        startValue[0][3] = "B";

        startValue[1][0] = "W";
        startValue[1][1] = "W";
        startValue[1][2] = "W";
        startValue[1][3] = "B";

        startValue[2][0] = "W";
        startValue[2][1] = "B";
        startValue[2][2] = "W";
        startValue[2][3] = "B";

        startValue[3][0] = "W";
        startValue[3][1] = "W";
        startValue[3][2] = "W";
        startValue[3][3] = "W";
    }

    //P = W, Y = B

    //TODO Comment this
    private static void setEnd()
    {
        endValue[0][0] = "B";
        endValue[0][1] = "B";
        endValue[0][2] = "W";
        endValue[0][3] = "W";

        endValue[1][0] = "B";
        endValue[1][1] = "W";
        endValue[1][2] = "B";
        endValue[1][3] = "W";

        endValue[2][0] = "W";
        endValue[2][1] = "W";
        endValue[2][2] = "W";
        endValue[2][3] = "B";

        endValue[3][0] = "B";
        endValue[3][1] = "B";
        endValue[3][2] = "B";
        endValue[3][3] = "W";
    }

    //TODO Comment this
    private static void getStart()
    {
        Scanner myReader = new Scanner(System.in);
        String[] tempArray;
        for(int i = 0; i < 4; i++)
        {
            tempArray = getInput(myReader, i, true);
            startValue[i][0] = tempArray[0];
            startValue[i][1] = tempArray[1];
            startValue[i][2] = tempArray[2];
            startValue[i][3] = tempArray[3];
        }
    }

    //TODO Comment this
    private static void getEnd()
    {
        Scanner myReader = new Scanner(System.in);
        String[] tempArray;
        for(int i = 0; i < 4; i++)
        {
            tempArray = getInput(myReader, i, false);
            endValue[i][0] = tempArray[0];
            endValue[i][1] = tempArray[1];
            endValue[i][2] = tempArray[2];
            endValue[i][3] = tempArray[3];
        }
    }

    //TODO Comment this
    private static String[] getInput(Scanner myReader, int myLine, boolean isStart)
    {
        String startOrEnd;
        if(isStart == true)
        {
            startOrEnd = "start";
        }
        else
        {
            startOrEnd = "end";
        }
        System.out.println("Print, in order, line number " + (myLine + 1) + " of the "  + startOrEnd +
                "ing configuration. \n Seperate each value with a comma.");
        String[] tempVal = myReader.nextLine().split("[, ]+");
        while(tempVal.length != 4)
        {
            System.out.println("Too many or too few numbers.");
            System.out.println("Print, in order, line number " + (myLine + 1) + " of the "  + startOrEnd +
                    "ing configuration. \n Seperate each value with a comma.");
            tempVal = myReader.nextLine().split("[, ]+");
        }

        return tempVal;
    }

    //TODO Comment this
    protected static void copyArrayOfSameSize(String[][] arrayOne, String[][] arrayTwo)
    {
        for(int i = 0; i < arrayOne.length; i++)
        {
            for (int j = 0; j < arrayOne[i].length; j++)
            {
                arrayTwo[i][j] = arrayOne[i][j];
            }
        }
    }

    //TODO Comment this
    private static void print2DArray(String[][] myArray, String name)
    {
        String retVal = "";
        for(int i = 0; i < myArray.length; i++)
        {
            for(int j = 0; j < myArray[i].length; j++)
            {
                retVal += (myArray[i][j] + " ");
            }
            retVal += "\n";
        }

        System.out.println("The array " + name + " is \n" + retVal);
    }
}
