package org.wso2.carbon.event.simulator.constants;

import scala.util.parsing.combinator.testing.Str;

/**
 * Created by mathuriga on 20/11/16.
 */
public class EventSimulatorConstants {
    public static final String Attributetype_String="String";
    public static final String Attributetype_Integer="Integer";
    public static final String Attributetype_Long="Long";
    public static final String Attributetype_Float="Float";
    public static final String Attributetype_Double="Double";
    public static final String Attributetype_Boolean="Boolean";

    public static final String STREAM_CONFIGURATION="streamConfiguration";
    //Feed Simulation type constants
    public static final String RANDOM_DATA_SIMULATION="RandomDataSimulation";
    public static final String FILE_FEED_SIMULATION="FileFeedSimulation";
    public static final String DATABASE_FEED_SIMULATION="DatabaseSimulation";
    public static final String SINGLE_EVENT_SIMULATION="SingleEventSimulation";

    public static final String ORDER_BY_TIMESTAMP="orderByTimeStamp";

    //Feed Simulation stream Configuration constants

          //RandomDataSimulation constants
    public static final String SIMULATION_TYPE="simulationType";
    public static final String STREAM_NAME="streamName";
    public static final String EVENTS="events";
    public static final String DELAY="delay";
    public static final String ATTRIBUTE_CONFIGURATION="attributeConfiguration";

        //filefeedsimulation constants
    public static final String FILE_NAME="fileName";
    public static final String DELIMITER="delimiter";

    //Random data feed simulation constants
    public static final String PRIMITIVEBASEDATTRIBUTE="PRIMITIVEBASED";
    public static final String PROPERTYBASEDATTRIBUTE="PROPERTYBASED";
    public static final String REGEXBASEDATTRIBUTE="REGEXBASED";
    public static final String CUSTOMDATABASEDATTRIBUTE="CUSTOMDATA";
    public static final String RANDOMDATAGENERATORTYPE ="type";
    public static final String PROPERTYBASEDATTRIBUTE_CATEGORY="category";
    public static final String PROPERTYBASEDATTRIBUTE_PROPERTY="property";
    public static final String REGEXBASEDATTRIBUTE_PATTERN="pattern";
    public static final String PRIMITIVEBASEDATTRIBUTE_MIN="min";
    public static final String PRIMITIVEBASEDATTRIBUTE_MAX="max";
    public static final String PRIMITIVEBASEDATTRIBUTE_LENGTH_DECIMAL="length";
    public static final String CUSTOMDATABASEDATTRIBUTE_LIST="list";
}
