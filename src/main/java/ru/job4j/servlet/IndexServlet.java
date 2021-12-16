package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.Item;
import ru.job4j.store.HbnStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class IndexServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; character=utf-8");
        boolean check = Boolean.parseBoolean(req.getParameter("checkbox"));
        System.out.println(check);
        OutputStream out = resp.getOutputStream();
        String json = "";
        if (check) {
            json = GSON.toJson(HbnStore.instOf().findAllItems());
        } else {
            json = GSON.toJson(HbnStore.instOf().findNotDoneItems());
        }
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String desc = req.getParameter("description");
        boolean isDone = Boolean.parseBoolean(req.getParameter("isDone"));
        HbnStore.instOf().add(new Item(desc, isDone));
    }
}
