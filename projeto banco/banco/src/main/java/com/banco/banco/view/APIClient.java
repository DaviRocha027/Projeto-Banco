package com.banco.banco.view;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient {
    public static String get(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        System.out.println("Resposta da API: " + response.toString());

        
        return response.toString();
    }

    public static void post(String url, String json) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();

        // Tratar 201 como sucesso
        if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
            // Sucesso: não lance exceção
            return;
        } else {
            // Caso contrário, trate como erro
            throw new Exception("Erro na requisição: HTTP " + responseCode);
        }
    }
}