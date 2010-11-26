package org.vaadin.sparklines;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.vaadin.sparklines.client.ui.VSparklines;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;

/**
 * <p>
 * A sparkline is a small information graphic with high information density.
 * Edward Tufte coined the term "sparklines" for what he calls "small, intense,
 * simple datawords" - see his book "Beautiful Evidence" and/or the online
 * version with recent develpoments and commentary at
 * http://www.edwardtufte.com/bboard/q-and-a-fetch-msg?msg_id=0001OR
 * </p>
 * <p>
 * This implementation supports the following features:
 * <ul>
 * <li>graph for a set of integers, settable min/max range (to allow
 * comparisons)</li>
 * <li>prominent label/caption indicating what the sparkline represents</li>
 * <li>textual label for the current (last) value (optional)</li>
 * <li>dot in the graph for the current (last) value (optional)</li>
 * <li>textual labels for min/max (optional)</li>
 * <li>dots in the graph for min/max values (optional)</li>
 * <li>customizable colors for value/min/max, connects dot with label</li>
 * <li>line in the graph indicating average value (optional)</li>
 * <li>colored range in the graph indicating 'normal' value range (optional)</li>
 * </ul>
 * </p>
 * <p>
 * Sparklines are very well suited for quick visual comparisons, provided that
 * they are properly configured: make sure to set the display range and use the
 * same amount of data (or adjust width) in order to make multiple sparklines
 * comparable.
 * </p>
 * <p>
 * Also note that aspect ratio is often important; as a rule of thumb,
 * variations in slopes are easiest to detect if the slopes average at 45
 * degrees. See Tufte for more on this subject.
 * </p>
 * 
 */
@com.vaadin.ui.ClientWidget(org.vaadin.sparklines.client.ui.VSparklines.class)
public class Sparklines extends AbstractField {

    private int graphHeight;
    private int graphWidth;
    private int displayRangeMax;
    private int displayRangeMin;

    private String pathColor = "#999";
    private int pathWidth = 1;

    private boolean valueLabelVisible = true;
    private boolean valueDotVisible = true;
    private String valueColor = "#69f";

    private boolean normalRangeVisible;
    private String normalRangeColor = "#ddd";
    private int normalRangeMax;
    private int normalRangeMin;

    private boolean averageVisible;
    private String averageColor = "#aaa";;

    private boolean minmaxLabelsVisible = true;
    private boolean minmaxDotsVisible = true;
    private String maxColor = pathColor;
    private String minColor = pathColor;

    public Sparklines(String caption, int graphWidth, int graphHeight,
            int displayRangeMin, int displayRangeMax) {
        setCaption(caption);
        this.graphHeight = graphHeight;
        this.graphWidth = graphWidth;
        this.displayRangeMax = displayRangeMax;
        this.displayRangeMin = displayRangeMin;
    }

    public Sparklines(String value) {
        setValue(value);
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        target.addAttribute(VSparklines.graphHeight, graphHeight);
        target.addAttribute(VSparklines.graphWidth, graphWidth);
        target.addAttribute(VSparklines.displayRangeMax, displayRangeMax);
        target.addAttribute(VSparklines.displayRangeMin, displayRangeMin);

        target.addAttribute(VSparklines.pathColor, pathColor);
        target.addAttribute(VSparklines.pathWidth, pathWidth);

        target.addAttribute(VSparklines.valueVisible, valueLabelVisible);
        target.addAttribute(VSparklines.valueDotVisible, valueDotVisible);
        target.addAttribute(VSparklines.valueColor, valueColor);

        target.addAttribute(VSparklines.normalRangeVisible, normalRangeVisible);
        target.addAttribute(VSparklines.normalRangeColor, normalRangeColor);
        target.addAttribute(VSparklines.normalRangeMax, normalRangeMax);
        target.addAttribute(VSparklines.normalRangeMin, normalRangeMin);

        target.addAttribute(VSparklines.averageVisible, averageVisible);
        target.addAttribute(VSparklines.averageColor, averageColor);

        target.addAttribute(VSparklines.minmaxLabelsVisible,
                minmaxLabelsVisible);
        target.addAttribute(VSparklines.minmaxDotsVisible, minmaxDotsVisible);
        target.addAttribute(VSparklines.maxColor, maxColor);
        target.addAttribute(VSparklines.minColor, minColor);

        Collection c = (Collection) getValue();
        if (c != null) {
            Double[] data = translateData(c);
            String[] rawData = new String[data.length];
            for (int i = 0; i < data.length; i++) {
                rawData[i] = String.valueOf(data[i]);
            }
            target.addAttribute(VSparklines.DATA, rawData);
        }
    }

    public String toString() {
        final Collection value = (Collection) getValue();
        return Arrays.deepToString(value.toArray());
    }

