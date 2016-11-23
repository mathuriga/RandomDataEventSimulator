package org.wso2.carbon.event.simulator.randomdatafeedsimulation.utils;

import com.mifmif.common.regex.Generex;
import fabricator.Alphanumeric;
import fabricator.Calendar;
import fabricator.Contact;
import fabricator.Fabricator;
import fabricator.Finance;
import fabricator.Internet;
import fabricator.Location;
import fabricator.Words;

import java.text.DecimalFormat;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.wso2.carbon.event.simulator.constants.RandomDataGeneratorConstants;

/**
 * Created by mathuriga on 12/11/16.
 */
public class RandomDataGenerator {

    private RandomDataGenerator() {

    }

    public static Object generateRandomData(String type, Object min, Object max, int length) {
        Alphanumeric alpha = Fabricator.alphaNumeric();
        Object result = null;
        DecimalFormat format = new DecimalFormat();
        switch (type) {
            case "Integer":
                result = alpha.randomInt(Integer.parseInt((String) min), Integer.parseInt((String) max));
                break;
            case "Long":
                result = alpha.randomLong(Long.parseLong((String) min), Long.parseLong((String) max));
                break;
            case "Float":
                format.setMaximumFractionDigits(length);
                result = Float.parseFloat(format.format(alpha.randomFloat(Float.parseFloat((String) min), Float.parseFloat((String) max))));
                break;
            case "Double":
                format.setMaximumFractionDigits(length);
                result = Double.parseDouble(format.format(alpha.randomFloat(Float.parseFloat((String) min), Float.parseFloat((String) max))));
                break;
            case "String":
                result = alpha.randomString(length);
                break;
            case "Boolean":
                result = alpha.randomBoolean();
                break;

        }
        return result;
    }

    //Generate data for given regular expression

    public static Object generateRandomPatternData(String pattern) {
        Object result = null;
        Generex generex = new Generex(pattern);
        result = generex.random();
        return result;
    }

    //Generate data for given typeBased input

