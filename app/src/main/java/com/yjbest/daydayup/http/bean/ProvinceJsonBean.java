package com.yjbest.daydayup.http.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Description:
 * Data：2019/1/30-10:59
 * Author: DerMing_You
 */
public class ProvinceJsonBean implements IPickerViewData {

    private String id;
    private String name;
    private String latitude;
    private String longitude;
    private ArrayList<CityBean> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ArrayList<CityBean> getChild() {
        return child;
    }

    public void setChild(ArrayList<CityBean> child) {
        this.child = child;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    public static class CityBean {
        private String id;
        private String name;
        private String latitude;
        private String longitude;
        private ArrayList<AreaBean> child;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public ArrayList<AreaBean> getChild() {
            return child;
        }

        public void setChild(ArrayList<AreaBean> child) {
            this.child = child;
        }

        public static class AreaBean {
            private String id;
            private String name;
            private String latitude;
            private String longitude;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }
    }
}
