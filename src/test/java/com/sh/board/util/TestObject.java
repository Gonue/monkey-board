package com.sh.board.util;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class TestObject {
    String str;
    String listStr1;
    String listStr2;
    String nullStr;
    Integer number;
    Double floatingNumber;
    Boolean bool;
    BigDecimal bigDecimal;
    FormDataEncoderTest.TestEnum testEnum;

}
