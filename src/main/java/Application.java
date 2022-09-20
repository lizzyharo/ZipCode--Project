import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.app.zipcode.data.dto.ZipCodeDTO;
import com.kenzie.app.zipcode.format.AddressFormatter;
import com.kenzie.app.zipcode.http.HTTPConnector;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        try {
            // declare variables
            String BASE_URL = "https://api.zippopotam.us/us/";
            String urlPath;
            Scanner scanner = new Scanner(System.in);
            String recipientName;
            String streetAddress;
            String city;
            String state;
            String zipCode;


            // Use scanner - read user input
            System.out.println("Enter recipient name: ");
            recipientName = scanner.nextLine();

            System.out.println("Enter street address: ");
            streetAddress = scanner.nextLine();

            System.out.println("Enter city: ");
            city = scanner.nextLine();

            System.out.println("Enter State: (two letter abbreviation)");
            state = scanner.nextLine();

            //System.out.println("City: " + city);


            // Clean user input
            // replace spaces with %20
            city = city.replaceAll(" ", "%20");
            //System.out.println("City: " + city);


            // Create URL
            // append the BASE_URL to the path - state/city
            urlPath = BASE_URL + state + "/" + city;
            System.out.println(urlPath);

            // Call HTTP GET
            String httpResponse = HTTPConnector.makeGETRequest(urlPath);
            if (httpResponse.equals("\\{\\}")) {   //Checking for empty url
                System.out.println("No valid zip code found");
            } else {
                // Use ObjectMapper on the DTO
                //1. Instantiate objectMapper
                //2. Declare DTO object
                //3. read data - read value
                ObjectMapper objectMapper = new ObjectMapper();
                ZipCodeDTO zipObj;
                zipObj = objectMapper.readValue(httpResponse, ZipCodeDTO.class);
                //System.out.println("First zip: " + zipObj.getPlaces().get(0).getPostCode());

                // Loop and ask user for the zipcode if more than one option
                if (zipObj.getPlaces().size() == 1) {
                    zipCode = zipObj.getPlaces().get(0).getPostCode();
                } else {
                    // Printing out the list of zipcodes
                    System.out.println("Choose a zip code from the following list: ");
                    for (int i = 0; i < zipObj.getPlaces().size(); i++) {
                        System.out.println(i + ")" + zipObj.getPlaces().get(i).getPostCode());
                    }
                    // Read in choice
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    // set zipCode to Choice
                    zipCode = zipObj.getPlaces().get(choice).getPostCode();
                }
                System.out.println("User chose zipcode: " + zipCode);

                //Address info
                // Need to call to initialize the map that we want to use
                AddressFormatter.initializeAddressMap();
                System.out.println("Name: " + AddressFormatter.formatAddress(recipientName));
                System.out.println("Street address: " + AddressFormatter.formatStreetAddress(streetAddress));
                System.out.println("City: " + AddressFormatter.formatAddress(city));
                System.out.println("State: " + AddressFormatter.formatAddress(state));
                System.out.println("Zip code: " + zipCode);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




    public static void main_backup(String[] args) {
        try {
            //API https://api.zippopotam.us/us/ca/LOS%20ANGELES
            //https://jamboard.google.com/d/1n9-68L50Ba8Yn1EMtG_Ri1jVhkridA7kb9uo7HPc7N4/viewer?f=0

            //declare variables
            final String TEST_URL = "https://api.zippopotam.us/us/ca/LOS%20ANGELES";
            final String TEST_FAIL_URL = "https://api.zippopotam.us/us/ca/LOS%20%20ANGELES";
            String httpResponseStr;

            // Read in user input - Scanner

            //Format user input using AddressFormatter
            AddressFormatter.initializeAddressMap();
            AddressFormatter.replaceAbbreviation("123 Main St.");

            //Connect to zippopotam.us and get zipcode
            httpResponseStr = HTTPConnector.makeGETRequest(TEST_URL);
            System.out.println(httpResponseStr);

            //ObjectMapper
            //1. Instantiate objectMapper
            //2. Declare DTO object
            //3. read data - read value
            ObjectMapper objectMapper = new ObjectMapper();
            ZipCodeDTO zipObj;
            zipObj = objectMapper.readValue(httpResponseStr, ZipCodeDTO.class);


            //Print out place name, zip code and state
            System.out.println("City: " + zipObj.getPlaces().get(0).getPlace_name());
            System.out.println("ZipCode: " + zipObj.getPlaces().get(0).getPostCode());
            System.out.println("State: " + zipObj.getState());

            //Loop and print the list of zipCodes
            //Checking if more than 1 zipCode
            if (zipObj.getPlaces().size() > 1) {
                for (int i = 0; i < zipObj.getPlaces().size(); i++) {
                    System.out.println("City: " + zipObj.getPlaces().get(i).getPlace_name());
                    System.out.println("ZipCode: " + zipObj.getPlaces().get(i).getPostCode());
                    System.out.println("State: " + zipObj.getState());
                    System.out.println();
                }
            }


//        System.out.println("Failing URL example: ");
//        httpResponseStr = HTTPConnector.makeGETRequest(TEST_FAIL_URL);
//        System.out.println(httpResponseStr);

        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }

    }
}
