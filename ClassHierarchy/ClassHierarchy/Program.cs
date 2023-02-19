enum Fuel
{
    DIESEL,
    CARBON,
    BIOMASS,
    PETROLEUM
}

class Schedule
{
    public String departure;
    public String destination;
    public String departureTime;
    public String destinationTime;
    public String date;
}

class Driver
{
    public String name;
    public int experience;
    public int id;
}

class RailTransport
{
    public Schedule schedule;
    public Driver driver;
    public int id;
    public int totalPlaces;
    public int maxSpeed;
}

class ElectricTrain : RailTransport
{
    public String powerSupply;
    public int voltage;
}

class DieselTrain : RailTransport
{
    public Fuel fuel;
}

class Subway : ElectricTrain
{
    public int depth;
}

class Tram : ElectricTrain
{
    public int noisiness;
}