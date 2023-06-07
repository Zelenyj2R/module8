package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.TimeUtils;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

  final private String DEFAULT_TIME_ZONE = "UTC";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html; charset=utf-8");
    resp.getWriter().write(getDateTimeFromUserQuery(req.getParameter("timezone")));
    resp.getWriter().close();
  }

  private String getDateTimeFromUserQuery(String userTimeZone) {
    ZoneId zoneId = TimeUtils.getTimeZone(userTimeZone);
    if (zoneId == null) {
      zoneId = ZoneId.of(DEFAULT_TIME_ZONE);
    }

    Clock clock = Clock.system(zoneId);
    return LocalDateTime.now(clock).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
        " " + zoneId;
  }
}
