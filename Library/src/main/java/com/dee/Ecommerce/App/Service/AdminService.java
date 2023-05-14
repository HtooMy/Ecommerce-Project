package com.dee.Ecommerce.App.Service;

import com.dee.Ecommerce.App.Dto.AdminDto;
import com.dee.Ecommerce.App.Model.Admin;

public interface AdminService {
	Admin findByUsername(String username);
	
	Admin save(AdminDto adminDto);
}
