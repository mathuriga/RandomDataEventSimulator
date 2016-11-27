package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;


/**
 * Created by mathuriga on 17/11/16.
 */

//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "option")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = primitiveBasedAttributeDto.class, name = "PrimitiveBased"),
//        @JsonSubTypes.Type(value = PropertyBasedAttributeDto.class, name = "propertyBASED"),
//        @JsonSubTypes.Type(value = RegexBasedAttributeDto.class, name = "REGEXBASED")
//})
//@XmlRootElement(name="AttributeSimulation")
public abstract class StreamAttributeDto {
    private String type;
    private StreamAttributeDto streamAttributeDto;



    public StreamAttributeDto() {
        this.type = type;
        this.streamAttributeDto = this;

    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }



}
