import java.util.List;

public class Tree {
    protected String[][] myValue;
    protected List<Tree> myRoots;
    protected int numberOfRoots = 0;

    public Tree(String[][] initVal)
    {
        copyArrayOfSameSize(initVal, myValue);
    }

    public void add(String[][] addedData)
    {
        Tree addedTree = new Tree(addedData);
        myRoots.add(addedTree);
        numberOfRoots++;
    }



    //Copies all data from the first array argument given to the second one
    protected void copyArrayOfSameSize(String[][] arrayOne, String[][] arrayTwo)
    {
        for (int i = 0; i < arrayOne.length; i++) {
            for (int j = 0; j < arrayOne[i].length; j++) {
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
                if(!arrayTwo[i][j].equalsIgnoreCase(arrayOne[i][j]))
                {
                    return false;
                }
            }
        }
        return true;
    }
}
