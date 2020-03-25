package org.liuzhai.factory.connector;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据生成器 工厂
 */
public class ModeFactory {
    private Map<String, Model> map = new HashMap<>();
    private String classpath;

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    /**
     * 获取int生成器
     *
     * @param methodName set方法名
     * @return int生成器
     */
    public Model model(String methodName) {
        methodName = methodName.replaceFirst("set", "");
        Model model = null;
        try {
            if (map.get(methodName) != null) {
                System.out.println("从容器获取" + methodName);
                model = map.get(methodName);
            } else {

                System.out.println("以创建并加入容器");

                Class ass = Class.forName(classpath + "." + methodName);
                model = (Model) ass.newInstance();
                map.put(methodName, model);

            }
        } catch (ClassNotFoundException e) {//默认配置
            model = new Default();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

}
