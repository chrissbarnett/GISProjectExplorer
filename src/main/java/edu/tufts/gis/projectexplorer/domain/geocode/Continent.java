package edu.tufts.gis.projectexplorer.domain.geocode;

/**
 * Created by cbarne02 on 5/1/15.
 */

/*
Continent codes :
AF : Africa			geonameId=6255146
AS : Asia			geonameId=6255147
EU : Europe			geonameId=6255148
NA : North America		geonameId=6255149
OC : Oceania			geonameId=6255151
SA : South America		geonameId=6255150
AN : Antarctica			geonameId=6255152
 */
public enum Continent {
    AF("Africa", 6255146),
    AS("Asia", 6255147),
    EU("Europe", 6255148),
    NA("North America", 6255149),
    OC("Oceania", 6255151),
    SA("South America", 6255150),
    AN("Antarctica", 6255152);

    private final String fullName;
    private final int geonameID;

    Continent(String fullName, int geonameID){
        this.fullName = fullName;
        this.geonameID = geonameID;
    }

    public String getFullName(){
        return this.fullName;
    }

    public int getGeonameID() {
        return geonameID;
    }

    public static Continent getContinent(int geonameID){
        for (Continent continent: Continent.values()){
            if (continent.getGeonameID() == geonameID){
                return continent;
            }
        }

        return null;
    };

}
