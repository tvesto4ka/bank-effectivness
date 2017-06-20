package ru.mipt.sbt.builder;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author dinyat
 *         16/06/2017
 */
public class ValueTest {

    @Test
    public void testDate() throws Exception {
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");


        System.out.println(formatter.parse("31.01.2015"));
    }
}
