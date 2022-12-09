package edu.romoshi;

import java.io.FileWriter;
import java.io.IOException;
public class CreatingFileAcc {
        public static void Creating(String string) {
            try(FileWriter writer = new FileWriter("DataBase.txt", true)) {
                writer.write(string);
                writer.flush();
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
}
