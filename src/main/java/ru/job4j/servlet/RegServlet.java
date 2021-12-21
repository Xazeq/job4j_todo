package ru.job4j.servlet;

import ru.job4j.model.User;
import ru.job4j.store.HbnStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = HbnStore.instOf().findUserByUsername(username);
        if (user == null) {
            user = HbnStore.instOf().findUserByEmail(email);
        }
        if (user != null) {
            OutputStream out = resp.getOutputStream();
            out.write("400".getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
        } else {
            user = new User(username, email, password);
            HbnStore.instOf().add(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("index.html");
        }
    }
}
