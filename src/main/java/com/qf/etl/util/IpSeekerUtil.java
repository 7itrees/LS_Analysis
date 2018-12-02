package com.qf.etl.util;

import com.qf.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class IpSeekerUtil extends IPSeeker {
    private static final Logger logger = Logger.getLogger(IPSeekerUtils.class);
    public static RegionInfo regionInfo = new RegionInfo();

    public static RegionInfo ipParser(String ip) {
        if (StringUtils.isNotEmpty(ip)) {
            //获取国家省市信息
            String country = IPSeeker.getInstance().getCountry(ip);
            if (StringUtils.isNotEmpty(country.trim())) {
                if (country.equals("局域网")) {   //192 10 等局域网
                    regionInfo.setCountry("中国");
                    regionInfo.setProvince("北京市");
                    regionInfo.setCity("昌平区");
                } else {
                    int index = country.indexOf("省");
                    if (index > 0) {
                        regionInfo.setCountry("中国");
                        regionInfo.setProvince(country.substring(0, index + 1));
                        int index2 = country.indexOf("市");
                        if (index2 > 0) {
                            regionInfo.setCity(country.substring(index + 1, index2 + 1));
                        } else {
                            String flag = country.substring(0, 2); //substring(x,y)不包括y
                            switch (flag) {
                                case "内蒙":
                                    regionInfo.setProvince("内蒙古");
                                    country.substring(3);
                                    System.out.println(country);
                                    index = country.indexOf("市");
                                    if (index > 0) {
                                        regionInfo.setCity(country.substring(0, index + 1));
                                    }
                                    break;
                                case "宁夏":
                                case "广西":
                                case "西藏":
                                case "新疆":
                                    regionInfo.setProvince(flag + "省");
                                    country = country.substring(2);
                                    index = country.indexOf("市");
                                    if (index > 0) {
                                        regionInfo.setCity(country.substring(0, index + 1));
                                    }
                                    break;
                                case "北京":
                                case "天津":
                                case "上海":
                                case "重庆":
                                    regionInfo.setProvince(flag + "市");
                                    country.substring(2);
                                    index = country.indexOf("区");
                                    if (index > 0) {
                                        char ch = country.charAt(index - 1);
                                        if (ch != '小' || ch != '校' || ch != '军') {
                                            regionInfo.setCity(country.substring(0, index + 1));
                                        }
                                    }

                                    index = country.indexOf("县");
                                    if (index > 0) {
                                        regionInfo.setCity(country.substring(0, index + 1));
                                    }
                                    break;
                                case "香港":
                                case "澳门":
                                case "台湾":
                                    regionInfo.setProvince(flag + "特别行政区");
                                    break;
                                default:
                                    break;
                            }
                        }

                    }
                }
            }
        }
        return regionInfo;
    }

    public static class RegionInfo {
        private String default_value = GlobalConstants.DEFAULT_VALUE;
        public String country = default_value;
        public String province = default_value;
        public String city = default_value;

        public RegionInfo() {
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "RegionInfo{" +
                    "country='" + country + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }
}



