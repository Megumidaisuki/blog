package com.megumi.service.impl;

import com.megumi.common.ResponseResult;
import com.megumi.entity.DTO.AdminInfoDTO;
import com.megumi.entity.DTO.MenuMessageDTO;
import com.megumi.entity.DTO.UserInfoDTO;
import com.megumi.entity.UserLogin;
import com.megumi.service.AdminService;
import com.megumi.utils.BeanCopyUtils;
import com.megumi.utils.RedisCache;
import com.megumi.utils.SecurityUtils;
import com.megumi.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public ResponseResult getInfo() {
        //第一步：拿到用户信息
        Long userId = SecurityUtils.getUserId();
        UserLogin userLogin = (UserLogin) redisCache.getCacheObject("login:" + userId);
        UserInfoDTO userInfoDTO = BeanCopyUtils.copyProperty(userLogin.getUser(), UserInfoDTO.class);
        //第二步：拿到角色信息
        List<String> roleNames = adminMapper.getRoleName(userId);
        //第三步：拿到权限信息
        Set<String> set = new HashSet<>();
        for (String roleName : roleNames) {
            List<String> auth = adminMapper.getAuthByRoleName(roleName);
            set.addAll(auth);
        }
        AdminInfoDTO adminInfoDTO = new AdminInfoDTO(set, roleNames, userInfoDTO);
        return ResponseResult.okResult(adminInfoDTO);
    }

    @Override
    //前端为了实现动态路由的效果，需要后端有接口能返回用户所能访问的菜单数据。
    public ResponseResult getRouters() {
        //拿到用户id
        Long userId = SecurityUtils.getUserId();
        //通过userId拿到对应的菜单信息
            //拿到一级菜单
            List<MenuMessageDTO> mainMenu = adminMapper.getMenuMessageById();
            //对一级菜单扩展
        setRouters(mainMenu);
        return ResponseResult.okResult(mainMenu);
    }

    //迭代产生父子目录(服务于"getRouters"方法)
    public void setRouters(List<MenuMessageDTO> list){
        for (MenuMessageDTO menu : list) {
            //得到子菜单
            Long menuId = menu.getId();
            List<MenuMessageDTO> sonMenu1 = adminMapper.getSonMenu(menuId);
            menu.setChildren(sonMenu1);
            List<MenuMessageDTO> children = menu.getChildren();
            if(children!=null) {
                //迭代
                setRouters(children);
            }
        }
    }

}
