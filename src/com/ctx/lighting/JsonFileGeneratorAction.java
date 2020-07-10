package com.ctx.lighting;

import com.ctx.lighting.json.JsonEntity;
import com.ctx.lighting.json.JsonProperty;
import com.ctx.lighting.json.parse.JsonParser;
import com.ctx.lighting.util.FileUtils;
import com.ctx.lighting.util.VelocityUtils;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class JsonFileGeneratorAction extends AnAction {
    Project mProject;


    @Override
    public void actionPerformed(AnActionEvent event) {
        mProject = event.getData(PlatformDataKeys.PROJECT);
        DataContext dataContext = event.getDataContext();

        if ("json".equals(getFileExtension(dataContext))) {
            JsonEditForm jsonEditForm = new JsonEditForm();
            jsonEditForm.setCallBack(name -> generateJava(dataContext,name));
            jsonEditForm.showFrame();

        }
        update(event);
    }

    private void generateJava(DataContext dataContext,String fileName) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);

        ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(mProject).getFileIndex();
        String packageName = projectFileIndex.getPackageNameByDirectory(file.getParent());

        WriteCommandAction.runWriteCommandAction(mProject, () -> {


            try {
                String json = new String(file.contentsToByteArray());
                JsonParser jsonParser = new JsonParser();
                List<JsonEntity> parse = jsonParser.parse(json,fileName);
                for (JsonEntity jsonEntity : parse) {
                    HashMap data = new HashMap();
                    data.put("packageName",packageName);
                    data.put("entity",jsonEntity);

                    final List<JsonProperty> properties = jsonEntity.getProperties();
                    final HashSet<String> imports = new HashSet<>();
                    for (JsonProperty property : properties) {
                        imports.add(property.getPackageName());
                    }
                    data.put("imports",imports);

                    String content = null;

                    final String templateFile = "/template/bean.vm";
                    URL resource = this.getClass().getResource(templateFile);
                    String path = resource.getPath();
                    if (path.contains(".jar!")) {

                        path = path.substring(0,path.lastIndexOf("!/template"));
                        content = VelocityUtils.parseData(data, templateFile,path);
                    }else{
                        content = VelocityUtils.parseData(data, templateFile);
                    }


                    FileUtils.write(mProject,file.getParent(), jsonEntity.getName()+".java",content);
                }
            } catch (IOException var5) {
                var5.printStackTrace();
            }

        });
    }


    @Override
    public void update(AnActionEvent event) {
        //在Action显示之前,根据选中文件扩展名判定是否显示此Action
        String extension = getFileExtension(event.getDataContext());
        event.getPresentation().setEnabledAndVisible("json".equals(extension));

    }


    public static String getFileExtension(DataContext dataContext) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);


        return file == null ? null : file.getExtension();
    }

}
