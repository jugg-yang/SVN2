package com.saltedfish.controller.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saltedfish.cmd.admin.RoleAddCmd;
import com.saltedfish.constants.Constants;
import com.saltedfish.constants.Url;
import com.saltedfish.constants.View;
import com.saltedfish.dto.BaseResultDTO;
import com.saltedfish.entity.security.SysRoles;
import com.saltedfish.service.security.RoleService;
import com.saltedfish.service.security.UserService;


@Controller
public class RoleController {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private RoleService roleService;
//=================================================增===========================================================
	/**
	 * 角色添加页面
	 * @return
	 */
	@RequestMapping(value = Url.ROLE_ADD_PAGE, method = RequestMethod.GET)
	public String turnToRoleAddPage() {
		return View.ROLE_ADD_VIEW;
	}
	
	/**
	 * 	添加角色/修改角色
	 * @param cmd
	 * @return
	 */
	@RequestMapping(value = Url.ROLE_ADD_DATA, method = RequestMethod.POST)
	@ResponseBody
	public BaseResultDTO<String> addRoleData(RoleAddCmd cmd) {
		BaseResultDTO<String> result = new BaseResultDTO<String>();
		logger.debug("-------->addRoleData 参数为:" + cmd.toString());
		try {
			if (cmd.getRoleId()==null) {  //如果roleId为空，添加
				roleService.addRole(cmd);
			}else{//否则为修改
				roleService.updateRole(cmd);
			}
			
			result.setStatus(Constants.R_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.debug("-------->添加角色异常 :" + e.getMessage());
			result.setStatus(Constants.R_STATUS_FAILTURE);
		}
		return result;
	}
//=================================================删===========================================================
	/**
	 * 	删除角色
	 * @param cmd
	 * @return
	 */
	@RequestMapping(value = Url.ROLE_DELETE_DATA, method = RequestMethod.POST)
	@ResponseBody
	public BaseResultDTO<String> deleteRoleData(Integer roleId) {
		BaseResultDTO<String> result = new BaseResultDTO<String>();
		logger.debug("-------->deleteRoleData 参数为:" +roleId);
		try {
			roleService.deleteRole(roleId);
			result.setStatus(Constants.R_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.debug("-------->deleteRoleData 异常 :" + e.getMessage());
			result.setStatus(Constants.R_STATUS_FAILTURE);
		}
		return result;
	}
//=================================================改===========================================================
	/**
	 * 角色修改页面
	 * @return
	 */
	@RequestMapping(value = Url.ROLE_UPDATE_PAGE, method = RequestMethod.GET)
	public String turnToRoleUpdatePage(Integer roleId,ModelMap map) {
		SysRoles role = roleService.queryRoleById(roleId);
		map.addAttribute("roleId", roleId);
		map.addAttribute("role", role);
		return View.ROLE_UPDATE_VIEW;
	}
	
//=================================================查===========================================================
	/**
	 * 角色列表页面
	 * @return
	 */
	@RequestMapping(value = Url.ROLE_LIST_PAGE, method = RequestMethod.GET)
	public String turnToAdminAddPage(ModelMap map) {
		List<SysRoles> roles = roleService.queryAllRoles();
		map.put("roles", roles);
		map.put("count", roles.size());
		return View.ROLE_LIST_VIEW;
	}
//=================================================END===========================================================
	
	
	
	


}
