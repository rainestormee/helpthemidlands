package com.hackthemidlands.processblinders.api;

public class VolunteerMainPage {
    public static void main(String[] argv) {
        //   Main.getAllValidOrders().stream().filter(o -> o.getUser().getPostcode());
        getLatitudeAndLongitude("B15 3AS");
    }

    public static void getLatitudeAndLongitude(String postcode) {
        GoogleGeoCode gc = new GoogleGeoCode();
        gc.main(postcode);
        String la = gc.getLat();
        String lon = gc.getLongit();
        if (la == null || lon == null) return;
        int a = Integer.parseInt(la);
        int b = Integer.parseInt(lon);
        System.out.println(a + " " + b);
    }

    public static int calculateDistance(String postcodeUser, String postcodeVolunteer) {
        return 0;
    }
}