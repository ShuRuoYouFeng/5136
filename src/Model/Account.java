package Model;

public class Account {

    private String accountEmail;
    private String accountPassword;
    private String accountType;

    /**
     * Default constructor for objects of class Account
     *
     */
    public Account() {
        accountEmail = "";
        accountPassword = "";
        accountType = "";
    }

    /**
     * Constructor for objects of class Account
     *
     * @param accountEmail     The mailbox of the new account
     * @param accountPassword  The password of the new account
     * @param accountType      The type of the new account
     */
    public Account(String accountEmail, String accountPassword, String accountType) {
        this.accountEmail = accountEmail;
        this.accountPassword = accountPassword;
        this.accountType = accountType;
    }

    /**
     * This method can get the email of this account
     *
     * @return accountEmail    the email of this account
     */
    public String getAccountEmail() {
        return accountEmail;
    }

    /**
     * This method can set the email of this account
     *
     * @param accountEmail    the new email of this account
     */
    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    /**
     * This method can get the password of this account
     *
     * @return accountPassword   the password of this account
     */
    public String getAccountPassword() {
        return accountPassword;
    }

    /**
     * This method can set the password of this account
     *
     * @param accountPassword   the new password of this account
     */
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    /**
     * This method can get the type of this account
     *
     * @return accountType  the type of this account
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * This method can set the type of this account
     *
     * @param accountType  the new type of this account
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
