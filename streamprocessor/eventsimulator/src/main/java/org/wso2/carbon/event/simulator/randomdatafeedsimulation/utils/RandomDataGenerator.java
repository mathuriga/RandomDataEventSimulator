/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import fabricator.enums.DateFormat;
import org.joda.time.DateTime;
import org.wso2.carbon.event.simulator.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.constants.RandomDataGeneratorConstants;

/**
 * Generates random value for given case
 * It is an utility class
 * Data can be generated in three ways
 * 1. Generate data according to given data type
 * For this it uses Fabricator library
 * 2. Generate meaning full data Eg : Full Name
 * For reference {<a href="https://www.mockaroo.com/">www.mockaroo.com</a>}
 * 3. Generate data according to given regular expression
 * For this it uses generex library
 * 4. Generate data with in given data list
 * <p>
 * <a href="http://biercoff.com/fabricator/">fabricator</a>
 * <a href="https://github.com/azakordonets/fabricator">fabricator - github source </a>
 * <a href="https://github.com/mifmif/Generex">Generex</a>
 */
public class RandomDataGenerator {
    /**
     * Initialize contact to generate contact related data
     */
    private static Contact contact = Fabricator.contact();

    /**
     * Initialize calendar to generate calendar related data
     */
    private static Calendar calendar = Fabricator.calendar();

    /**
     * Initialize Finance to generate finance related data
     */
    private static Finance finance = Fabricator.finance();

    /**
     * Initialize internet to generate internet related data
     */
    private static Internet internet = Fabricator.internet();

    /**
     * Initialize location to generate location related data
     */
    private static Location location = Fabricator.location();

    /**
     * Initialize words to generate words related data
     */
    private static Words words = Fabricator.words();

    /**
     * Initialize Alphanumeric to generate words related data
     */
    private static Alphanumeric alpha = Fabricator.alphaNumeric();

    /**
     * Initialize RandomDataGenerator and make it private
     */
    private RandomDataGenerator() {

    }

    /**
     * Generate data according to given data type. And cast it into relevant data type
     * For this it uses Alphanumeric from fabricator library
     *
     * @param type   attribute data type (String,Integer,Float,Double,Long,Boolean)
     * @param min    Minimum value for numeric values to be generate
     * @param max    Maximum value for numeric values to be generated
     * @param length If attribute type is string length indicates length of the string to be generated
     *               If attribute type is Float or Double length indicates no of Numbers after the decimal point
     * @return Generated value as object
     * <a href="http://biercoff.com/fabricator/">fabricator</a>
     */
    public static Object generatePrimitiveBasedRandomData(String type, Object min, Object max, int length) {
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
                //Format value to given no of decimals
                result = Float.parseFloat(format.format(alpha.randomFloat(Float.parseFloat((String) min), Float.parseFloat((String) max))));
                break;
            case "Double":
                format.setMaximumFractionDigits(length);
                //Format value to given no of decimals
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

    /**
     * Generate data according to given regular expression.
     * It uses  A Java library called Generex for generating String that match
     * a given regular expression
     *
     * @param pattern Regular expression used to generate data
     * @return Generated value as object
     * @see <a href="https://github.com/mifmif/Generex">Generex</a>
     */
    public static Object generateRegexBasedRandomData(String pattern) {
        Generex generex = new Generex(pattern);
        Object result;
        result = generex.random();
        return result;
    }


    /**
     * Generate meaning full data.
     * For this it uses fabricator library
     *
     * @param categoryType CategoryType
     * @param propertyType PropertyType
     * @return Generated value as object
     * @link <a href="http://biercoff.com/fabricator/">fabricator</a>
     */
    public static Object generatePropertyBasedRandomData(String categoryType, String propertyType) {
        Object result = null;
        switch (categoryType) {
            case RandomDataGeneratorConstants.Module_calendar:
                switch (propertyType) {
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
                        Random random = new Random();
                        int incrementValue = random.nextInt(10);
                        System.out.println(incrementValue);
                        result = calendar.relativeDate(DateTime.now().plusDays(incrementValue)).asString(DateFormat.dd_MM_yyyy_H_m_s_a);
                        break;
                }
                break;

            case RandomDataGeneratorConstants.Module_contact:

                switch (propertyType) {

                    case RandomDataGeneratorConstants.Module_contact_fullName:
                        result = contact.fullName(true, true);
                        break;
                    case RandomDataGeneratorConstants.Module_contact_firstName:
                        result = contact.firstName();
                        break;
                    case RandomDataGeneratorConstants.Module_contact_lastName:
                        result = contact.lastName();
                        break;
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


                switch (propertyType) {
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


                switch (propertyType) {
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

                switch (propertyType) {
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
                switch (propertyType) {
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
                System.out.println("Property type is not available in library");

        }

        return result;
    }

    /**
     * Generate data with in given data list
     * <p>
     * Initialize Random to select random element from array
     *
     * @param customDataList Array of data
     * @return generated data from array
     */
    public static Object generateCustomRandomData(String[] customDataList) {
        Random random = new Random();
        int randomElementSelector = random.nextInt(customDataList.length);
        Object result;
        result = customDataList[randomElementSelector];
        return result;
    }

    /**
     * Validate Regular Expression
     *
     * @param regularExpression regularExpression
     */
    public static void validateRegularExpression(String regularExpression) {
        try {
            Pattern.compile(regularExpression);
        } catch (PatternSyntaxException e) {
            throw new EventSimulationException("Invalid regular expression : " + regularExpression + " Error: " + e.getMessage());
        }

    }


}
