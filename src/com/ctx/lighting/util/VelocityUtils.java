package com.ctx.lighting.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

public class VelocityUtils {
    private static VelocityEngine velocityEngine;

    public static String parseData(Map params, String templatePath, String jarPath) {
        if (velocityEngine == null) {
            velocityEngine = new VelocityEngine();
            velocityEngine.setProperty("resource.loader", "jar");
            velocityEngine.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
            velocityEngine.setProperty("jar.resource.loader.path", "jar:" + jarPath);
            velocityEngine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.NullLogChute");
            velocityEngine.init();
        }

        Template template = velocityEngine.getTemplate(templatePath);
        VelocityContext velocityContext = new VelocityContext(params);
        StringWriter sw = new StringWriter();
        template.merge(velocityContext, sw);
        return sw.toString();
    }

    public static String parseData(Map params, String templatePath) {
        if (velocityEngine == null) {
            velocityEngine = new VelocityEngine();
            velocityEngine.setProperty("resource.loader", "file");
            velocityEngine.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            velocityEngine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.NullLogChute");
            velocityEngine.setProperty("file.resource.loader.path", VelocityUtils.class.getResource("/").getPath());
            velocityEngine.init();
        }

        Template template = velocityEngine.getTemplate(templatePath);
        VelocityContext velocityContext = new VelocityContext(params);
        StringWriter sw = new StringWriter();
        template.merge(velocityContext, sw);
        return sw.toString();
    }
}
