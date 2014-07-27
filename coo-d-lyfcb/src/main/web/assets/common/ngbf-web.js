/**
 * 定义Ngbf JavaScript缺省对象nj
 */
var nj = new Object;

// 定义Session的AccountID @since 1.8.0.0
var NJID_SESSION_ACCOUNT = "ngbf.session.account";

// //////////////////////////////////////////////////////////////////////////////
// /////////////////// nj.util /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * 定义简单工具类对象
 * 
 * @since 1.8.0.0
 */
nj.util = new Object;

// //////////////////////////////////////////////////////////////////////////////
// /////////////////// nj.web /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * 定义web应用对象
 * 
 * @since 1.8.0.0
 */
nj.web = new Object;

/**
 * 创建headmeta
 * 
 * @since 1.8.0.0
 */
nj.web.headmeta = function(title) {
	var meta = document.getElementsByTagName('meta');
	for ( var i = 0; i < meta.length; i++) {
		var n = meta[i].getAttribute('name');
		if (n == 'author') {
			meta[i].setAttribute('content', napp.head.author);
		}
		if (n == 'description') {
			meta[i].setAttribute('content', napp.head.description);
		}
	}
	// 获取title元素
	var $title = $("head>title");
	if (title != undefined) {
		$title.val(title);
	} else {
		$title.val(napp.head.title);
	}
}

/**
 * 创建MenuBar
 * 
 * @since 1.8.0.0
 */