    public void setValue(String newValue) throws ReadOnlyException,
            ConversionException {
        if (newValue.startsWith("[")) {
            newValue = newValue.substring(1, newValue.length() - 1);
        }
        String[] in = newValue.split(",");
        List<String> l = Arrays.asList(in);
        super.setValue(l);
    }

    public void setValue(Number... newValue) throws ReadOnlyException,
            ConversionException {
        super.setValue(Arrays.asList(newValue));
    }

    public void setValue(Collection<Number> newValue) throws ReadOnlyException,
            ConversionException {
        super.setValue(newValue);
    }

    @Override
    protected void setInternalValue(Object newValue) {
        super.setInternalValue(newValue);
    }

    @Override
    public Class<?> getType() {
        return Collection.class;
    }

    protected Double[] translateData(Collection data) {
        Object[] d = data.toArray();
        if (d.getClass().equals(Double.class)) {
            return (Double[]) d;

        } else {
            Double[] res = new Double[d.length];
            for (int i = 0; i < d.length; i++) {
                res[i] = Double.valueOf(d[i].toString());
            }
            return res;
        }
    }

    public int getGraphHeight() {
        return graphHeight;
    }

    public void setGraphHeight(int graphHeight) {
        this.graphHeight = graphHeight;
        requestRepaint();
    }

    public int getGraphWidth() {
        return graphWidth;
    }

    public void setGraphWidth(int graphWidth) {
        this.graphWidth = graphWidth;
        requestRepaint();
    }

    public int getDisplayRangeMax() {
        return displayRangeMax;
    }

    public void setDisplayRangeMax(int displayRangeMax) {
        this.displayRangeMax = displayRangeMax;
        requestRepaint();
    }

    public int getDisplayRangeMin() {
        return displayRangeMin;
    }

    public void setDisplayRangeMin(int displayRangeMin) {
        this.displayRangeMin = displayRangeMin;
        requestRepaint();
    }

    public int getPathWidth() {
        return pathWidth;
    }

    public void setPathWidth(int pathWidth) {
        this.pathWidth = pathWidth;
        requestRepaint();
    }

    public String getPathColor() {
        return pathColor;
    }

    public void setPathColor(String pathColor) {
        this.pathColor = pathColor;
        requestRepaint();
    }

    public boolean isValueLabelVisible() {
        return valueLabelVisible;
    }

    public void setValueLabelVisible(boolean valueVisible) {
        this.valueLabelVisible = valueVisible;
        requestRepaint();
    }

    public boolean isValueDotVisible() {
        return valueDotVisible;
    }

    public void setValueDotVisible(boolean valueDotVisible) {
        this.valueDotVisible = valueDotVisible;
        requestRepaint();
    }

    public String getValueColor() {
        return valueColor;
    }

    public void setValueColor(String valueColor) {
        this.valueColor = valueColor;
        requestRepaint();
    }

    public boolean isNormalRangeVisible() {
        return normalRangeVisible;
    }

    public void setNormalRangeVisible(boolean normalRangeVisible) {
        this.normalRangeVisible = normalRangeVisible;
        requestRepaint();
    }

    public String getNormalRangeColor() {
        return normalRangeColor;
    }

    public void setNormalRangeColor(String normalRangeColor) {
        this.normalRangeColor = normalRangeColor;
        requestRepaint();
    }

    public int getNormalRangeMax() {
        return normalRangeMax;
    }

    public void setNormalRangeMax(int normalRangeMax) {
        this.normalRangeMax = normalRangeMax;
        requestRepaint();
    }

    public int getNormalRangeMin() {
        return normalRangeMin;
    }

    public void setNormalRangeMin(int normalRangeMin) {
        this.normalRangeMin = normalRangeMin;
        requestRepaint();
    }

    public boolean isAverageVisible() {
        return averageVisible;
    }

    public void setAverageVisible(boolean averageVisible) {
        this.averageVisible = averageVisible;
        requestRepaint();
    }

    public String getAverageColor() {
        return averageColor;
    }

    public void setAverageColor(String averageColor) {
        this.averageColor = averageColor;
        requestRepaint();
    }

    public boolean isMinmaxLabelsVisible() {
        return minmaxLabelsVisible;
    }

    public void setMinmaxLabelsVisible(boolean minmaxLabelsVisible) {
        this.minmaxLabelsVisible = minmaxLabelsVisible;
        requestRepaint();
    }

    public boolean isMinmaxDotsVisible() {
        return minmaxDotsVisible;
    }

    public void setMinmaxDotsVisible(boolean minmaxDotsVisible) {
        this.minmaxDotsVisible = minmaxDotsVisible;
        requestRepaint();
    }

    public String getMaxColor() {
        return maxColor;
    }

    public void setMaxColor(String maxColor) {
        this.maxColor = maxColor;
        requestRepaint();
    }

    public String getMinColor() {
        return minColor;
    }

    public void setMinColor(String minColor) {
        this.minColor = minColor;
        requestRepaint();
    }

}
