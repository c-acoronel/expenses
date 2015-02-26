package com.expenses.filter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andres on 18/11/2014.
 */
public class BasicAuthTest {
    @Test
    public void testDecodeAuthString() {
        String authString = "Basic YWRtaW46YWRtaW4=";
        String[] result = BasicAuth.decode(authString);
        Assert.assertEquals(2, result.length);
        Assert.assertEquals("admin", result[0]);
        Assert.assertEquals("admin", result[1]);
    }

}
