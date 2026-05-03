public abstract class User {
    protected int id;
    protected String name;
    protected String username;
    protected String password;
    
    public User(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    
    public abstract void displayInfo();
}