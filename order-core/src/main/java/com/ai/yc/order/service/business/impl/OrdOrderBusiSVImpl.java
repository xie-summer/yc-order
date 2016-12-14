package com.ai.yc.order.service.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.SearchOption;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.yc.order.api.orderquery.param.QueryOrdCountRequest;
import com.ai.yc.order.api.orderstate.param.OrderStateUpdateRequest;
import com.ai.yc.order.api.orderstate.param.OrderStateUpdateResponse;
import com.ai.yc.order.constants.OrdersConstants;
import com.ai.yc.order.constants.SearchFieldConfConstants;
import com.ai.yc.order.dao.mapper.bo.OrdOdStateChg;
import com.ai.yc.order.dao.mapper.bo.OrdOrder;
import com.ai.yc.order.interperlevel.rule.InterperLevelMap;
import com.ai.yc.order.interperlevel.rule.OrderLevelRange;
import com.ai.yc.order.service.atom.interfaces.IOrdOdStateChgAtomSV;
import com.ai.yc.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.yc.order.service.business.impl.search.OrderSearchImpl;
import com.ai.yc.order.service.business.interfaces.IOrdOrderBusiSV;
import com.ai.yc.order.service.business.interfaces.search.IOrderSearch;
import com.ai.yc.order.util.SequenceUtil;
@Service
public class OrdOrderBusiSVImpl implements IOrdOrderBusiSV {

	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrdOdStateChgAtomSV ordOdStateChgAtomSV;// 订单轨迹表
	
	@Autowired
	private InterperLevelMap interperLevelMap;//译员级别判定订单查询级别
	
	@Override
	public OrdOrder findByPrimaryKey(OrdOrder ordOrder) {
		return this.ordOrderAtomSV.findByPrimaryKey(ordOrder);
	}
	@Override
	public Map<String,Integer> findOrderCount(QueryOrdCountRequest request) {
		OrdOrder orderRequest = new OrdOrder();
		copyProperties(orderRequest, request);
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		if(!StringUtil.isBlank(request.getState())){
			int stateCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(request.getState(), stateCount);
			return countMap;
		}
		if(!StringUtil.isBlank(request.getUserId())){
			//待支付
			orderRequest.setDisplayFlag(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_PAY);
			int waitPayCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_PAY, waitPayCount);
			
			
			//待报价
			orderRequest.setDisplayFlag(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_OFFER);
			int waitofferCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_OFFER, waitofferCount);
			
			//翻译中
			orderRequest.setDisplayFlag(OrdersConstants.OrderDisplayFlag.FLAG_TRASLATING);
			int translatingCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(OrdersConstants.OrderDisplayFlag.FLAG_TRASLATING, translatingCount);
			
