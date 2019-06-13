package application;

public class Date {

    private int id;
    private String login;
    private String loginHash;
    private String password;
    private String passwordHash;
    private String fileName;
    private String filePath;
    private String city;
    private String name;
    private String note;

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getLoginHash() {
        return loginHash;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getNote() {
        return note;
    }

    public Date(int id, String login, String loginHash, String password, String passwordHash, String fileName, String filePath, String city, String name, String note) {
        super();
        this.id = id;
        this.login = login;
        this.loginHash = loginHash;
        this.password = password;
        this.passwordHash = passwordHash;
        this.fileName = fileName;
        this.filePath = filePath;
        this.city = city;
        this.name = name;
        this.note = note;
    }

}
