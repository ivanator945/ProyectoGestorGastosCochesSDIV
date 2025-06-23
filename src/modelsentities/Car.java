package modelsentities;

public class Car {
    private String carId;
    private String brand;
    private String model;
    private String licensePlate;
    private int year;

    public Car(String carId, String brand, String model, String licensePlate, int year) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.year = year;
    }
  
    public String getCarId() { return carId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getLicensePlate() { return licensePlate; }
    public int getYear() { return year; }
}
