package model;

import java.io.*;

public class FileInput {

    private final String path;

    public FileInput(String path)
    {
        this.path = path;
    }

    public String read()
    {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public byte[] readBytes() {
        byte[] bytes = new byte[0];
        try(DataInputStream ds = new DataInputStream(new FileInputStream(path))) {
            bytes = ds.readAllBytes();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return bytes;
    }

}