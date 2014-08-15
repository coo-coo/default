$(document).ready(function() {
	nj.web.loadmenubar('', 'site-mgt');
});

// 页面控制器,定义范围
var siteCtrl = function siteCtrl($scope, $http) {
	// $scope.x = 1001; {{x}}
	// 初始化，默认一些值，加载数据等
	loadSites($scope, $http);

	$scope.loadCards = function(seq) {
		loadCards($scope, $http, seq);
	}

	$scope.showCardCreateDlg = function(seq) {
		showCardCreateDlg($scope, $http, seq);
	}

	$scope.showCardDeleteDlg = function(seq) {
		showCardDeleteDlg($scope, $http, seq);
	}

	// 创建site
	$scope.showSiteCreateDlg = function() {
		showSiteCreateDlg($scope, $http);
	}

	// 删除site
	$scope.showSiteDeleteDlg = function(site) {
		showSiteDeleteDlg($scope, $http, site);
	}

	// 修改site
	$scope.showSiteUpdateDlg = function(site) {
		showSiteUpdateDlg($scope, $http, site);
	}
}

// 触发angular,很重要
angular.bootstrap(document.documentElement);

function refresh() {
	window.location.reload();
}

// var RPC_URL = "/lyfcb/rest";

// 加载数据
// Angular语法，参见http://handyxuefeng.blog.163.com/blog/static/45452172201342944110818/
function loadSites($scope, $http) {
	var url = napp.ns.app_rpc_default + "/site/all";
	$http.get(url).success(function(msg) {
		// alert(msg.head.api_version);
		$scope.sites = msg.records;
	});
}

function loadCards($scope, $http, seq) {
	var timestamp=new Date().getTime()
	var url = napp.ns.app_rpc_default + "/card/all/seq/" + seq+"?ts="+timestamp;
	$http.get(url).success(function(msg) {
		if (msg.records.length == 0) {
			// $('#alert_dlg_span').html("没有记录..");
			// $('#alert_dlg').alert();
			// $scope.cards = msg.records;
		}
		$scope.cards = msg.records;
	});
}

// Card操作标志 SAVE|DELETE
var card_operate = "";
// 即将删除的Card对象
var card_item_td_delete = null;

// 显示创建Card对话框
function showCardCreateDlg($scope, $http, seq) {
	$("#cardSiteSeq").attr("value", seq);
	$("#cardSeq").attr("disabled", false);
	// 参见http://v3.bootcss.com/javascript/#modals
	$('#cardDlgTitle').html("创建卡号");
	$('#card_dlg').modal("show");
	card_operate = "SAVE";
}

// 显示删除Card对话框
function showCardDeleteDlg($scope, $http, card) {
	$("#cardSiteSeq").attr("value", card.siteSeq);
	$("#cardSeq").attr("value", card.seq);
	$('#cardDlgTitle').html("删除卡号");
	$("#cardSeq").attr("disabled", true);
	// 参见http://v3.bootcss.com/javascript/#modals
	$('#card_dlg').modal("show");
	card_operate = "DELETE";
	card_item_td_delete = card;
}

function doCardOperate() {
	if (card_operate == "SAVE") {
		doCardSave();
	}
	if (card_operate == "DELETE") {
		doCardDelete();
	}
}

// 卡号删除(未申请的卡号可以删除)
function doCardDelete() {
	var url = napp.ns.app_rpc_default + "/card/delete/uuid/"
			+ card_item_td_delete.uuid;
	// alert(url);
	$.get(url, function(data) {
		if (data.head.rep_code == '200') {
			// 页面重新刷新 uuid不能有中划线,否则div获得不到
			var cardItemId = "card_" + card_item_td_delete.siteSeq + "_"
					+ card_item_td_delete.seq;
			// 删除网页端前端对象
			$("#" + cardItemId).remove();
			// 对话框隐藏
			$('#card_dlg').modal("hide");
		}
	});
}

