package ua.poltava.energy.client.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ua.poltava.energy.client.model.Zakhid;
import ua.poltava.energy.client.model.ZapysSpozhyvannia;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Zakhid> getZakhody() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/zakhid"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Помилка сервера: " + response.statusCode());
        }

        Type listType = new TypeToken<List<Zakhid>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public void changeState(int id, String newState) throws Exception {
        String json = "{\"id\":" + id + ", \"stan\":\"" + newState + "\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/zakhid"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new Exception("Помилка сервера: " + response.body());
        }
    }

    public void importRecords(List<ZapysSpozhyvannia> records) throws Exception {
        String json = gson.toJson(records);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/import"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Помилка імпорту: " + response.body());
        }
    }

    public void createZakhid(Zakhid z) throws Exception {
        String json = gson.toJson(z);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/zakhid"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new Exception("Помилка створення: " + response.body());
        }
    }
}