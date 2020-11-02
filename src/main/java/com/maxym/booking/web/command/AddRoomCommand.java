package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.room.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRoomCommand implements Command {
    private static final long serialVersionUID = -8614487041194751620L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        RoomType type = RoomType.valueOf(request.getParameter("type"));
        double price = Double.parseDouble(request.getParameter("price"));

        Room room = Room.builder()
                .capacity(capacity)
                .type(type)
                .price(price)
                .status(RoomStatus.VACANT)
                .imgName(saveImgToRoom(request)).build();
        new RoomDaoImpl().saveRoom(room);

        return Path.REDIRECT_HOME;

    }

    private String saveImgToRoom(HttpServletRequest request) {
        // constructs path of the directory to save uploaded file
        String uploadFilePath = request.getServletContext().getRealPath(File.separator + "img");

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        String fileName = null;
        //Get all the parts from request and write it to the file on server
        try {
            for (Part part : request.getParts()) {
                String contentType = part.getContentType();
                if (contentType != null && contentType.contains("image")) {
                    String contentDisposition = part.getHeader("content-disposition");
                    String tmp = contentDisposition.split("filename=\"")[1];
                    fileName = tmp.substring(0, tmp.length() - 1);
                    String uuidFile = UUID.randomUUID().toString();
                    String imgResultName = uuidFile + "." + fileName;
                    part.write(uploadFilePath + File.separator + imgResultName);

                    return imgResultName;
                }

            }
        } catch (IOException | ServletException e) {
            // TODO: log
            e.printStackTrace();
        }

        return "";
    }
}
