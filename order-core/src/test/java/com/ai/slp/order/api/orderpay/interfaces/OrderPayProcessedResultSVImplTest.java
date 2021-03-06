package com.ai.slp.order.api.orderpay.interfaces;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.BaseTest;
import com.ai.yc.order.api.orderpay.interfaces.IOrderPayProcessedResultSV;
import com.ai.yc.order.api.orderpay.param.OrderPayProcessedResultBaseInfo;
import com.ai.yc.order.api.orderpay.param.OrderPayProcessedResultFeeInfo;
import com.ai.yc.order.api.orderpay.param.OrderPayProcessedResultProdInfo;
import com.ai.yc.order.api.orderpay.param.OrderPayProcessedResultRequest;
import com.ai.yc.order.api.orderpay.param.OrderPayProcessedResultResponse;
import com.alibaba.fastjson.JSON;

public class OrderPayProcessedResultSVImplTest extends BaseTest {
	@Autowired
	private IOrderPayProcessedResultSV orderPayProcessedResultSV;
	@Test
	public void orderPayProcessedResult(){
		OrderPayProcessedResultRequest request = new OrderPayProcessedResultRequest();
		//
		OrderPayProcessedResultBaseInfo baseInfo = new OrderPayProcessedResultBaseInfo();
		baseInfo.setAccountId(1001l);
		baseInfo.setCorporaId("1001");
		baseInfo.setDisplayFlag("23");
		baseInfo.setOrderId(2000000024373466l);
		baseInfo.setOrderType("1");
		baseInfo.setState("23");
		baseInfo.setUserId("1001");
		baseInfo.setUserType("11");
		//
		OrderPayProcessedResultProdInfo prodInfo = new OrderPayProcessedResultProdInfo();
		prodInfo.setStateTime(DateUtil.getSysDate());
		//
		OrderPayProcessedResultFeeInfo feeInfo = new OrderPayProcessedResultFeeInfo();
		feeInfo.setPayStyle("ZFB");
		feeInfo.setTotalFee(1000l);
		feeInfo.setPayTime(DateUtil.getSysDate());
		feeInfo.setExternalId("1000001");
		BigDecimal b = new BigDecimal("22.2");
		feeInfo.setDiscountSum(b);
		feeInfo.setDiscountFee(13l);
		//
		request.setBaseInfo(baseInfo);
		request.setProdInfo(prodInfo);
		request.setFeeInfo(feeInfo);
		//
		log.info("request:"+JSON.toJSONString(request));
		OrderPayProcessedResultResponse response = this.orderPayProcessedResultSV.orderPayProcessedResult(request);
		log.info("response:"+JSON.toJSONString(response));
	}
}
	