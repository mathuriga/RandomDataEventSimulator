package org.wso2.carbon.event.simulator.randomdatafeedsimulation.bean;

/**
 * Created by mathuriga on 24/11/16.
 */
public class PrimitiveBasedAttribute extends StreamAttributeDto{
    private Object min;
    private Object max;
    private int length;

    public PrimitiveBasedAttribute(String type, Object min, Object max, int length) {
        super();
        this.min = min;
        this.max = max;
        this.length = length;
    }

    public Object getMin() {
        return min;
    }

    public void setMin(Object min) {
        this.min = min;
    }

    public Object getMax() {
        return max;
    }

    public void setMax(Object max) {
        this.max = max;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