nj.web.menubar = function(items, id) {
	var bar = $('#ngbf-header-bar').addClass(
			"navbar-inverse navbar-fixed-top");
	bar.css("height" , "40px") ;
	var $navbar = $("<div'></div>");
	var $container = $("<div class='container' style='height:40px;' id='headerContainer'></div>");
	var $ul = $("<ul id='header_bar_ul' class='nav nav-pills pull-left'></ul>");

	for (i = 0; i < items.length; i++) {
		
		if(items[i].subMenu != undefined && items[i].subMenu != null &&  items[i].subMenu.length > 0){
			if (items[i].id == id) {
				var $dropdown = $("<li class='dropdown active' id='menubar_item_"+ items[i].id	+ "'>");
				$dropdown
				.append("<a class='dropdown-toggle' data-toggle='dropdown' href='#'>"+items[i].label+"<b class='caret'></b></a>");
				var $subUrl = $("<ul class='dropdown-menu' role='menu' aria-labelledby='dLabel'></ul>");
			
				for (j = 0; j < items[i].subMenu.length; j++) {
					var item = items[i].subMenu[j];
					if(item.blankLoad != null && item.blankLoad != undefined && item.blankLoad == true){
						$subUrl
						.append("<li id='menubar_item_"
								+ item.id
								+ "' class=''><a href='"+item.value+"' target='blank'>"
								+ item.label + "</a></li>");
					}else{
						$subUrl.append("<li><a href='#' onclick='onMenubarItemClick(\""
							+ item.id + "\",\"" + item.value + "\")'>"
							+ item.label + "</a></li>");
					}
				}
				$dropdown.append($subUrl);
				$ul.append($dropdown);
			}else{
				var $dropdown = $("<li class='dropdown' id='menubar_item_"+ items[i].id	+ "'>");
				$dropdown
				.append("<a class='dropdown-toggle' data-toggle='dropdown' href='#'>"+items[i].label+"<b class='caret'></b></a>");
				var $subUrl = $("<ul class='dropdown-menu' role='menu' aria-labelledby='dLabel'></ul>");
			
				for (j = 0; j < items[i].subMenu.length; j++) {
					var item = items[i].subMenu[j];
					if(item.blankLoad != null && item.blankLoad != undefined && item.blankLoad == true){
						$subUrl
						.append("<li id='menubar_item_"
								+ item.id
								+ "' class=''><a href='"+item.value+"' target='blank'>"
								+ item.label + "</a></li>");
					}else{
						$subUrl.append("<li><a href='#' onclick='onMenubarItemClick(\""
							+ items[i].id + "\",\"" + item.value + "\")'>"
							+ item.label + "</a></li>");
					}
				}
				$dropdown.append($subUrl);
				$ul.append($dropdown);
			}
		}else{
			if (items[i].id == id) {
				if(items[i].blankLoad != null && items[i].blankLoad != undefined && items[i].blankLoad == true){
					$ul
					.append("<li id='menubar_item_"
							+ items[i].id
							+ "' class='active'><a href='"+items[i].value+"' target='blank'>"
							+ items[i].label + "</a></li>");
				}else{
					$ul
						.append("<li id='menubar_item_"
								+ items[i].id
								+ "' class='active'><a href='#' onclick='onMenubarItemClick(\""
								+ items[i].id + "\",\"" + items[i].value + "\")'>"
								+ items[i].label + "</a></li>");
				}
			} else {
				if(items[i].blankLoad != null && items[i].blankLoad != undefined && items[i].blankLoad == true){
					$ul
					.append("<li id='menubar_item_"
							+ items[i].id
							+ "' class=''><a href='"+items[i].value+"' target='blank'>"
							+ items[i].label + "</a></li>");
				}else{
					$ul.append("<li id='menubar_item_" + items[i].id
						+ "'><a href='#' onclick='onMenubarItemClick(\""
						+ items[i].id + "\",\"" + items[i].value + "\")'>"
						+ items[i].label + "</a></li>");
				}
			}
		}

	}
	$container.append($ul);
	// 判断是否需要显示登录按钮

	/*if (napp.ns.web_login_support == "true") {
		// 增加Session判定部分，增加右上角部分内容
		var value = sessionStorage.getItem(NJID_SESSION_ACCOUNT);
		if (value == null) {
			//@deprecated @since 2.3.1.0
			//var loginModal = getLoginModal();
			//$container.append(loginModal);
			$container
					.append("<div id='loginArea'><a class='pull-right' id='login_info' href='#' onclick='nj.auth.loginDialogShow()' style='margin-top:12px;'><font color='white'>登录</font></a>"
							+ "<div class='col-md-2 pull-right' style='vertical-align:middle;margin-top:5px;'><div class='input-group'>"
							+ "<input type='text' class='form-control input-sm col-md-10' placeholder='search…'><span class='btn btn-default input-group-addon'><i class='glyphicon glyphicon-search'></i></span></div>&nbsp;&nbsp;"
							+ "</div></div>");
		} else {
			$container
					.append("<div id='loginArea'><div class='pull-right dropdown' style='vertical-align:middle;margin-top:5px;'><a class='dropdown-toggle' data-toggle='dropdown' ><img src='../home/image/team/"+value+".png' width='28px' height='30px' data-toggle='tooltip' title='"+value+"' data-trigger='hover' data-placement='bottom'/><b class='caret' style='border-top:4px solid #428bca;'></b></a>"
							+"<ul class='dropdown-menu' role='menu' aria-labelledby='dropdownMenu1'>"
						    +"<li role='presentation'><a role='menuitem' tabindex='-1' href='#' onclick='onMenubarItemClick(\"profile\" , \"../profile/profile.html\");'>我的账号</a></li>"
						    +"<li role='presentation'><a role='menuitem' tabindex='-1' href='#' onclick='onMenubarItemClick(\"organization\" , \"../profile/organization.html\");'>我的关系</a></li>"
						    +"<li role='presentation' class='divider'></li>"
						    +" <li role='presentation'><a role='menuitem' tabindex='-1' href='#' onclick='nj.auth.doLoginOut();'>注销</a></li>"
						    +"</ul>"
							+ "</div>"
							+ "<div class='pull-right col-md-2' style='vertical-align:middle;margin-top:5px;'>"
							+ "<div class='input-group'><input type='text' class='form-control input-sm col-md-10' placeholder='search…'>"
							+ "<span class='btn btn-default input-group-addon' onclick='onMenubarItemClick(\"search\" , \"home/search.html\");'><i class='glyphicon glyphicon-search'></i></span></div></div></div>");
		}
	}
*/
	$navbar.append($container);
	bar.append($navbar);
}
nj.web.loadmenubar = function(title ,  focus){
	nj.headmeta(title);
	nj.bar(napp.menuitems, focus);
	nj.footer();
} 
function getLoginModal(){
	var ls = '<div id="ngbf_login_dlg" class="modal fade" id="login_modal" tabindex="-1" role="dialog" aria-labelledby="login_label" aria-hidden="true">'+
	'<div class="modal-dialog" style="width:400px;padding-top: 50px;">'+
		'<div class="modal-content">'+
			'<div class="modal-header">'+
				'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
				'<h3>用户登录</h3>'+
			'</div>'+
			'<div class="modal-body">'+
					'<form class="form-horizontal"  method="post" role="form">'+
						'<div class="form-group">'+
							'<span for="login_username" class="col-sm-4 control-label">域账号</span>'+
						    '<div class="col-sm-6">'+
						      '<input id="login_username" type="text" placeholder="username" class="form-control"/>'+
						    '</div>'+
						'</div>'+
						'<div class="form-group">'+
							'<span for="login_password" class="col-sm-4 control-label">密&nbsp;&nbsp;&nbsp;码</span>'+
						    '<div class="col-sm-6">'+
						      '<input id="login_password" type="password" placeholder="password" class="form-control"/>'+
						    '</div>'+
	    				'</div>'+
					'</form>'+
			  '<div class="form-group" >'+
                  '<label class="col-sm-4 control-label"></label>'+
                     '<div class="col-sm-6" style="color:red;height:100%;min-height:30px;" id="loginErrorMsg">'+
                     '</div>'+
              '</div>'+
			'</div>'+
			'<div class="modal-footer">'+
				'<a href="http://ngbf/oss/plugins/ngbf-c-account/AccountForgotPassword.xhtml">忘记密码？</a>'+
				'<button id="btn_login" class="btn btn-primary" onclick="nj.auth.doLogin()">登 录</button>'+
			'</div>'+
		'</div>'+
	'</div>'+
'</div>';
	return ls;
}
// 单击
function onMenubarItemClick(id, url) {
	// 清除样式,重新设定样式
	$("#header_bar_ul>li").removeClass("active");
	$("#header_bar_ul>li[id='menubar_item_" + id + "']").addClass("active");
	currentUrl = url ;
	// 页面加载
	nj.web.changeUrl(url);
}
/**
 * 当前iframe加载的url地址
 */
