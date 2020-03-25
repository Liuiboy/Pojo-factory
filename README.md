# pojo-factery使用

## 1配置xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE data [
        <!ELEMENT data (path,value*)>
        <!ELEMENT path (#PCDATA)>
        <!ELEMENT value (#PCDATA|list)*>
        <!ELEMENT list (#PCDATA)>
        <!ATTLIST value id CDATA #REQUIRED >
        <!ATTLIST value type (int|String|boolean) #REQUIRED >]>
<data>
    <path>org.model</path>
</data>
```

## 2配置pojo属性生成方式

#### 1.通过java程序

 配置class所在的包  

![image-20200325173722917](C:\Users\krirt\AppData\Roaming\Typora\typora-user-images\image-20200325173722917.png)

 <path>org.model</path>

```
//根据同属性类型来继承不同的接口
public class Name implements StringModel {
    @Override
    public String createString() {
  //根据编写生成规则,然后return出去


        return "";
    }
}

注:一个class可也继承多个类型接口,程序根据属性的类型自动选择不同方法
```

#### 2配置加注解

```
@Data("id名")加在属性的set方法上
id区分大小写
<data>
    <path>org.model</path>
    <value id="user" type="String">张三</value>
</data>
 name的值会在<value>标签下的list随机选择一个
<data>
    <path>org.model</path>
    <value id="user" type="String">
    <list>1</list>
    <list>2</list>
    <list>3</list>
    </value>
</data>


@Data("name")
public void setNeme(String neme) {
    this.name = neme;
}
```

#### 4注解

@value("固定值")

## 3.实例

```
public static void main(String[] args) {
    Pojofactory pojofactory=new ClasspathXmlPojoFactory("C:\\Users\\krirt\\IdeaProjects\\APP-test\\src\\org\\liu\\pojo-factory.xml");
    User user=pojofactory.getPojo(User.class);
}
```
