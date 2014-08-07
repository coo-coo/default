package com.coo.u.lyfcb.wx.util;

import com.coo.u.lyfcb.wx.INameSpace;
import com.kingstar.ngbf.s.weixin.model.Button;
import com.kingstar.ngbf.s.weixin.model.ComplexButton;
import com.kingstar.ngbf.s.weixin.model.Menu;
import com.kingstar.ngbf.s.weixin.model.WeixinToken;
import com.kingstar.ngbf.s.weixin.util.WeixinUtil;

/**
 * 菜单管理器类
 * 
 * @author bingjue.sun
 * @date 2013-07-28
 */
public class MenuManager {

	public static void main(String[] args) {
		// 调用接口获取access_token
		WeixinToken at = WeixinUtil.getAccessToken(INameSpace.APP_ID,
				INameSpace.APP_SECRET);
		System.out.println(at.getToken());
		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());
			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败，错误码：" + result);
			// 调用接口删除菜单
			// int result = WeixinUtil.deleteMenu(at.getToken());
			// 判断菜单删除结果
            // if (0 == result)
            // System.out.println("菜单删除成功！");
            // else
            // System.out.println("菜单删除失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		Button btn11 = Button.view().name("双色球")
				.url("http://www.lyflcp.com.cn/");
		Button btn12 = Button.view().name("福彩3D")
				.url("http://www.lyflcp.com.cn/");
		Button btn13 = Button.view().name("东方6+1")
				.url("http://www.lyflcp.com.cn/");
		Button btn14 = Button.view().name("河南幸运彩")
				.url("http://www.lyflcp.com.cn/");
		Button btn15 = Button.view().name("购彩大厅")
				.url("http://www.lyflcp.com.cn/");

		////////////////////////////////////////////////
		Button btn21 = Button.view().name("站点地图")
				.url("http://www.lyflcp.com.cn/");
		Button btn22 = Button.click().name("在线申请")
				.key(INameSpace.MENU_KEY_ZXSQ);
		Button btn23 = Button.click().name("办卡须知")
				.key(INameSpace.MENU_KEY_BKXZ);
		Button btn24 = Button.click().name("在线客服")
				.key(INameSpace.MENU_KEY_ZXKF);

		// //////////////////////////////////////////////
		Button btn31 = Button.view().name("乐在骑中")
				.url("http://www.lyflcp.com.cn/");
		Button btn32 = Button.view().name("下载客户端")
				.url("http://www.lyflcp.com.cn/");

		ComplexButton mainBtn1 = new ComplexButton("买彩票").buttons(btn11, btn12,
				btn13, btn14, btn15);
		ComplexButton mainBtn2 = new ComplexButton("公益自行车").buttons(btn21,
				btn22, btn23, btn24);
		ComplexButton mainBtn3 = new ComplexButton("公益活动")
				.buttons(btn31, btn32);

		Menu menu = new Menu(mainBtn1, mainBtn2, mainBtn3);
//		String json = menu.toJson();
//		System.out.println(json);
		return menu;
	}
}
