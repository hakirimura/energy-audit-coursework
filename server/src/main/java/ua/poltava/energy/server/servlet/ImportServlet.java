package ua.poltava.energy.server.servlet;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.poltava.energy.server.model.ZapysSpozhyvannia;
import ua.poltava.energy.server.service.ZapysSpozhyvanniaService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/api/import")
public class ImportServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ImportServlet.class.getName());
    private final ZapysSpozhyvanniaService service = new ZapysSpozhyvanniaService();
    private final Gson gson = new Gson();

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
            ZapysSpozhyvannia[] records = gson.fromJson(sb.toString(), ZapysSpozhyvannia[].class);
            List<ZapysSpozhyvannia> list = Arrays.asList(records);

            int success = 0;
            for (ZapysSpozhyvannia z : list) {
                try {
                    service.create(z);
                    success++;
                } catch (Exception ex) {
                    LOG.warning("Пропущено некоректний запис: " + ex.getMessage());
                }
            }

            LOG.info("Імпорт завершено. Успішно: " + success);
            out.print("{\"imported\":" + success + "}");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Критична помилка імпорту", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
        out.flush();
    }
}