import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class CarPark {
    private int parkingSpotsNumber;
    private int freeSpotsLeft;
    private int boudget;
    private Map<String, String> parkingSpots = new HashMap<>();

    public CarPark(){
        parkingSpotsNumber = parkingSpots.size();
    }

    public CarPark(int carSpotsNumber, int bikeSpotsNumber, int truckSpotsNumber) {
        parkingSpotsNumber = 0;
        boudget = 0;
        for (int i = 0; i < carSpotsNumber; i++) {
            boudget+=200;
            addParkingSpot('C');
        }
        for (int i = 0; i < bikeSpotsNumber; i++) {
            boudget+=100;
            addParkingSpot('B');
        }
        for (int i = 0; i < truckSpotsNumber; i++) {
            boudget+=300;
            addParkingSpot('T');
        }
        freeSpotsLeft=parkingSpotsNumber;
    }

    public void setFreeSpotsLeft(int freeSpotsLeft) {
        this.freeSpotsLeft = freeSpotsLeft;
    }

    public int getFreeSpotsLeft() {
        return freeSpotsLeft;
    }

    public void setBoudget(int boudget) {
        this.boudget = boudget;
    }

    public int getBoudget() {
        return boudget;
    }

    public Map<String, String> getParkingSpots() {
        return parkingSpots;
    }

    public void addParkingSpot(Character type){
        String newID = type.toString() + ++parkingSpotsNumber;
        if((type == 'C')||(type == 'T')||(type == 'B')){
            if(boudget>=spotCost(type)) {
                parkingSpots.put(newID, "");
                boudget-=spotCost(type);
            }else
                System.out.println("Za mały budżet!");
        }else
            System.out.println("Błędny typ");
    }
    public int spotCost(Character type){
        switch (type) {
            case 'C': return 200;
            case 'B': return 100;
            default: return  300;
        }
    }

    public void parkCar(Character type, String registrationNumber){
        String vehicleID = type+registrationNumber;
        boolean isParked = false;
        if (freeSpotsLeft == 0) {
            System.out.println("Brak miejsc!");
        }else {
            for (Map.Entry<String, String> parkingSpotsEntry : parkingSpots.entrySet()) {
                if (parkingSpotsEntry.getValue().equals("")) {
                    if (parkingSpotsEntry.getKey().charAt(0) == type) {
                        parkingSpotsEntry.setValue(vehicleID);
                        System.out.println("Zaparkowano pomyślnie.");
                        freeSpotsLeft -= 1;
                        isParked = true;
                        break;
                    }
                }
            }
            if(!isParked) {
                System.out.println("Brak miejsc.");
            }
        }
    }

    public void removeCar(String vehicleID){
        for (Map.Entry<String, String> stringStringEntry : parkingSpots.entrySet()) {
            if(stringStringEntry.getValue().equals(vehicleID)){
                stringStringEntry.setValue("");
                freeSpotsLeft += 1;
            }
        }
    }

    public void switchCars(String firstSpotID, String secondSpotID){
        if( parkingSpots.get(firstSpotID) == parkingSpots.get(secondSpotID)) {
            String firstValue = parkingSpots.get(firstSpotID);
            parkingSpots.replace(firstSpotID,secondSpotID);
            parkingSpots.replace(secondSpotID,firstSpotID);
        }
    }

    public void removeAll(){
        freeSpotsLeft = parkingSpotsNumber;
        for (Map.Entry<String, String> stringStringEntry : parkingSpots.entrySet()) {
            stringStringEntry.setValue("");
        }
    }

    public void getBills(){
        int billsAmount = 0;
        for (Map.Entry<String, String> entry : parkingSpots.entrySet()) {
            if(!entry.getValue().equals("")) {
                switch (entry.getValue().charAt(0)) {
                    case 'B':
                        billsAmount += 5;
                        break;
                    case 'C':
                        billsAmount += 10;
                        break;
                    case 'T':
                        billsAmount += 15;
                        break;
                    default:
                        break;
                }
            }
        }
        boudget+=billsAmount;
    }

    public void getExtraBill(int amount){
        int billsAmount = 0;
        if(amount>5){
            Random random = new Random();
            int leavingVehicles = random.nextInt(parkingSpotsNumber-freeSpotsLeft);
            System.out.println("Wieksza od 5. Wyjezdza: "+leavingVehicles);
            for (int i = 0; i < leavingVehicles; i++) {
                removeCar(parkingSpots.get(getRandomID()));
            }
        }
        for (Map.Entry<String, String> entry : parkingSpots.entrySet()){
            if(!entry.getValue().equals("")) {
                if (entry.getValue().charAt(0) == 'C' || entry.getValue().charAt(0) == 'B' || entry.getValue().charAt(0) == 'T') {
                    billsAmount += amount;
                }
            }
        }
        boudget+=billsAmount;
    }

    public String getRandomID(){
        Random random = new Random();
        String ID = new String();
        int number = random.nextInt(parkingSpotsNumber) + 1;
        boolean isRight = false;
        do {
            int type = random.nextInt(3) + 1;
            switch (type) {
                case 1:
                    ID = "C";
                    break;
                case 2:
                    ID = "B";
                    break;
                case 3:
                    ID = "T";
                    break;
            }
            System.out.println(ID + number);
            isRight = !parkingSpots.get(ID+number).equals("")&&parkingSpots.containsKey(ID+number);
        }while (isRight);
        return ID+number;
    }

}