package com.nyw.pets.activity.util;

import java.util.List;

public class GetSomeMonthPetsDataUtil {


    /**
     * code : 1
     * message : 获取成功
     * data : {"bath":["2020-09-03","2020-09-10","2020-09-17","2020-09-24","2020-10-01","2020-10-08","2020-10-15","2020-10-22","2020-10-29"],"expelling":["2020-09-03","2020-09-10","2020-09-17","2020-09-24","2020-10-01","2020-10-08","2020-10-15","2020-10-22","2020-10-29"],"vaccin_0":["2020-09-27"],"vaccin_1":["2020-09-27"],"scedule":{"vaccin_0":["2020-08-25","2020-08-27"],"vaccin_1":["2020-08-25"],"expelling":["2020-08-20","2020-08-21","2020-08-27"],"bath":["2020-08-27"]}}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bath : ["2020-09-03","2020-09-10","2020-09-17","2020-09-24","2020-10-01","2020-10-08","2020-10-15","2020-10-22","2020-10-29"]
         * expelling : ["2020-09-03","2020-09-10","2020-09-17","2020-09-24","2020-10-01","2020-10-08","2020-10-15","2020-10-22","2020-10-29"]
         * vaccin_0 : ["2020-09-27"]
         * vaccin_1 : ["2020-09-27"]
         * scedule : {"vaccin_0":["2020-08-25","2020-08-27"],"vaccin_1":["2020-08-25"],"expelling":["2020-08-20","2020-08-21","2020-08-27"],"bath":["2020-08-27"]}
         */

        private SceduleBean scedule;
        private List<String> bath;
        private List<String> expelling;
        private List<String> vaccin_0;
        private List<String> vaccin_1;

        public SceduleBean getScedule() {
            return scedule;
        }

        public void setScedule(SceduleBean scedule) {
            this.scedule = scedule;
        }

        public List<String> getBath() {
            return bath;
        }

        public void setBath(List<String> bath) {
            this.bath = bath;
        }

        public List<String> getExpelling() {
            return expelling;
        }

        public void setExpelling(List<String> expelling) {
            this.expelling = expelling;
        }

        public List<String> getVaccin_0() {
            return vaccin_0;
        }

        public void setVaccin_0(List<String> vaccin_0) {
            this.vaccin_0 = vaccin_0;
        }

        public List<String> getVaccin_1() {
            return vaccin_1;
        }

        public void setVaccin_1(List<String> vaccin_1) {
            this.vaccin_1 = vaccin_1;
        }

        public static class SceduleBean {
            private List<String> vaccin_0;
            private List<String> vaccin_1;
            private List<String> expelling;
            private List<String> bath;

            public List<String> getVaccin_0() {
                return vaccin_0;
            }

            public void setVaccin_0(List<String> vaccin_0) {
                this.vaccin_0 = vaccin_0;
            }

            public List<String> getVaccin_1() {
                return vaccin_1;
            }

            public void setVaccin_1(List<String> vaccin_1) {
                this.vaccin_1 = vaccin_1;
            }

            public List<String> getExpelling() {
                return expelling;
            }

            public void setExpelling(List<String> expelling) {
                this.expelling = expelling;
            }

            public List<String> getBath() {
                return bath;
            }

            public void setBath(List<String> bath) {
                this.bath = bath;
            }
        }
    }
}
