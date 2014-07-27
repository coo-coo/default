$(document).ready(function() {
	nj.web.loadmenubar('cop','approved');
});

// var napp = new Object;
// napp.context = "/lyfcb";
// var RPC_URL = napp.context + "/rest";

/**
 * 页面控制器,定义范围
 */
var siteCtrl = function siteCtrl($scope , $http){
	$scope.x = 1001;
	
	//初始化，默认一些值，加载数据等
	loadSites($scope,$http);
}

// 触发angular,很重要
angular.bootstrap(document.documentElement);
 
var RPC_URL = "/lyfcb/rest";

// 加载数据 Angular语法，参见http://handyxuefeng.blog.163.com/blog/static/45452172201342944110818/
function loadSites($scope,$http){
	var url = "/lyfcb/rest/site/all";
	alert(url);
	$http.get(url).success(
		function(msg) {
			//alert(msg.head.api_version);
			$scope.sites = msg.records;
		}
	);
}