var currentUrl = "../home/index.html" ;
function getCurrentUrl(){
	return  currentUrl ;
}

/**
 * iFrame的url改变
 * 
 * @param items
 */
nj.web.changeUrl = function(url) {
	/*$("#main_frame").attr("src", url);*/
	window.location.href=url;
}
/**
 * 创建Footer,TODO 添加友情链接
 * 
 * @since 1.2.0.0
 */
nj.web.footer = function(items) {
	var footer = $('#ngbf-footer');
	var $row = $("<div class='row' align='center'></div>");
	/*
	 * $row.append("<address>@Sungard/NGBF 2011-2013
	 * 浏览器支持:Chrome|Safari|Firefox|Opera </address>");
	 */
	if (napp.ns.footer != null) {
		$row.append(napp.ns.footer);
	}
	footer.append($row);
}

/**
 * 检查浏览器版本
 * 
 * @since 1.2.0.0
 */
nj.web.browser = function() {
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
			alert("您的浏览器类型为:IE6,不建议使用IE8及以下版本");
		}
		if (navigator.userAgent.indexOf("MSIE 7.0") > 0) {
			alert("您的浏览器类型为:IE7,不建议使用IE8及以下版本");
		}
		if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
			alert("您的浏览器类型为:IE8,不建议使用IE8及以下版本");
		}
		if (navigator.userAgent.indexOf("MSIE 9.0") > 0) {
		}
		if (navigator.userAgent.indexOf("MSIE 10.0") > 0) {
		}
	}
	if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0) {
		/* return "Firefox"; */
		// TODO
	}
	if (isSafari = navigator.userAgent.indexOf("Safari") > 0) {
		/* return "Safari"; */
		// TODO
	}
	if (isCamino = navigator.userAgent.indexOf("Camino") > 0) {
		/* return "Camino"; */
		// TODO
	}
	if (isMozilla = navigator.userAgent.indexOf("Gecko/") > 0) {
		/* return "Gecko"; */
		// TODO
	}
}

// //////////////////////////////////////////////////////////////////////////////
// /////////////////// nj.rpc /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * 定义远程调用对象
 * 
 * @since 1.2.4.0
 */
nj.rpc = new Object;

/**
 * 处理RSS,调用jquery.jfeed.js组件访问RssURL,获取feed对象（feed.title,feed.items等）
 * 
 * @since 1.2.4.0
 * @param handler
 *            处理feed对象的方法
 */
nj.rpc.rss = function(rssUrl, handler) {
	jQuery.getFeed({
		url : rssUrl,
		success : function(feed) {
			handler(feed);
		}
	});
}

