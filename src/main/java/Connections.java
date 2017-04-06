import org.apache.commons.net.util.SubnetUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by sergey on 03.04.17
 */
public class Connections {

    public static void main(String[] args) throws IOException {

        //Please enter ipMask in format: "xxx.xxx.xxx.xxx/yyy"
        String ipMask = null;
        Integer startPort = 0;
        Integer endPort = 65535;

        if (args.length == 1) {
            try {
                ipMask = args[0];
            } catch (Exception e) {
                System.out.println("please enter ipMask in format: \"xxx.xxx.xxx.xxx/yyy\"");
                System.exit(-1);
            }
        } else {
            System.out.println("please enter ipMask in format: \"xxx.xxx.xxx.xxx/yyy\"");
            System.exit(-1);
        }

        JSONObject root = new JSONObject();
        JSONArray openPorts = new JSONArray();

        SubnetUtils utils = new SubnetUtils(ipMask);
        String[] allIps = utils.getInfo().getAllAddresses();

        for (String ip : allIps ) {
            System.out.println(ip);
            for (int port = startPort; port <= endPort; port++) {
                try {
                    new Socket(ip, port);
                    JSONObject openIPPort = new JSONObject();
                    openIPPort.put("IP", ip);
                    openIPPort.put("port", port);
                    openPorts.add(openIPPort);
                    System.out.println("\nPort is Open:" + " IP: " + ip + " port: " + port);
                } catch (Exception e) {
                    System.out.print(".");
                }
            }
        }

        root.put("Open Ports", openPorts);
        FileWriter file = new FileWriter("Connections.json");
        file.write(root.toJSONString());
        file.close();
        System.out.println("\nThe result was written to the file \"Connections.json\".");
    }
}