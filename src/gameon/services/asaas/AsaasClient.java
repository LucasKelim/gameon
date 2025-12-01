package gameon.services.asaas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gameon.utils.Config;

public class AsaasClient {

    private final String token = Config.get("ASAAS_SANDBOX_TOKEN");
    private final String baseUrl = Config.get("ASAAS_SANDBOX_URL");
    private final String version = Config.get("ASAAS_API_VERSION");

    private static final ObjectMapper mapper = new ObjectMapper();

    public Map<String, Object> get(String endpoint) {
        return request("GET", endpoint, null);
    }

    public Map<String, Object> post(String endpoint, Map<String, Object> data) {
        return request("POST", endpoint, data);
    }

    public Map<String, Object> put(String endpoint, Map<String, Object> data) {
        return request("PUT", endpoint, data);
    }

    public Map<String, Object> delete(String endpoint) {
        return request("DELETE", endpoint, null);
    }

    public Map<String, Object> request(String method, String endpoint, Map<String, Object> data) {
        try {
            URI uri = URI.create(baseUrl + "/" + version + "/" + endpoint);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();

            conn.setRequestMethod(method);
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("access_token", token);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoInput(true);

            if (data != null) {
                conn.setDoOutput(true);
                String json = mapper.writeValueAsString(data);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                }
            }

            int responseCode = conn.getResponseCode();
            boolean success = responseCode >= 200 && responseCode < 300;

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            success ? conn.getInputStream() : conn.getErrorStream(),
                            StandardCharsets.UTF_8
                    )
            )) {
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                Map<String, Object> responseMap = mapper.readValue(
                        sb.toString(),
                        new TypeReference<Map<String, Object>>() {}
                );

                return responseMap;
            }

        } catch (Exception e) {
            e.printStackTrace();

            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return error;
        }
    }
}
