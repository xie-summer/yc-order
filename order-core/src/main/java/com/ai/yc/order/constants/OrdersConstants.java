package com.ai.yc.order.constants;

import java.math.BigDecimal;

public final class OrdersConstants {
	public final static String TENANT_ID = "yeecloud";
	public final static String YC_ORDERSUBMISSION_TOPIC = "ycOrderSubmissionTopic";
	
	/**
	 * 系统操作
	 */
	public final static String SYS_OPER_ID = "1000011";
	/**
	 * 查询默认开始时间
	 */
	public final static String START_TIME = "2000-01-01 00:00:00";
	/**
	 * 评价显示状态
	 */
	public final static String EVALUTE_SHOW_STATE = "0";
	/**
	 * 业务类型-正常单
	 */
	public final static String NORMAL_BUSI_TYPE = "1";
	/**
	 * 领取状态-1已领取
	 */
	public final static String RECEIVE_ALREADY_STATE = "1";
	/**
	 * 未领取状态-0未领取
	 */
	public final static String RECEIVE_STATE = "0";
	/**
	 * 待领取-2
	 */
	public final static String WAIT_RECEIVE_STATE = "2";
	/**
	 * 0-未开始
	 */
	public final static String FINISH_STATE = "0";
	/**
	 * 1-进行中
	 */
	public final static String FINISHING_STATE = "1";
	/**
	 * 2-已完成
	 */
	public final static String FINISHED_STATE = "2";
	

	public final static class TranslatePrice {
		private TranslatePrice() {
		}

		public final static BigDecimal BASE_DOLLAR = new BigDecimal(15);

		public final static BigDecimal BASE_RMB = new BigDecimal(100);

		/**
		 * 币种 1：RMB 2：$
		 */
		public final static String DOLLAR = "2";

		public final static String RMB = "1";
	}
	public final static class OrdOperType {
		private OrdOperType() {
		}

		/**
		 * 0:翻译 1:审校 2:口译
		 */
		public final static String OPER_TRANSLATE_TYPE = "0";

		public final static String OPER_CHECK_TYPE = "1";
		
		public final static String OPER_MOUTH_TYPE = "2";
	}
	public final static class OrdOdFeeTotal {
		private OrdOdFeeTotal() {
		}

		/**
		 * in:收入 out:支出
		 */
		public final static String PAY_FLAG_IN = "in";

		public final static String PAY_FLAG_OUT = "out";
	}

	public final static class TranslateLevel {

		private TranslateLevel() {
		}

		/**
		 * 陪同翻译
		 */
		public final static String ACCOMAPANY_INTERPRETATION = "100110";

		/**
		 * 交替传译
		 */
		public final static String CONSECUTIVE_INTERPRETATION = "100120";

		/**
		 * 同声翻译
		 */
		public final static String SIMULTANEOUS_INTERPRETATION = "100130";

		/**
		 * 标准级或普通级
		 */
		public final static String ORDINARY = "100210";

		/**
		 * 专业级
		 */
		public final static String PERFESSION = "100220";

		/**
		 * 出版集
		 */
		public final static String PUBLISH = "100230";

	}

	/**
	 * 翻译类型
	 */
	public final static class TranslateType {

		private TranslateType() {
		}

		/**
		 * 文本翻译
		 */
		public static final String ORDER_TYPE_FAST = "0";

		/**
		 * 文档翻译
		 */
		public static final String ORDER_TYPE_DOC = "1";

		/**
		 * 口译
		 */
		public static final String ORDER_TYPE_ORAL = "2";
	}

	/*
	 * 订单状态(后厂)
	 */
	public final static class OrderState {
		private OrderState() {
		}

		/**
		 * 提交
		 */
		public final static String STATE_COMMIT = "10";

		/**
		 * 待支付
		 */
		public final static String STATE_WAIT_PAY = "11";

		/**
		 * 已支付
		 */
		public final static String STATE_PAIED = "12";

		/**
		 * 待报价
		 */
		public final static String STATE_WAIT_OFFER = "13";

