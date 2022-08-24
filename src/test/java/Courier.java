
public class Courier {
    String login;
    String password;
    String name;

    public Courier(){
    }

    public Courier(String login, String password, String name){
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public Courier setLogin(String login) {
        this.login = login;

        return this;
    }

    public String getPassword() {
        return password;
    }

    public Courier setPassword(String password) {
        this.password = password;

        return this;
    }

    public String getName() {
        return name;
    }

    public Courier setName(String name) {
        this.name = name;

        return this;
    }
}
