package com.ai.slp.order.api.ordersubmission.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.BaseTest;
import com.ai.yc.order.api.ordersubmission.interfaces.IOrderSubmissionSV;
import com.ai.yc.order.api.ordersubmission.param.BaseInfo;
import com.ai.yc.order.api.ordersubmission.param.ContactInfo;
import com.ai.yc.order.api.ordersubmission.param.FeeInfo;
import com.ai.yc.order.api.ordersubmission.param.FileInfo;
import com.ai.yc.order.api.ordersubmission.param.LanguagePairInfo;
import com.ai.yc.order.api.ordersubmission.param.OrderSubmissionRequest;
import com.ai.yc.order.api.ordersubmission.param.OrderSubmissionResponse;
import com.ai.yc.order.api.ordersubmission.param.ProductInfo;
import com.ai.yc.order.api.ordersubmission.param.StateChgInfo;
import com.ai.yc.order.api.ordersubmission.param.TranslateLevelInfo;
import com.ai.yc.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.yc.order.service.business.interfaces.IOrderSubmissionBusiSV;
import com.ai.yc.order.validate.OrderSubmissionParam;
import com.ai.yc.order.validate.OrderSubmissionValidate;
import com.alibaba.fastjson.JSON;

public class OrderSubmisissonSVImplTest extends BaseTest {
	@Autowired
	private IOrderSubmissionSV orderSubmissionSV;
	
	@Autowired
	private OrderSubmissionParam orderSubmissionParam;
	@Autowired
	private OrderSubmissionValidate validate;
	
	@Autowired
	private IOrderSubmissionBusiSV orderSubmissionBusiSV;
	
	//@Test
	public void orderSubmission(){
		OrderSubmissionRequest request = new OrderSubmissionRequest();
		log.info("request:"+JSON.toJSONString(request));
		OrderSubmissionResponse response = this.orderSubmissionSV.orderSubmission(request);
		log.info("response:"+JSON.toJSONString(response));
	}
	//@Test
	public void mapTest(){
		Map<String,String> flagMap = this.orderSubmissionParam.getFlagMap();
		log.info("flagMap:"+JSON.toJSONString(flagMap));
		log.info("orderSubmissionParam:"+JSON.toJSONString(this.orderSubmissionParam));
		//
		//OrderSubmissionValidate validate = new OrderSubmissionValidate();
		//
		OrderSubmissionRequest request = new OrderSubmissionRequest();
		//
		BaseInfo baseInfo = new BaseInfo();
		//
		baseInfo.setFlag("3");
		//
		request.setBaseInfo(baseInfo);
		//
		validate.validateNull(request);
	}
	
