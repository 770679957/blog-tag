package com.waylau.spring.boot.blog.api;

import com.waylau.spring.boot.blog.domain.Authority;
import com.waylau.spring.boot.blog.domain.User;
import com.waylau.spring.boot.blog.service.AuthorityService;
import com.waylau.spring.boot.blog.service.UserService;
import com.waylau.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import com.waylau.spring.boot.blog.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@Api(tags="用户管理")
@RestController
@RequestMapping("/api/users")
public class UsersController {

	private static final Long ROLE_USER_AUTHORITY_ID = 2L; // 用户权限（博主）

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@ApiOperation(value="用户注册",notes="手机号、密码都是必输项，年龄随边填，但必须是数字")
	@ApiImplicitParams({
			@ApiImplicitParam(name="username",value="用户账号，用户登录时的唯一标识",required=true,paramType="form"),
			@ApiImplicitParam(name="email",value="邮箱",required=true,paramType="form"),
			@ApiImplicitParam(name="password",value="登录时密码",required=true,paramType="form"),
			@ApiImplicitParam(name="name",value="姓名",required=true,paramType="form")
			//@ApiImplicitParam(name="name",value="姓名",required=true,paramType="form",dataType="Integer")
	})
	@PostMapping(("/register"))
	public ResponseEntity<Response> saveOrUpate(@ApiIgnore @ModelAttribute User user) {

		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID).get());
		user.setAuthorities(authorities);

		try {
			userService.registerUser(user);
		}  catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", user));
	}



}