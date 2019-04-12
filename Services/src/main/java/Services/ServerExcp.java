package Services;


public class ServerExcp extends Exception{
    public ServerExcp() {
    }

    public ServerExcp(String message) {
        super(message);
    }

    public ServerExcp(String message, Throwable cause) {
        super(message, cause);
    }
}
