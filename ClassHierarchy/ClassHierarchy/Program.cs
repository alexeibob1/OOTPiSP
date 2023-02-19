public class Schedule
{
    String departure;
    String destination;
    String departureTime;
    String destinationTime;
}

public class RailTransport
{
    int id;
    Schedule schedule;
    int totalPlaces;
    int maxSpeed;
    int totalCarriages;
}

public class ElectricTrain : RailTransport
{
    String powerSupply;
    int voltage;
}

enum Fuel
{
    DIESEL,
    CARBON,
    BIOMASS,
    PETROLEUM
}

public class DieselTrain : RailTransport
{
    Fuel fuel;
}

public class Subway : ElectricTrain
{
    int depth;
}

public class Tram : ElectricTrain
{
    int noisiness;
}