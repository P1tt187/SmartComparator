package smartcomparator.dataclasses;

import smartcomparator.annotations.MethodName;
import smartcomparator.annotations.NamedSort;
import smartcomparator.annotations.NamedSorts;

/**
 * Created with IntelliJ IDEA.
 * User: fabian
 * Date: 31.10.13
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */

@NamedSorts({
        @NamedSort(name= "nativeBoolean",methodNames = {@MethodName(name = "isNativeBoolean")}),
        @NamedSort(name= "wrapperBoolean",methodNames = {@MethodName(name = "getWrapperBoolean")}),
        @NamedSort(name= "nativeChar",methodNames = {@MethodName(name = "getNativeChar")}),
        @NamedSort(name= "wrapperChar",methodNames = {@MethodName(name = "getWrapperChar")}),
        @NamedSort(name= "nativeShort",methodNames = {@MethodName(name = "getNativeShort")}),
        @NamedSort(name= "wrapperShort",methodNames = {@MethodName(name = "getWrapperShort")}),
        @NamedSort(name= "nativeInt",methodNames = {@MethodName(name = "getNativeInt")}),
        @NamedSort(name= "wrapperInt",methodNames = {@MethodName(name = "getWrapperInt")}),
        @NamedSort(name= "nativeLong",methodNames = {@MethodName(name = "getNativeLong")}),
        @NamedSort(name= "wrapperLong",methodNames = {@MethodName(name = "getWrapperLong")}),
        @NamedSort(name= "nativeFloat",methodNames = {@MethodName(name = "getNativeFloat")}),
        @NamedSort(name= "wrapperFloat",methodNames = {@MethodName(name = "getWrapperFloat")}),
        @NamedSort(name= "nativeDouble",methodNames = {@MethodName(name = "getNativeDouble")}),
        @NamedSort(name= "wrapperDouble",methodNames = {@MethodName(name = "getWrapperDouble")})

})

public class TestWrapper {

    private boolean nativeBoolean;

    private Boolean wrapperBoolean;

    private char nativeChar;

    private Character wrapperChar;

    private short nativeShort;

    private Short wrapperShort;

     private int nativeInt;

    private Integer wrapperInt;

    private long nativeLong;

    private Long wrapperLong;

    private float nativeFloat;

    private Float wrapperFloat;

    private double nativeDouble;

    private Double wrapperDouble;

    public boolean isNativeBoolean() {
        return nativeBoolean;
    }

    public Boolean getWrapperBoolean() {
        return wrapperBoolean;
    }

    public char getNativeChar() {
        return nativeChar;
    }

    public Character getWrapperChar() {
        return wrapperChar;
    }

    public short getNativeShort() {
        return nativeShort;
    }

    public Short getWrapperShort() {
        return wrapperShort;
    }

    public int getNativeInt() {
        return nativeInt;
    }

    public Integer getWrapperInt() {
        return wrapperInt;
    }

    public long getNativeLong() {
        return nativeLong;
    }

    public Long getWrapperLong() {
        return wrapperLong;
    }

    public float getNativeFloat() {
        return nativeFloat;
    }

    public Float getWrapperFloat() {
        return wrapperFloat;
    }

    public double getNativeDouble() {
        return nativeDouble;
    }

    public Double getWrapperDouble() {
        return wrapperDouble;
    }

    public TestWrapper(boolean nativeBoolean, Boolean wrapperBoolean, char nativeChar, Character wrapperChar, short nativeShort, Short wrapperShort, int nativeInt, Integer wrapperInt, long nativeLong, Long wrapperLong, float nativeFloat, Float wrapperFloat, double nativeDouble, Double wrapperDouble) {
        this.nativeBoolean = nativeBoolean;
        this.wrapperBoolean = wrapperBoolean;
        this.nativeChar = nativeChar;
        this.wrapperChar = wrapperChar;
        this.nativeShort = nativeShort;
        this.wrapperShort = wrapperShort;
        this.nativeInt = nativeInt;
        this.wrapperInt = wrapperInt;
        this.nativeLong = nativeLong;
        this.wrapperLong = wrapperLong;
        this.nativeFloat = nativeFloat;
        this.wrapperFloat = wrapperFloat;
        this.nativeDouble = nativeDouble;
        this.wrapperDouble = wrapperDouble;
    }
}
