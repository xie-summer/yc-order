package com.ai.yc.order.service.business.interfaces;

import com.ai.yc.order.dao.mapper.bo.OrdOdStateChg;

public interface IOrdOdStateChgBusiSV {
	
	/**
	 * 支付轨迹
	 */
	void addPayChgDesc(OrdOdStateChg chg);
	
	/**
	 * 修改订单轨迹
	 */
	void addUpdateChgDesc(OrdOdStateChg chg);
	
	/**
	 * 报价轨迹
	 */
	void addOfferChgDesc(OrdOdStateChg chg);
	/**
	 * 关闭轨迹
	 */
	void addCloseChgDesc(OrdOdStateChg chg);
	
	

}
