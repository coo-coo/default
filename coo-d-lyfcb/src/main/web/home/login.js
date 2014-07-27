$(document).ready(function() {
	// 检查浏览器版本
	nj.checkBrowser();
	
	var url = window.location.pathname;
	var l = url.split("/")
	if(l[l.length-1] == "login.html"){
		nj.web.loadmenubar('login','login');
	}
	checkLogin();
	
//	$('#login_password').keyup(function(e){ 
	$('#login_verifycode').keyup(function(e){ 
		  if(e.keyCode==13){ 
			  doLogin();
		  } 
	}); 
});

var app = new Object();
app.context = "/oss";
var RPC_URL = "/copsvr/copks/";

function checkLogin() {
	if (napp.ns.web_login_support == "true") {
		var $container = $("#headerContainer");
		// 增加Session判定部分，增加右上角部分内容
		var value = sessionStorage.getItem(NJID_SESSION_ACCOUNT);
		var dept = sessionStorage.getItem("ngbf.session.dept");
//		alert(dept);
		if (value == null) {
			$container
					.append("<div id='loginArea'><a class='pull-right' id='login_info' href='#' onclick='nj.auth.loginDialogShow()' style='margin-top:12px;'><font color='white'>登录</font></a>");
		}else {
			if(dept == "运维"){
				$container.append('<div class="pull-right dropdown" style="vertical-align: middle; margin-top: 5px;">'
						+ '<a class="dropdown-toggle" data-toggle="dropdown" ><img src="../home/image/icon-ngbf.png"  width="35px" height="30px" data-toggle="tooltip" title="mytitle" data-trigger="hover" data-placement="bottom"/><span style="color:white; margin-left:5px; text-decoration: none;">'+ value +'</span></a>'
						+ '<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
						+ '<li role="presentation"><a role="menuitem" tabindex="-1" href="../account/ops.html">运维账号</a></li>'
						+ '<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="doLoginOut()">退出</a></li>'
						+ '</ul>' + '</div>');
			}else{
				$container.append('<div class="pull-right dropdown" style="vertical-align: middle; margin-top: 5px;">'
						+ '<a class="dropdown-toggle" data-toggle="dropdown" ><img src="../home/image/'+ dept +'.png"  width="35px" height="30px" data-toggle="tooltip" title="mytitle" data-trigger="hover" data-placement="bottom"/><span style="color:white; margin-left:5px; text-decoration: none;">'+ value +'</span></a>'
						+ '<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">'
						+ '<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="doLoginOut()">退出</a></li>'
						+ '</ul>' + '</div>');				
			}
		}
	}
}

function doLogin() {
	var username = $('#login_username').val();
	var password = $('#login_password').val();
	var verifycode = $('#login_verifycode').val();
//	alert(verifycode);
//	var req_url = app.context + "/rest/loginAuth/"+username+"/"+password;
	var d = {
			"username" : username,
			"password" : password,
			"verifycode" : verifycode
	};
	$.post(RPC_URL + "loginAuth", d, function(msg){
//		alert(JSON.stringify(msg));
		if(msg.head.rep_code == '200'){
			// TODO 密码加密
			// TODO 验证登录是否成功
			sessionStorage.setItem("ngbf.session.account", username);
			sessionStorage.setItem("ngbf.session.token", msg.data.token);
			sessionStorage.setItem("ngbf.session.dept", msg.data.dept);
			
			//发送一条twitter信息
			var content = "登陆应用" ;
			var data = {account:username,twitter:content,type:"login"} ;
			$.post("/oss/rest/twitter/",data, function(msg){});
			if(msg.data.dept == "运维"){
				window.location.href = "../account/ops.html"
			}else{
				window.location.href = "../profile/pending.html"
			}
		}else if(msg.head.rep_code == '102'){
			alert("验证码错误！");
		}
		else{
			alert("用户名和密码错误！");
		}
	});
}

/**
 * 退出，清除session内容
 */
function doLoginOut() {
	var username = nj.auth.getAccount() ;
	sessionStorage.removeItem("ngbf.session.account");
	sessionStorage.removeItem("ngbf.session.token");
	//发送一条twitter信息
	var content = "登出应用";
	var data = {account:username,twitter:content,type:"login"} ;
	$.post("/oss/rest/twitter/",data, function(msg){});
	var url = getCurrentUrl();
	window.location.href="../home/login.html";
}

/**
 * 得到session中保存的account信息
 */
function getSessionAccount(){
	return sessionStorage.getItem("ngbf.session.account");
}