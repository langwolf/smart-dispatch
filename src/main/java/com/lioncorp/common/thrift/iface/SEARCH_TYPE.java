/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lioncorp.common.thrift.iface;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum SEARCH_TYPE implements org.apache.thrift.TEnum {
  PC_RELATED(1),
  MOBILE_RELATED(2);

  private final int value;

  private SEARCH_TYPE(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static SEARCH_TYPE findByValue(int value) { 
    switch (value) {
      case 1:
        return PC_RELATED;
      case 2:
        return MOBILE_RELATED;
      default:
        return null;
    }
  }
}
