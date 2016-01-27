public class IndexNotFoundException extends RuntimeException
{
    public IndexNotFoundException()
    {
        super("Error: Index not found. Returning the closest possible match");
    }

    public IndexNotFoundException(String message)
    {
        super(message);
    }
}
