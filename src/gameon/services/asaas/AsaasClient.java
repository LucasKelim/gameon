package gameon.services.asaas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gameon.utils.Config;

public class AsaasClient {
	
    private final String token = Config.get("ASAAS_SANDBOX_TOKEN");
    private final String url = Config.get("ASAAS_SANDBOX_URL");
    
    private final ObjectMapper mapper = new ObjectMapper();

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
        	URI uri = URI.create(url + endpoint);
        	URL apiUrl = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();

            conn.setRequestMethod(method);
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("access_token", token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);

            if (data != null) {
                conn.setDoOutput(true);
                String json = mapper.writeValueAsString(data);

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                os.close();
            }

            int responseCode = conn.getResponseCode();
            BufferedReader reader;

            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            conn.disconnect();

            return mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
