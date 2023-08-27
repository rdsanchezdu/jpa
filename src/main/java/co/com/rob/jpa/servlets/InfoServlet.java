package co.com.rob.jpa.servlets;

import co.com.rob.jpa.db.DbInfo;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/infoServlet")
public class InfoServlet extends HttpServlet {

    @Inject
    DbInfo dbInfo;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("****************************");
        System.out.println("salida en consola");
        PrintWriter printWriter = response.getWriter();
        response.setContentType("text/plain;charset=UTF-8");
        printWriter.print("User: ");
        printWriter.println(dbInfo.getUser());
        printWriter.print("Database Version: ");
        printWriter.println(dbInfo.getVersion());
    }

}