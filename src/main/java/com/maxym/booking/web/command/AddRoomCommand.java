package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.room.RoomType;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddRoomCommand implements Command {
    private static final long serialVersionUID = -8614487041194751620L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) return Path.REDIRECT_FORBIDDEN_COMMAND;

        int capacity = Integer.parseInt(request.getParameter("capacity"));
        RoomType type = RoomType.valueOf(request.getParameter("type"));
        double price = Double.parseDouble(request.getParameter("price"));

        Room room = Room.builder()
                .capacity(capacity)
                .type(type)
                .price(price)
                .status(RoomStatus.VACANT).build();
//        saveImgToRoom(file, room);

        new RoomDaoImpl().saveRoom(room);

        return Path.REDIRECT_HOME;
    }

//    private void saveImgToRoom(MultipartFile file, Room room) throws IOException {
//        if (file != null && !file.isEmpty()) {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//
//            String uuidFile = UUID.randomUUID().toString();
//            String imgResultName = uuidFile + "." + file.getOriginalFilename();
//
//            file.transferTo(new File(uploadPath + "/" + imgResultName));
//
//            room.setImgName(imgResultName);
//        }
//    }
}
