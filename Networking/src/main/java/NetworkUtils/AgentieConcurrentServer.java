package NetworkUtils;




import ObjectProtocol.AgentieClientObjectWorker;
import Services.IAgentieServer;

import java.net.Socket;

public class AgentieConcurrentServer extends AbstractConcurrentServer {
    private IAgentieServer iAgentieServer;

    public AgentieConcurrentServer(int port, IAgentieServer iAgentieServer) {
        super(port);
        this.iAgentieServer = iAgentieServer;
        System.out.println("AgentieObjectConcurrentServer");
    }



    @Override
    protected Thread createWorker(Socket client) {

        AgentieClientObjectWorker agentieClientObjectWorker=new AgentieClientObjectWorker(iAgentieServer,client);
        Thread thread=new Thread(agentieClientObjectWorker);
        return thread;
    }
}