/**
 * 调用jQuery的ajax Call,增加一些系统的信息
 * 
 * @since 1.2.4.0
 * @param remoteUrl
 * @param handler
 */
nj.rpc.call = function(remoteUrl, handler) {
	// 增加请求的系统参数,主要放置API被其他应用调用过于频繁
	// TODO 数据加密
	// var apikey = napp.ns.apikey;
	remoteUrl = remoteUrl + "?t=json&k=1234&v=1";
	$.get(remoteUrl, function(data) {
		if (handler != null) {
			// 由Handler方法来处理数据
			handler(data);
		}
	});
}

// //////////////////////////////////////////////////////////////////////////////
// /////////////////// nj.cache /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * 定义nj的存储对象,参见jquery.storage.js http://plugins.jquery.com/localstorage/
 * 
 * @since 1.2.2.0
 */
nj.cache = new Object;

/**
 * 将指定的对象键值对缓存在localStorage中
 * 
 * @since 1.2.2.0
 */
nj.cache.cache = function(key, value, durable) {
	var ts = new Date().getTime();
	var storable = {
		"source" : value,
		"timestamp" : ts,
		"durable" : durable
	};
	$.localStorage(key, storable);
}

/**
 * 从缓存中获取对象，如超出时间间隔则返回null
 * 
 * @since 1.2.2.0
 */
nj.cache.get = function(key) {
	var storable = $.localStorage(key);
	if (storable != null) {
		var ts = new Date().getTime();
		if ((ts - storable.timestamp) > (storable.durable * 1000)) {
			// 超出间隔时间，删除该对象
			$.localStorage(key, null);
			return null;
		} else {
			return storable.source;
		}
	} else {
		return null;
	}
}

// //////////////////////////////////////////////////////////////////////////////
// /////////////////// nj.time /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * 定义时间对象
 * 
 * @since 1.2.2.0
 */
nj.time = new Object;

/**
 * 获得当前的时间戳
 * 
 * @since 1.2.2.0
 */
nj.time.ts = function() {
	return new Date().getTime();
}
/**
 * 将long类型的时间戳变成需要的format（yyyy-MM-dd hh:mm:ss）格式的时间
 * @param tsi
 * @param format
 * @returns
 */
nj.time.date = function(tsi , format){
	if(format == null || format =="undefined"){
		format = "yyyy-MM-dd hh:mm:ss" ;
	}
	var date = new Date(tsi) ;
	var o = {
	        "M+": date.getMonth() + 1,
	        "d+": date.getDate(),
	        "h+": date.getHours(),
	        "m+": date.getMinutes(),
	        "s+": date.getSeconds(),
	        "q+": Math.floor((date.getMonth() + 3) / 3),
	        "S": date.getMilliseconds()
	    } ;
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
	  
	return format;
}
// //////////////////////////////////////////////////////////////////////////////
// /////////////////// nj.auth /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * 定义权限|认证对象
 * 
 * @since 1.2.3.0
 */
nj.auth = new Object;

/**
 * 验证是否已经登录
 * 
 * @since 1.2.3.0
 */
nj.auth.checkSession = function() {
	var value = sessionStorage.getItem("ngbf.session.account");
	if (value == null || value == '') {
		return false;
	}
	return true;
}

/**
 * 获得登录账号
 * 
 * @since 1.2.3.0
 */
nj.auth.getAccount = function() {
	return sessionStorage.getItem("ngbf.session.account");
}

/**
 * 弹出登录对话框,页面必须有<div id="ngbf_login_dlg"></div>
 * 
 * @since 1.2.3.0
 */
nj.auth.loginDialogShow = function() {

	
	/*
	 * dlg.attr("class", "modal fade").attr("role", "dialog"); var $modalDialog =
	 * $("<div class='modal-dialog'>"+ "<div class='modal-content'></div></div>") ;
	 * $modalDialog .append("<div class='modal-header'><h3>用户登录</h3></div><div
	 * class='modal-body'><div class='container-fluid'><div class='span1'><label>用户名</label></div><div
	 * class='span1'><input id='login_username' type='text' /></div></div><div
	 * class='container-fluid'><div class='span1'><label>密码</label></div><div
	 * class='span1'><input id='login_password' type='password' /></div></div></div><div
	 * class='modal-footer'><button id='btn_login' class='btn btn-default'
	 * onclick='nj.auth.doLogin()'>登 录</button></div>");
	 * dlg.append($modalDialog) ;
	 */
	//@deprecated since 2.3.0.0
	/*var dlg = $('#ngbf_login_dlg');
	$("#loginErrorMsg").text("");
	dlg.modal({
		  backdrop:true,
		  keyboard:true,
		  show:true
		});*/
	window.location.href="../home/login.html";
}
nj.context="/oss";
/**
 * 进行登录判定，loginDialogShow的登录按钮实现
 * 
 * @since 1.2.3.0
 */
