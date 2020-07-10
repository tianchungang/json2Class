package com.ctx.lighting.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

public class FileUtils {
    public static void write(Project project,VirtualFile packageDirectory,String fileName,String content) throws IOException {
        VirtualFile child = packageDirectory.findChild(fileName);
        if (child == null) {
            child = packageDirectory.createChildData((Object)null, fileName);
        }

        child.setBinaryContent(content.getBytes("UTF-8"));
        child.refresh(true, true);
    }


}
