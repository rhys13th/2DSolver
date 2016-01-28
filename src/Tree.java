//Java.util.list is used for internal storage of the roots
import java.util.List;

/**  CLASS: Tree
 *
 *   DESC:  This is a 2D String Array tree class, designed to contain any number of roots
 *          It has expanded functionality beyond the basic trees that are included, and is missing some uneeded
 *          functions.
 *
 *   FUNCTIONS:
 *          public Tree(args)
 *              This is the constructor, and will create and assign the initial value of the tree to the value
 *              given
 *          public void add(args)
 *              An adding function that does not validate data or pre-existence in the tree and its roots
 *          public boolean contains(args)
 *              Compares and returns if the args are identical to the data of this tree
 *          public boolean recursiveContains
 *              Similar to contains, checks all roots as well. Meaning if Node A has 3 roots (B, C, D), and B has
 *              3 roots in turn (E, F, G), this function would search in the order of
 *                  A -> B -> E -> F -> G -> C -> D
 *              due to the nature of the stack. However, if at any point in that order, B or E contained a matching
 *              value, it would exit immediately returning true. Note that this means you are going through every node
 *              in the entire tree in the worst case scenario
 *          protected void copyArrayOfSameSize(args)
 *              Takes two 2D String Arrays, and copies data from the first array passed into the second.
 *              It assumes they are of the same size.
 *          protected boolean arrayComparison(args)
 *              Takes two 2D String Arrays, and checks if the values in every cell are identical. Ignores case,
 *              assumes both arrays are of the same size.
 */

public class Tree
{
    //This is our data
    protected String[][] myValue = new String[4][4];
    //And here are our roots. It's hypothetically infinite, but practically less (16)
    protected List<Tree> myRoots;
    //To keep track of how many roots we have. This is the same as myRoots.size(), but enumerated as a
    //seperate value for my sake
    protected int numberOfRoots = 0;

    //Constructor, initializes the data of myValue
    public Tree(String[][] initVal)
    {
        copyArrayOfSameSize(initVal, myValue);
    }

    //Adding is pretty simple
    public void add(String[][] addedData)
    {
        //First, we make a new tree to hold our added root
        Tree addedTree = new Tree(addedData);
        //Then we add it to our roots
        myRoots.add(addedTree);
        //And we incrememnt the number of roots
        numberOfRoots++;
    }

    //Our basic contains. Note, this is only comparing our data, not the roots
    public boolean contains(String[][] comparedArray)
    {
        return arrayComparison(comparedArray, myValue);
    }

    //This is where it gets a little tougher for me. I wanted to have it be recursive, but limited
    //Please note, this should *never* be used practically. In Solver, there will be a queue with all the current
    //states that have been created. However, this exists so there is a technically valid solution
    public boolean recursiveContains(String[][] comparedArray)
    {
        //So we start with our exit clause: If the data this Tree holds is the same, we return true
        if(arrayComparison(comparedArray, myValue) == true)
        {
            return true;
        }

        //Then we want to check all its roots. Because of the way the function calls itself, the exit clause
        //will truncate the depth to the first one found
        for(int i = 0; i < numberOfRoots; i++)
        {
            //But this would go through for *all* roots and their roots. Meaning if Node A has 3 roots (B, C, D)
            //and B has 3 roots in turn (E, F, G), this would search in the order of
            // A -> B -> E -> F -> G -> C -> D
            //Due to the nature of the stack. However, if at any point in that order, B or E contained a matching
            //value, it would exit immediately returning true. Note that this means you are going through every node
            //in the entire tree in the worst case scenario
           if(myRoots.get(i).recursiveContains(comparedArray) == true)
           {
               return true;
           }
        }

        //Well, we haven't found it in ourselves or our children, so we return false
        return false;
    }

    //Copies all data from the first array argument given to the second one
    protected void copyArrayOfSameSize(String[][] arrayOne, String[][] arrayTwo)
    {
        for (int i = 0; i < arrayOne.length; i++)
        {
            for (int j = 0; j < arrayOne[i].length; j++)
            {
                arrayTwo[i][j] = arrayOne[i][j];
            }
        }
    }

    //Returns true if the two arrays passed OF THE SAME SIZE are identical
    protected boolean arrayComparison(String[][] arrayOne, String[][] arrayTwo)
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

    public String toString()
    {
        String retVal = "";
        //Starting at the first row, until the end of it
        for (int i = 0; i < myValue.length; i++)
        {
            //For every single column in there
            for (int j = 0; j < myValue[i].length; j++)
            {
                retVal += (myValue[i][j] + ", ");
            }
            retVal += "\n";
        }

        return retVal;
    }
}
