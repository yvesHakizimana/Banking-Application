package com.code.banksystem.bank.controllers.userControllers;
import com.code.banksystem.bank.daos.UserDao;
import com.code.banksystem.bank.impls.UserDaoImpl;
import com.code.banksystem.bank.models.User;
import com.code.banksystem.bank.utils.PasswordHarsher;
import com.code.banksystem.bank.utils.ParameterValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "RegisterUserServlet", urlPatterns = {"/register-user"})
@MultipartConfig
public class RegisterUser extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userDao = new UserDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        int age = Integer.parseInt(request.getParameter("age"));
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String accountType = request.getParameter("accountType");
        Part imagePart = request.getPart("imageFile");
        String filePath = handleImage(imagePart);


        //Validation of credentials
        boolean isValidPassword  = ParameterValidator.isValidPassword(password);
        boolean isEmailValid = ParameterValidator.isValidEmail(email);
        boolean isPhoneNumberValid = ParameterValidator.isValidPhoneNumber(phoneNumber);

        if(isEmailValid && !userDao.checkUserExists(email)){
            //If the email is valid is not taken
            if(isValidPassword && isPhoneNumberValid){
                //Create the user to be saved to the database
                User user = new User();
                user.setAge(age);
                user.setFirst_name(firstName);
                user.setLast_name(lastName);
                user.setEmail(email);
                user.setPassword(PasswordHarsher.hashPassword(password));
                user.setPhoneNumber(Integer.parseInt(phoneNumber));
                user.setAccountType(accountType);
                user.setProfilePictureUrl(filePath);

                boolean result = userDao.insertUser(user);
                if(result){
                    response.sendRedirect("loginUser.jsp");
                    return;
                }
                else
                    response.getWriter().println("An error occurred. Please try again");
            }
            else{
                //Set the error messages for each input field
                if(!isValidPassword){
                    List<String> errorMessages = ParameterValidator.validatePassword(password);
                    request.setAttribute("errorMessages", errorMessages);
                }
                if(!isPhoneNumberValid){
                    request.setAttribute("phoneNumberError", "Invalid phone number format");
                }
                //Forward the request back to registration JSP page
                request.getRequestDispatcher("registerUser.jsp").forward(request, response);
            }
        }
        else {
            List<String> emailErrors = new ArrayList<>();
            //Email is invalid or taken
            if(!isEmailValid)
                emailErrors.add("The email format is Invalid");
            if(userDao.checkUserExists(email))
                emailErrors.add("The email is already taken");
            request.setAttribute("emailErrors", emailErrors);
            request.getRequestDispatcher("registerUser.jsp").forward(request, response);
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
