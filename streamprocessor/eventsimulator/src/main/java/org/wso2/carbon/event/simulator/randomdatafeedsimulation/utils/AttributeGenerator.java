package org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.utils;


import org.wso2.carbon.event.simulator.core.exception.EventSimulationException;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.CustomBasedAttribute;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.PrimitiveBasedAttribute;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.PropertyBasedAttributeDto;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.RegexBasedAttributeDto;
import org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean.StreamAttributeDto;

/**
 * Created by mathuriga on 17/11/16.
 */
public class AttributeGenerator {

    private AttributeGenerator() {
    }

    public static String generateAttributeValue(StreamAttributeDto streamAttributeDto, String attributeType) {
        String value = null;
        if (streamAttributeDto.getType() != null) {
            if (streamAttributeDto instanceof PrimitiveBasedAttribute) {
                value = String.valueOf(RandomDataGenerator.generatePrimitiveBasedRandomData(attributeType, ((PrimitiveBasedAttribute) streamAttributeDto).getMin(), ((PrimitiveBasedAttribute) streamAttributeDto).getMax(), ((PrimitiveBasedAttribute) streamAttributeDto).getLength()));
            } else if (streamAttributeDto instanceof RegexBasedAttributeDto) {
                value = String.valueOf(RandomDataGenerator.generateRegexBasedRandomData(((RegexBasedAttributeDto) streamAttributeDto).getPattern()));
            } else if (streamAttributeDto instanceof PropertyBasedAttributeDto) {
                value = String.valueOf(RandomDataGenerator.generatePropertyBasedRandomData(((PropertyBasedAttributeDto) streamAttributeDto).getCategory(), ((PropertyBasedAttributeDto) streamAttributeDto).getProperty()));
            } else if (streamAttributeDto instanceof CustomBasedAttribute) {
                value = String.valueOf(RandomDataGenerator.generateCustomRandomData(((CustomBasedAttribute) streamAttributeDto).getCustomDataList()));
            }
        } else {
            throw new EventSimulationException(new NullPointerException().getMessage());
        }
        return value;
    }
}