		/**
		 * 待领取
		 */
		public final static String STATE_WAIT_RECEIVE = "20";

		/**
		 * 已领取
		 */
		public final static String STATE_RECEIVED = "21";

		/**
		 * 已分配
		 */
		public final static String STATE_DISTRIBUTION = "211";

		/**
		 * 翻译中
		 */
		public final static String STATE_TRASLATING = "23";

		/**
		 * 翻译完成 已提交
		 */
		public final static String STATE_TRASLATE_FINISHED = "24";

		/**
		 * 翻译修改中
		 */
		public final static String STATE_TRASLATE_UPDATING = "25";
		
		/**
		 * 完成
		 */
		public final static String FLAG_FINISHED = "90";
		
		/**
		 * 取消
		 */
		public final static String CANCEL_STATE = "91";
		/**
		 * 待审核
		 */
		public final static String WAIT_REVIEW_STATE = "40";
		/**
		 * LSP待审核
		 */
		public final static String LSP_WAIT_REVIEW_STATE = "43";
		
		/**
		 * 已经审核
		 */
		public final static String REVIEWED_STATE = "41";
		/**
		 * 审核失败
		 */
		public final static String REVIEW_FAILD_STATE = "42";
		
		/**
		 * 待确认
		 */
		public final static String WAIT_OK_STATE = "50";
		
		/**
		 * 待确认
		 */
		public final static String WAIT_COMMENT_STATE = "52";
		/**
		 * 已确认
		 */
		public final static String COMMENTED_STATE = "51";
		/**
		 * 待评价
		 */
		public final static String WAIT_JUDGE_STATE = "52";

	}

	/*
	 * 客户端显示状态
	 */
	public final static class OrderDisplayFlag {
		private OrderDisplayFlag() {
		}

		/**
		 * 待支付
		 */
		public final static String FLAG_WAIT_PAY = "11";

		/**
		 * 待报价
		 */
		public final static String FLAG_WAIT_OFFER = "13";

		/**
		 * 翻译中
		 */
		public final static String FLAG_TRASLATING = "23";

		/**
		 * 待确认
		 */
		public final static String FLAG_WAIT_OK = "50";

		/**
		 * 评论
		 */
		public final static String FLAG_WAIT_COMMENT = "52";

		/**
		 * 完成
		 */
		public final static String FLAG_FINISHED = "90";

		/**
		 * 取消、关闭
		 */
		public final static String FLAG_CANCEL = "91";

		/**
		 * 已退款
		 */
		public final static String FLAG_REFUNDED = "92";
		
		/**
		 * 待评价
		 */
		public final static String WAIT_JUDGE_STATE = "52";

	}

	/*
	 * 操作机构
	 */
	public final static class OrgID {
		private OrgID() {
		}

		/**
		 * 用户操作
		 */
		public final static String ORG_ID_USER = "0";

		/**
		 * 系统操作
		 */
		public final static String ORG_ID_SYS = "1";
	}

	public static final class OrdOdStateChg {

		/**
		 * 處理信息 Date: 2016年11月7日 <br>
		 * Copyright (c) 2016 asiainfo.com <br>
		 * 
		 * @author zhanglh
		 */
		public static class ChgDesc {
			/**
			 * 订单－取消
			 */
			public static final String ORDER_TO_CANCEL = "您的订单已取消";

		}

	}
	
	public static final class OrdOdProd{
		public static class ProdDetalState{
			/**
			 * 0：未处理
			 */
			public static final String UNTREATED = "0";
		}
	}
	public static final class OrdOrder{
		public static class UpdateFlag{
			/**
			 * Y：是
			 * N：否
			 */
			public static final String UPDATE_FLAG_Y = "Y";
			public static final String UPDATE_FLAG_N = "N";
		}
	}
	
	public static final class Language{
		
		public final static String LANGUAGE_ZH_CN = "zh_CN";
		
		public final static String LANGUAGE_US_EN = "us_EN";
	}
}