nj.auth.doLogin = function() {
	var username = $('#login_username').val();
	var password = $('#login_password').val();
var req_url = nj.context + "/rest/login/"+username+"/"+password;
	$.get(req_url, function(msg){
		if(msg.head.rep_code == '500'){
			 $("#loginErrorMsg").text(msg.head.rep_message);
//			window.location = "index.html";
			// 隐藏登录框
//			 $('#ngbf_login_dlg').modal('toggle');
		}
		if(msg.head.rep_code == '200'){
			
			// alert(username + "-" + password);
			// TODO 密码加密
			// TODO 验证登录是否成功
			sessionStorage.setItem("ngbf.session.account", username);
			//发送一条twitter信息
			var content = "登陆应用" ;
			var data = {account:username,twitter:content,type:"login"} ;
			$.post("/oss/rest/twitter/",data, function(msg){});
			// 隐藏登录框
			 $('#ngbf_login_dlg').modal('toggle');
			 $("#loginArea")
				.html("<div class='pull-right dropdown' style='vertical-align:middle;margin-top:5px;'><a class='dropdown-toggle' data-toggle='dropdown' ><img src='../home/image/team/"+username+".png' width='28px' height='30px' data-toggle='tooltip' title='"+username+"' data-trigger='hover' data-placement='bottom'/><b class='caret' style='border-top:4px solid #428bca;'></b></a>"
						+"<ul class='dropdown-menu' role='menu' aria-labelledby='dropdownMenu1'>"
					    +"<li role='presentation'><a role='menuitem' tabindex='-1' href='#' onclick='onMenubarItemClick(\"profile\" , \"../profile/profile.html\");'>我的账号</a></li>"
					    +"<li role='presentation'><a role='menuitem' tabindex='-1' href='#' onclick='onMenubarItemClick(\"organization\" , \"../profile/organization.html\");'>我的关系</a></li>"
					    +"<li role='presentation' class='divider'></li>"
					    +" <li role='presentation'><a role='menuitem' tabindex='-1' href='#' onclick='nj.auth.doLoginOut();'>注销</a></li>"
					    +"</ul>"
						+ "</div>"
						+ "<div class='pull-right col-md-2' style='vertical-align:middle;margin-top:5px;'>"
						+ "<div class='input-group'><input type='text' class='form-control input-sm col-md-10' placeholder='search…'>"
						+ "<span class='btn btn-default input-group-addon' onclick='onMenubarItemClick(\"search\" , \"home/search.html\");'><i class='glyphicon glyphicon-search'></i></span></div></div>");
			 nj.web.changeUrl(getCurrentUrl()) ; 
			// 暂时先简单的页面刷新处理
//			window.location = "index.html";
			// 修改右上角登录人信息
//			 $('#login_info').attr("class","btn disabled").attr('onclick',null).html("登录人:"+username);

		}
		$('#login_username').val('');



		$('#login_password').val('');
		
	});
	// alert(username + "-" + password);
	// TODO 密码加密
	// TODO 验证登录是否成功
	//sessionStorage.setItem("ngbf.session.account", username);

	// 隐藏登录框
	// $('#ngbf_login_dlg').modal('toggle');

	// 暂时先简单的页面刷新处理
	//window.location = "index.html";
	// 修改右上角登录人信息
	// $('#login_info').attr("class","btn
	// disabled").attr('onclick',null).html("登录人:"+username);
}
/**
 * 退出，清除session内容
 */
