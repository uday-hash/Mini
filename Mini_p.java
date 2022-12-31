import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Mini_p {

    private static void menu() {

        System.out.println("Online Cab Booking System");
        System.out.println("--------------------------");
        System.out.println("1. Drivers' registration");
        System.out.println("2. Riders' registration");
        System.out.println("3. Book a ride");
        System.out.println("4. End a ride (by drivers only)");
        System.out.println("5. Exit");

    }

    public static void main(String[] args) {

        do {
            menu();
            System.out.print("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            String choice = sc.hasNext() ? sc.next() : "5";
            switch (choice) {
                case "1":
                    driverRegistration();
                    break;
                case "2":
                    riderRegistration();
                    break;
                case "3":
                    bookRide();
                    break;
                case "4":
                    endRide();
                    break;
                case "5":
                    System.out.println("Thank you for using our service.");
                    System.exit(0);

                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (true);

    }

    public static void driverRegistration() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Driver registration screen:");
        System.out.print("Enter your driver ID: ");
        String driverID = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        System.out.print("Enter your name: ");
        String name = sc.next();
        System.out.print("Enter your city: ");
        String city = sc.next();
        System.out.print("Enter your car model: ");
        String carModel = sc.next();
        System.out.print("Enter your car number: ");
        String carNumber = sc.next();
        System.out.println("The availability of a driver is set as Y.");
        String available = "Y";
        String driverInfo = driverID + " " + password + " " + name + " " + city + " " + carModel + " " + carNumber + " "
                + available;
        writeToFile("Drivers_Record.txt", driverInfo);
        System.out.println("Driver registration successful.");

    }

    private static String lastline = null;

    public static void writeToFile(String filename, String driverinfo) {
        // should check 'previous line written'. If the same, return;, doing nothing. If
        // not, write it, and change the 'previous line written' field.
        if (lastline != null && lastline.equals(driverinfo)) {
            System.out.println("Same driver info");
            return;
        } else {
            lastline = driverinfo;
        }
        // write to file
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            out.println(driverinfo);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void riderRegistration() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your rider ID: ");
        String riderID = sc.next();
        System.out.print("Enter your password: ");
        String password = sc.next();
        System.out.print("Enter your name: ");
        String name = sc.next();
        System.out.print("Enter your city: ");
        String city = sc.next();
        String riderInfo = riderID + " " + password + " " + name + " " + city;
        writeToFile("Riders_Record.txt", riderInfo);
        System.out.println("Rider registration successful.");
    }

    public static void bookRide() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ride booking screen:");
        System.out.print("Enter your rider ID: ");
        String riderID = sc.next();
        System.out.print("Enter your password: ");
        String password = sc.next();
        String riderInfo = riderID + " " + password;
        String riderInfoInFile = readFromFile("Riders_Record.txt", riderInfo);
        if (riderInfoInFile == null) {
            System.out.println("Wrong credentials. Please try again.");
            return;
        }
        String driverInfo = readFromFiled("Drivers_Record.txt", "Y", riderInfoInFile);
        if (driverInfo == null) {
            System.out.println("Sorry, no driver is available at the moment. Please try again later.");
            return;
        }
        String[] driverInfoArr = driverInfo.split(" ");
        String driverID = driverInfoArr[0];
        String pass = driverInfoArr[1];
        String driverName = driverInfoArr[2];
        String city = driverInfoArr[3];
        String carModel = driverInfoArr[4];
        String carNumber = driverInfoArr[5];
        System.out.println("Your driver's name is " + driverName + ", Car: " + carModel + " " + carNumber);
        System.out.println("A trip is booked successfully. Have a nice trip!");
        String newDriverInfo = driverID + " " + pass + " " + driverName + " " + city + " " + carModel + " " + carNumber
                + " " + "N";
        try {
            updateFile("Drivers_Record.txt", driverInfo, newDriverInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String string, String riderInfo) {
        String driverInfo = null;
        try {
            FileReader fr = new FileReader(string);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                if (line.contains(riderInfo)) {
                    driverInfo = line;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return driverInfo;
    }

    public static String readFromFiled(String string, String available, String riderInfo) {
        String driverInfo = null;
        try {
            FileReader fr = new FileReader(string);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                if (line.contains(available)) {
                    String[] riderInfoArr = riderInfo.split(" ");
                    String riderCity = riderInfoArr[3];
                    String[] driverInfoArr = line.split(" ");
                    String driverCity = driverInfoArr[3];
                    if (riderCity.equals(driverCity)) {
                        driverInfo = line;
                    }
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return driverInfo;
    }

    public static void updateFile(String string, String driverInfo, String newDriverInfo)
            throws IOException {
        // read file
        FileReader fr = new FileReader(string);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        String file = "";
        while (line != null) {
            if (line.equals(driverInfo)) {
                file += newDriverInfo + '\n';
            } else {
                file += line + '\n';
            }
            line = br.readLine();
        }
        br.close();
        // write to file
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(string, false)));
        out.println(file);
        out.close();
    }

    public static void endRide() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ride ending screen:");
        System.out.print("Enter your driver ID: ");
        String driverID = sc.next();
        System.out.print("Enter your password: ");
        String password = sc.next();
        String driverInfo = driverID + " " + password;
        String driverInfoInFile = readFromFile("Drivers_Record.txt", driverInfo);
        if (driverInfoInFile == null) {
            System.out.println("Wrong credentials. Please try again.");
            return;
        }
        String[] driverInfoArr = driverInfoInFile.split(" ");
        String driverIDInFile = driverInfoArr[0];
        String pass = driverInfoArr[1];
        String driverName = driverInfoArr[2];
        String city = driverInfoArr[3];
        String carModel = driverInfoArr[4];
        String carNumber = driverInfoArr[5];
        String newDriverInfo = driverIDInFile + " " + pass + " " + driverName + " " + city + " " + carModel + " "
                + carNumber
                + " " + "Y";
        try {
            updateFile("Drivers_Record.txt", driverInfoInFile, newDriverInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Your ride is ended successfully. Thank you for using our service.");
    }

}