	/**
	 * 
	 * 文本翻译下单测试 translateType:0
	 */
	@Test
	public void textTraslateOrder(){
		OrderSubmissionRequest request = new OrderSubmissionRequest();
		/**
		 * 基本信息
		 */
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setFlag("0");
		baseInfo.setChlId("0");
		// 订单类型
		baseInfo.setOrderType("1");
		// 业务类型
		baseInfo.setBusiType("1");
		// 翻译主题
		baseInfo.setTranslateName("翻译主题信息");
		// 翻译类型
		baseInfo.setTranslateType("0");
		// 订单级别
		baseInfo.setOrderLevel("1");
		// 报价标识
		baseInfo.setSubFlag("1");
		// 用户类型
		baseInfo.setUserType("10");
		// 用户ID
		baseInfo.setUserId("1001");
		//下单时间
		baseInfo.setOrderTime(DateUtil.getSysDate());
		//时区
		baseInfo.setTimeZone("GMT+8");
		/**
		 * 产品信息
		 */
		ProductInfo productInfo = new ProductInfo();
		// 翻译字数
		productInfo.setTranslateSum(1002l);
		// 用途ID
		productInfo.setUseCode("1111");
		// 领域ID
		productInfo.setFieldCode("2222");
		// 是否排版
		productInfo.setIsSetType("0");
		// 是否加急
		productInfo.setIsUrgent("N");
		// 需翻译内容
		productInfo.setNeedTranslateInfo("翻译成英文");
		
		//开始时间需要支付完成再添加，然后根据字数计算出结束时间
		// 开始时间
//		productInfo.setStartTime(DateUtil.getSysDate());
//		// 结束时间
//		productInfo.setEndTime(DateUtil.getSysDate());
		/**
		 * 费用信息
		 */
		FeeInfo feeInfo = new FeeInfo();
		// 币种
		feeInfo.setCurrencyUnit("1");
		// 总费用
		feeInfo.setTotalFee(1000l);
		// 总优惠金额
		// 总减免费用
		// 减免原因
		// 总应收费用
		// 总实收费用
		// 总已收费用
		/**
		 * 联系人信息
		 */
		ContactInfo contactInfo = new ContactInfo();
		// 联系人姓名
		contactInfo.setContactName("zhangsan");
		// 联系人电话
		contactInfo.setContactTel("16811169253");
		// 联系人邮箱
		contactInfo.setContactEmail("zhangsan@asiainfo.com");
		// 附加信息
		contactInfo.setRemark("备注信息");
		/**
		 * 语言对信息
		 */
		LanguagePairInfo languagePairInfo = new LanguagePairInfo();
		// 语言对
		languagePairInfo.setLanguagePairId("1");
		// 语言对名称
		languagePairInfo.setLanguagePairName("中文->英文");
		// 扩展信息
		languagePairInfo.setInfoJson("语言对扩展信息");
		//
		List<LanguagePairInfo> languagePairInfoList = new ArrayList<LanguagePairInfo>();
		languagePairInfoList.add(languagePairInfo);
		//
		
		//
		/**
		 * 翻译级别信息
		 *
		 */
		TranslateLevelInfo translateLevelInfo = new TranslateLevelInfo ();
		// 翻译级别
		translateLevelInfo.setTranslateLevel("100210");
		// 扩展信息
		translateLevelInfo.setInfoJson("翻译级别扩展信息");
		//
		List<TranslateLevelInfo> translateLevelInfoList = new ArrayList<TranslateLevelInfo>();
		translateLevelInfoList.add(translateLevelInfo);
		//
		//将总信息放入请求参数
		request.setBaseInfo(baseInfo);
		productInfo.setTranslateLevelInfoList(translateLevelInfoList);
		productInfo.setLanguagePairInfoList(languagePairInfoList);
		request.setProductInfo(productInfo);
		request.setContactInfo(contactInfo);
		request.setFeeInfo(feeInfo);
		//
		//this.validate.textTraslateOrder(request);
		
		log.info("request:"+JSON.toJSONString(request));
		//
		OrderSubmissionResponse response = this.orderSubmissionSV.orderSubmission(request);
		log.info("response:"+JSON.toJSONString(response));
	}
	/**
	 * 
	 * 文档翻译下单测试 translateType:1
	 */
	@Test
	public void docTraslateOrder(){
		OrderSubmissionRequest request = new OrderSubmissionRequest();
		/**
		 * 基本信息
		 */
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setFlag("0");
		baseInfo.setChlId("0");
		// 订单类型
		baseInfo.setOrderType("1");
		// 业务类型
		baseInfo.setBusiType("1");
		// 翻译主题
		baseInfo.setTranslateName("翻译主题信息");
		// 翻译类型
		baseInfo.setTranslateType("1");
		// 订单级别
		baseInfo.setOrderLevel("1");
		// 报价标识
		baseInfo.setSubFlag("1");
		// 用户类型
		baseInfo.setUserType("10");
		// 用户ID
		baseInfo.setUserId("1001");
		//下单时间
		baseInfo.setOrderTime(DateUtil.getSysDate());
		//时区
		baseInfo.setTimeZone("GMT+8");
		/**
		 * 产品信息
		 */
		ProductInfo productInfo = new ProductInfo();
		// 翻译字数
		productInfo.setTranslateSum(1000l);
		// 用途ID
		productInfo.setUseCode("1111");
		// 领域ID
		productInfo.setFieldCode("2222");
		// 是否排版
		productInfo.setIsSetType("0");
		// 是否加急
		productInfo.setIsUrgent("N");
		
		//开始时间需要支付完成再添加
		// 开始时间
//		productInfo.setStartTime(DateUtil.getSysDate());
//		// 结束时间
//		productInfo.setEndTime(DateUtil.getSysDate());
		/**
		 * 费用信息
		 */
		FeeInfo feeInfo = new FeeInfo();
		// 币种
		feeInfo.setCurrencyUnit("1");
		// 总费用
		//feeInfo.setTotalFee();
		// 总优惠金额
		// 总减免费用
		// 减免原因
		// 总应收费用
		// 总实收费用
		// 总已收费用
		/**
		 * 联系人信息
		 */
		ContactInfo contactInfo = new ContactInfo();
		// 联系人姓名
		contactInfo.setContactName("zhangzd");
		// 联系人电话
		contactInfo.setContactTel("15811169257");
		// 联系人邮箱
		contactInfo.setContactEmail("zhangzd@asiainfo.com");
		// 附加信息
		contactInfo.setRemark("备注信息");
		/**
		 * 语言对信息
		 */
		LanguagePairInfo languagePairInfo = new LanguagePairInfo();
		// 语言对
		languagePairInfo.setLanguagePairId("1");
		// 语言对名称
		languagePairInfo.setLanguagePairName("中文->英文");
		// 扩展信息
		languagePairInfo.setInfoJson("语言对扩展信息");
		//
		List<LanguagePairInfo> languagePairInfoList = new ArrayList<LanguagePairInfo>();
		languagePairInfoList.add(languagePairInfo);
		//
		
		//
		/**
		 * 翻译级别信息
		 *
		 */
		TranslateLevelInfo translateLevelInfo = new TranslateLevelInfo ();
		// 翻译级别
		translateLevelInfo.setTranslateLevel("1");
		// 扩展信息
		translateLevelInfo.setInfoJson("翻译级别扩展信息");
		//
		List<TranslateLevelInfo> translateLevelInfoList = new ArrayList<TranslateLevelInfo>();
		translateLevelInfoList.add(translateLevelInfo);
		//
		/**
		 * 文件信息
		 */
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileSaveId("10001");
		fileInfo.setFileName("中华人民共和国刑法.doc");
		//
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		fileInfoList.add(fileInfo);
		//将总信息放入请求参数
		request.setBaseInfo(baseInfo);
		productInfo.setTranslateLevelInfoList(translateLevelInfoList);
		productInfo.setLanguagePairInfoList(languagePairInfoList);
		productInfo.setFileInfoList(fileInfoList);
		request.setProductInfo(productInfo);
		request.setContactInfo(contactInfo);
		request.setFeeInfo(feeInfo);
		//
		//this.validate.docTranslateOrder(request);
		
		log.info("request:"+JSON.toJSONString(request));
		//
		OrderSubmissionResponse response = this.orderSubmissionSV.orderSubmission(request);
		log.info("response:"+JSON.toJSONString(response));

	}
	/**
	 * 
	 * 口译下单测试
	 */
	@Test
	public void interpretOrder(){
		OrderSubmissionRequest request = new OrderSubmissionRequest();
		/**
		 * 基本信息
		 */
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setFlag("0");
		baseInfo.setChlId("0");
		// 订单类型
		baseInfo.setOrderType("1");
		// 业务类型
		baseInfo.setBusiType("1");
		// 翻译主题
		baseInfo.setTranslateName("翻译主题信息");
		// 翻译类型
		baseInfo.setTranslateType("2");
		// 订单级别
		baseInfo.setOrderLevel("1");
		// 报价标识
		baseInfo.setSubFlag("1");
		// 用户类型
		baseInfo.setUserType("10");
		// 用户ID
		baseInfo.setUserId("1001");
		//下单时间
		baseInfo.setOrderTime(DateUtil.getSysDate());
		//时区
		baseInfo.setTimeZone("GMT+8");
		/**
		 * 产品信息
		 */
		ProductInfo productInfo = new ProductInfo();
		//
		// 会场数量
		productInfo.setMeetingSum(10l);
		// 译员性别
		productInfo.setInterperGen("0");
		// 会议地点
		productInfo.setMeetingAddress("北京市石景山区");
		// 译员数量
		productInfo.setInterperSum(10l);
		// 开始时间
		productInfo.setStartTime(DateUtil.getSysDate());
		// 结束时间
		productInfo.setEndTime(DateUtil.getSysDate());
		/**
		 * 费用信息
		 */
		FeeInfo feeInfo = new FeeInfo();
		// 币种
		feeInfo.setCurrencyUnit("1");
		// 总费用
		//feeInfo.setTotalFee(1000l);
		// 总优惠金额
		// 总减免费用
		// 减免原因
		// 总应收费用
		// 总实收费用
		// 总已收费用
		/**
		 * 联系人信息
		 */
		ContactInfo contactInfo = new ContactInfo();
		// 联系人姓名
		contactInfo.setContactName("zhangzd");
		// 联系人电话
		contactInfo.setContactTel("15811169257");
		// 联系人邮箱
		contactInfo.setContactEmail("zhangzd@asiainfo.com");
		// 附加信息
		contactInfo.setRemark("备注信息");
		/**
		 * 语言对信息
		 */
		LanguagePairInfo languagePairInfo = new LanguagePairInfo();
		// 语言对
		languagePairInfo.setLanguagePairId("1");
		// 语言对名称
		languagePairInfo.setLanguagePairName("中文->英文");
		// 扩展信息
		languagePairInfo.setInfoJson("语言对扩展信息");
		//
		List<LanguagePairInfo> languagePairInfoList = new ArrayList<LanguagePairInfo>();
		languagePairInfoList.add(languagePairInfo);
		//
		
		//
		/**
		 * 翻译级别信息
		 *
		 */
		TranslateLevelInfo translateLevelInfo = new TranslateLevelInfo ();
		// 翻译级别
		translateLevelInfo.setTranslateLevel("1");
		// 扩展信息
		translateLevelInfo.setInfoJson("翻译级别扩展信息");
		//
		List<TranslateLevelInfo> translateLevelInfoList = new ArrayList<TranslateLevelInfo>();
		translateLevelInfoList.add(translateLevelInfo);
		//
		//将总信息放入请求参数
		request.setBaseInfo(baseInfo);
		productInfo.setTranslateLevelInfoList(translateLevelInfoList);
		productInfo.setLanguagePairInfoList(languagePairInfoList);
		request.setProductInfo(productInfo);
		request.setContactInfo(contactInfo);
		request.setFeeInfo(feeInfo);
		//
		//this.validate.interpretOrder(request);
		
		log.info("request:"+JSON.toJSONString(request));
		//
		OrderSubmissionResponse response = this.orderSubmissionSV.orderSubmission(request);
		log.info("response:"+JSON.toJSONString(response));

	}
	//@Test
	public void saveContact(){
		OrdOdLogistics ordOdLogistics = new OrdOdLogistics();
		ordOdLogistics.setContactId(1l);
		ordOdLogistics.setContactEmail("zhangzd@asiainfo.com");
		ordOdLogistics.setContactName("zhangzd");
		ordOdLogistics.setOrderId(1l);
		
		this.orderSubmissionBusiSV.saveContact(ordOdLogistics);
	}
	@Test
	public void testa(){
		OrderSubmissionRequest request = new OrderSubmissionRequest();
		StateChgInfo stateChgInfo = new StateChgInfo();
		stateChgInfo.setOperName("zhangzd");
		request.setStateChgInfo(stateChgInfo);
		//
		String operName = "";
		if(null == request.getStateChgInfo()){
			operName = "";
		}else{
			if(!StringUtil.isBlank(request.getStateChgInfo().getOperName())){
				operName = request.getStateChgInfo().getOperName();
			}
		}
		log.info("operName:"+operName);
	}
}	
