package Model;

public class Administrator extends Account {

    /**
     * Default constructor for objects of class Administrator
     */
    public Administrator() {
        super("", "", "Administrator");
    }

    /**
     * Constructor for objects of class Administrator
     *
     * @param accountEmail       The mailbox of the new Administrator
     * @param accountPassword    The password of the new Administrator
     * @param accountType        The type of the new Administrator
     */
    public Administrator(String accountEmail, String accountPassword, String accountType) {
        super(accountEmail, accountPassword, accountType);
    }
}