nj.auth.doLoginOut = function() {
	var username = nj.auth.getAccount() ;
	sessionStorage.removeItem("ngbf.session.account");
	//发送一条twitter信息
	var content = "登出应用" ;
	var data = {account:username,twitter:content,type:"login"} ;
	$.post("/oss/rest/twitter/",data, function(msg){});
	
	$("#loginArea").html("<a class='pull-right' id='login_info' href='#' onclick='nj.auth.loginDialogShow()' style='margin-top:12px;'><font color='white'>登录</font></a>"
			+ "<div class='col-md-2 pull-right' style='vertical-align:middle;margin-top:5px;'><div class='input-group'>"
			+ "<input type='text' class='form-control input-sm col-md-10' placeholder='search…'><span class='btn btn-default input-group-addon'><i class='glyphicon glyphicon-search'></i></span></div>&nbsp;&nbsp;"
			+ "</div>");
	var url = getCurrentUrl();
	if(url == "profile/profile.html" || url == "profile/organization.html"){
		nj.web.changeUrl("home/index.html");
	}else{
		nj.web.changeUrl(getCurrentUrl()) ; 
	}
//	nj.web.changeUrl(getCurrentUrl()) ; 
	//window.location = "index.html";
}

/**
 * 得到session中保存的account信息
 */
nj.auth.getSessionAccount =function (){
	return sessionStorage.getItem("ngbf.session.account");
}
// //////////////////////////////////////////////////////////////////////////////
// /////////////////// nj.color /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * 定义颜色对象
 * 
 * @since 1.2.6.0
 */
nj.color = new Object;

/**
 * 随机获得颜色
 * 
 * @since 1.2.6.0
 */
nj.color.random = function() {
	// 16进制方式表示颜色0-F
	var arrHex = [ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
			"C", "D", "E", "F" ];
	var strHex = "#";
	var index;
	for ( var i = 0; i < 6; i++) {
		// 取得0-15之间的随机整数
		index = Math.round(Math.random() * 15);
		strHex += arrHex[index];
	}
	return strHex;
}

/**
 * 根据序号获得颜色
 * 
 * @since 1.2.6.0
 */
nj.color.index = function(i) {
	// 16进制方式表示颜色0-F
	var color = [ "black", "blue", "brown", "chocolate", "coral", "crimson",
			"cyan", "darkorange", "deeppink", "gray", "green", "gold",
			"hotpink", "lightgreen", "lime", "linen", "magenta", "navy",
			"olive", "orange", "orchid", "peru", "peru", "plum", "red",
			"purple", "salmon", "seashell", "silver", "snow", "tan", "teal",
			"tomato", "wheat", "yellow", "violet", "turquoise" ];
	if (i >= color.length) {
		i = 0;
	}
	return color[i];
}

// //////////////////////////////////////////////////////////////////////////////
// /////////////////// 代码冢 /////////////////////////
// //////////////////////////////////////////////////////////////////////////////

/**
 * @deprecated
 * @since 1.8.0.0
 */
nj.rss = function(rssUrl, handler) {
	nj.rpc.rss(rssUrl, handler);
}

/**
 * @deprecated
 * @since 1.8.0.0
 */
nj.headmeta = function(title) {
	nj.web.headmeta(title);
}

/**
 * @deprecated
 * @since 1.8.0.0
 */
nj.bar = function(items, id) {
	nj.web.menubar(items, id);
}

/**
 * @deprecated
 * @since 1.8.0.0
 */
nj.footer = function(items) {
	nj.web.footer(items)
}

/**
 * @deprecated
 * @since 1.8.0.0
 */
nj.checkBrowser = function() {
	nj.web.browser();
}
/**
 * 定义图形对象
 * 
 * @since 1.9.2.0
 */
nj.chart = new Object;
/**
 * 同时定义两条折现
 * 
 * @param rgraphId
 * @param labels
 * @param data1
 * @param data2
 * @param title
 * @returns
 * @deprecated
 * 
 */
nj.chart.line2 = function(rgraphId, labels, data1, data2, title) {
	var line = new RGraph.Line(rgraphId, data1, data2);
	line.Set('labels', labels);
	line.Set('title', title);
	// 默认给红色，黑色两条线
//	line.Set('colors', [ 'red', 'black' ]);
//	line.Set('chart.key', [ 'key1', 'key2' ]);
	line.Set('chart.key', [ ]);
//	line.Set('chart.key.interactive', true);
	line.Set('chart.key.position', 'graph');
	line = nj.chart.params(line);
	return line;
}
/**
 * 定义一条折现
 * 
 * @param rgraphId
 * @param labels
 * @param data1
 * @param data2
 * @param title
 * @returns
 * 
 */
