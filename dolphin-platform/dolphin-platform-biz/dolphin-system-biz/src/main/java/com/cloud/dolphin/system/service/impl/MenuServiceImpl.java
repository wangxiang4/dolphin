package com.cloud.dolphin.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.common.core.constant.RegexConstants;
import com.cloud.dolphin.system.api.entity.Menu;
import com.cloud.dolphin.system.api.entity.RoleMenu;
import com.cloud.dolphin.system.api.enums.MenuEnum;
import com.cloud.dolphin.system.api.vo.MenuVo;
import com.cloud.dolphin.system.api.vo.MetaVo;
import com.cloud.dolphin.system.mapper.MenuMapper;
import com.cloud.dolphin.system.service.MenuService;
import com.cloud.dolphin.system.util.MenuRouteUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *<p>
 * 菜单权限表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> selectMenuListByRoleId(String roleId) {
        return baseMapper.selectMenuListByRoleId(roleId);
    }

    @Override
    public List<String> selectPermsByRoleId(String roleId) {
        return baseMapper.selectPermsByRoleId(roleId);
    }

    @Override
    public List<RoleMenu> selectMenusByRoleId(String roleId) {
        return baseMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<MenuVo> buildMenuRoute(List<Menu> menus) {
        return buildMenuRoute(menus, null);
    }

    public List<MenuVo> buildMenuRoute(List<Menu> menus, MenuVo superMenuVo) {
        List<MenuVo> menuVoList = menus.stream().map(menu -> {
            MenuVo menuVo = new MenuVo();
            menuVo.setName(menu.getName());
            menuVo.setComponent(menu.getComponent());

            // 配置路由设置属性
            MetaVo metaVo = new MetaVo();
            metaVo.setHideMenu(MenuEnum.MENU_1.getValue().equals(menu.getHideMenu()));
            metaVo.setIcon(menu.getIcon());
            metaVo.setTitle(menu.getName());
            metaVo.setKeepAlive(menu.getKeepAlive().equals(MenuEnum.MENU_1.getValue()));
            metaVo.setHideChildrenInMenu(MenuEnum.MENU_1.getValue().equals(menu.getHideChildrenMenu()));

            // 配置vue-router的规则根级前面必须加上/,要不然会报错
            if (MenuEnum.MENU_0.getValue().equals(menu.getParentId())) {
                menuVo.setPath(menu.getPath().startsWith("/") ? menu.getPath() : "/".concat(menu.getPath()));
            } else {
                menuVo.setPath(menu.getPath());
            }

            // 菜单目录配置,支持多级子菜单
            if (StrUtil.equalsIgnoreCase(MenuEnum.MENU_M.getValue(), menu.getType())) {
                if (MenuEnum.MENU_0.getValue().equals(menu.getParentId())) {
                    menuVo.setComponent(MenuEnum.COMPONENT_LAYOUT.getValue());
                    String redirect = "/".concat(handleRouteRedirect(menu));
                    menuVo.setRedirect(StrUtil.isNotBlank(redirect) ? redirect : MenuEnum.MENU_ROUTE_DEFAULT_URL.getValue());
                } else {
                    menuVo.setComponent(MenuEnum.COMPONENT_PARENT_LAYOUT.getValue());
                    menuVo.setRedirect(superMenuVo.getRedirect());
                }

            // 外链配置,自动识别填充IFRAME (两种模式,跳转页面)
            } else if (StrUtil.equalsIgnoreCase(MenuEnum.MENU_C.getValue(), menu.getType()) && ReUtil.isMatch(RegexConstants.MATCHER_URL, menu.getPath())) {
                menuVo.setComponent(MenuEnum.COMPONENT_IFRAME.getValue());

            // 外链配置 (两种模式,内嵌页面)
            } else if (StrUtil.equalsIgnoreCase(MenuEnum.MENU_C.getValue(), menu.getType()) && ReUtil.isMatch(RegexConstants.MATCHER_URL, menu.getComponent())) {
                menuVo.setComponent(MenuEnum.COMPONENT_IFRAME.getValue());
                metaVo.setFrameSrc(menu.getComponent());
            }

            menuVo.setMeta(metaVo);
            List<Menu> childrenMenus = menu.getChildren();
            if (childrenMenus != null && childrenMenus.size() > 0 && MenuEnum.MENU_M.getValue().equals(menu.getType())) {
                menuVo.setChildren(buildMenuRoute(childrenMenus, menuVo));
            }
            return menuVo;
        }).collect(Collectors.toList());
        return menuVoList;
    }

    /** 处理顶端分割菜单点击路由重定向 */
    public static String handleRouteRedirect(Menu menu){
        String redirect = menu.getPath();
        List<Menu> children = menu.getChildren();
        // 检查子集,只查询第一个子菜单不包括按钮的路由路径
        if (CollectionUtil.isNotEmpty(children)) {
            Menu childMenu = children.get(0);
            if(!childMenu.getType().equals(MenuEnum.MENU_F.getValue())){
                redirect = MenuRouteUtil.routePathJoin(redirect, handleRouteRedirect(childMenu));
            }
        }
        return redirect;
    }

    @Override
    public List<Menu> buildMenuTree(List<Menu> list, String parentId) {
        List<Menu> menuList = new ArrayList();
        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext();) {
            Menu menu = iterator.next();
            if (StrUtil.equals(menu.getParentId(), parentId)) {
                menuRecursion(list, menu);
                menuList.add(menu);
            }
        }
        return menuList;
    }

    /** 子菜单递归加入父菜单列表 */
    private void menuRecursion(List<Menu> list, Menu menu) {
        List<Menu> childList = getChildList(list, menu);
        menu.setChildren(childList);
        for (Menu menuChild : childList) {
            if (getChildList(list, menuChild).size() > 0) {
                Iterator<Menu> it = childList.iterator();
                while (it.hasNext()) {
                    Menu next = it.next();
                    menuRecursion(list, next);
                }
            }
        }
    }

    /** 查询list菜单集合得到子节点列表 */
    private List<Menu> getChildList(List<Menu> list, Menu menu) {
        List<Menu> menuList = new ArrayList();
        Iterator<Menu> it = list.iterator();
        while (it.hasNext()) {
            Menu next = it.next();
            if (StrUtil.equals(next.getParentId(), menu.getId())) {
                menuList.add(next);
            }
        }
        return menuList;
    }

}
