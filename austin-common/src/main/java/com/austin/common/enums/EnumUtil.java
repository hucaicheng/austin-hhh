package com.austin.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author hucaicheng
 * 枚举工具类（获取枚举的元素、描述、获取枚举的code、获取枚举的code列表）
 */
public class EnumUtil {

    /**
     * 获取满足条件的枚举元素
     * @param code code
     * @param enumClass 枚举类
     * @param <T>
     * @return 枚举元素
     */
    public static <T extends PowerfulEnum> T getEnumByCode(Integer code , Class<T> enumClass){
        //getEnumConstants()方法用于返回枚举常量数组，换句话说，可以说此方法用于返回此枚举类的元素。
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(e.getCode(),code))
                .findFirst().orElse(null);
    }

    /**
     * 获取当前枚举类的所有code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends PowerfulEnum> List<Integer> getCodeList(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(PowerfulEnum::getCode).collect(Collectors.toList());
    }
}