nj.chart.line = function(rgraphId, labels, data, title) {
	var line = new RGraph.Line(rgraphId, data);
	line.Set('labels', labels);
	line.Set('title', title);
	// 默认一条线为红色
	line = nj.chart.params(line);
	return line;
}
/**
 * drawline
 */
nj.chart.drawLine = function(line,style){
    if(style!=null && style != "undefined"){
    	line.Set('curvy', style.curvy);
        line.Set('curvy.tickmarks', true);
        line.Set('curvy.tickmarks.fill', null);
        line.Set('curvy.tickmarks.stroke', '#aaa');
        line.Set('curvy.tickmarks.stroke.linewidth', 2);
        line.Set('curvy.tickmarks.size', 5);
        line.Set('tickmarks', style.tickmarks);
        line.Set('background.grid', style.grid);
    	line.Set('filled', style.fill);
    	line.Set('filled.accumulative', true);
    	line.Set('fillstyle', style.fillStyle);
        line.Set('colors', style.colors);
        line.Set("linewidth",style.linewidth);
        if(style.key != null && style.key == undefined && style.key.length > 0){
        	line.Set('chart.key', style.key);
        	line.Set('chart.key.interactive', true);
        	line.Set('chart.key.position', 'graph');
        }
		line.Set('gutter.left',style.left) ;
		line.Set('gutter.right',style.right) ;
		line.Set('gutter.bottom',style.bottom) ;
    }
    if(style!=null && style.trace==true){
    	RGraph.Effects.Line.jQuery.Trace(line);
    }else{
    	line.Draw() ;
    }
}
/**
 * 定义柱状图
 * @param rgraphId
 * @param labels
 * @param data
 * @param title
 * @returns
 */
nj.chart.bar = function(rgraphId, labels, data, title) {
	var bar = new RGraph.Bar(rgraphId, data);
	bar.Set('labels', labels);
	bar.Set('title', title);
//	var color = new Array();
//	for ( var i = 0; i < labels.length; i++) {
//		color[i] = nj.color.index(RGraph.random(1, 37));
//	}
	// variant:3d or bar
	bar.Set('variant', '3d');
	bar.Set('labels.above', true)
	bar.Set('bevel', true)
	bar = nj.chart.params(bar);
	return bar;
}
nj.chart.drawBar = function(bar,style){
	 if(style!=null && style != "undefined"){
		 bar.Set('variant', '3d');
		 bar.Set('colors', style.colors);
		 bar.Set('dashed', style.dashed);
		 bar.Set('text.angle', style.textAngle);
		 bar.Set('hmargin', style.hmargin);
		 bar.Set('background.grid.autofit.numvlines', style.numvlines);
		 bar.Set('background.grid.autofit.numhlines', style.numhlines);
		 bar.Set("linewidth",style.linewidth);
		 bar.Set('gutter.left',style.left) ;
		bar.Set('gutter.right',style.right) ;
		bar.Set('gutter.bottom',style.bottom) ;
	 }
	 if(style!=null && style.trace==true){
		 RGraph.Effects.Fade.In(bar, {
				'duration' : 250
			});
	    }else{
	    	bar.Draw() ;
	    }
}
/**
 * 定义饼图
 * @param rgraphId
 * @param labels
 * @param data
 * @param title
 * @returns
 */
nj.chart.pie = function(rgraphId, labels, data, title) {
	var pie = new RGraph.Pie(rgraphId, data)
	pie.Set('radius', 50);
//	for ( var i = 0; i < labels.length; i++) {
//		labels[i] = labels[i] + "(" + data[i] + ")";
//	}
	pie.Set('labels', labels);
	pie.Set('title', title);
//	pie.Set('exploded', 5)
//	pie.Set('strokestyle', 'transparent');
//	pie.Set('tooltips', labels);
//	pie.Set('tooltips.event', 'onmousemove');
	// 可以指定pie的颜色
	// pie.Set('colors',
	// ['#EC0033','#A0D300','#FFCD00','#00B869','#999999','#FF7300','#004CB0'])
	// ;
	pie = nj.chart.params(pie);
	return pie;

}
nj.chart.drawPie = function(pie , style){
	 if(style!=null && style != "undefined"){
		pie.Set('radius' , style.radius) ;
		pie.Set('exploded', style.exploded) ;
		pie.Set('strokestyle', 'transparent');
		pie.Set('tooltips', style.tooltips);
		pie.Set('tooltips.event', 'onmousemove');
		pie.Set('linewidth',style.linewidth) ;
        pie.Set('gutter.left',style.left) ;
		pie.Set('gutter.right',style.right) ;
		pie.Set('gutter.bottom',style.bottom) ;
		pie.Set('colors' , style.colors) ;
	 }
	 if(style!=null && style.trace==true){
		 RGraph.Effects.Pie.RoundRobin(pie, {frames:30});
	 }else{
	    	pie.Draw() ;
	 }
}
/**
 * chart 图表基本参数设置
 */
