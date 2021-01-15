package com.yashas003;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Model {

    ArrayList<String> getAllCategories() {
        ArrayList<String> list;

        File[] files = new File(".").listFiles((dir, name) -> name.toLowerCase().endsWith(".todo"));

        if (files != null) {
            list = new ArrayList<>();

            for (File f : files) list.add(f.getName());
            return list;
        }
        return null;
    }

    TaskBean checkTaskExist(String taskName, String catName) {
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(catName + ".todo"));
            while ((line = br.readLine()) != null) {
                String fileTaskName = line.split(":")[0];
                if (fileTaskName.equalsIgnoreCase(taskName)) {
                    String[] sa = line.split(":");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    return new TaskBean(sa[0], sa[1], sa[2], sdf.parse(sa[3]), Integer.parseInt(sa[4]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    boolean deleteTask(TaskBean taskBean, String catName) {
        List<TaskBean> taskBeans = loadCategory(catName);
        File file = new File(catName + ".todo");
        boolean isDeleted = false;
        if (file.exists()) isDeleted = file.delete();

        for (TaskBean bean : taskBeans) {
            if (!bean.getTaskName().equalsIgnoreCase(taskBean.getTaskName())) {
                addTask(bean, catName);
            }
        }

        return (new File(catName + ".todo").exists() && isDeleted);
    }

    String updateTask(TaskBean updatedBean, String catName, String oldName) {
        List<TaskBean> taskBeans = loadCategory(catName);
        File file = new File(catName + ".todo");
        if (file.exists()) file.delete();

        for (TaskBean bean : taskBeans) {
            if (!bean.getTaskName().equalsIgnoreCase(oldName)) {
                addTask(bean, catName);
            }
        }
        return addTask(updatedBean, catName);
    }

    List<TaskBean> loadCategory(String catName) {
        File[] files = new File(".").listFiles();
        List<TaskBean> beans = null;
        BufferedReader br = null;
        String line;

        if (files != null) {
            for (File f : files) {
                if (f.getName().toLowerCase().equals(catName.toLowerCase() + ".todo")) {
                    beans = new ArrayList<>();

                    try {
                        br = new BufferedReader(new FileReader(catName + ".todo"));
                        while ((line = br.readLine()) != null) {

                            String[] sa = line.split(":");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            beans.add(new TaskBean(sa[0], sa[1], sa[2], sdf.parse(sa[3]), Integer.parseInt(sa[4])));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return beans;
    }

    String addTask(TaskBean bean, String catName) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(catName + ".todo", true));
            bw.write(bean.getTaskName()
                    + ":" + bean.getDesc()
                    + ":" + bean.getTags()
                    + ":" + new SimpleDateFormat("dd/MM/yyyy").format(bean.getDate())
                    + ":" + bean.getPriority()
                    + ":" + new SimpleDateFormat("dd/MM/yyyy").format(new Date().getTime()));
            bw.newLine();

            return "SUCCESS";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    boolean checkCatExist(String name) {
        return new File(name + ".todo").exists();
    }

    boolean deleteCategory(String catName) {
        return new File(catName + ".todo").delete();
    }
}