// 卡号保存
function doCardSave() {
	var seq = $("#cardSeq").val();
	var siteSeq = $("#cardSiteSeq").val();
	var param = {
		"info" : '{"seq":"' + seq + '","siteSeq":"' + siteSeq + '"}'
	};
	// alert(param);
	$.post(napp.ns.app_rpc_default + "/card/create", param, function(msg) {
		if (msg.head.rep_code == '200') {
			// alert("注册成功:" + location.href);
			$("#infoCardSuccess").html("1添加成功,可以继续添加或单击关闭按钮停止添加.");
			// location.href = location.href;
		} else {
			// $("#labelError").html("注册失败");
		}
	}, 'json');
}

function showSiteCreateDlg($scope, $http) {
	$('#site_dlg').modal("show");
}

function showSiteUpdateDlg($scope, $http, site) {
	$("#u_uuid").attr("value", site.uuid);
	$("#u_siteSeq").attr("value", site.seq);
	$("#u_siteName").attr("value", site.name);
	$('#u_siteAddress').attr("value", site.address);
	$("#u_siteTelephone").attr("value", site.telephone);
	$("#u_siteEndTime").attr("value", site.startTime);
	$("#u_siteEndTime").attr("value", site.endTime);
	// 参见http://v3.bootcss.com/javascript/#modals
	$('#site_dlg_update').modal("show");
}

function showSiteDeleteDlg($scope, $http, site) {
	$("#del_u_uuid").attr("value", site.uuid);
	$('#site_dlg_del').modal("show");
}

// 站点保存
function doSiteSave() {
	var seq = $("#siteSeq").val();
	var name = $("#siteName").val();
	var address = $("#siteAddress").val();
	var telephone = $("#siteTelephone").val();
	var startTime = $("#siteStartTime").val();
	var endTime = $("#siteEndTime").val();
	// 拼写成为Json对象 info对应后台的入口参数
	var param = {
		"info" : '{"seq":"' + seq + '","name":"' + name + '","address":"'
				+ address + '","telephone":"' + telephone + '","startTime":"'
				+ startTime + '","endTime":"' + endTime + '"}'
	};
	// alert(param);
	$.post(napp.ns.app_rpc_default + "/site/create", param, function(msg) {
		if (msg.head.rep_code == '200') {
			// alert("注册成功:" + location.href);
			// $("#infoSiteSuccess").html("添加成功,可以继续添加或单击关闭按钮停止添加.");
			// location.href = location.href;
			refresh()
		} else {
			// $("#labelError").html("注册失败");
		}
	}, 'json');
}

function doSiteDelete() {
	var uuid = $("#del_u_uuid").val();
	var url = napp.ns.app_rpc_default + "/site/delete/uuid/" + uuid;
	// alert(url);
	$.get(url, function(data) {
		if (data.head.rep_code == '200') {
			// location.href = location.href;
			refresh()
		}
	});
}

// 站点修改
function doSiteUpdate() {
	var uuid = $("#u_uuid").val();
	var seq = $("#u_siteSeq").val();
	var name = $("#u_siteName").val();
	var address = $("#u_siteAddress").val();
	var telephone = $("#u_siteTelephone").val();
	var startTime = $("#u_siteStartTime").val();
	var endTime = $("#u_siteEndTime").val();
	// 拼写成为Json对象 info对应后台的入口参数
	var param = {
		"info" : '{ "uuid":"' + uuid + '","seq":"' + seq + '","name":"' + name
				+ '","address":"' + address + '","telephone":"' + telephone
				+ '","startTime":"' + startTime + '","endTime":"' + endTime
				+ '"}'
	};
	// alert(param);
	$.post(napp.ns.app_rpc_default + "/site/update", param, function(msg) {
		if (msg.head.rep_code == '200') {
			// alert("注册成功:" + location.href);
			// $("#infoSiteSuccess").html("添加成功,可以继续添加或单击关闭按钮停止添加.");
//			location.href = location.href;
            refresh()
		} else {
			// $("#labelError").html("注册失败");
		}
	}, 'json');
}