nj.chart.params = function(obj) {
	obj.Set('title.size', 10);
	//obj.Set('gutter.left', 40);
	//obj.Set('gutter.right', 15);
	//obj.Set('gutter.bottom', 30);
//	obj.Set('linewidth', 3);
//	obj.Set('hmargin', 5);
	obj.Set('text.color', '#333');
	obj.Set('text.font', 'Arial');
	obj.Set('background.grid.autofit', true);
	obj.Set('background.grid.autofit.numvlines', 11);
	obj.Set('shadow', true);
	obj.Set('shadow.color', 'rgba(20,20,20,0.3)');
	obj.Set('shadow.blur', 10);
	obj.Set('shadow.offsetx', 0);
	obj.Set('shadow.offsety', 0);
	obj.Set('background.grid.vlines', false);
	obj.Set('background.grid.border', true);
	obj.Set('noxaxis', true);
	obj.Set('title.size', 10);
	obj.Set('axis.color', '#666');
	obj.Set('text.color', '#666');
	obj.Set('strokestyle', 'transparent');
	obj.Set('spline', true);
	return obj;
}

function LineStyle(){
	nj.chart.line.style = {
			"curvy":true ,
			"tickmarks" : "",
			"grid" : true,
			"trace" : true ,
			"colors" :['red'],
			"fill":false,
			"fillStyle":[ 'rgba(255,0,0,0.3)', 'rgba(0,255,0,0.3)','rgba(0,0,255,0.3)' ],
			"linewidth":3,
			"key":[],
			'left':20,
			'right':15,
			'bottom':30
			};
	return nj.chart.line.style ;
}
function BarStyle(){
	nj.chart.bar.style = {
			"textAngle":0 ,
			"hmargin" : 5,
			"colors" : ['#428BCA', '#DFF0D8', '#5CB85C', '#5BC0DE', '#F0AD4E', '#D9534F', '#FCF8E3', '#F2DEDE'],
			"numvlines": 20,
			"numhlines":5,
			"dashed":false ,
			"trace":true,
			"variant":'3d',
			"linewidth":3,
			'left':20,
			'right':15,
			'bottom':30
	};
	return nj.chart.bar.style ;
}
function BarStyleOneColor(){
	nj.chart.bar.style = {
			"textAngle":0 ,
			"hmargin" : 5,
			"colors" : [ 'Gradient(white:rgba(153, 208, 249,1.0))' ],
			"numvlines": 20,
			"numhlines":5,
			"dashed":false ,
			"trace":true,
			"variant":'3d',
			"linewidth":3,
			'left':20,
			'right':15,
			'bottom':30
	};
	return nj.chart.bar.style ;
}
function PieStyle(){
	nj.chart.pie.style = {
			"tooltips": [],
			"radius":50,
			"trace":true,
			"exploded":5,
			"linewidth":3,
			'left':20,
			'right':15,
			'bottom':30,
			'colors':["Gradient(rgba(0, 153, 86, 1.0))",
			     "Gradient(rgba(119, 119, 119, 1.0))",
			     "Gradient(rgba(255, 181, 3, 1.0))",
			     "Gradient(rgba(213, 65, 45, 1.0))",
			     "Gradient(rgba(15, 101, 234, 1.0))",
			     "Gradient(rgba(255, 153, 102, 1.0))",
			     "Gradient(rgba(72, 68, 152, 1.0))",
			     "Gradient(rgba(204, 102, 153, 1.0))",
			     "Gradient(rgba(204, 204, 153, 1.0))",
			     "Gradient(rgba(102, 102, 153, 1.0))"]
	};
	return nj.chart.pie.style ;
}
