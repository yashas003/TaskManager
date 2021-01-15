package com.yashas003;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner sc1 = new Scanner(System.in);
            Model model = new Model();
            List<TaskBean> list;
            int ch1 = 0, ch2;
            String catName;
            Scanner sc2;

            while (ch1 != 6) {
                System.out.println("Press 1 to create new category");
                System.out.println("Press 2 to modify task in existing category");
                System.out.println("Press 3 to delete category");
                System.out.println("Press 4 to list category");
                System.out.println("Press 5 to search category");
                System.out.println("Press 6 to exit");

                ch1 = sc1.nextInt();

                switch (ch1) {
                    case 1:
                        sc2 = new Scanner(System.in);
                        System.out.print("Enter the category name: ");
                        catName = sc2.nextLine();

                        while (!Utils.validateName(catName)) {
                            System.out.println("Invalid category name!! Try different category name.. \nEnter the category catName: ");
                            catName = sc2.nextLine();
                        }

                        while (model.checkCatExist(catName)) {
                            System.out.println("Category with the name " + catName + " already exist. Please try different name!!");
                            catName = sc2.nextLine();
                        }

                        System.out.println("<===== A category should have at least one task ====>");

                        ch2 = 0;
                        while (ch2 != 2) {
                            System.out.println("Enter 1 to create new task");
                            System.out.println("Enter 2 to exit");

                            ch2 = sc1.nextInt();

                            switch (ch2) {
                                case 1:
                                    createTask(sc2, model, catName);
                                    break;
                                case 2:
                                    System.out.println("Going back without creating category " + catName);
                                    break;
                            }
                        }
                        break;
                    case 2:
                        sc2 = new Scanner(System.in);
                        System.out.println("Enter category name which you want to modify");
                        catName = sc2.nextLine();
                        list = model.loadCategory(catName);

                        while (list == null) {
                            System.out.println("No category exist with that name please try different name again");
                            catName = sc2.nextLine();
                            list = model.loadCategory(catName);
                        }

                        ch2 = 0;
                        while (ch2 != 5) {
                            System.out.println("Press 1 to create new task in " + catName + " category");
                            System.out.println("Press 2 to list all the tasks in " + catName + " category");
                            System.out.println("Press 3 to update an existing task in " + catName + " category");
                            System.out.println("Press 4 to delete an existing task in " + catName + " category");
                            System.out.println("Press 5 go back");

                            ch2 = sc1.nextInt();

                            switch (ch2) {
                                case 1:
                                    createTask(sc2, model, catName);
                                    break;
                                case 2:
                                    listAllTasks(model, catName);
                                    break;
                                case 3:
                                    updateTask(sc2, model, catName);
                                    break;
                                case 4:
                                    deleteTask(sc2, model, catName);
                                    break;
                                default:
                                    System.out.println("Going back..");
                                    break;
                            }
                        }
                        break;
                    case 3:
                        sc2 = new Scanner(System.in);
                        System.out.println("Enter category name which you want to delete");
                        catName = sc2.nextLine();
                        list = model.loadCategory(catName);

                        while (list == null) {
                            System.out.println("No category exist with that name please try different name again");
                            catName = sc2.nextLine();
                            list = model.loadCategory(catName);
                        }

                        boolean isDeleted = model.deleteCategory(catName);
                        if (isDeleted) System.out.println(catName + " successfully deleted");
                        else System.out.println("Error occurred while " + catName);
                        break;
                    case 4:
                        ArrayList<String> catList = model.getAllCategories();
                        for (String category : catList) System.out.println(category);
                        break;
                    case 5:
                        sc2 = new Scanner(System.in);
                        System.out.println("Enter category name which you want to search");
                        catName = sc2.nextLine();
                        list = model.loadCategory(catName);

                        while (list == null) {
                            System.out.println("No category exist with that name please try different name again");
                            catName = sc2.nextLine();
                            list = model.loadCategory(catName);
                        }

                        listAllTasks(model, catName);
                        break;
                    default:
                        System.out.println("Thank You");
                        break;
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void deleteTask(Scanner sc2, Model model, String catName) {
        System.out.println("Enter the task name which you want to delete");
        String taskName = sc2.nextLine();
        TaskBean bean = model.checkTaskExist(taskName, catName);

        boolean isDeleted = model.deleteTask(bean, catName);
        if (isDeleted) {
            System.out.println("Task " + taskName + " successfully deleted");
        } else {
            System.out.println("Error occurred while deleting " + taskName + " task");
        }
    }

    private static void updateTask(Scanner sc2, Model model, String catName) throws Exception {
        System.out.println("Enter the task name which you want to update");
        Scanner sc3 = new Scanner(System.in);
        String taskName = sc2.nextLine();
        TaskBean bean = model.checkTaskExist(taskName, catName);
        String name, desc, tags, date, result;
        int priority;
        Scanner sc4;

        while (bean == null) {
            System.out.println("Task " + taskName + " doesn't exist in " + catName + " category, try different name again");
            taskName = sc2.nextLine();
            bean = model.checkTaskExist(taskName, catName);
        }

        int ch = 0;
        while (ch != 6) {
            System.out.println("Enter 1 to update name of " + taskName + " task");
            System.out.println("Enter 2 to update description of " + taskName + " task");
            System.out.println("Enter 3 to update tags of " + taskName + " task");
            System.out.println("Enter 4 to update end date of " + taskName + " task");
            System.out.println("Enter 5 to update priority of " + taskName + " task");
            System.out.println("Enter 6 to go back");

            ch = sc3.nextInt();

            switch (ch) {
                case 1:
                    System.out.println("Enter the name for task to be updated");
                    sc4 = new Scanner(System.in);
                    name = sc4.nextLine();
                    result = model.updateTask(new TaskBean(
                            name,
                            bean.getDesc(),
                            bean.getTags(),
                            bean.getDate(),
                            bean.getPriority()), catName, taskName);

                    if (result.equals("SUCCESS")) {
                        System.out.println("Task " + taskName + " successfully updated inside " + catName + " category");
                    } else {
                        System.out.println("Error:" + result);
                    }

                    break;
                case 2:
                    System.out.println("Enter the desc for task to be updated");
                    sc4 = new Scanner(System.in);
                    desc = sc4.nextLine();
                    result = model.updateTask(new TaskBean(
                            bean.getTaskName(),
                            desc,
                            bean.getTags(),
                            bean.getDate(),
                            bean.getPriority()), catName, taskName);

                    if (result.equals("SUCCESS")) {
                        System.out.println("Task " + taskName + " successfully updated inside " + catName + " category");
                    } else {
                        System.out.println("Error:" + result);
                    }
                    break;
                case 3:
                    System.out.println("Enter the tags for task to be updated");
                    sc4 = new Scanner(System.in);
                    tags = sc4.nextLine();
                    result = model.updateTask(new TaskBean(
                            bean.getTaskName(),
                            bean.getDesc(),
                            tags,
                            bean.getDate(),
                            bean.getPriority()), catName, taskName);

                    if (result.equals("SUCCESS")) {
                        System.out.println("Task " + taskName + " successfully updated inside " + catName + " category");
                    } else {
                        System.out.println("Error:" + result);
                    }
                    break;
                case 4:
                    System.out.println("Enter the date for task to be updated");
                    sc4 = new Scanner(System.in);
                    date = sc4.nextLine();
                    result = model.updateTask(new TaskBean(
                            bean.getTaskName(),
                            bean.getDesc(),
                            bean.getTags(),
                            new SimpleDateFormat("dd/MM/yyyy").parse(date),
                            bean.getPriority()), catName, taskName);

                    if (result.equals("SUCCESS")) {
                        System.out.println("Task " + taskName + " successfully updated inside " + catName + " category");
                    } else {
                        System.out.println("Error:" + result);
                    }
                    break;
                case 5:
                    System.out.println("Enter the priority for task to be updated");
                    sc4 = new Scanner(System.in);
                    priority = sc4.nextInt();
                    result = model.updateTask(new TaskBean(
                            bean.getTaskName(),
                            bean.getDesc(),
                            bean.getTags(),
                            bean.getDate(),
                            priority), catName, taskName);

                    if (result.equals("SUCCESS")) {
                        System.out.println("Task " + taskName + " successfully updated inside " + catName + " category");
                    } else {
                        System.out.println("Error:" + result);
                    }
                    break;
                default:
                    System.out.println("Going back..");
                    break;
            }

        }
    }

    private static void listAllTasks(Model model, String catName) {
        List<TaskBean> list = model.loadCategory(catName);
        int tc = 1;

        System.out.println("===========================Tasks under " + catName + " category=======================");
        for (TaskBean bean : list) {
            System.out.println("Task " + tc + ":\n"
                    + "Name=" + bean.getTaskName() + "\n"
                    + "Description=" + bean.getDesc() + "\n"
                    + "Tags=" + bean.getTags() + "\n"
                    + "Date=" + bean.getDate() + "\n"
                    + "Priority=" + bean.getPriority() + "\n");
            tc++;
        }
    }

    private static void createTask(Scanner sc2, Model model, String catName) throws Exception {
        int priority;
        String taskName, desc, tags, planDate;
        System.out.println("Enter task name");
        taskName = sc2.nextLine();
        System.out.println("Enter task description");
        desc = sc2.nextLine();
        System.out.println("Enter task tags with comma separated");
        tags = sc2.nextLine();
        System.out.println("Enter task planned date (dd/mm/yyyy)");
        planDate = sc2.nextLine();
        System.out.println("Enter task priority");
        priority = sc2.nextInt();

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(planDate);
        TaskBean bean = new TaskBean(taskName, desc, tags, date, priority);
        String result = model.addTask(bean, catName);

        if (result.equals("SUCCESS")) {
            System.out.println("Task " + taskName + " successfully created inside " + catName + " category");
        } else {
            System.out.println("Error:" + result);
        }
    }
}
