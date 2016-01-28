import java.io.*;
import java.util.*;

public class Solver
{

    //Our data sets, all 2D arrays of strings
    protected static String[][] startValue = new String[4][4];
    protected static String[][] endValue = new String[4][4];
    protected static String[][] stepValue = new String[4][4];
    protected static Tree myDataTree;
    
    public static void main(String[] args)
    {

        //To prompt the user for the start and end
        //getStart();
        //getEnd();

        //Optional if I just want to set them manually internally
        setStart();
        setEnd();

        //We do this no matter what
        //copyArrayOfSameSize(startValue, stepValue);

        myDataTree = new Tree(startValue);

        treeTest(myDataTree);

        //Testing the Queue to make sure it throws errors properly
        //queueTest();

        //For debugging
        //print2DArray(startValue, "Starting Array");
        //print2DArray(stepValue, "Stepping Array");
    }

    private static void treeTest(Tree testTree)
    {
        System.out.println(testTree);
    }

    protected void rotateRowOnceLeft(String[][] data, int row)
    {
        String tempVal = data[row][3];
        data[row][3] = data[row][2];
        data[row][2] = data[row][1];
        data[row][1] = data[row][0];
        data[row][0] = tempVal;
    }

    protected void rotateColumnOnceUp(String[][] data, int column)
    {
        String tempVal = data[3][column];
        data[3][column] = data[2][column];
        data[2][column] = data[1][column];
        data[1][column] = data[0][column];
        data[0][column] = tempVal;
    }

    protected void addValidMoves(Tree currentStep)
    {

    }


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

    private static void setStart()
    {
        startValue[0][0] = "B";
        startValue[0][1] = "W";
        startValue[0][2] = "W";
        startValue[0][3] = "W";

        startValue[1][0] = "W";
        startValue[1][1] = "W";
        startValue[1][2] = "W";
        startValue[1][3] = "W";

        startValue[2][0] = "W";
        startValue[2][1] = "W";
        startValue[2][2] = "W";
        startValue[2][3] = "W";

        startValue[3][0] = "W";
        startValue[3][1] = "W";
        startValue[3][2] = "W";
        startValue[3][3] = "W";
    }

    private static void setEnd()
    {
        endValue[0][0] = "W";
        endValue[0][1] = "W";
        endValue[0][2] = "W";
        endValue[0][3] = "B";

        endValue[1][0] = "W";
        endValue[1][1] = "W";
        endValue[1][2] = "W";
        endValue[1][3] = "W";

        endValue[2][0] = "W";
        endValue[2][1] = "W";
        endValue[2][2] = "W";
        endValue[2][3] = "W";

        endValue[3][0] = "W";
        endValue[3][1] = "W";
        endValue[3][2] = "W";
        endValue[3][3] = "W";
    }

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
