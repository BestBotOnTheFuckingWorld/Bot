package com.hax.bestbot.bot.plugins;


import com.hax.bestbot.bot.entities.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class PluginLoader {

    private static Logger logger = LoggerFactory.getLogger(PluginLoader.class);

    public static List<Plugin> loadPlugins(File pluginDir) throws IOException {
        File[] pluginJars = pluginDir.listFiles(f -> f.getName().toLowerCase().endsWith(".jar"));
        ClassLoader loader = new URLClassLoader(fileArrayToURLArray(pluginJars));
        List<Class<Plugin>> pluginClasses = PluginLoader.extractClassesFromJARs(pluginJars, loader);
        return createPluginObjects(pluginClasses);
    }

    private static List<Plugin> createPluginObjects(List<Class<Plugin>> pluginClasses) {
        List<Plugin> plugins = new ArrayList<>(pluginClasses.size());
        for (Class<Plugin> pluginClass : pluginClasses) {
            try {
                plugins.add(pluginClass.newInstance());
            } catch (IllegalAccessException e) {
                logger.error("IllegalAccessException for plugin class " + pluginClass.getName(), e);
            } catch (InstantiationException e) {
                logger.error("Can not instantiate plugin class " + pluginClass.getName(), e);
            }
        }
        return plugins;
    }

    private static List<Class<Plugin>> extractClassesFromJARs(File[] jars, ClassLoader loader) throws IOException {
        List<Class<Plugin>> pluginClasses = new ArrayList<>();
        for (File jar : jars)
            pluginClasses.addAll(PluginLoader.extractClassesFromJAR(jar, loader));
        return pluginClasses;
    }

    private static List<Class<Plugin>> extractClassesFromJAR(File jar, ClassLoader loader) throws IOException {
        List<Class<Plugin>> pluginClasses = new ArrayList<>();
        JarFile jarFile = new JarFile(jar);
        BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(new JarEntry(jarFile.getJarEntry("plugin.config")))));

        String file = null;
        String in;
        while ((in = reader.readLine()) != null) {
            if (in.isEmpty()||in.startsWith("#")) {
                continue;
            }
            String[] split = in.split(" ");
            if (split[0].equals("Main:")) file = split[1];
        }
        if (file!=null) {
            JarEntry entry = null;
                    try {
                        Class<?> clazz = Class.forName(file, true, new URLClassLoader(new URL[]{jar.toURI().toURL()}));
                        if (PluginLoader.isPluginClass(clazz))
                            pluginClasses.add((Class<Plugin>) clazz);
                    } catch (ClassNotFoundException e) {
                        logger.error("Can not load class " + entry.getName(), e);
                    }
            return pluginClasses;
        } else return null;
    }

    private static boolean isPluginClass(Class<?> clazz) {
        for (Class<?> interfaceClass : clazz.getInterfaces())
            if(interfaceClass.equals(Plugin.class))
                return true;
        return false;
    }

    private static URL[] fileArrayToURLArray(File[] files) throws MalformedURLException {
        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++)
            urls[i] = files[i].toURI().toURL();
        return urls;
    }


}
