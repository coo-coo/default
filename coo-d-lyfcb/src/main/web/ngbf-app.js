/**
 * 供APP端静态参数等设置
 */

$(document).ready(function() {
	var url = location.href;
	var token = sessionStorage.getItem("ngbf.session.token");
	if(!token && url.indexOf("/login.html") < 0){
		// location.href = "../home/login.html";
	}
});

var napp = new Object;

napp.head = {
	"title" : "LYFCB",
	"author" : "COO",
	"version" : "1.0.0.0",
	"keywords" : "LYFCB",
	"description" : "LYFCB"
};

// 定义菜单栏
napp.menuitems = [{
	"id" : "site-mgt",
	"label" : "站点管理",
	"value" : "../home/site.html"
}, {
	"id" : "card-mgt",
	"label" : "卡号管理",
	"value" : "../home/card.html"
}];

// 应用上下文命名空间
napp.ns = {
	"web_footer_text" : "<address>@Sungard/NGBF 2011-2013浏览器支持:Chrome|Safari|Firefox|Opera </address>",
	"web_login_support" : "true",
	"app_apikey" : "1234",
	"app_rpc_default" : "http://ngbf/oss/"
};

