package org.wso2.carbon.event.simulator.core.randomdatafeedsimulation.bean;

/**
 * Created by mathuriga on 23/11/16.
 */


public class RegexBasedAttributeDto extends StreamAttributeDto {
    private String pattern;

    public RegexBasedAttributeDto(String type, String pattern) {
        super();
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
