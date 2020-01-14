package pl.put.poznan;

import pl.put.poznan.DataStructure.Building;
import pl.put.poznan.DataStructure.Location;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for connecting to server
 * and querying REST API
 */
public class Connection {
    // REST client object
    private Client client;
    // Object with URI to REST API
    private WebTarget webTarget;

    /**
     * Creates connection to hostname at given port
     * @param hostname Address of the server
     * @param port Port of the REST API
     */
    public Connection(String hostname, int port) {
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target("http://" + hostname + ":" + port);
    }

    /**
     * Request area from REST API
     * @param building Building with given location
     * @param locationId Id of location for with to compute area
     * @return Area of location with given id
     */
    public int getArea(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/area")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Integer> result = response.readEntity(Map.class);
        return result.get("area");
    }

    /**
     * Request volume of location with given id from REST API
     * @param building Building with given location
     * @param locationId Id of location
     * @return Volume of location
     */
    public int getCube(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/cube")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Integer> result = response.readEntity(Map.class);
        return result.get("cube");
    }

    /**
     * Request light per area for location with given id
     * @param building Building with given location
     * @param locationId Id of location
     * @return Light per area of location
     */
    public double getLight(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/light")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Double> result = response.readEntity(Map.class);
        return result.get("light");
    }

    /**
     * Request heating per volume for location with given id
     * @param building Building with given location
     * @param locationId Id of location
     * @return Heating per volume
     */
    public double getHeating(Building building, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/heating")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        Map<String, Double> result = response.readEntity(Map.class);
        return result.get("heating");
    }

    /**
     * Request list of rooms that exceed given heating per volume
     * @param building Building with given location
     * @param heatingEnergy Threshold of heating energy per volume
     * @param locationId Id of location
     * @return List of rooms exceeding given heating energy consumption per volume
     */
    public List<Location> getHighEnergyRooms(Building building, double heatingEnergy, int locationId) {
        Response response = webTarget
                .path("locations/" + locationId + "/exceed")
                .queryParam("heatingEnergy", heatingEnergy)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(building, MediaType.APPLICATION_JSON));
        return response.readEntity(new GenericType<List<Location>>(){});
    }
}