    public static Object generateRandomDataTypeBase(String moduleType, String type) {
        Object result = null;
        switch (moduleType) {

            case RandomDataGeneratorConstants.Module_alphaNumeric:
                Alphanumeric alpha = Fabricator.alphaNumeric();

                switch (type) {
                    case RandomDataGeneratorConstants.Module_alphaNumeric_randomString:
                        result = alpha.randomString();
                        break;
                    case RandomDataGeneratorConstants.Module_alphaNumeric_randomInteger:
                        result = alpha.randomInt();
                        break;
                    case RandomDataGeneratorConstants.Module_alphaNumeric_randomLong:
                        result = alpha.randomLong();
                        break;
                    case RandomDataGeneratorConstants.Module_alphaNumeric_randomDouble:
                        result = alpha.randomDouble();
                        break;
                    case RandomDataGeneratorConstants.Module_alphaNumeric_randomFloat:
                        result = alpha.randomFloat();
                        break;
                    case RandomDataGeneratorConstants.Module_alphaNumeric_randomBoolean:
                        result = alpha.randomBoolean();
                        break;
                }
                break;

            case RandomDataGeneratorConstants.Module_calendar:
                Calendar calendar = Fabricator.calendar();

                switch (type) {
                    case RandomDataGeneratorConstants.Module_calendar_time12h:
                        result = calendar.time12h();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_time24h:
                        result = calendar.time24h();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_second:
                        result = calendar.second();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_minute:
                        result = calendar.minute();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_month:
                        result = calendar.month();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_monthNumber:
                        result = calendar.month(true);
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_year:
                        result = calendar.year();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_day:
                        result = calendar.day();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_dayOfWeek:
                        result = calendar.dayOfWeek();
                        break;
                    case RandomDataGeneratorConstants.Module_calendar_date:
                        result = calendar.randomDate();
                        break;
                }
                break;

            case RandomDataGeneratorConstants.Module_contact:
                Contact contact = Fabricator.contact();

                switch (type) {
                    case RandomDataGeneratorConstants.Module_contact_fullName:
                        result = contact.fullName(true, true);

                        break;
                    case RandomDataGeneratorConstants.Module_contact_firstName:
                        result = contact.firstName();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_lastName:
                        result = contact.lastName();
                        break;
//                    case RandomDataGeneratorConstants.Module_contact_birthday:
//                        result=contact.birthday(25);break;
                    case RandomDataGeneratorConstants.Module_contact_bsn:
                        result = contact.bsn();
                        break;

                    case RandomDataGeneratorConstants.Module_contact_address:
                        result = contact.address();
                        break;

                    case RandomDataGeneratorConstants.Module_contact_email:
                        result = contact.eMail();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_phoneNo:
                        result = contact.phoneNumber();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_postcode:
                        result = contact.postcode();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_state:
                        result = contact.state();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_city:
                        result = contact.city();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_company:
                        result = contact.company();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_country:
                        result = contact.country();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_StreetName:
                        result = contact.streetName();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_houseNo:
                        result = contact.houseNumber();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_height_cm:
                        result = contact.height(true);
                        break;
                    case RandomDataGeneratorConstants.Module_contact_height_m:
                        result = contact.height(false);
                        break;
                    case RandomDataGeneratorConstants.Module_contact_weight:
                        result = contact.weight(true);
                        break;
                    case RandomDataGeneratorConstants.Module_contact_bloodType:
                        result = contact.bloodType();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_occupation:
                        result = contact.occupation();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_religion:
                        result = contact.religion();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_zodiac:
                        result = contact.zodiac();
                        break;
                }
                break;

            case RandomDataGeneratorConstants.Module_finance:
                Finance finance = Fabricator.finance();

                switch (type) {
                    case RandomDataGeneratorConstants.Module_finance_iban:
                        result = finance.iban();
                        break;
                    case RandomDataGeneratorConstants.Module_finance_bic:
                        result = finance.bic();
                        break;
                    case RandomDataGeneratorConstants.Module_finance_visacreditCard:
                        result = finance.visaCard();
                        break;
                    case RandomDataGeneratorConstants.Module_finance_pinCode:
                        result = finance.pinCode();
                        break;
                }
                break;

            case RandomDataGeneratorConstants.Module_internet:
                Internet internet = Fabricator.internet();

                switch (type) {
                    case RandomDataGeneratorConstants.Module_internet_urlBuilder:
                        result = internet.urlBuilder();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_ip:
                        result = internet.ip();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_ipv6:
                        result = internet.ipv6();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_macAddress:
                        result = internet.macAddress();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_UUID:
                        result = internet.UUID();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_color:
                        result = internet.color();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_twitter:
                        result = internet.twitter();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_hashtag:
                        result = internet.hashtag();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_facebookID:
                        result = internet.facebookId();
                        break;
                    case RandomDataGeneratorConstants.Module_internet_userName:
                        result = internet.username();
                        break;
                }
                break;

            case RandomDataGeneratorConstants.Module_location:
                Location location = Fabricator.location();

                switch (type) {
                    case RandomDataGeneratorConstants.Module_location_altitude:
                        result = location.altitude();
                        break;
                    case RandomDataGeneratorConstants.Module_location_depth:
                        result = location.depth();
                        break;
                    case RandomDataGeneratorConstants.Module_location_coordinates:
                        result = location.coordinates();
                        break;
                    case RandomDataGeneratorConstants.Module_location_latitude:
                        result = location.latitude();
                        break;
                    case RandomDataGeneratorConstants.Module_location_longtitude:
                        result = location.longitude();
                        break;
                    case RandomDataGeneratorConstants.Module_location_geoHash:
                        result = location.geohash();
                        break;
                }
                break;

            case RandomDataGeneratorConstants.Module_words:
                Words words = Fabricator.words();

                switch (type) {
                    case RandomDataGeneratorConstants.Module_words_words:
                        result = words.word();
                        break;
                    case RandomDataGeneratorConstants.Module_words_paragraph:
                        result = words.paragraph();
                        break;
                    case RandomDataGeneratorConstants.Module_words_sentence:
                        result = words.sentence();
                        break;
                }
                break;

            default:
                System.out.println("Your option is not available in library");

        }

        return result;
    }

    public static boolean validateRegularExpression(String regularExpression) {
        boolean status = false;
        try {
            Pattern.compile(regularExpression);
            status = true;
        } catch (PatternSyntaxException exception) {
            System.out.println("Invalid regular expression : " + regularExpression + " Error: " + exception.getDescription());
        }
        return status;
    }


}
