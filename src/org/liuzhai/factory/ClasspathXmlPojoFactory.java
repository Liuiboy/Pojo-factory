package org.liuzhai.factory;

import org.liuzhai.factory.Xmlanalysis.XmlFactory;
import org.liuzhai.factory.connector.IntModel;
import org.liuzhai.factory.connector.ModeFactory;
import org.liuzhai.factory.connector.StringModel;
import org.liuzhai.factory.note.Data;
import org.liuzhai.factory.note.Value;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * pojo工厂类
 */

public class ClasspathXmlPojoFactory implements Pojofactory {
    private static XmlFactory xml;
    private static ModeFactory modeFactory = new ModeFactory();
    private String classpath;

    public ClasspathXmlPojoFactory(String classpath) {
        xml = new XmlFactory(classpath);
        classpath = xml.getpath();
        modeFactory.setClasspath(classpath);
    }

    /**
     * pojo List集合获取
     *
     * @param pojo   pojo class
     * @param amount pojo数量
     * @param <T>    pojo类
     * @return pojo List集合
     */
    public <T> List<T> getPojo(Class<T> pojo, int amount) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            list.add(getPojo(pojo));
        }
        return list;

    }

    /**
     * pojo获取
     *
     * @param pojo pojo class
     * @param <T>  pojo类
     * @return pojo实例
     */
    public <T> T getPojo(Class<T> pojo) {

        T user = null;
        try {
            user = pojo.newInstance();
            Method[] M = user.getClass().getMethods();
            autoAssign(M, user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void autoAssign(Method[] method, Object object) {
        try {

            String pattern = "set.*";
            for (Method m : method) {
                if (Pattern.matches(pattern, m.getName())) {
                    String type = m.getGenericParameterTypes()[0].getTypeName();
                    if (m.isAnnotationPresent(Data.class)) {//检测Data注释
                        Data data = m.getAnnotation(Data.class);
                        if (type.equals("int")) {
                            m.invoke(object, xml.getInt(data.value()));
                        }
                        if (type.equals("java.lang.String")) {
                            m.invoke(object, xml.getString(data.value()));
                        }

                    }
                    if (m.isAnnotationPresent(Value.class)) {//检测Data注释
                        Value data = m.getAnnotation(Value.class);
                        if (type.equals("int")) {
                            m.invoke(object, Integer.parseInt(data.value()));
                        }
                        if (type.equals("java.lang.String")) {
                            m.invoke(object, data.value());
                        }

                    } else if (type.equals("int")) {

                        m.invoke(object, ((IntModel) modeFactory.model(m.getName())).createInt());


                    } else if (type.equals("java.lang.String")) {
                        m.invoke(object, ((StringModel) modeFactory.model(m.getName())).createString());

                    } else if (type.equals("boolean")) {
                        m.invoke(object, true);
                    } else {
                        m.invoke(object, getPojo(Class.forName(type)));

                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
