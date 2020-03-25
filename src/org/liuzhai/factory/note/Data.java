package org.liuzhai.factory.note;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface Data {

    /**
     * id
     *
     * @return id
     */

    String value();
}
