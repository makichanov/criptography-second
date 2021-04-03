package model;

import java.io.*;

public class FileOutput {

    private final String path;

    public FileOutput(String path) {
        this.path = path;
    }

    public void write(String data) {
        try (Writer writer = new FileWriter(path)) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] data) {
        try(DataOutputStream ds = new DataOutputStream(new FileOutputStream(path))) {
            ds.write(data);
            ds.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}