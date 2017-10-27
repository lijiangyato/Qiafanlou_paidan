package com.ings.gogopaidan.entity;

import java.util.List;
import java.util.Scanner;

public class MyCheckOrderEntity {
    private List<OrderDatas> data;

    public List<OrderDatas> getData() {
        return data;
    }

    public void setData(List<OrderDatas> data) {
        this.data = data;
    }

    public static class OrderDatas {

        // "orderno":"20160730180126",
        // "payno":null,
        // "payway":"����֧��",
        // "phone":null,
        // "orderclassify":"20160727223856",
        // "total":20,
        // "postage":0,
        // "createdate":"2016-07-30 18:01:26",
        // "mark":null,
        // "state":"������",
        // "orderPro":{
        // "orderPros":null
        // },
        private String orderno;
        private String payno;
        private String payway;
        private String phone;
        private String orderclassify;
        private Double total;
        private Double postage;
        private String state;
        private String createdate;
        private String consigneetime;
        private String mark;
        private MyorderPro orderPro;
        private String orderPros;
        private Myconsigee consignee;
        private String senduser;



        public String getSenduser() {
            return senduser;
        }

        public void setSenduser(String senduser) {
            this.senduser = senduser;
        }


        public String getConsigneetime() {
            return consigneetime;
        }

        public Myconsigee getMyconsigee() {
            return consignee;
        }

        public void setConsigneetime(String consigneetime) {
            this.consigneetime = consigneetime;
        }

        public void setMyconsigee(Myconsigee myconsigee) {
            this.consignee = myconsigee;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getPayno() {
            return payno;
        }

        public void setPayno(String payno) {
            this.payno = payno;
        }

        public String getPayway() {
            return payway;
        }

        public void setPayway(String payway) {
            this.payway = payway;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOrderclassify() {
            return orderclassify;
        }

        public void setOrderclassify(String orderclassify) {
            this.orderclassify = orderclassify;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getPostage() {
            return postage;
        }

        public void setPostage(Double postage) {
            this.postage = postage;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public MyorderPro getOrderPro() {
            return orderPro;
        }

        public void setOrderPro(MyorderPro orderPro) {
            this.orderPro = orderPro;
        }

        public String getOrderPros() {
            return orderPros;
        }

        public void setOrderPros(String orderPros) {
            this.orderPros = orderPros;
        }


        public static class MyorderPro {
            // "proid":"f09ec7cb-ba68-43c9-b255-5ac05a1fdaaa",
            // "amount":1,
            // "price":20,
            // "star":0,
            // "pronum":1,
            // "proname":"�ع���",
            // "imgurl":"http://112.74.25.40:8086/upfile/gogo/2016-11/d5918930-365c-4e59-967f-4597b1bc2229.png"


            private String proid;
            private Double amount;
            private Double price;
            private Double star;
            private int pronum;
            private String proname;
            private String imgurl;

            public String getProid() {
                return proid;
            }

            public void setProid(String proid) {
                this.proid = proid;
            }

            public Double getAmount() {
                return amount;
            }

            public void setAmount(Double amount) {
                this.amount = amount;
            }

            public Double getPrice() {
                return price;
            }

            public void setPrice(Double price) {
                this.price = price;
            }

            public Double getStar() {
                return star;
            }

            public void setStar(Double star) {
                this.star = star;
            }

            public int getPronum() {
                return pronum;
            }

            public void setPronum(int pronum) {
                this.pronum = pronum;
            }

            public String getProname() {
                return proname;
            }

            public void setProname(String proname) {
                this.proname = proname;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

        }

        public static class Myconsigee {
            /**
             * consigneeid : c7448aa0-e207-4483-adb5-7f77b247bb59
             * consignee_name : 王超
             * sex :
             * consignee_phone : 18280867937
             * consignee_add : 观山湖区美的·林城时代
             * consignee_no :
             * gpsx : 106.652328
             * gpsy : 26.63925
             */
            private String consigneeid;
            private String phone;
            private String consignee_name;
            private String sex;
            private String consignee_phone;
            private String consignee_add;
            private String consignee_no;
            private String gpsx;
            private String gpsy;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getConsigneeid() {
                return consigneeid;
            }

            public void setConsigneeid(String consigneeid) {
                this.consigneeid = consigneeid;
            }

            public String getConsignee_name() {
                return consignee_name;
            }

            public void setConsignee_name(String consignee_name) {
                this.consignee_name = consignee_name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getConsignee_phone() {
                return consignee_phone;
            }

            public void setConsignee_phone(String consignee_phone) {
                this.consignee_phone = consignee_phone;
            }

            public String getConsignee_add() {
                return consignee_add;
            }

            public void setConsignee_add(String consignee_add) {
                this.consignee_add = consignee_add;
            }

            public String getConsignee_no() {
                return consignee_no;
            }

            public void setConsignee_no(String consignee_no) {
                this.consignee_no = consignee_no;
            }

            public String getGpsx() {
                return gpsx;
            }

            public void setGpsx(String gpsx) {
                this.gpsx = gpsx;
            }

            public String getGpsy() {
                return gpsy;
            }

            public void setGpsy(String gpsy) {
                this.gpsy = gpsy;
            }
        }


    }


}
