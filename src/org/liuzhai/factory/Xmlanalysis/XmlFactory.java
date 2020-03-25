package org.liuzhai.factory.Xmlanalysis;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlFactory {

    private String url;
    private List<Element> body;
    private Map<String, Object> values;
    private Document document;


    public XmlFactory(String url) {
        SAXReader saxReader = new SAXReader();
        values = new HashMap<>();
        try {
            document = saxReader.read(url);
            body = document.getRootElement().elements("value");
            System.out.println(body.get(0).elements().size());
            init();
            //System.out.println(((String [])values.get("zhk"))[1]);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {


    }

    public String getpath() {
        return ((Element) document.getRootElement().elements("path").get(0)).getText();
    }

    private void init() {
        for (Element element : body) {
            put(element);
        }

    }

    public String getString(String id) {
        String val = null;
        try {
            val = (String) values.get(id);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
            String[] va = (String[]) (values.get(id));
            val = va[(int) (Math.random() * va.length)];
        }
        return val;
    }

    public int getInt(String id) {
        int val = 12;
        try {
            val = (int) values.get(id);
        } catch (NullPointerException e) {
            e.printStackTrace();

        } catch (ClassCastException e) {
            e.printStackTrace();
            int[] va = (int[]) (values.get(id));
            val = va[(int) (Math.random() * va.length)];
        }
        return val;
    }

    private void put(Element element) {
        int size = element.elements().size();
        if (size == 0) {
            switch (element.attributeValue("type")) {
                case "int":
                    values.put(element.attributeValue("id"), Integer.parseInt(element.getText()));
                    break;
                case "String":
                    values.put(element.attributeValue("id"), element.getText());
                    break;
            }
        } else {
            switch (element.attributeValue("type")) {
                case "int":
                    int[] ival = new int[size];
                    for (int i = 0; i < size; i++) {
                        ival[i] = Integer.parseInt(((List<Element>) (element.elements())).get(i).getText());
                    }
                    values.put(element.attributeValue("id"), ival);
                    break;
                case "String":
                    String[] sval = new String[size];
                    for (int i = 0; i < size; i++) {
                        sval[i] = ((List<Element>) (element.elements())).get(i).getText();
                    }
                    values.put(element.attributeValue("id"), sval);
                    break;
            }
        }
    }

    /**
     * 更新并通过Id查询Element
     *
     * @param id id
     * @return Node
     */
    private Element queryElementById(String id) {
        for (Element element : body) {
            if (element.attributeValue("id") == id) {
                return element;
            }

        }
        return null;
    }
}
