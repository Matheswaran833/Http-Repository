package com.madhu.JavaImage_client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUploaderClient {

	public static void main(String[] args) throws Exception   {
		HttpClient client=HttpClient.newHttpClient();
		 
		Path imagePath= Paths.get("D:/images/Sample.jpg");
		String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
        String mimeType = "image/jpeg";
        
        String filename = imagePath.getFileName().toString();
        byte[] fileBytes = Files.readAllBytes(imagePath);

        String pre = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"file\"; filename=\"" + filename + "\"\r\n"
                + "Content-Type: " + mimeType + "\r\n\r\n";

        String post = "\r\n--" + boundary + "--";

        byte[] body = concatenate(pre.getBytes(), fileBytes, post.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/upload"))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    private static byte[] concatenate(byte[]... arrays) {
        int length = 0;
        for (byte[] arr : arrays) length += arr.length;

        byte[] result = new byte[length];
        int pos = 0;
        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, result, pos, arr.length);
            pos += arr.length;
        }
        return result;
	}
}
