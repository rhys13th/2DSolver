public class Queue
{
    //The head of our data structure
    private Node head = null;

    //How many things we have in the list
    private int entitiesInList = 0;

    //How we store all our data
    protected class Node
    {
        Object data = null;
        Node next = null;

        public Node(Object d, Node n)
        {
            data = d;
            next = n;
        }
    }

    //Basic insertion at an index
    public void insert(Object next, int index)
    {
        //We first make a node to hold the object we're inserting
        Node insertedNode;
        //if the list is empty, we make the new node the head node
        if (head == null)
        {
            //We make the next one null
            insertedNode = new Node(next, null);
            //And insert our node as the head
            head = insertedNode;
            //Incrememnt how many things are in the list
            entitiesInList++;
        }
        //If the head's the only thing in the list
        else if (head.next == null)
        {
            //And we wanted to put it in at the head (index == 0 or less)
            if (index < 1)
            {
                //Same thing as before
                insertedNode = new Node(next, head);
                addHead(insertedNode);
                entitiesInList++;
            }
            //Otherwise, it points to null, and we add it to our tail
            else
            {
                insertedNode = new Node(next, null);
                addTail(insertedNode);
            }
        }
        //Otherwise, we try and find the node at the index
        else
        {
            //Doing this once so we don't repeat it over and over again
            Node fetchedNode = getNode(index);
            //If the index is at or beyond the end of our list
            if (fetchedNode.next == null)
            {
                //We add it on the very end, pointing to null
                insertedNode = new Node(next, null);
                addTail(insertedNode);
            }
            //Otherwise, we insert it properly
            else
            {
                //Point to the Node at the index of I's next
                //AKA our fetched node's next
                insertedNode = new Node(next, fetchedNode.next);
                //and have the Node at the position we're inserting at point to us
                fetchedNode.next = insertedNode;
            }
        }
    }

    //Removal's got the same logic as insertion
    public Object remove(int index) throws IndexNotFoundException
    {
        //First, we make a node for what we're removing
        Node returnNode = null;

        //A boolean value so we can throw it at the end
        boolean IndexException = false;

        //If the list is empty, return null
        if (head == null)
        {
            if(index > 0)
            {
                IndexException = true;
            }
        }
        //If the head is the only thing in the list, the only thing we can return is the head
        else if (head.next == null)
        {
            returnNode = head;
            head = null;
            if(index > 1)
            {
                IndexException = true;
            }
        }
        else
        {
            returnNode = getNode(index);

            if (index == 0)
            {
                head = returnNode.next;
            }
            else if(returnNode.next != null)
            {
                getNode(index - 1).next = returnNode.next;
            }
            else
            {
                getNode(index - 1).next = null;
            }
        }

        try
        {
            if(IndexException == true)
            {
                throw new IndexNotFoundException();
            }
        }
        catch(IndexNotFoundException e)
        {
            e.printStackTrace();
        }

        if(returnNode == null)
        {
            return null;
        }

        return returnNode.data;
    }

    public int size()
    {
        Node start = head;
        int nodesCrossed = 0;
        while (start != null)
        {
            start = start.next;
            nodesCrossed++;
        }

        return nodesCrossed;
    }

    public boolean isEmpty()
    {
        return (head == null);
    }

    public int getIndexOf(Object myObj)
    {
        Node currentNode = head;
        int index = -1;
        int loopCounter = 0;
        while (currentNode != null)
        {
            if (currentNode.data == myObj)
            {
                index = loopCounter;
            }
            else
            {
                currentNode = currentNode.next;
                loopCounter++;
            }
        }
        return index;
    }

    public String toString()
    {
        String returnString = "";
        for(int i = 0; i < size(); i++)
        {
            returnString += (", " + getNode(i).data.toString());
        }

        return returnString;
    }

    protected void addTail(Node myNode)
    {
        if(head != null)
        {
            Node tailNode = getNode(-1);
            tailNode.next = myNode;
        }
        else
        {
            head = myNode;
        }
    }

    protected void addHead(Node myNode)
    {
        myNode.next = head;
        head = myNode;
    }

    protected Node getNode(int index)
    {
        Node start;
        if(head != null)
        {
            start = head;
            if (index == -1)
            {
                while (start.next != null)
                {
                    start = start.next;
                }
            }
            else
            {
                int nodesCrossed = 0;
                while (start.next != null && nodesCrossed < index)
                {
                    start = start.next;
                    nodesCrossed++;
                }
            }
        }
        else
        {
            start = null;
        }

        return start;
    }
    public void Enqueue(Object next)
    {
        Node myNode = new Node(next, null);
        addTail(myNode);
    }

    public Object Dequeue()
    {
        return remove(0);
    }

    public void insert(Object next)
    {
        Enqueue(next);
    }
}