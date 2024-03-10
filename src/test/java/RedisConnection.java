package com.paperless.tests.OTP;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;



public class VerifyOTP {

    private static String BASE_URL = " ";

    private Jedis jedis;

    @BeforeClass
    public void setUp() {
        jedis = new Jedis("paperless-cache-test", 6379);
        jedis.hset("test", "012", "123456");
    }

    @Test(priority = 1)
    public void OtpShouldBeInvalidAfterTimerExpiry() throws InterruptedException {

        String OTPFromRedis = jedis.hget("test", "012");
        System.out.println(OTPFromRedis);

         Thread.sleep(300000);

        Response response = RestAssured.given()
                .body(OTPFromRedis)
                .post("");

        Assert.assertNotEquals(response.getStatusCode(), 200);

        Assert.assertTrue(response.getBody().asString().contains("timer_expired"));
        
    }
