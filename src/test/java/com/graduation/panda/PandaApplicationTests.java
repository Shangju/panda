package com.graduation.panda;

import com.graduation.panda.model.UserAddress;
import com.graduation.panda.service.UserAddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PandaApplicationTests {
	@Autowired
	UserAddressService userAddressService;


	@Test
	public void contextLoads() {
		List<UserAddress> addresses = userAddressService.findByUserId("U165817119 ");
		for (UserAddress address : addresses){
			System.out.println(address.getAddressId());
			System.out.println(address.getAreaName());
		}
	}

}
