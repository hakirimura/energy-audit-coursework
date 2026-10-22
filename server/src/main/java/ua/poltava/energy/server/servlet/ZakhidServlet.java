package ua.poltava.energy.server.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.poltava.energy.server.model.StanZakhodu;
import ua.poltava.energy.server.model.Zakhid;
import ua.poltava.energy.server.service.ZakhidService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/api/zakhid/*")
public class ZakhidServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ZakhidServlet.class.getName());
    private final ZakhidService service = new ZakhidService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            out.print(gson.toJson(service.getAll()));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Помилка отримання списку заходів", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Помилка сервера\"}");
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Zakhid z = gson.fromJson(sb.toString(), Zakhid.class);
            service.create(z);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"status\":\"ok\"}");
        } catch (Exception e) {
            LOG.warning("Помилка створення заходу: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();
            int id = jsonObject.get("id").getAsInt();
            String newState = jsonObject.get("stan").getAsString();

            service.changeState(id, StanZakhodu.valueOf(newState));
            out.print("{\"status\":\"ok\"}");
        } catch (Exception e) {
            LOG.warning("Помилка зміни стану: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
        out.flush();
    }
}