package org.wso2.carbon.event.simulator.randomdatafeedsimulation.utils;

import org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean.StreamAttributeDto;


/**
 * Created by mathuriga on 17/11/16.
 */
public class AttributeGenerator {
//    private List<StreamAttributeDto> streamAttributeDtoList=new ArrayList<>();

//    private enum AttributeType {
//        RANDOM,
//        REGEX,
//        TYPEBASED
//    }

    private static final String random="RANDOM";
    private static final String regex="REGEX";
    private static final String typeBased="TYPEBASED";

    private AttributeGenerator() {
    }

    public static String generateAttributeValue(StreamAttributeDto streamAttributeDto){
        String value = null;

        if(streamAttributeDto.getOption().compareTo(random)==0){
            value=String.valueOf(RandomDataGenerator.generateRandomData(streamAttributeDto.getAttributeDataType(),streamAttributeDto.getMin(),streamAttributeDto.getMax(),streamAttributeDto.getLength()));

        }else if(streamAttributeDto.getOption().compareTo(regex)==0){
            //validate wheater the regular expression
            if(RandomDataGenerator.validateRegularExpression(streamAttributeDto.getPattern())){
                value=String.valueOf(RandomDataGenerator.generateRandomPatternData(streamAttributeDto.getPattern()));
            }
        }else if(streamAttributeDto.getOption().compareTo(typeBased)==0){
            value=String.valueOf(RandomDataGenerator.generateRandomDataTypeBase(streamAttributeDto.getModuleName(),streamAttributeDto.getSubTypeName()));
        }
        return value;
    }
}
