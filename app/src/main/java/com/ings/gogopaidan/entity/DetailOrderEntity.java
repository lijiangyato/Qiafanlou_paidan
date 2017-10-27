package com.ings.gogopaidan.entity;

import java.util.List;

public class DetailOrderEntity {
	private Boolean success;
	private String msg;

	private DetailOrderDatas data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DetailOrderDatas getData() {
		return data;
	}

	public void setData(DetailOrderDatas data) {
		this.data = data;
	}

	public static class DetailOrderDatas {

		// "orderno":"G20161216060553_11",
		// "takeself":null,
		// "payway":"»õµ½¸¶¿î",
		// "versionno":null,
		// "total":22.5,
		// "postage":0,
		// "createdate":"2016-12-16 18:05:53",
		// "mark":null,
		// "state":"´ýÅäËÍ",
		// "orderPro":null,

		private String orderno;
		private String takeself;
		private String payway;
		private String versionno;
		private float total;
		private float postage;
		private String createdate;
		private String mark;
		private String state;
		private String orderPro;
		private List<OrderProducts> orderPros;
		private UserMsg consignee;

		public String getOrderno() {
			return orderno;
		}

		public void setOrderno(String orderno) {
			this.orderno = orderno;
		}

		public String getTakeself() {
			return takeself;
		}

		public void setTakeself(String takeself) {
			this.takeself = takeself;
		}

		public String getPayway() {
			return payway;
		}

		public void setPayway(String payway) {
			this.payway = payway;
		}

		public String getVersionno() {
			return versionno;
		}

		public void setVersionno(String versionno) {
			this.versionno = versionno;
		}

		public float getTotal() {
			return total;
		}

		public void setTotal(float total) {
			this.total = total;
		}

		public float getPostage() {
			return postage;
		}

		public void setPostage(float postage) {
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

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getOrderPro() {
			return orderPro;
		}

		public void setOrderPro(String orderPro) {
			this.orderPro = orderPro;
		}

		public List<OrderProducts> getOrderPros() {
			return orderPros;
		}

		public void setOrderPros(List<OrderProducts> orderPros) {
			this.orderPros = orderPros;
		}

		public UserMsg getConsignee() {
			return consignee;
		}

		public void setConsignee(UserMsg consignee) {
			this.consignee = consignee;
		}

		public static class OrderProducts {
			// "proid":"116c9c14-11ca-48bf-8feb-2502472c7e64",
			// "amount":1,
			// "price":9,
			// "star":5,
			// "pronum":0,
			// "proname":"ÍÁ¶¹Ë¿",
			// "imgurl":"
			private String proid;
			private int amount;
			private float price;
			private float star;
			private int pronum;
			private String proname;
			private String imgurl;
			private String mark;

			public String getProid() {
				return proid;
			}

			public void setProid(String proid) {
				this.proid = proid;
			}

			public int getAmount() {
				return amount;
			}

			public void setAmount(int amount) {
				this.amount = amount;
			}

			public float getPrice() {
				return price;
			}

			public void setPrice(float price) {
				this.price = price;
			}

			public float getStar() {
				return star;
			}

			public void setStar(float star) {
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

			public String getMark() {
				return mark;
			}

			public void setMark(String mark) {
				this.mark = mark;
			}

		}

		public static class UserMsg {
			// "consigneeid":"34566de9-d32f-421d-8048-506e7f62baad",
			// "phone":"15185045789",
			// "consignee_name":"ÍÃ×Ó",
			// "sex":"Å®Ê¿ ",
			// "consignee_phone":"15516082523",
			// "consignee_add":"Å£Æ¤Ìï",
			// "consignee_no":"2ºÅÂ¥",
			// "gpsx":"106.658354",
			// "gpsy":"26.669045"

			private String consigneeid;
			private String phone;
			private String consignee_name;
			private String sex;
			private String consignee_phone;
			private String consignee_add;
			private String consignee_no;
			private String gpsx;
			private String gpsy;

			public String getConsigneeid() {
				return consigneeid;
			}

			public void setConsigneeid(String consigneeid) {
				this.consigneeid = consigneeid;
			}

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
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
