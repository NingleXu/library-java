package cn.ningle;

import cn.ningle.library.config.NetProxyConfig;
import cn.ningle.library.entity.QueryCondition;
import cn.ningle.library.service.impl.ZLibrary;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        NetProxyConfig netProxyConfig = new NetProxyConfig();
        netProxyConfig.setIp("127.0.0.1");
        netProxyConfig.setPort(80);
        ZLibrary zLibrary = new ZLibrary(netProxyConfig);

        System.out.println(zLibrary.getSearchInfoURL("5787032", "ef6e3d"));

        System.out.println(zLibrary.getSearchListURL(new QueryCondition("小说", 1)));

    }
}
