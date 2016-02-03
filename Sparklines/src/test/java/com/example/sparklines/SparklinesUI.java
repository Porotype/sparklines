package com.example.sparklines;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.vaadin.addonhelpers.AbstractTest;
import org.vaadin.sparklines.Sparklines;

@SuppressWarnings("serial")
@Theme("sparklines")
public class SparklinesUI extends AbstractTest {

    @Override
    public Component getTestComponent() {
        final VerticalLayout content = new VerticalLayout();
        content.setStyleName("black");
        content.setHeight("100%");
        content.setMargin(true);

        Label info = new Label(
                "<h1>Sparklines for GWT and Vaadin</h1><h2>\"small, intense, simple datawords\"</h2><a href=\"http://www.edwardtufte.com/bboard/q-and-a-fetch-msg?msg_id=0001OR\">Refer to Edward Tufte for more information about sparklines</a> (must read)");
        info.setContentMode(Label.CONTENT_XHTML);
        content.addComponent(info);

        List<Integer> data1 = Arrays.asList(60, 62, 55, 62, 63, 64, 63, 65, 68,
                65, 69, 70, 75, 74, 75, 74, 78, 76, 74, 85, 70, 65, 63, 64, 69,
                74, 65);
        Integer[] data2 = new Integer[30];
        List<Integer> data3 = Arrays.asList(60, 62, 55, 62, 63, 64, 63, 65, 68,
                65, 69, 70, 75, 74, 75, 74, 78, 76, 74, 85, 70, 65, 63, 64, 69,
                74, 55);
        List<String> data4 = Arrays.asList("60", "62", "55", "62", "63", "64",
                "63", "65", "68", "65", "69", "70", "75", "74", "75", "74",
                "78", "76", "74", "85", "70", "65", "63", "64", "69", "74",
                "55");

        Sparklines s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("All extras turned off");
        s.setValue(data1);
        s.setValueDotVisible(false);
        s.setValueLabelVisible(false);
        s.setMinmaxLabelsVisible(false);
        s.setMinmaxDotsVisible(false);
        content.addComponent(s);
        s.setWidth("100px");
        s.setHeight("50px");

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription(
                "Shows current value, visually connected to graph with color");
        s.setValue(data1);
        s.setMinmaxLabelsVisible(false);
        s.setMinmaxDotsVisible(false);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Current, minimum and maximum values are shown");
        s.setValue(data1);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Adds line indicating average");
        s.setValue(data1);
        s.setAverageVisible(true);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Shaded area indicates 'normal' range");
        s.setValue(data1);
        s.setAverageVisible(true);
        s.setNormalRangeColor("#444"); // default works better on white
        s.setNormalRangeMax(80);
        s.setNormalRangeMin(60);
        s.setNormalRangeVisible(true);
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Everything turned on");
        s.setValue(data1);
        s.setAverageVisible(true);
        s.setNormalRangeColor("#444"); // default works better on white
        s.setNormalRangeMax(80);
        s.setNormalRangeMin(60);
        s.setNormalRangeVisible(true);
        s.setMaxColor("#f69");
        s.setMinColor("#6f9");
        content.addComponent(s);

        s = new Sparklines("Stuff", 0, 0, 50, 100);
        s.setDescription("Color indicates if min/max is the current value");
        s.setValue(data3);
        s.setAverageVisible(true);
        s.setNormalRangeColor("#444"); // default works better on white
        s.setNormalRangeMax(80);
        s.setNormalRangeMin(60);
        s.setNormalRangeVisible(true);
        s.setMaxColor("#f69");
        s.setMinColor("#6f9");
        content.addComponent(s);

        for (int i = 0; i < data2.length; i++) {
            data2[i] = Integer.valueOf((int) (Math.random() * 140 + 60));
        }
        s = new Sparklines("Pulse", 0, 0, 0, 200);
        s.setDescription("Minimum and maximum can be color-connected as well");
        s.setMaxColor("#f69");
        s.setMinColor("#6f9");
        s.setValue(Arrays.asList(data2));
        content.addComponent(s);

        data2 = new Integer[30];
        for (int i = 0; i < data2.length; i++) {
            data2[i] = Integer.valueOf((int) (Math.random() * 140 + 60));
        }

        s = new Sparklines("Strings", 0, 0, 50, 100);
        s.setDescription("Using strings as data");
        s.setValue(data4);
        content.addComponent(s);

        HorizontalLayout hl = new HorizontalLayout();
        content.addComponent(hl);
        content.setExpandRatio(hl, 1);
        content.setComponentAlignment(hl,
                Alignment.MIDDLE_LEFT);

        final Sparklines s2 = new Sparklines("Random", 200, 70, 0, 200) {
            // Round to two decimal points.
            // Can be used for lotsa stuff, though.
            protected Double[] translateData(Collection data) {
                Double[] d = super.translateData(data);
                for (int i = 0; i < d.length; i++) {
                    d[i] = Math.round(d[i] * 100.0) / 100.0;
                }
                return d;
            }

        };
        s2.setWidth("380px");
        s2.setValueColor("#69f");
        s2.setPathWidth(2);
        // s2.setNormalRangeVisible(true);
        // s2.setNormalRangeMax(130);
        // s2.setNormalRangeMin(80);
        // s2.setNormalRangeColor("#444");
        // s2.setAverageVisible(true);
        // s2.setAverageColor("#999");
        s2.setMaxColor("#f69");
        s2.setMinColor("#6f9");
        s2.setDescription("40 random values between 40 and 200");
        s2.setValue(random());

        s2.setValue(s2.toString()); // test

        hl.addComponent(s2);
        hl.setComponentAlignment(s2, Alignment.MIDDLE_LEFT);

        Button rand = new Button("Randomize", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                s2.setValue(random());
            }
        });
        hl.addComponent(rand);
        hl.setComponentAlignment(rand, Alignment.MIDDLE_LEFT);

        final ConfigureWindow configure = new ConfigureWindow(s2);
        Button conf = new Button("Configure", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (configure.getParent() == null || !configure.isVisible()) {
                    addWindow(configure);
                }
            }
        });
        hl.addComponent(conf);
        hl.setComponentAlignment(conf, Alignment.MIDDLE_LEFT);

        return content;
    }

    private Double[] random() {
        Double[] data = new Double[40];
        for (int i = 0; i < data.length; i++) {
            data[i] = Math.random() * 160 + 40;
        }
        return data;
    }

    class ConfigureWindow extends Window {

        ConfigureWindow(Sparklines s) {
            VerticalLayout content = new VerticalLayout();
            setContent(content);

            setWidth("400px");
            setHeight("300px");

            final Form conf = new Form();
            conf.setFormFieldFactory(new FormFieldFactory() {

                public Field createField(Item item, Object propertyId,
                        Component uiContext) {
                    if ("value".equals(propertyId)) {
                        return new Sparklines(null, 50, 20, 0, 200);
                    } else if ("parent".equals(propertyId)) {
                        return null;
                    }
                    return DefaultFieldFactory.get().createField(item,
                            propertyId, uiContext);
                }
            });
            // This don't seem to work any more.  getValue causes issues in 
            // Vaadin these days :-(. Why is Sparklines a Field??
//            conf.setItemDataSource(new BeanItem<Sparklines>(s));
//            conf.setItemDataSource(new BeanItem<Sparklines>(s, new String[]{
//                "averageVisible", "averageColor", "caption", "description",
//                "displayRangeMax", "displayRangeMin", "graphHeight",
//                "graphWidth", "minmaxDotsVisible", "minmaxLabelsVisible",
//                "maxColor", "minColor", "normalRangeVisible",
//                "normalRangeColor", "normalRangeMin", "normalRangeMax",
//                "pathColor", "pathWidth", "valueDotVisible",
//                "valueLabelVisible", "valueColor"}));
            /*-
            conf.setVisibleItemProperties(new String[] { "averageVisible",
                    "averageColor", "caption", "description",
                    "displayRangeMax", "displayRangeMin", "graphHeight",
                    "graphWidth", "minmaxDotsVisible", "minmaxLabelsVisible",
                    "maxColor", "minColor", "normalRangeVisible",
                    "normalRangeColor", "normalRangeMin", "normalRangeMax",
                    "pathColor", "pathWidth", "valueDotVisible",
                    "valueLabelVisible", "valueColor" });
                    -*/
            conf.setBuffered(true);
            content.addComponent(conf);

            content.addComponent(new Button("Apply",
                    new Button.ClickListener() {
                public void buttonClick(ClickEvent event) {
                    conf.commit();
                }
            }));
        }

    }

}