			//待确认
			orderRequest.setDisplayFlag(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_OK);
			int waitOkCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_OK, waitOkCount);
			
			//待评价
			orderRequest.setDisplayFlag(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_COMMENT);
			int waitCommentCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(OrdersConstants.OrderDisplayFlag.FLAG_WAIT_COMMENT, waitCommentCount);
			
		}else if(!StringUtil.isBlank(request.getInterperId())||!StringUtil.isBlank(request.getInterperId())){
			//待支付
			orderRequest.setState(OrdersConstants.OrderState.STATE_RECEIVED);
			int receivedCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(OrdersConstants.OrderState.STATE_RECEIVED, receivedCount);
			
			//翻译中
			orderRequest.setState(OrdersConstants.OrderState.STATE_TRASLATING);
			int translatingCount = countAllOrders(orderRequest,request.getInterperLevel());
			countMap.put(OrdersConstants.OrderState.STATE_TRASLATING, translatingCount);
		}
		
		return countMap;
	}
	
	private void copyProperties(OrdOrder orderRequest,QueryOrdCountRequest request){
		orderRequest.setBusiType(request.getBusiType());
		orderRequest.setChlId(request.getChlId());
		orderRequest.setFlag(request.getFlag());
		orderRequest.setUserType(request.getUserType());
		orderRequest.setUserId(request.getUserId());
		orderRequest.setSubFlag(request.getSubFlag());
		orderRequest.setOrderType(request.getOrderType());
		orderRequest.setBusiType(request.getBusiType());
		orderRequest.setOrderLevel(request.getOrderLevel());
		orderRequest.setTranslateType(request.getTranslateType());
		orderRequest.setCorporaId(request.getCorporaId());
		orderRequest.setInterperType(request.getInterperType());
		orderRequest.setInterperId(request.getInterperId());
		orderRequest.setState(request.getState());
		orderRequest.setLspId(request.getLspId());
	}
	
	@Override
	@Transactional
	public OrderStateUpdateResponse updateState(OrderStateUpdateRequest request) {
		OrderStateUpdateResponse response = new OrderStateUpdateResponse();
		//
		OrdOrder ordOrderDb = this.ordOrderAtomSV.findByPrimaryKey(request.getOrderId());
		if (null == ordOrderDb) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "此订单信息不存在");
		}
		//
		OrdOrder ordOrder = new OrdOrder();
		ordOrder.setOrderId(request.getOrderId());
		ordOrder.setState(request.getState());
		ordOrder.setStateChgTime(DateUtil.getSysDate());
		if(OrdersConstants.OrderState.FLAG_FINISHED.equals(request.getState())){
			ordOrder.setFinishTime(DateUtil.getSysDate());
		}
		//
		if(!StringUtil.isBlank(request.getDisplayFlag())){
			ordOrder.setDisplayFlag(request.getDisplayFlag());
			ordOrder.setDisplayFlagChgTime(DateUtil.getSysDate());
		}
		//
		this.ordOrderAtomSV.updateByPrimaryKeySelective(ordOrder);
		//
		String userId = ordOrderDb.getUserId();
		Long orderId = request.getOrderId();
		String translateType = ordOrderDb.getTranslateType();//数据库获取订单翻译类型
		String oldState = ordOrderDb.getState();//数据库获取订单当前状态
		String newState = request.getState();//新状态为 待领取
		//
		this.orderStateChgInfoSubmit(userId, orderId, translateType, oldState, newState);
		//
		response.setOrderId(request.getOrderId());
		//
		return response;
	}
	/**
	 * 订单提交-订单轨迹表
	 */
	public void orderStateChgInfoSubmit(String userId,Long orderId,String translateType,String oldState,String newState){
		OrdOdStateChg ordOdStateChg = new OrdOdStateChg();
		//
		Long stateChgId = SequenceUtil.createStateChgId();
		//
		ordOdStateChg.setStateChgId(stateChgId);
		ordOdStateChg.setOrderId(orderId);
		ordOdStateChg.setOperId(userId);
		ordOdStateChg.setOrgState(oldState);
		ordOdStateChg.setNewState(newState);
		ordOdStateChg.setStateChgTime(DateUtil.getSysDate());
		this.ordOdStateChgAtomSV.insertSelective(ordOdStateChg);
	}
	
	private int countAllOrders(OrdOrder request,String interperLevel){
		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> searchCriterias = installConditions(request,interperLevel);
		return orderSearch.countAll(searchCriterias);
	}
	
	// 搜索引擎数据公共查询条件
	private List<SearchCriteria> installConditions(OrdOrder request,String interperLevel) {
			List<SearchCriteria> searchfieldVos = new ArrayList<SearchCriteria>();
			if (!StringUtil.isBlank(interperLevel)) {
				Map<String,OrderLevelRange> interperLevelMap = this.interperLevelMap.getInterperLevelMap();
				SearchCriteria searchCriteria = new SearchCriteria();
				searchCriteria.setOption(new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.range));
				searchCriteria.setField(SearchFieldConfConstants.ORDER_LEVEL);
				String minValue = "1";
				String maxValue = "1";
				if(null != interperLevelMap.get(interperLevel)){
					minValue = interperLevelMap.get(interperLevel).getMinValue();
					maxValue = interperLevelMap.get(interperLevel).getMaxValue();
				}
				searchCriteria.addFieldValue(minValue);
				searchCriteria.addFieldValue(maxValue);
				searchfieldVos.add(searchCriteria);
			}
			// 如果业务标识不为空
			if (!StringUtil.isBlank(request.getFlag())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.FLAG, request.getFlag(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果订单id不为空
			if (request.getOrderId() != null) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.ORDER_ID, request.getOrderId().toString(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果用户类型不为空
			if (!StringUtil.isBlank(request.getUserType())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_TYPE, request.getUserType(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果用户id不为空
			if (!StringUtil.isBlank(request.getUserId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_ID, request.getUserId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果报价标识不为空
			if (!StringUtil.isBlank(request.getSubFlag())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.SUB_FLAG, request.getSubFlag(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果订单来源不为空
			if (!StringUtil.isBlank(request.getChlId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CHL_ID, request.getChlId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果订单类型不为空
			if (!StringUtil.isBlank(request.getOrderType())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.ORDER_TYPE, request.getOrderType(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果订单级别不为空
			if (!StringUtil.isBlank(request.getOrderLevel())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.ORDER_LEVEL, request.getOrderLevel(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果翻译类型不为空
			if (!StringUtil.isBlank(request.getTranslateType())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.TRANSLATE_TYPE, request.getTranslateType(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
		
			// 如果企业id不为空
			if (!StringUtil.isBlank(request.getCorporaId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CORPORA_ID, request.getCorporaId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果state不为空
			if (!StringUtil.isBlank(request.getState())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.STATE, request.getState(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果客户端显示状态不为空
			if (!StringUtil.isBlank(request.getDisplayFlag())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.DISPLAY_FLAG, request.getDisplayFlag(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果译员类型不为空
			if (!StringUtil.isBlank(request.getInterperType())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.INTERPER_TYPE, request.getInterperType(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果操作员id不为空
			if (!StringUtil.isBlank(request.getOperId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.OPER_ID, request.getOperId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果lspid不为空
			if (!StringUtil.isBlank(request.getLspId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.LSP_ID, request.getLspId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			// 如果译员id不为空
			if (!StringUtil.isBlank(request.getInterperId())) {
				searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.INTERPER_ID, request.getInterperId(),
						new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
			}
			return searchfieldVos;
		}

}
