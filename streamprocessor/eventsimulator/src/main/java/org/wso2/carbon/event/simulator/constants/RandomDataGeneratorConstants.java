package org.wso2.carbon.event.simulator.constants;

/**
 * Created by mathuriga on 16/11/16.
 */
public class RandomDataGeneratorConstants {

    public static final String PrimitiveBasedAttribute="PRIMITIVEBASED";
    public static final String PropertyBasedAttribute="PROPERTYBASED";
    public static final String RegexBasedAttribute="REGEXBASED";
    public static final String CustomDataBasedAttribute="CUSTOMDATA";

//constants for typebased data generator
    public static final String Module_alphaNumeric = "AlphaNumeric";
    public static final String Module_calendar="Calendar";
    public static final String Module_contact="Contact";
    public static final String Module_finance="Finance";
    public static final String Module_internet="Internet";
    public static final String Module_userAgent="User Agent";
    public static final String Module_location="Location";
    public static final String Module_mobile="Mobile";
    public static final String Module_words="Words";


//constants for each sub
//ALPHANUMERIC
//    public static final String Module_alphaNumeric_letterify="Letterify";
//    public static final String Module_alphaNumeric_botify="Botify";
    public static final String Module_alphaNumeric_randomString="RandomString";
    public static final String Module_alphaNumeric_randomInteger="RandomInteger";
    public static final String Module_alphaNumeric_randomLong="RandomLong";
    public static final String Module_alphaNumeric_randomDouble="RandomDouble";
    public static final String Module_alphaNumeric_randomFloat="RandomFloat";
    public static final String Module_alphaNumeric_randomBoolean="RandomBoolean";

//CALENDAR
    public static final String Module_calendar_time12h="Time12h";
    public static final String Module_calendar_time24h="Time24h";
    public static final String Module_calendar_second="Second";
    public static final String Module_calendar_minute="Minute";
    public static final String Module_calendar_month="Month";
    public static final String Module_calendar_year="Year";
    public static final String Module_calendar_day="Day";
    public static final String Module_calendar_dayOfWeek="Day of week";
    public static final String Module_calendar_monthNumber="Month(Number)";
    public static final String Module_calendar_date="Date";

//CONTACT
    public static final String Module_contact_fullName="Full Name";
    public static final String Module_contact_firstName="First Name";
    public static final String Module_contact_lastName="last Name";
    public static final String Module_contact_address="Address";
   // public static final String Module_contact_birthday="Birthday";
    public static final String Module_contact_bsn="BSN";
    public static final String Module_contact_email="Email";
    public static final String Module_contact_phoneNo="PhoneNo";
    public static final String Module_contact_postcode="PostCode";
    public static final String Module_contact_state="State";
    public static final String Module_contact_city="City";
    public static final String Module_contact_company="Company";
    public static final String Module_contact_country="Country";
    public static final String Module_contact_StreetName="StreetName";
    public static final String Module_contact_houseNo="HouseNo";
    public static final String Module_contact_height_cm="Height(cm)";
    public static final String Module_contact_height_m="Height(m)";
    public static final String Module_contact_weight="Weight";
    public static final String Module_contact_bloodType="Blood Type";
    public static final String Module_contact_occupation="Occupation";
    public static final String Module_contact_religion="Religion";
    public static final String Module_contact_zodiac="Zodiac";

//FINANCE
    public static final String Module_finance_iban="iban";
    public static final String Module_finance_bic="bic";
    public static final String Module_finance_visacreditCard="Visa CreditCard";
    public static final String Module_finance_pinCode="PinCode";

//Internet
    public static final String Module_internet_urlBuilder="url";
    public static final String Module_internet_ip="ip";
    public static final String Module_internet_ipv6="ipv6";
    public static final String Module_internet_macAddress="Mac Address";
    public static final String Module_internet_UUID="UUID";
    public static final String Module_internet_color="Color";
    public static final String Module_internet_twitter="Twitter";
    public static final String Module_internet_hashtag="HashTag";
    public static final String Module_internet_facebookID="FacebookId";
    public static final String Module_internet_userName="User Name";

//LOCATION
    public static final String Module_location_altitude="altitude";
    public static final String Module_location_depth="Depth";
    public static final String Module_location_coordinates="Coordinates";
    public static final String Module_location_latitude="Latitude";
    public static final String Module_location_longtitude="Longtitude";
    public static final String Module_location_geoHash="GeoHash";

//WORDS
    public static final String Module_words_words="words";
    public static final String Module_words_paragraph="paragraph";
    public static final String Module_words_sentence="Sentence";
}
