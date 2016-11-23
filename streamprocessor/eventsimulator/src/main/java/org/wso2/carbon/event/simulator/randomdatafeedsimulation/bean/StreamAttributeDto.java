package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by mathuriga on 17/11/16.
 */

@XmlRootElement(name="StreamAttributeDto")
public class StreamAttributeDto {
    private String attributeName;
    private String attributeDataType;
    private String option;

    //properties of datatypebased data generation option
    private Object min;
    private Object max;
    private int length;

    //properties of regex based datageneration
    private String pattern;

    //properties of typebased datageneration
    private String moduleName;
    private String subTypeName;

    public enum AttributeType {
        RANDOM,
        REGEX,
        TYPEBASED
    }

    public String getOption() {
        return option;
    }

    @XmlElement
    public void setOption(String option) {
        this.option = option;
    }

    public String getAttributeName() {
        return attributeName;
    }

    @XmlElement
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeDataType() {
        return attributeDataType;
    }

    @XmlElement
    public void setAttributeDataType(String attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    public Object getMin() {
        return min;
    }

    @XmlElement
    public void setMin(Object min) {
        this.min = min;
    }

    public Object getMax() {
        return max;
    }

    @XmlElement
    public void setMax(Object max) {
        this.max = max;
    }

    public int getLength() {
        return length;
    }

    @XmlElement
    public void setLength(int length) {
        this.length = length;
    }

    public String getPattern() {
        return pattern;
    }

    @XmlElement
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getModuleName() {
        return moduleName;
    }

    @XmlElement
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    @XmlElement
    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }
}
