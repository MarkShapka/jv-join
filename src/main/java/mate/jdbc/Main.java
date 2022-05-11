package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Car;
import mate.jdbc.model.Driver;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.service.CarService;
import mate.jdbc.service.DriverService;
import mate.jdbc.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);

        Driver driverBob = new Driver("Bob", "11211");
        Driver driverDan = new Driver("Dan", "12345");
        Driver driverBen = new Driver("Ben", "456789");
        driverService.create(driverBob);
        driverService.create(driverDan);
        driverService.create(driverBen);

        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        Manufacturer manufacturerHonda = new Manufacturer("honda", "japan");
        Manufacturer manufacturerToyota = new Manufacturer("toyota", "japan");
        Manufacturer manufacturerDaewoo = new Manufacturer("daewoo", "korea");
        manufacturerService.create(manufacturerHonda);
        manufacturerService.create(manufacturerToyota);
        manufacturerService.create(manufacturerDaewoo);

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carSedan = new Car();
        carSedan.setModel("sedan");
        carSedan.setManufacturer(manufacturerHonda);
        carService.create(carSedan);

        Car carCoupe = new Car();
        carCoupe.setModel("coupe");
        carCoupe.setManufacturer(manufacturerToyota);
        carService.create(carCoupe);

        Car carHatchback = new Car();
        carHatchback.setModel("hatchback");
        carHatchback.setManufacturer(manufacturerDaewoo);
        carService.create(carHatchback);

        System.out.println(carService.get(2L));

        List<Car> allCars = carService.getAll();
        allCars.stream()
                .forEach(System.out::println);

        carCoupe.setId(2L);
        carCoupe.setModel("newCoupe");
        manufacturerDaewoo.setId(3L);
        carCoupe.setManufacturer(manufacturerDaewoo);

        System.out.println(carService.update(carCoupe));

        carService.addDriverToCar(driverBen, carSedan);
        carService.addDriverToCar(driverBen, carCoupe);
        carService.addDriverToCar(driverDan, carCoupe);
        carService.addDriverToCar(driverBob, carHatchback);

        carService.removeDriverFromCar(driverBob, carHatchback);

        carService.delete(carHatchback.getId());

        List<Optional<Car>> allByDriver = carService.getAllByDriver(driverBen.getId());
        allByDriver.stream()
                .forEach(System.out::println);

        carService.delete(3L);
    }
}
