package com.wt.bl.export;

import java.awt.*;
import java.util.Arrays;

/**
 * @author WangTao
 * Created at 18/11/28 上午11:38.
 */
public enum ColorEnums {

    GREEN(Color.GREEN),
    RED(Color.RED),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW)
    ;
    private Color color;

    ColorEnums(Color color) {
        this.color = color;
    }

}
