package com.waylau.spring.boot.blog.api;

import com.waylau.spring.boot.blog.condition.UserCondition;
import com.waylau.spring.boot.blog.domain.Authority;
import com.waylau.spring.boot.blog.domain.MyTest;
import com.waylau.spring.boot.blog.domain.User;
import com.waylau.spring.boot.blog.service.AuthorityService;
import com.waylau.spring.boot.blog.service.UserService;
import com.waylau.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import com.waylau.spring.boot.blog.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	@ApiOperation(value="用户登陆",notes="手机号、密码都是必输项，年龄随边填，但必须是数字")
	@ApiImplicitParams({
			@ApiImplicitParam(name="username",value="用户账号，用户登录时的唯一标识",required=true,dataType = "String",paramType="query"),
			@ApiImplicitParam(name="password",value="登录时密码",required=true,dataType = "String",paramType="query"),
	})
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {

		return "login";
	}


	@ApiOperation(value="用户注册",notes="手机号、密码都是必输项，年龄随边填，但必须是数字")
	@ApiImplicitParams({
			@ApiImplicitParam(name="id",value="用户id,(修改时必填)",dataType = "long",paramType="query"),
			@ApiImplicitParam(name="username",value="用户账号，用户登录时的唯一标识",required=true,dataType = "String",paramType="query"),
			@ApiImplicitParam(name="email",value="邮箱",required=true,dataType = "String",paramType="query"),
			@ApiImplicitParam(name="password",value="登录时密码",required=true,dataType = "String",paramType="query"),
			@ApiImplicitParam(name="name",value="姓名",required=true,dataType = "String",paramType="query")
			//@ApiImplicitParam(name="name",value="姓名",required=true,paramType="form",dataType="Integer")
	})
	@RequestMapping(value = "/saveOrUpate", method = RequestMethod.POST)
    public ResponseEntity<Response> saveOrUpate(UserCondition userCondition) {

	    User user = new User();

	    BeanUtils.copyProperties(userCondition ,user);

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

    //测试swagger
    @RequestMapping(value = "/test/sw/{num}", method = RequestMethod.POST)
    @ApiOperation(value = "测试Swagger", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "测试字符串", name = "str", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "测试数字", name = "num", required = true, dataType = "int", paramType = "path"),
    })
    public String swaggerTest(@RequestParam String str,@PathVariable Integer num){
        return str + num.toString();
    }



	@ApiOperation(value = "测试MyTest")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "测试字符串", name = "name", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(value = "测试数字", name = "password", required = true, dataType = "String", paramType = "query"),
	})
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Response test(@ApiIgnore @ModelAttribute MyTest test) {
		Response rsponse = new Response();
		rsponse.setCode(200);
		rsponse.setMessage("请求成功！");
		rsponse.setBody(test);
		return  rsponse;
	}




}