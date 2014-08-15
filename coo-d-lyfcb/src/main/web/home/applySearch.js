$(document).ready(function() {
	nj.web.loadmenubar('', 'apply-search');
});

/**
 * 页面控制器,定义范围
 */
var searchCtrl = function pageCtrl($scope, $http) {
	$scope.selected = '';
	// 默认加载所有的信息 ，状态为1234加载所有
	loadApply($scope, $http, 1234);

	loadSites($scope, $http);

	$scope.doSearch = function() {
		var status = jQuery("#selectpicker").val();
		if ($scope.selected == ''||$scope.selected == null) {
			var siteSeq = '1234';
			loadSearchApply($scope, $http, siteSeq, status);
		} else {
			loadSearchApply($scope, $http, $scope.selected.seq, status);
		}
	}
}

// 触发angular,很重要
angular.bootstrap(document.documentElement);

var applyStatusDict = {
	"0" : "已申请，未办理",
	"1" : "已申请，已办理",
	"9" : "申请后放弃"
};

// 加载数据
// Angular语法，参见http://handyxuefeng.blog.163.com/blog/static/45452172201342944110818/
function loadSites($scope, $http) {
	var url = napp.ns.app_rpc_default + "/site/all";
	$http.get(url).success(function(msg) {
		// alert(msg.head.api_version);
		$scope.sites = msg.records;
	});
}

// 根据状态查询申请单的信息
function loadApply($scope, $http, status) {
	var url = napp.ns.app_rpc_default + "/apply/all/status/" + status;
	$http.get(url).success(function(msg) {
		$scope.apply0 = msg.records;
		// 遍历重新赋值
		angular.forEach($scope.apply0, function(item) {
			// 新属性 = F(旧属性)
			item.applyStatus = applyStatusDict[item.status];
		});
	});
}

// 根据站点和状态查询申请单信息
function loadSearchApply($scope, $http, site, status) {
	var timestamp = new Date().getTime()
	var url = napp.ns.app_rpc_default + "/apply/site/" + site + "/status/"
			+ status + "?ts=" + timestamp;
	;
	$http.get(url).success(function(msg) {
		$scope.apply0 = msg.records;
		// 遍历重新赋值
		angular.forEach($scope.apply0, function(item) {

			item.applyStatus = applyStatusDict[item.status];
		});
	});
}
