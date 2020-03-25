package org.liuzhai.factory;

import java.util.List;

public interface Pojofactory {
    <T> List<T> getPojo(Class<T> pojo, int amount);

    <T> T getPojo(Class<T> pojo);
}
