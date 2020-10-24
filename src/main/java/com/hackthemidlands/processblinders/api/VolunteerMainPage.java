import com.hackthemidlands.processblinders.api.GoogleGeoCode;

public class VolunteerMainPage{
    public static void main(String[] argv){
     //   Main.getAllValidOrders().stream().filter(o -> o.getUser().getPostcode());
        getLatitudeAndLongitude("B15 3AS");
    }

    public static void getLatitudeAndLongitude(String postcode){
      GoogleGeoCode.main(postcode);
      String la = GoogleGeoCode.lat;
      String lon = GoogleGeoCode.longit;
      int a = Integer.parseInt(la);
      int b = Integer.parseInt(lon);
      System.out.println(a + " " + b);
    }

    public static int calculateDistance(String postcodeUser, String postcodeVolunteer){
        return 0;
    }
}