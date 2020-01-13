package pl.put.poznan;

import pl.put.poznan.DataStructure.Building;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class Connection {
    Client client;
    WebTarget webTarget;

    Connection(String hostname, int port) {
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target("http://" + hostname + ":" + port);
    }

    int getArea(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Integer> result = response.readEntity(Map.class);
        return result.get("area");
    }
}
