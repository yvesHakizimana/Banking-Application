package com.code.banksystem.bank.controllers.userControllers;

import com.code.banksystem.bank.daos.UserDao;
import com.code.banksystem.bank.impls.UserDaoImpl;
import com.code.banksystem.bank.models.User;
import com.code.banksystem.bank.utils.ParameterValidator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@WebServlet(name = "UserProfileUpdate", value = "/update-profile")
@MultipartConfig
public class UpdateUserProfile  extends HttpServlet {

    private  UserDao userDao;

    public void init(){
        this.userDao = new UserDaoImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user == null){
            response.sendRedirect("loginUser.jsp");
            return;
        }

        String first_name = request.getParameter("firstName");
        String last_name = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        String phoneNumber = request.getParameter("phoneNumber");
        String account_type = request.getParameter("accountType");
        String pictureUrl = handleImage(request.getPart("photo"));

        boolean isPhoneNumberValid = ParameterValidator.isValidPhoneNumber(phoneNumber);
        boolean isAccountTrue = account_type.equals("saving") || account_type.equals("current");
        if(isPhoneNumberValid && isAccountTrue){
         user.setPhoneNumber(Integer.parseInt(phoneNumber));
         user.setAccountType(account_type);
         user.setProfilePictureUrl(pictureUrl);
         user.setFirst_name(first_name);
         user.setLast_name(last_name);

         boolean isSuccessful = userDao.updateUser(user);
         if(isSuccessful)
             response.sendRedirect("userProfile.jsp");
         else
             response.getWriter().println("Request not successfully");

        }
        else {
            if(!isPhoneNumberValid){
                request.setAttribute("phoneError", "Phone Number format is Invalid");
            }
            if(!isAccountTrue)
                request.setAttribute("accountError", "Account can be saving or current");
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        }
    }

    public String handleImage(Part imagePart) throws IOException {
        final String UPLOAD_DIRECTORY = "/CustomerImages"; // Specify the relative directory
        String uploadDirectory = getServletContext().getRealPath(UPLOAD_DIRECTORY); // Get the absolute path using the servlet context
        String originalFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        String filePath = uploadDirectory + File.separator + uniqueFileName;

        try {
            // Create upload directory if it doesn't exist
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (InputStream fileContent = imagePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            // Log the error for debugging purposes
            e.printStackTrace();
            throw e; // Rethrow the exception to indicate failure
        }

        // Construct the relative path from the context path to the uploaded file
        String relativeFilePath = UPLOAD_DIRECTORY + "/" + uniqueFileName;
        return relativeFilePath;
    }
}
