import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *<p>
 * mysql crud代码自动生成
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/7/22
 */
public class SimpleCrudGeneratorTest {

    /** 代码生成全局配置 */
    interface Config {

        String url = "jdbc:mysql://192.168.3.10:8052/dolphin?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true";

        String username = "root";

        String password = "root";

        String author = "entfrm开发团队-王翔";

        String rootOutputDir = "/Users/wangxiang/IdeaProjects/my/dolphin/dolphin-platform/dolphin-platform-biz/dolphin-common-biz/src/main/java";

        String entityOutputDir = "/Users/wangxiang/IdeaProjects/my/dolphin/dolphin-platform/dolphin-platform-api/dolphin-common-api/src/main/java/com/cloud/dolphin/commonbiz/api/entity";

        String mapperXmlOutputDir = "/Users/wangxiang/IdeaProjects/my/dolphin/dolphin-platform/dolphin-platform-biz/dolphin-common-biz/src/main/resources/mapper";

        String packageParent = "com.cloud.dolphin.commonbiz";

        String packageService = "service";

        String packageEntity = "api.entity";

        String packageServiceImpl = "service.impl";

        String packageMapper = "mapper";

        String packageController = "controller";

        String[] includeTable = { "common_message" };

        String[] filterEntityTablePrefix = { "common_" };

        String[] superEntityColumns = { "createById", "createByName", "createTime", "updateById","updateByName", "updateTime", "remarks", "delFlag" };

        String[] ignoreColumns = { "tenant_id" };

    }

    @Test
    /** 运行代码生成 */
    public void CrudSimpleGenerator() {

        AutoGenerator generator = new AutoGenerator(new DataSourceConfig.Builder(Config.url, Config.username, Config.password).build());
        // 全局配置 参考:https://github.com/baomidou/generator#%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AEglobalconfig
        generator.global(new GlobalConfig.Builder()
                .author(Config.author)
                .enableSwagger()
                .fileOverride()
                .outputDir(Config.rootOutputDir)
                .build()
        );
        // 包路径配置 参考:https://github.com/baomidou/generator#%E5%8C%85%E9%85%8D%E7%BD%AEpackageconfig
        Map<OutputFile, String> pathInfos = new ConcurrentHashMap();
        pathInfos.put(OutputFile.entity, Config.entityOutputDir);
        pathInfos.put(OutputFile.mapperXml, Config.mapperXmlOutputDir);
        generator.packageInfo(new PackageConfig.Builder()
                .parent(Config.packageParent)
                .service(Config.packageService)
                .entity(Config.packageEntity)
                .serviceImpl(Config.packageServiceImpl)
                .mapper(Config.packageMapper)
                .controller(Config.packageController)
                .pathInfo(pathInfos)
                .build()
        );
        // 策略配置 参考:https://github.com/baomidou/generator#%E7%AD%96%E7%95%A5%E9%85%8D%E7%BD%AEstrategyconfig
        generator.strategy(new StrategyConfig.Builder()
                .addInclude(Config.includeTable)
                .addTablePrefix(Config.filterEntityTablePrefix)
                .entityBuilder()
                .enableChainModel()
                .enableLombok()
                .enableRemoveIsPrefix()
                .superClass(CommonEntity.class)
                .addSuperEntityColumns(Config.superEntityColumns)
                .addIgnoreColumns(Config.ignoreColumns)
                .build()
        );
        generator.execute();
    }


}
