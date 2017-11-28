
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static CarPark carPark;
    private static final Scanner scanner = new Scanner(System.in);
    private File savedCarPark = new File("savedCarPark");

    public void showMenu1(){
        sayHello();
        System.out.println("1. Stwórz nowy parking.     2. Wczytaj ostatni parking");
        int menuCase = Integer.parseInt(scanner.nextLine());
        switch (menuCase){
            case 1:
                carPark = new CarPark(10, 3, 2);
                break;
            case 2:
                read();
                break;
        }
    }
    public void showMenu2() {
        int menuCase=1;
        do {
            System.out.println("                            //////MENU\\\\\\\\\\\\\n" +
                    "1. Wolnych miejsa.         2. Zaparkuj pojazd.     3. Zamień miejscami.\n" +
                    "4. Stwórz miejsce.         5. Zwolnij miejsce.     6. Zwolnij wszystkie.\n" +
                    "7. Pobierz należności.     8. Pobierz opłatę.      9. Wpłać pieniądze.\n"+
                    "10. Zapisz.                0. Wyjdź.");
            menuCase = Integer.parseInt(scanner.nextLine());
            switch (menuCase) {
                case 1:
                    System.out.println("Liczba wolnych miejsc: " + carPark.getFreeSpotsLeft());
                    scanner.nextLine();
                    break;
                case 2:
                    carPark.parkCar(getVehicleType(),getRegistrationNumber());
                    scanner.nextLine();
                    break;
                case 3:
                    carPark.switchCars(getSpotID(),getSpotID());
                    scanner.nextLine();
                    break;
                case 4:
                    carPark.addParkingSpot(getVehicleType());
                    scanner.nextLine();
                    break;
                case 5:
                    carPark.removeCar(getVehicleID());
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.println("Czy na pewno chcesz zwolnić wszystkie miejsca? T - Tak");
                    if(getChar()=='T')
                        carPark.removeAll();
                    scanner.nextLine();
                    break;
                case 7:
                    carPark.getBills();
                    scanner.nextLine();
                    break;
                case 8:
                    carPark.getExtraBill(getAmount());
                    scanner.nextLine();
                    break;
                case 9:
                    carPark.setBoudget(carPark.getBoudget() + getAmount());
                    scanner.nextLine();
                    break;
                case 10:
                    save();
                    break;
            }
            for (Map.Entry<String, String> entry : carPark.getParkingSpots().entrySet()) {
                System.out.println("Miejsce "+entry.getKey()+" -> "+entry.getValue());
            }
            System.out.println("Budżet = "+carPark.getBoudget());
        }while(menuCase!=0);

    }

    public void sayHello(){
        System.out.println("Witaj w CarParkManager\n" +
                "Typy aut:  Car   -> C\n" +
                "           Truck -> T\n" +
                "           Bike  -> B\n");
    }

    public Character getVehicleType(){
        System.out.println("Podaj typ samochodu");
        Character type = getChar();
        return type;
    }

    public String getRegistrationNumber(){
        System.out.println("Podaj numer rejestracyjny pojazdu");
        String registrationNumber = scanner.nextLine();
        return registrationNumber;
    }

    public String getSpotID(){
        System.out.println("Podaj ID miejsca: ");
        String spotID = scanner.nextLine();
        return spotID;
    }

    public String getVehicleID() {
        System.out.println("Podaj ID pojazdu");
        return scanner.nextLine();
    }

    public Character getChar() {
        return scanner.nextLine().toUpperCase().charAt(0);
    }

    public int getAmount(){
        System.out.println("Podaj kwotę do pobrania od każdego zaparkowanego auta:");
        return Integer.parseInt(scanner.nextLine());
    }

    public void save(){
        String park = carPark.getParkingSpots().toString().replace(" ","").replace(',','\n');
        park = park.substring(1,park.length()-1) + "\n"+carPark.getBoudget();
        try {
            Files.write(savedCarPark.toPath(),park.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(){
        File savedCarPark = new File("savedCarPark.txt");
        List saved=null;
        carPark = new CarPark();
        try {
            saved = Files.readAllLines(savedCarPark.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object o : saved) {
            String[] splited = o.toString().split("=");

            if(Character.isDigit(splited[0].charAt(0)))
            {
                carPark.setBoudget(Integer.parseInt(splited.toString()));
            }
            else {
                if (splited.length == 2) {
                    carPark.getParkingSpots().put(splited[0], splited[1]);
                } else {
                    carPark.getParkingSpots().put(splited[0], "");
                    carPark.setFreeSpotsLeft(carPark.getFreeSpotsLeft() + 1);
                }
            }
        }

    }
}