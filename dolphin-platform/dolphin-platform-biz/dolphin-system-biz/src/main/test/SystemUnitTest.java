import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloud.dolphin.common.core.util.BaseUtil;
import com.cloud.dolphin.system.DolphinSystemApplication;
import com.cloud.dolphin.system.api.entity.Menu;
import com.cloud.dolphin.system.api.entity.RoleMenu;
import com.cloud.dolphin.system.service.MenuService;
import com.cloud.dolphin.system.service.RoleMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *<p>
 * 单元测试
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/1/5
 */
@SpringBootTest(classes = DolphinSystemApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemUnitTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Test
    @Transactional
    @Rollback(false)
    public void build() {
        List<Menu> menuList = menuService.list();
        menuList.forEach(item -> {
            item.setId(BaseUtil.snowflakeId()+"");
            item.setTenantId("02");
            menuService.save(item);
        });
    }

    @Test
    @Transactional
    @Rollback(false)
    public void build1() {
        List<Menu> menuList = menuService.list(new LambdaQueryWrapper<Menu>().eq(Menu::getTenantId, "02"));
        menuList.forEach(item -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(item.getId());
            roleMenu.setRoleId("1526739811165073409");
            roleMenuService.save(roleMenu);
        });
    }


}
