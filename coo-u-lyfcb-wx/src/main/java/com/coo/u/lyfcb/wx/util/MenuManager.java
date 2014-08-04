package com.coo.u.lyfcb.wx.util;

import com.coo.u.lyfcb.wx.INameSpace;
import com.kingstar.ngbf.s.weixin.model.Button;
import com.kingstar.ngbf.s.weixin.model.CommonClickButton;
import com.kingstar.ngbf.s.weixin.model.CommonViewButton;
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
		
		//TODO 换成客户的
		// 第三方用户唯一凭证
		String appId = "wx3ea45aaf283521c1";
		// 第三方用户唯一凭证密钥
		String appSecret = "2bb2ed33d576bc554f91f7ea0a761b32 ";

		// 调用接口获取access_token
		WeixinToken at = WeixinUtil.getAccessToken(appId, appSecret);
        System.out.println(at.getToken()); 
		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(),at.getToken());
			// 调用接口删除菜单
//			int result = WeixinUtil.deleteMenu(at.getToken());
			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonViewButton btn11 = new CommonViewButton();
		btn11.setName("双色球");
		btn11.setType("view");
		btn11.setUrl("http://www.lyflcp.com.cn/");

		CommonViewButton btn12 = new CommonViewButton();
		btn12.setName("福彩3D");
		btn12.setType("view");
		btn12.setUrl("http://www.lyflcp.com.cn/");

		CommonViewButton btn13 = new CommonViewButton();
		btn13.setName("东方6+1");
		btn13.setType("view");
		btn13.setUrl("http://www.lyflcp.com.cn/");

		CommonViewButton btn14 = new CommonViewButton();
		btn14.setName("河南幸运彩");
		btn14.setType("view");
		btn14.setUrl("http://www.lyflcp.com.cn/");

		CommonViewButton btn15 = new CommonViewButton();
		btn15.setName("购彩大厅");
		btn15.setType("view");
		btn15.setUrl("http://www.lyflcp.com.cn/");
		
		CommonViewButton btn21 = new CommonViewButton();
		btn21.setName("站点地图");
		btn21.setType("view");
		btn21.setUrl("http://www.lyflcp.com.cn/");

		CommonClickButton btn22 = new CommonClickButton();
		btn22.setName("在线申请");
		btn22.setType("click");
		btn22.setKey(INameSpace.MENU_KEY_ZXSQ);

		CommonClickButton btn23 = new CommonClickButton();
		btn23.setName("办卡须知");
		btn23.setType("click");
		btn23.setKey(INameSpace.MENU_KEY_BKXZ);

		CommonClickButton btn24 = new CommonClickButton();
		btn24.setName("在线客服");
		btn24.setType("click");
		btn24.setKey(INameSpace.MENU_KEY_ZXKF);

		CommonViewButton btn31 = new CommonViewButton();
		btn31.setName("乐在骑中");
		btn31.setType("view");
		btn31.setUrl("http://www.lyflcp.com.cn/");

		CommonViewButton btn32 = new CommonViewButton();
		btn32.setName("下载客户端");
		btn32.setType("view");
		btn32.setUrl("http://www.lyflcp.com.cn/");

		/////////////////////////////////////////////
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("买彩票");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("公益自行车");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("公益活动");
		mainBtn3.setSub_button(new Button[] { btn31, btn32  });
		/**
		 * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
