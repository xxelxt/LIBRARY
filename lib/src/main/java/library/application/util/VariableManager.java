package library.application.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;

public class VariableManager {

    private static final String FILE_NAME = "date.ser";
    private static Date lastModifiedDate = null;

    public static Date getLastModifiedDate() {
		return lastModifiedDate;
	}

    /* Only save can set it */
	public static void saveDate(Date date) {
        lastModifiedDate = date;
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(lastModifiedDate);

            objectOut.close();
            fileOut.close();
            System.out.println("Date saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDate() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
	            FileInputStream fileIn = new FileInputStream(FILE_NAME);
	            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	            lastModifiedDate = (Date) objectIn.readObject();
	            objectIn.close();
	            fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
