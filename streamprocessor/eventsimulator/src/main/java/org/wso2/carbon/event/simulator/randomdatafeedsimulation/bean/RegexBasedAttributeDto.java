package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

/**
 * Created by mathuriga on 23/11/16.
 */

//@XmlRootElement(name="REGEXBASED")
public class RegexBasedAttributeDto extends StreamAttributeDto {
    private String pattern;

    public RegexBasedAttributeDto(String type, String pattern) {
        super();
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    //@XmlElement
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
