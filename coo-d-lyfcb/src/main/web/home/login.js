$(document).ready(function() {
	// 检查浏览器版本
	nj.checkBrowser();
});

function doLogin() {
	var username = $('#login_username').val();
	var password = $('#login_password').val();
	
	var url = napp.ns.app_rpc_default + "/admin/login/username/"+username+"/password/"+password;
	
	// TODO 表单验证
	$.get(url, function(msg) {
		if (msg.head.rep_code == '200') {
			// 存储Token到当前Session中
			sessionStorage.setItem("ngbf.session.token", msg.data.token);
			sessionStorage.setItem("ngbf.session.account", msg.data.account);
			// 页面重新刷新
			location.href = "site.html";
		}
		else{
			$('#infoError').html(msg.head.rep_message);
		}
	});
}