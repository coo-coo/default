$(document).ready(function() {
	nj.web.loadmenubar('','apply-mgt');
});

/**
 * 页面控制器,定义范围
 */
var pageCtrl = function pageCtrl($scope , $http){
	
	// 加载已申请未办理的申请信息
	loadApply0($scope,$http);
	
	$scope.showApllyAgree = function(apply){
		showApllyDlg($scope,$http,apply,1);
	}
	
	$scope.showApllyCancel = function(apply){
		showApllyDlg($scope,$http,apply,9);
	}
}

// 触发angular,很重要
angular.bootstrap(document.documentElement);
 

// 加载已申请未办理的申请信息
function loadApply0($scope,$http){
	var url = napp.ns.app_rpc_default + "/apply/all/status/0";
	$http.get(url).success(
		function(msg) {
			$scope.apply0 = msg.records;
			// 遍历重新赋值
			angular.forEach($scope.apply0, function(item) {
				// 新属性 = F(旧属性)
				item.applyTsValue = nj.time.date(item.applyTs);
            });
		}
	);
}

var applyStatusDict = {"1":"发证","9":"取消"};

// 提示申请办卡信息
function showApllyDlg($scope,$http,apply,status){
	$("#applySiteSeq").attr("value",apply.siteSeq);
	$("#applyCardSeq").attr("value",apply.cardSeq);
	$("#applyMemberName").attr("value",apply.memberName);
	$("#applyMemberIdCard").attr("value",apply.memberIdCard);
	// 指定办理状态
	$("#applyOperatorStatus").attr("value",status);
	$("#applyOperatorStatusValue").attr("value",applyStatusDict[status]);
	
	// 指定UUID
	$("#applyUuid").attr("value",apply.uuid);
	// 参见http://v3.bootcss.com/javascript/#modals
	$('#apply_agree_dlg').modal("show");
}

// 操作人,进行申请审核
function doApplyUpdate(){
	var applyUuid = $("#applyUuid").val();
	var applyOperatorStatus = $("#applyOperatorStatus").val();
	// 获取登录Token
	var token = sessionStorage.getItem("ngbf.session.token");
	var url = napp.ns.app_rpc_default + "/apply/update/uuid/"+applyUuid+"/status/" + applyOperatorStatus + "/token/" + token;
	// alert(url);
	$.get(url, function(data) {
		if (data.head.rep_code == '200') {
			// 页面重新刷新
			location.href = location.href;
		}
	});
}

