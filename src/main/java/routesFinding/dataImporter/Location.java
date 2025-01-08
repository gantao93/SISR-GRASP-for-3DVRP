package routesFinding.dataImporter;

public record Location(
        double lat,
        double lon
) {
    public static Location of(double lat, double lon){
        return new Location(lat, lon);
    }

}
