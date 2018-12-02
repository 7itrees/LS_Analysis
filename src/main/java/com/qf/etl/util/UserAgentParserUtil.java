package com.qf.etl.util;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserAgentParserUtil {
    private static Logger logger = Logger.getLogger(UserAgentParserUtil.class);
    private static UASparser uaSparser = null;
    static {
        try {
            uaSparser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            logger.error("获取uaSparser异常", e);
        }
    }

    public static AgentInfo parserUserAgent(String userAgent) {
        AgentInfo agentInfo = null;
        try {
            if (StringUtils.isNotEmpty(userAgent)) {
                UserAgentInfo userAgentInfo = uaSparser.parse(userAgent);
                if (userAgentInfo != null) {
                    agentInfo = new AgentInfo();
                    agentInfo.setBrowserName(userAgentInfo.getUaFamily());
                    agentInfo.setBrowserVersion(userAgentInfo.getBrowserVersionInfo());
                    agentInfo.setOsName(userAgentInfo.getOsName());
                    agentInfo.setOsVersion(userAgentInfo.getOsFamily());

                }
            }
        } catch (IOException e) {
            logger.error("解析useragent异常", e);
        }
        return agentInfo;
    }

    public static class AgentInfo {
        private String browserName;
        private String browserVersion;
        private String osName;
        private String osVersion;

        public AgentInfo(){}

        public AgentInfo(String browserName, String browserVersion, String osName, String osVersion) {
            this.browserName = browserName;
            this.browserVersion = browserVersion;
            this.osName = osName;
            this.osVersion = osVersion;
        }

        public String getBrowserName() {
            return browserName;
        }

        public void setBrowserName(String browserName) {
            this.browserName = browserName;
        }

        public String getBrowserVersion() {
            return browserVersion;
        }

        public void setBrowserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
        }

        public String getOsName() {
            return osName;
        }

        public void setOsName(String osName) {
            this.osName = osName;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        @Override
        public String toString() {
            return "AgentInfo{" +
                    "browserName='" + browserName + '\'' +
                    ", browserVersion='" + browserVersion + '\'' +
                    ", osName='" + osName + '\'' +
                    ", osVersion='" + osVersion + '\'' +
                    '}';
        }
    }

}
