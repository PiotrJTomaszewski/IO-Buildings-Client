package pl.put.poznan;

import pl.put.poznan.DataStructure.Building;
import pl.put.poznan.DataStructure.Location;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
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

    int getCube(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/cube")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Integer> result = response.readEntity(Map.class);
        return result.get("cube");
    }

    double getLight(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/light")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Double> result = response.readEntity(Map.class);
        return result.get("light");
    }

    double getHeating(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/heating")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Double> result = response.readEntity(Map.class);
        return result.get("heating");
    }

    List<Location> getHighEnergyRooms(Building building, double heatingEnergy, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/exceed")
                .queryParam("heatingEnergy", heatingEnergy)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        return response.readEntity(new GenericType<List<Location>>(){});
    }
}
