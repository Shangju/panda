package com.graduation.panda.service.impl;

import com.alipay.api.AlipayApiException;

import com.graduation.panda.config.Alipay;
import com.graduation.panda.model.AlipayBean;
import com.graduation.panda.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private Alipay alipay;
	
	@Override
	public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
		return alipay.pay(alipayBean);
	}

}
