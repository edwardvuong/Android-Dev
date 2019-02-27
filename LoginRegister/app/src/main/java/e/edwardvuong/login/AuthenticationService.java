package e.edwardvuong.login;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class AuthenticationService extends LoginActivity{

    Context loginContext;
    HashMap<String, String> database;
    String fileLocation;

    public AuthenticationService(Context c){
        loginContext = c;
        fileLocation = new File(loginContext.getFilesDir().getAbsolutePath()) + "/database.txt";
        database = loadDatabase();
    }

    /**
     * Adds a user to database.txt
     * @param username Username
     * @param password Password
     */
    public void registerUser(String username, String password) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation, true));
            out.write(username + ":" + password + "\n");
            out.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Creates a file to store user data in a usernameInput.txt
     * @param usernameInput Username
     * @param firstNameInput First name
     * @param lastNameInput Last name
     * @param ageInput Age
     * @param emailInput Email
     */
    public void userData(String usernameInput, String firstNameInput, String lastNameInput, String ageInput, String emailInput){
        try {
            String userDataLocation = new File(loginContext.getFilesDir().getAbsolutePath()) + "/" + usernameInput + ".txt";
            BufferedWriter out = new BufferedWriter(new FileWriter(userDataLocation, true));
            out.write(firstNameInput + ":" + lastNameInput + ageInput + ":" + emailInput + "\n");
            out.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Verify that username and password are in the database
     * @param username Username
     * @param password Password
     * @return Valid login
     */
    public boolean authenticateLogin(String username, String password){
        if(!database.containsKey(username))
            return false;
        else if (!database.get(username).equals(password))
            return false;
        return true;
    }

    /**
     * Checks to see if username is avaliable
     * Ensures that username does not overwrite database.txt
     * @param username Username
     * @return Username availability
     */
    public boolean isUsernameAvaliable(String username){
        if(database.containsKey(username) || username.equals("database"))
            return false;
        return true;
    }

    /**
     * Reads database.txt file loading the usernames and logins to a database hash map.
     * @return
     */
    public HashMap<String, String> loadDatabase(){
        HashMap<String, String> database = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            String line;
            while((line = br.readLine()) != null) {
                String loginData[] = line.split(":");
                database.put(loginData[0], loginData[1]);
            }
            br.close();
        }catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
        return database;
    }

}
